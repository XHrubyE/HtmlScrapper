package org.example.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //TODO check retention
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CssSelect {
    String value();
}
