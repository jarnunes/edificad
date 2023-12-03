package com.puc.edificad.commons.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("admin");
//        return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
//                .map(Authentication::getPrincipal).map(it -> (User)it).map(User::getUsername);
    }
}
