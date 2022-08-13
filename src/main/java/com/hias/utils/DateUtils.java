package com.hias.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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

}
