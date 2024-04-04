package fit.vutbr.HtmlScrapper.scrapper.exceptions;

public class ScrapperTemplateSourceDocumentDownloadException extends RuntimeException {
    public ScrapperTemplateSourceDocumentDownloadException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
