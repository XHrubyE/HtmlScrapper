package org.example.demo;

import org.example.demo.A1.MovieListDemo;
import org.example.demo.A2.Ote;
import org.example.scrapper.ScrapperTemplate;



public class MainDemo {
    public static void main(String[] args) {
        ScrapperTemplate template = new ScrapperTemplate();

//        MovieListDemo movies = template.scrapeData("https://www.imdb.com/chart/top/", MovieListDemo.class);

        Ote ote = template.scrapeData("https://www.ote-cr.cz/cs/kratkodobe-trhy/elektrina/denni-trh?date=2024-03-27", Ote.class);

        System.out.println("break label");
    }
}