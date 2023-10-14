package com.puc.edificad.commons.utils;

import com.puc.edificad.commons.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validate(boolean expectedCondition, String msgKey, Object... args) {
        if (!expectedCondition)
            throw new ValidationException(msgKey, args);
    }

    public static void validateNotBlank(Object value, String msgKey, Object... args) {
        validate(StringUtils.isNotBlank(String.valueOf(value)), msgKey, args);
    }

    public static void validateNonNull(Object value, String msgKey, Object... args) {
        validate(Objects.nonNull(value), msgKey, args);
    }

    public static void validateNull(Object value, String msgKey, Object... args) {
        validate(Objects.isNull(value), msgKey, args);
    }
}