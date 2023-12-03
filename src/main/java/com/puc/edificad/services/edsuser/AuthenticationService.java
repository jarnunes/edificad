package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.config.Message;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.edsuser.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static com.puc.edificad.commons.utils.UserUtils.matches;

@Component
public class AuthenticationService implements AuthenticationProvider {

    private Message msg;
    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    public void setMsg(Message msgIn){
        this.msg = msgIn;
    }

    @Autowired
    public void setTokenService(TokenService tokenServiceIn){
        this.tokenService = tokenServiceIn;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepositoryIn){
        this.userRepository = userRepositoryIn;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        validateUsernamePassword(authentication);

        User user = findByUsername(authentication.getName());
        validateUserAccount(user);
        return getAuthenticationToken(user, authentication);
    }

    private void validateUsernamePassword(Authentication authentication){
        ValidationUtils.validateNotBlank(authentication.getName(), "eds.err.required.username");
        ValidationUtils.validateNotBlank(authentication.getCredentials(), "eds.err.required.password");
    }

    private void validateUserAccount(User user){
        ValidationUtils.validate(user.isAccountNonExpired(), "eds.err.user.expired");
        ValidationUtils.validate(user.isEnabled(), "eds.err.user.disabled");
        ValidationUtils.validate(user.isAccountNonLocked(), "eds.err.user.locked");
    }

    private User findByUsername(final String userName) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> usernameNotFound =
            () -> new UsernameNotFoundException(msg.get("eds.err.user.not.found", userName));

        return userRepository.findUserByUsername(userName).orElseThrow(usernameNotFound);
    }

    private Authentication getAuthenticationToken(User user, final Authentication authentication){
        ValidationUtils.matches(String.valueOf(authentication.getCredentials()), user.getPassword());
        return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Authentication authenticate(String token) {
        ValidationUtils.validateNotBlank(token, "eds.err.null.token");

        final String clearToken = token.replace("Bearer ", StringUtils.EMPTY);
        final String subject = tokenService.getSubject(clearToken);

        final User user = findByUsername(subject);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
