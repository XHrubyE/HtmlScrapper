package fit.vutbr.HtmlScrapper.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ScrapperUtils {
    /**
     * Checks if this field is list type.
     * @param field - field which we want to check
     * @return true if field is list type, false otherwise
     */
    public static boolean fieldIsOfTypeList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    /**
     * Get type of inner parameter of list -> for List<String> return String etc.
     * @param listField - list field which we want to get inner parameter type from
     * @return type of inner parameter of the list
     */
    public static Class<?> getInnerTypeOfList(Field listField) {
        ParameterizedType parameterizedType = (ParameterizedType)  listField.getGenericType();
        Type innerType = parameterizedType.getActualTypeArguments()[0];
        return (Class<?>) innerType;
    }

    /**
     * Remove all white spaces from String
     * @param value - value to remove white spaces from
     * @return value without white spaces
     */
    public static String removeWhiteSpaces(String value) {
        return value.replaceAll(" ", "");
    }

    /**
     * Convert any double String into standard format, so it can be converted into Double object
     * @param value - double value
     * @return value in standard format
     */
    public static String convertDoubleToStandardFormat(String value) {
        value = value.replaceAll("[^\\d,.]", "");  // Remove all unnecessary characters, such as white spaces and a thousand separators
        value = value.replaceAll(",", ".");        // Replace comma decimal separator with dot
        return value;
    }

    /**
     *  This function is used to extract attribute value from html element
     * @param html - html of element we want to extract attribute from
     * @param attribute - attribute which we want to extract
     * @return value of attribute in html element
     */
    public static String extractAttributeValue(String html, String attribute) {
        Document doc = Jsoup.parse(html);
        return doc.attr(attribute);
    }
}
