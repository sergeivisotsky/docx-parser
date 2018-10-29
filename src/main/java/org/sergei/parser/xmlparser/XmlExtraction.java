/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.parser.xmlparser;

import org.sergei.parser.xmlparser.util.DocNamespaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private File localFile;

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

    public XmlExtraction(File localFile) {
        this.localFile = localFile;
    }

    // Method used to extract .docx and parse xml file
    public void parseXmlFile() {
        try {
            // Gets .docx file
            URI docxUri = URI.create("jar:" + localFile.toURI());
            Map<String, String> zipProperties = new HashMap<>();
            zipProperties.put("encoding", "UTF-8");
            FileSystem zipFS = FileSystems.newFileSystem(docxUri, zipProperties);

            // Extracts document.xml file from .docx
            Path documentXmlPath = zipFS.getPath("/word/document.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true); // Sets DocumentBuilderFactory to be namespace sensitive

            // define namespaces
            HashMap<String, String> prefMap = new HashMap<String, String>() {{
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
            LOGGER.error(e.getMessage());
        }
    }
}
