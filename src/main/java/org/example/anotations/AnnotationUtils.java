package org.example.anotations;

import java.lang.reflect.Field;

public class AnnotationUtils {
    public static AnnotationType resolveFieldAnnotation(Field field) {
        if (field.isAnnotationPresent(CssSelect.class)) {
            return AnnotationType.CSS;
        } else if (field.isAnnotationPresent(XPathSelect.class)) {
            return AnnotationType.XPATH;
        } else if (field.isAnnotationPresent(ObjectSelect.class)) {
            return AnnotationType.OBJECT;
        } else {
            return AnnotationType.UNKNOWN;
        }
    }

    public static String getFieldAnnotationValue(Field field, AnnotationType type) {
        return switch (type) {
            case CSS -> field.getAnnotation(CssSelect.class).value();
            case XPATH -> field.getAnnotation(XPathSelect.class).value();
            default -> null;
        };
    }

    public static String resolveDateTimePatternAnnotation(Field field) {
        if (field.isAnnotationPresent(DateTimePattern.class)) {
            return field.getAnnotation(DateTimePattern.class).value();
        } else {
            return null;
        }
    }
}
