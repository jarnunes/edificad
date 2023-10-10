package com.puc.edificad.commons.utils;

import com.puc.edificad.commons.exceptions.ValidationException;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validate(boolean expectedCondition, String msgKey, Object... args) {
        if (!expectedCondition)
            throw new ValidationException(msgKey, args);

    }
}
