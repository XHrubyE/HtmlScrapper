package org.example.demo.A2;

import org.example.anotation.CssSelector;
import org.example.anotation.EntityAnotation;
import org.example.demo.A1.PersonDemo;

import java.util.List;

public class MovieListDemo {
    @CssSelector(key = ".ipc-title__text")
    private List<String> movies;
}
