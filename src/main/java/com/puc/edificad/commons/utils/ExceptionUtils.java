package com.puc.edificad.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NestedRuntimeException;

import java.util.Optional;

public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static String getRootCause(Exception e) {
        if (e instanceof NestedRuntimeException exec)
            return getRootCause(exec);

        return e.getMessage();
    }

    public static String getRootCause(NestedRuntimeException e) {
        return Optional.of(e).map(NestedRuntimeException::getRootCause).map(String::valueOf)
                .map(rc -> StringUtils.substringAfter(rc, "Detalhe: ")).orElse(StringUtils.EMPTY);
    }

}
