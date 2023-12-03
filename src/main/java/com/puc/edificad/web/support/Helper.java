package com.puc.edificad.web.support;


import com.puc.edificad.commons.utils.DateTimeUtils;
import com.puc.edificad.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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

}
