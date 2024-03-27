package org.example.scrapper;

import org.example.anotations.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScrapperTemplate {

    //TODO Annotation RunTimePolicy https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/annotation/RetentionPolicy.html
    //TODO how to handle exceptions
    //TODO tests
    //TODO check if constructor can cause a problem
    public <T> T scrapeData(String url, Class<T> clazz) {
        Document document = getDocumentFromUrl(url);
        return createAndFillObject(document.body(), clazz);
    }

    private Document getDocumentFromUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private <T> T createAndFillObject(Element currentElement, Class<T> clazz) {
        try {
            T object = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                processAnnotationAndSetField(currentElement, object, field);
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void processAnnotationAndSetField(Element currentElement, Object object, Field field) throws IllegalAccessException {
        AnnotationType annotationType = AnnotationUtils.resolveFieldAnnotation(field);
        switch (annotationType) {
            case CSS -> processValueField(currentElement, object, field, AnnotationType.CSS);
            case XPATH -> processValueField(currentElement, object, field, AnnotationType.XPATH);
            case OBJECT -> processObjectField(currentElement, object, field);
        }
    }


    private void processValueField(Element currentElement, Object object, Field field, AnnotationType type) throws IllegalAccessException {
        field.setAccessible(true);
        String selector = AnnotationUtils.getFieldAnnotationValue(field, type);
        String pattern = AnnotationUtils.resolveDateTimePatternAnnotation(field);
        if (Utils.fieldIsOfTypeList(field)) {
            field.set(object, findAndGetListOfValues(currentElement, Utils.getInnerTypeOfList(field), type, selector, pattern));
        } else {
            field.set(object, findAndGetValue(currentElement, field.getType(), type, selector, pattern));
        }
    }

    private List<Object> findAndGetListOfValues(Element currentElement, Class<?> clazz, AnnotationType type, String selector, String pattern) {
        List<Object> result = new ArrayList<>();
        Elements elements = new Elements();

        switch (type) {
            case CSS -> elements = currentElement.select(selector);
            case XPATH -> elements = currentElement.selectXpath(selector);
        }

        for (Element element : elements) {
            result.add(ValueConverter.convertValue(element.text(), clazz, pattern));
        }
        return result;
    }

    private Object findAndGetValue(Element currentElement, Class<?> clazz, AnnotationType type, String selector, String pattern) {
        Element selectedElement = selectElement(currentElement, type, selector);
        if (selectedElement != null) {
            return ValueConverter.convertValue(selectedElement.text(), clazz, pattern);
        }
        return null;
    }

    private Element selectElement(Element currentElement, AnnotationType type, String selector) {
        return switch (type) {
            case CSS -> selectElementWithCss(currentElement, selector);
            case XPATH -> selectElementWithXPath(currentElement, selector);
            default -> null;
        };
    }

    private Element selectElementWithCss(Element currentElement, String cssSelector) {
        return currentElement.selectFirst(cssSelector);
    }

    private Element selectElementWithXPath(Element currentElement, String xPathSelector) {
        return currentElement.selectXpath(xPathSelector).first(); //TODO possible NPE
    }

    private void processObjectField(Element currentElement, Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        if (Utils.fieldIsOfTypeList(field)) {
            field.set(object, findAndGetListOfObjects(currentElement, Utils.getInnerTypeOfList(field)));
        } else {
            field.set(object, createAndFillObject(currentElement, field.getType()));
        }
    }

    private <T> List<T> findAndGetListOfObjects(Element currentElement, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        Elements elements = new Elements();

        if (clazz.isAnnotationPresent(CssSelect.class)) { //TODO must be always present
            CssSelect annotation = clazz.getAnnotation(CssSelect.class);
            elements = currentElement.select(annotation.value());

        } else if (clazz.isAnnotationPresent(XPathSelect.class)) {
            XPathSelect annotation = clazz.getAnnotation(XPathSelect.class);
            elements = currentElement.selectXpath(annotation.value());
        }

        for (Element element : elements) {
            result.add(createAndFillObject(element, clazz));
        }
        return result; //TODO remove
    }
}
