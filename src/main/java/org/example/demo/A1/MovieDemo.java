package org.example.demo.A1;

import org.example.anotation.CssSelect;
import org.example.anotation.ObjectSelect;
import org.example.anotation.XPathSelect;
import org.example.demo.A2.MovieListDemo;

@CssSelect(".sc-b0691f29-0.jbYPfh.cli-children")
public class MovieDemo {
    @CssSelect(".ipc-title__text")
    private String title;
    @CssSelect(".sc-b0691f29-8.ilsLEX.cli-title-metadata-item")
    private String year;
    @ObjectSelect
    private MovieInfoDemo movieInfo;
}
