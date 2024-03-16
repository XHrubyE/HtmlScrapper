package org.example.demo.A1;

import org.example.anotation.CssSelector;
import org.example.anotation.ElementSelector;

@CssSelector(key = ".sc-b0691f29-0 jbYPfh cli-children")
public class MovieDemo {
    @CssSelector(key = ".ipc-title__text")
    private String title;
    @CssSelector(key = ".sc-b0691f29-8.ilsLEX.cli-title-metadata-item")
    private String year;
    @CssSelector(key = "ipc-rating-star.ipc-rating-star--base.ipc-rating-star--imdb.ratingGroup--imdb-rating")
    private String rating;
}
