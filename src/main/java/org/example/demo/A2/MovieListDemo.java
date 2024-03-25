package org.example.demo.A2;

import org.example.anotation.ObjectSelect;
import org.example.demo.A1.MovieDemo;

import java.util.List;

public class MovieListDemo {
    @ObjectSelect
    private List<MovieDemo> movies;
//    @CssSelector(key = ".ipc-title__text")
//    private List<String> movies;
}
