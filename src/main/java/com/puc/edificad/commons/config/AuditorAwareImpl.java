package com.puc.edificad.commons.config;

import com.puc.edificad.model.edsuser.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal).map(it -> (User)it).map(User::getUsername);
    }
}
