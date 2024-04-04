package fit.vutbr.HtmlScrapper.demo.A1;

import fit.vutbr.HtmlScrapper.anotations.XPathSelect;

public class MovieInfoDemo {
    @XPathSelect("div[2]/span[1]")
    private String year;
    @XPathSelect("div[2]/span[2]")
    private String duration;
    @XPathSelect("div[2]/span[3]")
    private String ageRating;
}
