package org.example.scrapper;

import org.example.anotation.CssSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ScrapperTemplate {

    private Document document;

    //TODO move all commonly used method parameter to class attributes for better access
    //TODO how to handle exceptions
    public <T> T scrapeData(String url, Class<T> clazz) {
        cleanBefore();
        this.document = getDocumentFromUrl(url);
        return createAndFillObject(clazz);
    }

    private void cleanBefore() {
        document = null;
    }

    private <T> T  createAndFillObject(Class<T> clazz) {
        try {
            T object = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                scrapeAndSetField(object, field);
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void scrapeAndSetField(Object object, Field field) {
        try {
            field.setAccessible(true);
            if (field.isAnnotationPresent(CssSelector.class)) {
                Element selectedElement = document.selectFirst(field.getAnnotation(CssSelector.class).key());
                if (selectedElement != null) {
                    //TODO add for other types (Int,... etc.)
                    if (field.getType() == String.class) {
                        field.set(object, selectedElement.text());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Document getDocumentFromUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getClassLevelSelector(Class<?> clazz) {
        Annotation annotation = clazz.getDeclaredAnnotation(CssSelector.class);
        //TODO throw error on multiple annotations collision
        if (annotation != null) {
            return annotation.toString();
        }
        return "";
    }
}
