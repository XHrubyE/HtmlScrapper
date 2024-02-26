package org.example;

import org.example.anotation.ByClass;
import org.example.anotation.BySelector;

public class PersonDemo {
    @ByClass(key = "primary")
    private String firstName;
    @BySelector(key = "\"h3.r > div\"")
    private String lastName;
}
