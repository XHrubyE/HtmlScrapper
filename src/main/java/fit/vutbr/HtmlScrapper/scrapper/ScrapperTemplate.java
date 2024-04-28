package fit.vutbr.HtmlScrapper.scrapper;

import fit.vutbr.HtmlScrapper.annotations.AnnotationType;
import fit.vutbr.HtmlScrapper.annotations.AnnotationUtils;
import fit.vutbr.HtmlScrapper.annotations.CssSelect;
import fit.vutbr.HtmlScrapper.annotations.XPathSelect;

import fit.vutbr.HtmlScrapper.scrapper.exceptions.ScrapperTemplateException;
import fit.vutbr.HtmlScrapper.scrapper.exceptions.ScrapperTemplateSourceDocumentDownloadException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ScrapperTemplate {

    //TODO Annotation RunTimePolicy https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/annotation/RetentionPolicy.html

    /**
     * This method maps data from source document to object of specified class.
     *
     * @param url   - url of source document
     * @param clazz - contains information about which data should an object contain
     *              how to find them in source document.
     * @return object of type clazz, that contains values found in source document
     */
    public <T> T scrapeData(String url, Class<T> clazz) throws ScrapperTemplateException {
        Document document = getDocumentFromUrl(url);
        return createAndFillObject(document.body(), clazz);
    }

    /**
     * Downloads source document from specified URL.
     * In case we need authentication to access the URL this method can be overridden.
     *
     * @param url - url of source document
     * @return - jsoup Document representation of source document
     */
    protected Document getDocumentFromUrl(String url) throws ScrapperTemplateSourceDocumentDownloadException {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ScrapperTemplateSourceDocumentDownloadException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Creates object of specified class and calls method that fills this object with data found in source document.
     *
     * @param currentElement - element is found only in this part of the document
     * @param clazz          - contains information about which data should an object contain
     *                       how to find them in source document.
     * @return object of type clazz, that contains values found in source document
     */
    private <T> T createAndFillObject(Element currentElement, Class<T> clazz) throws ScrapperTemplateException {
        try {
            T object = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                processAnnotationAndSetField(currentElement, object, field);
            }
            return object;
        } catch (Exception e) {
            throw new ScrapperTemplateException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Checks if this field contains annotations that can be processed and calls method based on annotation type.
     *
     * @param currentElement - element is found only in this part of the document
     * @param object         - object that is currently being processed
     * @param field          - which field of object is currently being processed
     * @throws IllegalAccessException - //TODO
     */
    private void processAnnotationAndSetField(Element currentElement, Object object, Field field) throws IllegalAccessException, ScrapperTemplateException {
        AnnotationType annotationType = AnnotationUtils.resolveFieldAnnotation(field);
        if (annotationType != null) {
            switch (annotationType) {
                case CSS -> processValueField(currentElement, object, field, AnnotationType.CSS);
                case XPATH -> processValueField(currentElement, object, field, AnnotationType.XPATH);
                case OBJECT -> processObjectField(currentElement, object, field);
            }
        }
    }


    /**
     * Process field that is annotated with CSS or XPath annotation and is therefore.
     * either some single elementary value that should be processed e.g. String,Integer,Double..., or list of elementary values
     *
     * @param currentElement - element is found only in this part of the document
     * @param object         - object that is currently being processed
     * @param field          - which field of object is currently being processed
     * @param type           - type of annotation that is defined on this field
     * @throws IllegalAccessException - trying to set the field value of the object can produce this exception
     */
    private void processValueField(Element currentElement, Object object, Field field, AnnotationType type) throws IllegalAccessException {
        field.setAccessible(true);
        String selector = AnnotationUtils.getFieldAnnotationValue(field, type);
        String pattern = AnnotationUtils.resolveDateTimePatternAnnotation(field);
        if (Utils.fieldIsOfTypeList(field)) {
            field.set(object, findAndGetListOfValues(currentElement, Utils.getInnerTypeOfList(field), type, selector, pattern));
        } else {
            field.set(object, findAndGetValue(currentElement, field.getType(), type, selector, pattern));
        }
    }

    /**
     * Find all values in a source document and parse them into final list of objects.
     *
     * @param currentElement - elements are found only in this part of the document
     * @param clazz          - this class represents inner type of list e.g. String,Integer,Double...
     * @param type           - type of annotation that is defined on this field
     * @param selector       - selector expression
     * @param pattern        - additional information about format for Double and DateTime values
     * @return list of elementary objects of specified class that was found in document null otherwise
     */
    private List<Object> findAndGetListOfValues(Element currentElement, Class<?> clazz, AnnotationType type, String selector, String pattern) {
        List<Object> result = new ArrayList<>();
        Elements elements = new Elements();

        switch (type) {
            case CSS -> elements = currentElement.select(selector);
            case XPATH -> elements = currentElement.selectXpath(selector);
        }

        for (Element element : elements) {
            result.add(ValueConverter.convertValue(element.text(), clazz, pattern));
        }
        return result;
    }

    /**
     * Find first value in a source document.
     *
     * @param currentElement - element is found only in this part of the document
     * @param clazz          - which elementary value are we parsing e.g. String,Integer,Double...
     * @param type           - type of annotation that is defined on this field
     * @param selector       - selector expression
     * @param pattern        - additional information about format for Double and DateTime values
     * @return single elementary value of specified class that was found in document or null otherwise
     */
    private Object findAndGetValue(Element currentElement, Class<?> clazz, AnnotationType type, String selector, String pattern) {
        Element selectedElement = selectElement(currentElement, type, selector);
        if (selectedElement != null) {
            return ValueConverter.convertValue(selectedElement.text(), clazz, pattern);
        }
        return null;
    }

    /**
     * Find element in document.
     *
     * @param currentElement - element is found only in this part of the document
     * @param type           - specifies which selector to use
     * @param selector       - selector expression
     * @return element that was found or null otherwise
     */
    private Element selectElement(Element currentElement, AnnotationType type, String selector) {
        return switch (type) {
            case CSS -> selectElementWithCss(currentElement, selector);
            case XPATH -> selectElementWithXPath(currentElement, selector);
            default -> null;
        };
    }

    /**
     * Find element using css selector.
     *
     * @param currentElement - element is found only in this part of the document
     * @param cssSelector    - css selector expression
     * @return element that was found or null otherwise
     */
    private Element selectElementWithCss(Element currentElement, String cssSelector) {
        return currentElement.selectFirst(cssSelector);
    }

    /**
     * Find element using XPath.
     *
     * @param currentElement - element is found only in this part of the document
     * @param xPathSelector  - XPath expression
     * @return element that was found or null otherwise
     */
    private Element selectElementWithXPath(Element currentElement, String xPathSelector) {
        return currentElement.selectXpath(xPathSelector).first(); //TODO possible NPE
    }


    /**
     * Process field that is annotated with ObjectSelect annotation and is therefore singular object,
     * and we call createAndFillObject method recursively, or is list of objects.
     *
     * @param currentElement - element is found only in this part of the document
     * @param object         - object that is currently being processed
     * @param field          - which field of object is currently being processed
     * @throws IllegalAccessException - trying to set the field value of the object can produce this exception
     */
    private void processObjectField(Element currentElement, Object object, Field field) throws IllegalAccessException, ScrapperTemplateException {
        field.setAccessible(true);
        if (Utils.fieldIsOfTypeList(field)) {
            field.set(object, findAndGetListOfObjects(currentElement, Utils.getInnerTypeOfList(field)));
        } else {
            field.set(object, createAndFillObject(currentElement, field.getType()));
        }
    }

    /**
     * @param currentElement - element is found only in this part of the document
     * @param clazz          - which object are we parsing, this class must contain class level annotation with selector
     * @return - list of objects with mapped values found in source document or empty list otherwise
     */
    private <T> List<T> findAndGetListOfObjects(Element currentElement, Class<T> clazz) throws ScrapperTemplateException {
        List<T> result = new ArrayList<>();
        Elements elements = new Elements();

        if (clazz.isAnnotationPresent(CssSelect.class)) { //TODO must be always present
            CssSelect annotation = clazz.getAnnotation(CssSelect.class);
            elements = currentElement.select(annotation.value());

        } else if (clazz.isAnnotationPresent(XPathSelect.class)) {
            XPathSelect annotation = clazz.getAnnotation(XPathSelect.class);
            elements = currentElement.selectXpath(annotation.value());
        }

        for (Element element : elements) {
            result.add(createAndFillObject(element, clazz));
        }
        return result; //TODO remove
    }
}
