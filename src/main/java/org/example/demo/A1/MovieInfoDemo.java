package org.example.demo.A1;

import org.example.anotation.XPathSelect;

public class MovieInfoDemo {
    @XPathSelect("div[2]/span[1]")
    private String year;
    @XPathSelect("div[2]/span[2]")
    private String duration;
    @XPathSelect("div[2]/span[3]")
    private String ageRating;
}
