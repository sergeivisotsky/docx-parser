package org.sergei.parser.model.xmlparser.parser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

@Component
public class DocElemParsers {
    private static final Logger LOGGER = Logger.getLogger(DocElemParsers.class);

    @Autowired
    private XmlExtraction xmlExtractionService;

    public void setXmlExtractionService(XmlExtraction xmlExtractionService) {
        this.xmlExtractionService = xmlExtractionService;
        xmlExtractionService.parseXmlFile();
    }

    public Object xmlDataTextParser(String expr) {
        String param;
        try {
            NodeList dataNodeList = (NodeList) xmlExtractionService.getXPath().compile(expr)
                    .evaluate(xmlExtractionService.getDocument(), XPathConstants.NODESET);

            for (int i = 0; i < dataNodeList.getLength(); i++) {

                Node dataNode = dataNodeList.item(i);

                if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element paramElem = (Element) dataNode;
                    param = paramElem.getElementsByTagName("w:t")
                            .item(0)
                            .getTextContent()
                            .trim();
                    return param;
                }
            }
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Integer xmlDataIntParser(String expr) {
        Integer param;
        try {
            NodeList dataNodeList = (NodeList) xmlExtractionService.getXPath().compile(expr)
                    .evaluate(xmlExtractionService.getDocument(), XPathConstants.NODESET);

            for (int i = 0; i < dataNodeList.getLength(); i++) {

                Node dataNode = dataNodeList.item(i);

                if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element paramElem = (Element) dataNode;
                    param = Integer.parseInt(paramElem.getElementsByTagName("w:t")
                            .item(0)
                            .getTextContent()
                            .trim());
                    return param;
                }
            }
        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
