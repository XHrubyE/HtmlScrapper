package fit.vutbr.HtmlScrapper.demo;

import fit.vutbr.HtmlScrapper.demo.A2.Ote;
import fit.vutbr.HtmlScrapper.scrapper.ScrapperTemplate;



public class MainDemo {
    public static void main(String[] args) {
        ScrapperTemplate template = new ScrapperTemplateImpl();

//        MovieListDemo movies = template.scrapeData("https://www.imdb.com/chart/top/", MovieListDemo.class);

        Ote ote = template.scrapeData("https://www.ote-cr.cz/cs/kratkodobe-trhy/elektrina/denni-trh?date=2024-03-27", Ote.class);

        System.out.println("break label");
    }
}