package org.example.anotations;

import java.lang.reflect.Field;

public class AnnotationUtils {
    /**
     * Check if field contains defined annotations.
     *
     * @param field - field which we want to check
     * @return return type of annotation this field contains, if it is one of the defined annotations or null otherwise
     */
    public static AnnotationType resolveFieldAnnotation(Field field) {
        if (field.isAnnotationPresent(CssSelect.class)) {
            return AnnotationType.CSS;
        } else if (field.isAnnotationPresent(XPathSelect.class)) {
            return AnnotationType.XPATH;
        } else if (field.isAnnotationPresent(ObjectSelect.class)) {
            return AnnotationType.OBJECT;
        } else {
            return null;
        }
    }

    /**
     * Get selector expression from annotation.
     *
     * @param field - field which we want to get expression from
     * @param type  - type of annotation that is defined on this field
     * @return css or XPath expression if they are defined on this field, or null otherwise
     */
    public static String getFieldAnnotationValue(Field field, AnnotationType type) {
        return switch (type) {
            case CSS -> field.getAnnotation(CssSelect.class).value();
            case XPATH -> field.getAnnotation(XPathSelect.class).value();
            default -> null;
        };
    }

    /**
     * Process pattern annotations if it is defined on this field.
     *
     * @param field - field which we want to process
     * @return patter expression that this annotation contains or null otherwise
     */
    public static String resolveDateTimePatternAnnotation(Field field) {
        if (field.isAnnotationPresent(DateTimePattern.class)) {
            return field.getAnnotation(DateTimePattern.class).value();
        } else {
            return null;
        }
    }
}
