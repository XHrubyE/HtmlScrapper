package org.example.scrapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {
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
}
