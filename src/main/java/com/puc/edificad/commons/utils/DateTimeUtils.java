package com.puc.edificad.commons.utils;

import lombok.NonNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class DateTimeUtils {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private DateTimeUtils() {
    }

    public static Instant instantOf(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

    public static String formatter(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;

        ZonedDateTime saoPauloDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        return saoPauloDateTime.format(formatter);
    }

    public static Year toYear(Integer year) {
        return year == null ? null : Year.of(year);
    }

    public static Month toMonth(Integer month) {
        return month == null ? null : Month.of(month);
    }

    public static String toDate(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map(LocalDateTime::toLocalDate).map(LocalDate::toString).orElse(null);
    }

    public static LocalDateTime beginOfFirstDay(@NonNull Year year, @NonNull Month month) {
        return DateTimeUtils.of(year, month, LocalTime.MIN, it -> it.atDay(1));
    }

    public static LocalDateTime endOfLastDay(@NonNull Year year, @NonNull Month month) {
        return DateTimeUtils.of(year, month, LocalTime.MAX, YearMonth::atEndOfMonth);
    }

    public static LocalDateTime of(@NonNull Year year, @NonNull Month month, @NonNull LocalTime time,
                                   Function<YearMonth, LocalDate> dateFunction) {
        YearMonth yearMonth = YearMonth.of(year.getValue(), month);
        LocalDate localDate = dateFunction.apply(yearMonth);
        return LocalDateTime.of(localDate, time);
    }

    public static String getShortName(@NonNull Month month) {
        String shortName = month.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        return shortName.toUpperCase().replace(".", "");
    }
}
