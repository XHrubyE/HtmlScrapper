package org.example;

import org.example.anotation.ByClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        checkIfAnnotationsWork(PersonDemo.class);
        try {
            Document doc = Jsoup.connect("http://example.com/").get();
            String title = doc.title();
            System.out.println(title);
        } catch (Exception ignored) {

        }


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