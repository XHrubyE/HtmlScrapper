package fit.vutbr.HtmlScrapper.demo.A2;

import fit.vutbr.HtmlScrapper.anotations.CssSelect;
import fit.vutbr.HtmlScrapper.anotations.XPathSelect;

@XPathSelect("(//tbody)[2]/tr[position() <= 24]")
public class ValuesPerHour {
    @CssSelect("th")
    private Integer hour;
    @XPathSelect("td[1]")
    private Double price;
    @XPathSelect("td[2]")
    private Double volume;
    @XPathSelect("td[3]")
    private Double saldo;
    @XPathSelect("td[4]")
    private Double exp;
    @XPathSelect("td[5]")
    private Double imp;


}