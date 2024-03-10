package org.example.scrapper;

import org.example.anotation.CssSelector;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScrapperTemplate {

    private Document document;

    //TODO move all commonly used method parameter to class attributes for better access
    //TODO how to handle exceptions
    public <T> @NotNull T scrapeData(@NotNull String url, Class<T> clazz) {
        cleanBefore();
        this.document = getDocumentFromUrl(url);
        return createAndFillObject(clazz);
    }

    private void cleanBefore() {
        document = null;
    }

    private @NotNull Document getDocumentFromUrl(@NotNull String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private <T> @NotNull T  createAndFillObject(Class<T> clazz) {
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
                // check if String, Class, List etc..
                if(List.class.isAssignableFrom(field.getType())) {
                    scrapeTypeList(object, field);
                } else if(field.getType() == Object.class) {
                    //TODO
                } else {
                    Element selectedElement = document.selectFirst(field.getAnnotation(CssSelector.class).key());
                    if (selectedElement != null) {
                        //TODO add for other types (Int,... etc.)
                        if (field.getType() == String.class) {
                            field.set(object, selectedElement.text());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void scrapeTypeList(Object object, Field field) throws IllegalAccessException {
        List<String> list = new ArrayList<>();
        Elements selectedElements = document.select(field.getAnnotation(CssSelector.class).key());
        for (Element element : selectedElements) {
            list.add(element.text());
        }
        field.set(object, list);
    }

    private void scrapeTypeValue(Object object, Field field) {


    }

    private void scrapeTypeObject(Object object, Field field) {

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
