package com.puc.edificad.commons.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO: implements here to gets current user from spring
//         SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        return Optional.of("admin");
    }
}
