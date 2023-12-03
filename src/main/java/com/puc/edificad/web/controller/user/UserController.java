package com.puc.edificad.web.controller.user;

import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.UserService;
import com.puc.edificad.services.edsuser.dto.UserDto;
import com.puc.edificad.web.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/eds-user")
public class UserController extends CrudController<User> {

    private UserService service;

    @Autowired
    public void setService(UserService serviceIn){
        this.service = serviceIn;
    }

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
    String create(Model model) {
        model.addAttribute("entity", new UserDto());
        return "eds-user/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "saveAndNew", defaultValue = "false") boolean saveAndNew,
        UserDto entity, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) return "eds-user/create";
        final Long entityId = entity.getId();

        UserDto dto = service.save(entity);
        if(entityId == null){
            addSuccess(attributes, message.get("eds.success.create", dto.getPassword()));
        }else{
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
    String create(@RequestParam("code") Long code,  RedirectAttributes attributes) {
        try{
            String senha = service.resetPassword(code);
            addSuccess(attributes, message.get("eds.success.update.password", senha));
            return redirect("/eds-user/update", code);
        }catch (Exception e){
            addError(attributes, e.getMessage());
            return redirect("/eds-user/update", code);
        }
    }

    @ModelAttribute("roleList")
    List<Role> cestaList() {
        return Arrays.stream(Role.values()).toList();
    }
}
