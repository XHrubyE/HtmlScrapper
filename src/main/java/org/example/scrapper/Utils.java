package org.example.scrapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {
    public static boolean fieldIsOfTypeList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    public static Class<?> getInnerTypeOfList(Field listField) {
        ParameterizedType parameterizedType = (ParameterizedType)  listField.getGenericType();
        Type innerType = parameterizedType.getActualTypeArguments()[0];
        return (Class<?>) innerType;
    }
}
