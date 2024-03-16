package org.example.demo;

import org.example.demo.A1.MovieDemo;
import org.example.demo.A2.MovieListDemo;
import org.example.scrapper.ScrapperTemplate;



public class MainDemo {
    public static void main(String[] args) {
        ScrapperTemplate template = new ScrapperTemplate();

//        PersonDemo person = template.scrapeData("https://www.sheldonbrown.com/web_sample1.html", PersonDemo.class);
        MovieListDemo movies = template.scrapeData("https://www.imdb.com/chart/top/", MovieListDemo.class);

        System.out.println("break label");
    }
}