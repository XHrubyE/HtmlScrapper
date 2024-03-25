package org.example.scrapper;

import org.example.anotation.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScrapperTemplate {


    //TODO move all commonly used method parameter to class attributes for better access
    //TODO how to handle exceptions
    //TODO selecting position (not first)
    //TODO how many types to support
    //TODO tests
    //TODO check if constructor can cause a problem
    //TODO annotation validity checks
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
                scrapeAndSetField(currentElement, object, field);
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void scrapeAndSetField(Element currentElement, Object object, Field field) throws IllegalAccessException {
        AnnotationType annotationType = AnnotationUtils.resolveFieldAnnotation(field);
        switch (annotationType) {
            case CSS -> processValueField(currentElement, object, field, AnnotationType.CSS);
            case XPATH -> processValueField(currentElement, object, field, AnnotationType.XPATH);
            case OBJECT -> processObjectField(currentElement, object, field);
        }
    }



    private void processValueField(Element currentElement, Object object, Field field, AnnotationType type) throws IllegalAccessException {
        field.setAccessible(true);
        if (List.class.isAssignableFrom(field.getType())) {
            field.set(object, null);
        } else {
            String selector = getFieldAnnotationValue(field, type);
            field.set(object, scrapeValueOfClass(currentElement, field.getType(), type, selector));
        }
    }

    private String getFieldAnnotationValue(Field field, AnnotationType type) {
        return switch (type) {
            case CSS -> field.getAnnotation(CssSelect.class).value();
            case XPATH -> field.getAnnotation(XPathSelect.class).value();
            default -> "";
        };
    }

    private void processObjectField(Element currentElement, Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType t = (ParameterizedType) field.getGenericType();
            Type innerType = t.getActualTypeArguments()[0];
            field.set(object, scrapeListOfClass(currentElement, (Class<?>) innerType));
        } else {
            field.set(object, createAndFillObject(currentElement, field.getType()));
        }
    }

//    private void scrapeTypeList(Object object, Field field) throws IllegalAccessException {
//        if (field.getGenericType() == Object.class) {
////            scrapeObjectsOfClass(field.getGenericType().)
//        } else {
//            List<String> list = new ArrayList<>();
//            Elements selectedElements = documentBody.select(field.getAnnotation(CssSelector.class).key());
//            for (Element element : selectedElements) {
//                list.add(element.text());
//            }
//            field.set(object, list);
//        }
//    }

    private <T> List<T> scrapeListOfClass(Element currentElement, Class<T> clazz) {
        List<T> scrapedObjects = new ArrayList<>();
        if (clazz.isAnnotationPresent(CssSelect.class)) { //TODO must be always present
            CssSelect annotation = clazz.getAnnotation(CssSelect.class);
            Elements elements = currentElement.select(annotation.value());
            for (Element element : elements) {
                scrapedObjects.add(createAndFillObject(element, clazz));
            }
        }
        return scrapedObjects; //TODO remove
    }

    private Object scrapeValueOfClass(Element currentElement, Class<?> clazz, AnnotationType type, String selector) {
        Element selectedElement = selectElement(currentElement, type, selector);
        if (selectedElement != null) {
            //TODO add for other types (Integer, Time, etc.)
            if (clazz == String.class) {
                return selectedElement.text();
            }
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

}
