package com.cdsen.power.core.util;

import com.cdsen.power.core.cons.TimeCons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuSen
 * create on 2019/10/21 15:20
 */
public class DateTimeUtils {

    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new HashMap<>();

    static {
        FORMATTER_CACHE.put(TimeCons.F1, DateTimeFormatter.ofPattern(TimeCons.F1));
        FORMATTER_CACHE.put(TimeCons.F2, DateTimeFormatter.ofPattern(TimeCons.F2));
        FORMATTER_CACHE.put(TimeCons.F3, DateTimeFormatter.ofPattern(TimeCons.F3));
        FORMATTER_CACHE.put(TimeCons.F4, DateTimeFormatter.ofPattern(TimeCons.F4));
    }

    public static LocalDateTime parseLocalDateTime(String rawStr, String format) {
        DateTimeFormatter formatter = FORMATTER_CACHE.get(format);
        return LocalDateTime.parse(rawStr, formatter);
    }

    public static LocalDate parseLocalDate(String rawStr, String format) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_CACHE.get(format);
        return LocalDate.parse(rawStr, dateTimeFormatter);
    }
}
