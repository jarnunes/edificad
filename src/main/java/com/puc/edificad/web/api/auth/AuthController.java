package com.puc.edificad.web.api.auth;


import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.UserUtils;
import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.AuthenticationService;
import com.puc.edificad.services.edsuser.TokenService;
import com.puc.edificad.services.edsuser.UserService;
import com.puc.edificad.services.edsuser.dto.AccessToken;
import com.puc.edificad.services.edsuser.dto.Login;
import com.puc.edificad.web.api.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private UserService userService;
    private AuthenticationService authenticationManager;
    private TokenService tokenService;

    @Autowired
    public  void setUserService(UserService serviceIn){
        this.userService = serviceIn;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationService serviceIn){
        this.authenticationManager = serviceIn;
    }

    @Autowired
    public void setTokenService(TokenService serviceIn){
        this.tokenService = serviceIn;
    }

    @PostMapping("/login")
    AccessToken login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.username(), login.password());

        Authentication authentication = this.authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        return tokenService.buildAccessToken(user);
    }

    @GetMapping
    List<User> list() {
        return userService.findAll();
    }

    @GetMapping("/available-roles")
    List<Role> listRoles(){
        return Arrays.stream(Role.values()).filter(role -> !role.equals(Role.WEBSERVICES)).toList();
    }

    @GetMapping("/{email}")
    User findByEmail(@PathVariable String email){
        return userService.findByEmail(email).orElseThrow(EntityNotFoundException::notFound);
    }

    @PostMapping
    User create(@RequestBody User user) {
        // qualquer usuário criado via API tem a role webservices.
        user.getUserRoles().add(UserUtils.of(Role.WEBSERVICES));
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        user.map(User::getId).ifPresent(userService::deleteById);

        return user.map(us -> msg.get("eds.success.remove.user", us.getUsername()))
                .orElseThrow(EntityNotFoundException::notFoundForId);
    }

    @PostMapping("/reset-password")
    String resetPassword(@RequestBody Login login) {
        userService.resetPassword(login);
        return msg.get("eds.success.updated.password");
    }

}
