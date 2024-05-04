import fit.vutbr.HtmlScrapper.scrapper.ScrapperTemplate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *  Gets source document from resource file  test.html instead of downloading.
 */
public class ScrapperTemplateLocal extends ScrapperTemplate {
    @Override
    protected Document getDocumentFromUrl(String url) {
        try {
            InputStream inputStream = ScrapperTemplateLocal.class.getResourceAsStream("/test.html");
            String htmlContent = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);
            return Jsoup.parse(htmlContent, "");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
