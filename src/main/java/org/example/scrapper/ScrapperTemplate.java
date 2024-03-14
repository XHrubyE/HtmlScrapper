package org.example.scrapper;

import org.example.anotation.CssSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScrapperTemplate {

    private Element documentBody;

    //TODO move all commonly used method parameter to class attributes for better access
    //TODO how to handle exceptions
    //TODO selecting position (not first)
    //TODO how many types to support
    //TODO object recursion
    //TODO tests
    //TODO annotation key parameter
    //TODO check if constructor can cause a problem
    //TODO annotation validity checks
    public <T> T scrapeData(String url, Class<T> clazz) {
        cleanBefore();
        this.documentBody = getDocumentFromUrl(url).body();
        processClassLevelSelector(clazz);
        return createAndFillObject(clazz);
    }

    private void cleanBefore() {
        documentBody = null;
    }

    private Document getDocumentFromUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private <T> T createAndFillObject(Class<T> clazz) {
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
                if (List.class.isAssignableFrom(field.getType())) {
                    scrapeTypeList(object, field);
                } else if (field.getType() == Object.class) {
                    //TODO
                } else {
                    scrapeTypeValue(object, field);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void scrapeTypeList(Object object, Field field) throws IllegalAccessException {
        if (field.getGenericType() == Object.class) {
//            scrapeObjectsOfClass(field.getGenericType().)
        } else {
            List<String> list = new ArrayList<>();
            Elements selectedElements = documentBody.select(field.getAnnotation(CssSelector.class).key());
            for (Element element : selectedElements) {
                list.add(element.text());
            }
            field.set(object, list);
        }
    }

    private <T> List<T> scrapeObjectsOfClass(Class<T> clazz) {

        return Collections.emptyList(); //TODO remove
    }

    private void scrapeTypeValue(Object object, Field field) throws IllegalAccessException {
        Element selectedElement = documentBody.selectFirst(field.getAnnotation(CssSelector.class).key());
        if (selectedElement != null) {
            //TODO add for other types (Int,... etc.)
            if (field.getType() == String.class) {
                field.set(object, selectedElement.text());
            }
        }
    }

    private void scrapeTypeObject(Object object, Field field) {

    }

    private void processClassLevelSelector(Class<?> clazz) {
        if (clazz.isAnnotationPresent(CssSelector.class)) {
            CssSelector annotation = clazz.getAnnotation(CssSelector.class);
            documentBody = documentBody.selectFirst(annotation.key());
        }
    }
}
