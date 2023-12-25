package com.puc.edificad.commons.utils;

import com.puc.edificad.commons.exceptions.UserAuthenticationException;
import com.puc.edificad.commons.exceptions.ValidationException;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

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

    public static void validateNonNull(Supplier<Object> value, String msgKey, Object... args) {
        validate(Objects.nonNull(value.get()), msgKey, args);
    }

    public static void validateNull(Object value, String msgKey, Object... args) {
        validate(Objects.isNull(value), msgKey, args);
    }

    public static void matches(String rawPassword, String encodedPassword) {
        final boolean match =
                PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(rawPassword, encodedPassword);
        if (!match) {
            throw new UserAuthenticationException("eds.err.invalid.username.password");
        }
    }

    public static void authValidate(boolean expectedCondition, String msgKey, Object... args) {
        if (!expectedCondition) {
            throw new UserAuthenticationException(msgKey, args);
        }
    }

    public static void validateDateTimeAfterNow(LocalDateTime localDateTime) {
        validate(localDateTime == null || localDateTime.isBefore(LocalDateTime.now()),
                "datetime.after.now", DateTimeUtils.formatter(localDateTime));
    }

    public static void validateDateTimeAfterNow(LocalDate localDateTime) {
        validate(localDateTime == null || localDateTime.isBefore(LocalDate.now()),
                "datetime.after.now", DateTimeUtils.formatter(localDateTime));
    }

    public static void validateIsAfterNow(LocalDateTime localDate, String msgKey, Object... args) {
        validate(localDate == null || localDate.isAfter(LocalDateTime.now()), msgKey, args);
    }

    public static <T> void validateEquals(T valueA, T valueB, String msgKey, Object... args){
        validate(!ObjectUtils.notEqual(valueA, valueB), msgKey, args);
    }
}
