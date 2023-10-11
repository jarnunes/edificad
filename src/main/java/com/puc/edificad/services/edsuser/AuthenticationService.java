package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.config.Message;
import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.edsuser.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isAnyEmpty;

@Component
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    private Message msg;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        validateUsernamePassword(authentication);
        final User user = findByUsername(authentication.getName());

        if (canAuthenticate(user, authentication))
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());

        throw ValidationException.ofMessageKey("eds.err.authentication");
    }

    private void validateUsernamePassword(Authentication authentication) {
        Predicate<Authentication> invalidCredentials =
                auth -> isAnyEmpty(auth.getName(), String.valueOf(auth.getCredentials()));

        if (invalidCredentials.test(authentication))
            throw new BadCredentialsException(msg.get("eds.err.invalid.username.password"));
    }

    private User findByUsername(final String userName) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> supplierException =
                () -> new UsernameNotFoundException(msg.get("eds.err.user.not.found", userName));

        return userRepository.findUserByUsername(userName).orElseThrow(supplierException);
    }

    private boolean canAuthenticate(User user, final Authentication authentication) {
        final String password = String.valueOf(authentication.getCredentials());

        return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password, user.getPassword());
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
