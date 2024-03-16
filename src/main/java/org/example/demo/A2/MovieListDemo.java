package org.example.demo.A2;

import org.example.anotation.CssSelector;
import org.example.anotation.ObjectSelector;
import org.example.demo.A1.MovieDemo;

import java.util.List;

@CssSelector(key = "ul[role=presentation]")
public class MovieListDemo {
    @ObjectSelector
    private List<MovieDemo> movies;
//    @CssSelector(key = ".ipc-title__text")
//    private List<String> movies;
}
