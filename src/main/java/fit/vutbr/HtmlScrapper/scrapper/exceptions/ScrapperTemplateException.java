package fit.vutbr.HtmlScrapper.scrapper.exceptions;

public class ScrapperTemplateException extends Exception {
    public ScrapperTemplateException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
