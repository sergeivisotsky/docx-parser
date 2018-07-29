package app.model.xmlparser.parser;

import app.model.xmlparser.util.DocNamespaceContext;
import app.model.xmlparser.util.ParserConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to parse XML document
 */
@Component
public class XmlExtraction {

    private static final Logger LOGGER = Logger.getLogger(XmlExtraction.class);

    private CommonsMultipartFile file;

    private Document document;

    private XPath xPath;

    public Document getDocument() {
        return document;
    }

    public XPath getXPath() {
        return xPath;
    }

    public XmlExtraction() {
    }

    public XmlExtraction(CommonsMultipartFile file) {
        this.file = file;
    }

    public void parseXmlFile() {
        try {
            // Gets .docx file
            File docxFile = new File(ParserConstants.UPL_DIR + file.getOriginalFilename());
            URI docxUri = URI.create("jar:" + docxFile.toURI());
            Map<String, String> zipProperties = new HashMap<>();
            zipProperties.put("encoding", "UTF-8");
            FileSystem zipFS = FileSystems.newFileSystem(docxUri, zipProperties);

            // Extracts document.xml file from .docx
            Path documentXmlPath = zipFS.getPath("/word/document.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true); // Sets DocumentBuilderFactory to be namespace sensitive

            // define namespaces
            HashMap<String, String> prefMap = new HashMap<>() {{
                put("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
                put("w14", "http://schemas.microsoft.com/office/word/2010/wordml");
            }};

            DocNamespaceContext namespaces = new DocNamespaceContext(prefMap);

            xPath = XPathFactory.newInstance().newXPath();
            xPath.setNamespaceContext(namespaces);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            document = builder.parse(Files.newInputStream(documentXmlPath));
            document.getDocumentElement().normalize();

        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error(e);
        }
    }
}
