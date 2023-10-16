package com.puc.edificad.commons.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtils {

    private DateTimeUtils(){}

    public static Instant instantOf(Integer minutes){
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }
}
