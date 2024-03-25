package org.example.anotation;

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
}
