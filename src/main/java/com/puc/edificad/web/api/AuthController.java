package com.puc.edificad.web.api;


import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.AuthenticationService;
import com.puc.edificad.services.edsuser.TokenService;
import com.puc.edificad.services.edsuser.dto.AccessToken;
import com.puc.edificad.services.edsuser.dto.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AccessToken login(@RequestBody Login login){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.username(), login.password());

        Authentication authentication = this.authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        return tokenService.buildAccessToken(user);
    }
}
