package com.hias.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {

    private static final Locale localeVN = new Locale("vi", "VN");

    public static String formatDate(LocalDate localDate, String pattern) {
        if (localDate == null) {
            return StringUtils.EMPTY;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, localeVN);
        String formatDate = localDate.format(dtf);
        return formatDate;
    }

    public static String currentDateTimeAsString(String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, localeVN);
        String formatDate = LocalDateTime.now().format(dtf);
        return formatDate;
    }

    public static boolean isDateBetween(LocalDate startDate, LocalDate effectiveDate, LocalDate endDate) {
        if (startDate == null || effectiveDate == null) {
            return false;
        }
        return (startDate.isEqual(effectiveDate) || startDate.isBefore(effectiveDate)) && endDate != null && (endDate.isEqual(effectiveDate) || endDate.isAfter(effectiveDate));
    }

}
