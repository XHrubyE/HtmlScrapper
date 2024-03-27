package org.example.demo.A1;

import org.example.anotations.ObjectSelect;
import org.example.anotations.XPathSelect;

import java.util.List;

public class MovieListDemo {
    @ObjectSelect
    private List<MovieDemo> movies;

    @XPathSelect("//li/div[2]/div/div/div/a/h3")
    private List<String> movieNames;
}
