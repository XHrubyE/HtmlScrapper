package fit.vutbr.HtmlScrapper.demo.A1;

import fit.vutbr.HtmlScrapper.anotations.ObjectSelect;
import fit.vutbr.HtmlScrapper.anotations.XPathSelect;

import java.util.List;

public class MovieListDemo {
    @ObjectSelect
    private List<MovieDemo> movies;

    @XPathSelect("//li/div[2]/div/div/div/a/h3")
    private List<String> movieNames;
}
