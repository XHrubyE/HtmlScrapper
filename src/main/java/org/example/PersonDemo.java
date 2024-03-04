package org.example;

import org.example.anotation.ByClass;
import org.example.anotation.CssSelector;
import org.example.anotation.ElementSelector;

@CssSelector(key = "ipc-page-grid__item ipc-page-grid__item--span-2")
public class PersonDemo {
//    @ByClass(key = "primary")
//    private String firstName;
    @CssSelector(key = "h1")
    private String headerOneCss;
    @ElementSelector(key = "h1")
    private String headerOneElement;
}
