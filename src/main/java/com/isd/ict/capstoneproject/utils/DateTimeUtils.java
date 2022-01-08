package com.isd.ict.capstoneproject.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * The {@link DateTimeUtils dateTimeUtils} class provides functionalities for
 * date time
 */
public class DateTimeUtils {

    /**
     * Parse string to date
     * @param stringLocalDateTime
     * @return {@link LocalDateTime localDateTime}
     */
    public static LocalDateTime parseLocalDateTime(String stringLocalDateTime) {
        return stringLocalDateTime == null || stringLocalDateTime.equals("null") ? null : LocalDateTime.parse(stringLocalDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
