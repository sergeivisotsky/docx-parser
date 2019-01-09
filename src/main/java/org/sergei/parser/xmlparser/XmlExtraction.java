/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
 *
 * @author Sergei Visotsky
 */
@Component
public class XmlExtraction {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlExtraction.class);

    private File localFile;

    private Document document;

    private XPath xPath;

    public Document getDocument() {
        return document;
    }

    XPath getXPath() {
        return xPath;
    }

    public XmlExtraction() {
    }

    public XmlExtraction(File localFile) {
        this.localFile = localFile;
    }

    /**
     * Method used to extract .docx and parse xml file
     */
    void parseXmlFile() {
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
            HashMap<String, String> prefMap = new HashMap<>();
            prefMap.put("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
            prefMap.put("w14", "http://schemas.microsoft.com/office/word/2010/wordml");

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
