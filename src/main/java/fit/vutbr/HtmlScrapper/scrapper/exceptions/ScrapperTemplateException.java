package fit.vutbr.HtmlScrapper.scrapper.exceptions;

/**
 * General exception for ScrapperTemplate
 */
public class ScrapperTemplateException extends Exception {
    public ScrapperTemplateException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
