package org.example;

import org.example.anotation.ByClass;
import org.example.scrapper.ScrapperTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;


public class MainDemo {
    public static void main(String[] args) {
        ScrapperTemplate template = new ScrapperTemplate();

        PersonDemo person = template.scrapeData("https://www.sheldonbrown.com/web_sample1.html", PersonDemo.class);

        System.out.println("break label");
    }

    private static void checkIfAnnotationsWork(Class<?> c) {
        Object o = null;
        try {
            Constructor<?> constructor = c.getConstructor();
            o = constructor.newInstance();

            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ByClass.class)) {
                    Field byClassField = o.getClass().getDeclaredField(field.getName());
                    byClassField.setAccessible(true);
                    byClassField.set(o, "Piggy " + field.getAnnotation(ByClass.class).key());

                }
            }
        } catch (Exception e) {
            System.out.println("No constructor found for class!");
        }


        System.out.println(o);
    }
}