package fit.vutbr.HtmlScrapper.scrapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ValueConverter {
    /**
     * Converts parsed value from document which is always elementary type String into other elementary type.
     * @param parsedValue - parsed value
     * @param clazz - which type to convert parsed value to
     * @param pattern - additional information about format for Double and DateTime values
     * @return converted value of type clazz
     */
    public static Object convertValue(String parsedValue, Class<?> clazz, String pattern) {
        if (clazz == String.class) {
            return parsedValue;

        } else if (clazz == Integer.class) {
            return Integer.valueOf(Utils.removeWhiteSpaces(parsedValue));

        } else if (clazz == Double.class) {
            return Double.valueOf(Utils.convertDoubleToStandardFormat(parsedValue));

        } else if (clazz == LocalDate.class) {
            return pattern == null ? LocalDate.parse(parsedValue) : LocalDate.parse(parsedValue, DateTimeFormatter.ofPattern(pattern));

        } else if (clazz == LocalTime.class) {
            return pattern == null ? LocalTime.parse(parsedValue) : LocalTime.parse(parsedValue, DateTimeFormatter.ofPattern(pattern));

        } else if (clazz == LocalDateTime.class) {
            return pattern == null ? LocalDateTime.parse(parsedValue) : LocalDateTime.parse(parsedValue, DateTimeFormatter.ofPattern(pattern));

        } else {
            return null;
        }
    }
}
