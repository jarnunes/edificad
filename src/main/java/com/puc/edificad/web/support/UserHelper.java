package com.puc.edificad.web.support;

import com.puc.edificad.model.edsuser.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserHelper {

    public User currentUser() {
        return getCurrentUser().orElseThrow(RuntimeException::new);
    }

    public String currentUserName() {
        return getCurrentUser().map(User::getUsername).orElse("NULL");
    }

    public String currentUserReduceFullName() {
        return getCurrentUser().map(User::getFullName).map(this::reduceFullName).orElse("NUL");
    }

    private String reduceFullName(String fullName) {
        String[] splitName = fullName.split("\\s+");
        List<String> values = Arrays.stream(splitName).filter(StringUtils::isNotEmpty).map(it -> it.charAt(0)).map(String::valueOf).map(String::toUpperCase).toList();

        return values.size() > 1 ? String.join("", values.subList(0, 2)) : values.get(0);
    }

    private Optional<User> getCurrentUser() {
        return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication).map(Authentication::getPrincipal).map(it -> (User) it);
    }

}
