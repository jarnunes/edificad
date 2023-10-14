package com.puc.edificad.commons.config;

import com.puc.edificad.model.edsuser.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(principal).map(User::getUsername);
    }
}
