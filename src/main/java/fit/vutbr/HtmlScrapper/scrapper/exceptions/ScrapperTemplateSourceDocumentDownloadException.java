package fit.vutbr.HtmlScrapper.scrapper.exceptions;

/**
 * This exception is thrown when there is some error with downloading or opening web document
 */
public class ScrapperTemplateSourceDocumentDownloadException extends ScrapperTemplateException {
    public ScrapperTemplateSourceDocumentDownloadException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
