package com.puc.edificad.web.support;


import com.puc.edificad.commons.utils.DateTimeUtils;
import com.puc.edificad.model.BaseEntity;
import com.puc.edificad.model.edsuser.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class Helper {

    private static final String DOLLAR = "$";

    public <T extends BaseEntity> String getOperation(T entity) {
        String operation = Objects.nonNull(entity.getId()) ? "Edição" : "Cadastro";
        return operation + " " + splitClassName(entity);
    }

    private <T extends BaseEntity> String splitClassName(T entity) {
        String entityName = getEntityNameWithoutProxyPackage(entity.getClass().getSimpleName());
        List<String> names = Arrays.asList(entityName.split("(?=[A-Z])"));
        return String.join(" ", names);
    }

    private String getEntityNameWithoutProxyPackage(String className) {
        return StringUtils.contains(className, DOLLAR) ? className.split("\\$")[0] : className;
    }

    public String dateTimeFormat(LocalDateTime dateTime){
        return DateTimeUtils.formatter(dateTime);
    }

    public String dateFormat(LocalDate date){
        return DateTimeUtils.formatter(date);
    }

    public String authenticatedUserReduceFullName(){
        return Optional.of(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal).map(it -> (User)it).map(User::getFullName)
                .map(this::reduceFullName).orElse("NUL");
    }

    private String reduceFullName(String fullName){
        String[] splitName = fullName.split("\\s+");
        List<String> values = Arrays.stream(splitName).filter(StringUtils::isNotEmpty).map(it -> it.charAt(0))
                .map(String::valueOf).map(String::toUpperCase) .toList();

        return values.size() > 1 ? String.join("", values.subList(0, 2)) : values.get(0);
    }
}
