package fit.vutbr.HtmlScrapper.demo.A1;

import fit.vutbr.HtmlScrapper.anotations.CssSelect;
import fit.vutbr.HtmlScrapper.anotations.ObjectSelect;

@CssSelect(".sc-b0691f29-0.jbYPfh.cli-children")
public class MovieDemo {
    @CssSelect(".ipc-title__text")
    private String title;
    @CssSelect(".sc-b0691f29-8.ilsLEX.cli-title-metadata-item")
    private Integer year;
    @ObjectSelect
    private MovieInfoDemo movieInfo;
}
