import fit.vutbr.HtmlScrapper.scrapper.ScrapperTemplate;
import fit.vutbr.HtmlScrapper.scrapper.exceptions.ScrapperTemplateSourceDocumentDownloadException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class HtmlScrapperTest {

    private final ScrapperTemplateLocal scrapperTemplate = new ScrapperTemplateLocal();

    @Test
    public void testBasicSelectors() {
        try {
            ScrappedValues scrappedValues = scrapperTemplate.scrapeData(null, ScrappedValues.class);
            assertEquals(scrappedValues.getStringUsingCss(), "AAA");
            assertEquals(scrappedValues.getStringUsingXpath(), "AAA");
            assertEquals(scrappedValues.getStringList(), List.of("AAA", "BBB", "CCC"));
            assertEquals(scrappedValues.getWholeNumber(), Integer.valueOf(15865));
            assertEquals(scrappedValues.getDecimalNumber(), Double.valueOf(15.86));
            assertEquals(scrappedValues.getDecimalNumberWithComma(), Double.valueOf(1256.82));
            assertEquals(scrappedValues.getLocalDateA(), LocalDate.of(2022, 5, 6));
            assertEquals(scrappedValues.getLocalDateB(), LocalDate.of(1988, 9, 29));
            assertEquals(scrappedValues.getLocalDateTime(), LocalDateTime.of(LocalDate.of(2022, 4, 8), LocalTime.of(12, 30)));
            assertEquals(scrappedValues.getLocalTime(), LocalTime.of(7, 55, 23));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void testDocumentDownloadException() {
        assertThrows(ScrapperTemplateSourceDocumentDownloadException.class, () -> {
            new ScrapperTemplate().scrapeData("http://asff.xxx", String.class);
        });
    }
}
