import fit.vutbr.HtmlScrapper.annotations.CssSelect;
import fit.vutbr.HtmlScrapper.annotations.DateTimePattern;
import fit.vutbr.HtmlScrapper.annotations.XPathSelect;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScrappedValues {
    @CssSelect(".content .text-values span")
    private String stringUsingCss;

    @XPathSelect("//div[contains(@class, 'content')]/div[contains(@class, 'text-values')]/span[1]")
    private String stringUsingXpath;

    @CssSelect(".content .text-values span")
    private List<String> stringList;

    @CssSelect(".content .numerical-values .whole")
    private Integer wholeNumber;

    @CssSelect(".content .numerical-values .decimal")
    private Double decimalNumber;

    @CssSelect(".content .numerical-values .decimal-comma")
    private Double decimalNumberWithComma;

    @DateTimePattern("d MMMM yyyy")
    @CssSelect(".content .time-values .local-date-format-A")
    private LocalDate localDateA;

    @DateTimePattern("dd/MM/yyyy")
    @CssSelect(".content .time-values .local-date-format-B")
    private LocalDate localDateB;

    @DateTimePattern("yyyy-MM-dd HH:mm")
    @CssSelect(".content .time-values .local-date-time")
    private LocalDateTime localDateTime;

    @DateTimePattern("HH:mm:ss")
    @CssSelect(".content .time-values .local-time")
    private LocalTime localTime;

    public String getStringUsingCss() {
        return stringUsingCss;
    }

    public String getStringUsingXpath() {
        return stringUsingXpath;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public Integer getWholeNumber() {
        return wholeNumber;
    }

    public Double getDecimalNumber() {
        return decimalNumber;
    }

    public Double getDecimalNumberWithComma() {
        return decimalNumberWithComma;
    }

    public LocalDate getLocalDateA() {
        return localDateA;
    }

    public LocalDate getLocalDateB() {
        return localDateB;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
