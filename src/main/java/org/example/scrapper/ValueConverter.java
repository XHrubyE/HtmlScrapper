package org.example.scrapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ValueConverter {
    public static Object convertValue(String scrapedValue, Class<?> clazz, String pattern) {
        if (clazz == String.class) {
            return scrapedValue;

        } else if (clazz == Integer.class) {
            return Integer.valueOf(scrapedValue); //TODO try

        } else if (clazz == Double.class) {
            return Double.valueOf(scrapedValue.replaceAll("\\s+","").replaceAll(",", ".")); //TODO try

        } else if (clazz == LocalDate.class) {
            return pattern == null ? LocalDate.parse(scrapedValue) : LocalDate.parse(scrapedValue, DateTimeFormatter.ofPattern(pattern));

        } else if (clazz == LocalTime.class) {
            return pattern == null ? LocalTime.parse(scrapedValue) : LocalTime.parse(scrapedValue, DateTimeFormatter.ofPattern(pattern));

        } else if (clazz == LocalDateTime.class) {
            return pattern == null ? LocalDateTime.parse(scrapedValue) : LocalDateTime.parse(scrapedValue, DateTimeFormatter.ofPattern(pattern));

        } else {
            return null;
        }
    }
}
