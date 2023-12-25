package com.puc.edificad.web.controller.user;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ServletUtils;
import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.PasswordResetTokenService;
import com.puc.edificad.services.edsuser.UserService;
import com.puc.edificad.services.edsuser.dto.ResetPasswordToken;
import com.puc.edificad.services.edsuser.dto.UserDto;
import com.puc.edificad.services.support.mail.EmailServiceImpl;
import com.puc.edificad.web.controller.CrudController;
import com.puc.edificad.web.support.UserHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/eds-user")
public class UserController extends CrudController<User> {

    private UserService service;

    @Autowired
    public void setService(UserService serviceIn) {
        this.service = serviceIn;
    }

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private PasswordResetTokenService resetTokenService;


    @GetMapping("/login")
    public String getView() {
        return "eds-user/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return redirect("/logout");
    }


    @Override
    protected ModelAndView getModelAndViewListPage() {
        return new ModelAndView("eds-user/list");
    }

    @GetMapping("/create")
    String create(Model model, RedirectAttributes attributes) {
        model.addAttribute("entity", new UserDto());
        return "eds-user/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        UserDto entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "eds-user/create";
        final Long entityId = entity.getId();

        UserDto dto;
        if (entityId == null) {
            dto = service.save(entity);
            addSuccess(attributes, message.get("eds.success.create", dto.getPassword()));
        } else {
            dto = service.update(entity);
            addSuccess(attributes, message.get("eds.success.update"));
        }
        return saveAndNew ? redirect("/eds-user/create") : redirect("/eds-user/update", dto.getId());
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable Long id, ModelMap modelMap) {
        UserDto entity = service.findByIdDto(id).orElse(new UserDto());
        modelMap.addAttribute("entity", entity);
        return "eds-user/create";
    }

    @GetMapping("/update-pwd")
    String resetPassword(@RequestParam("code") Long code, RedirectAttributes attributes,
        HttpServletRequest request) {
        final User user = service.findById(code).orElseThrow(EntityNotFoundException::notFoundForId);
        final String token = UUID.randomUUID().toString();
        resetTokenService.createPasswordResetTokenForUser(user, token);


        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("userToken", token);
        context.setVariable("url", createRestorePasswordUrl(request, token));

        try {
            emailService.sendEmailWithHtmlTemplate(user.getEmail(), "Edificad - Reset Password",
                    "email/template-reset-password", context);

            addSuccess(attributes, message.get("eds.success.send.email.update.pass"));
            return redirect("/eds-user/update", code);
        } catch (Exception e) {
            addError(attributes, e.getMessage());
            return redirect("/eds-user/update", code);
        }
    }

    private String createRestorePasswordUrl(HttpServletRequest request, String token) {
        final String baseUrl = ServletUtils.getBaseUrl(request);
        final String path = "/eds-user/change-password";
        final String query = ServletUtils.toQueryString(Map.of("token", token));
        return baseUrl + path + query;
    }

    @GetMapping("/change-password")
    String changePassword(ModelMap model, @RequestParam(name = "token") String token) {
        resetTokenService.validateResetPasswordToken(token);

        ResetPasswordToken resetPassword = new ResetPasswordToken();
        resetPassword.setToken(token);

        model.addAttribute("resetPassword", resetPassword);
        return "eds-user/update-password";
    }

    @PostMapping("/save-password")
    String savePassword(ResetPasswordToken resetPassword, RedirectAttributes attributes) {
        resetTokenService.validateResetPasswordToken(resetPassword.getToken());
        service.resetPasswordToken(resetPassword);

        addSuccess(attributes, message.get("eds.success.updated.password"));
        return redirect("/eds-user/login");
    }

    @ModelAttribute("roleList")
    List<Role> cestaList() {
        return Arrays.stream(Role.values()).toList();
    }
}
