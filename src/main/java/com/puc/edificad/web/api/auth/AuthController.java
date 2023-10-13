package com.puc.edificad.web.api.auth;


import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.AuthenticationService;
import com.puc.edificad.services.edsuser.TokenService;
import com.puc.edificad.services.edsuser.UserService;
import com.puc.edificad.services.edsuser.dto.AccessToken;
import com.puc.edificad.services.edsuser.dto.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.puc.edificad.web.response.BaseResponse.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AccessToken login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.username(), login.password());

        Authentication authentication = this.authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        return tokenService.buildAccessToken(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        return of(userService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.save(user);
        return ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        user.map(User::getId).ifPresent(userService::deleteById);
        return of(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        userService.update(user);
        return ok(user);
    }


}
