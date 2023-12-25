package com.puc.edificad.commons.config;

import com.puc.edificad.model.edsuser.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal).map(this::getUsername);
    }

    private String getUsername(Object object) {
        if (object instanceof User user)
            return user.getUsername();
        else if (object instanceof String username)
            return username;

        return null;
    }
}
