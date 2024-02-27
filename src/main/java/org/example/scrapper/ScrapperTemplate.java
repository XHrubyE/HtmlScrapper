package org.example.scrapper;

import org.example.anotation.CssSelector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ScrapperTemplate {

    public <T> T scrapeData(String url, Class<T> clazz) {
        Document document = getDocumentFromUrl(url);


        T o = null;
        try {
            Constructor<T> constructor = clazz.getConstructor();

            String classKey = getClassLevelSelector(clazz);

            o = constructor.newInstance();
        } catch (Exception e) {
            System.out.println("No constructor found for class!");
        }
        return o;
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
