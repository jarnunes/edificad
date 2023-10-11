package com.puc.edificad.commons.utils;

import com.puc.edificad.commons.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validate(boolean expectedCondition, String msgKey, Object... args) {
        if (!expectedCondition)
            throw new ValidationException(msgKey, args);

    }

    public static void validateNotBlank(String value, String msgKey, Object... args) {
        validate(StringUtils.isNotBlank(value), msgKey, args);
    }
}
