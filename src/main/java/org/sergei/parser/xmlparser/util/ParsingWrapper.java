package org.sergei.parser.xmlparser.util;

import org.sergei.parser.service.EmployeeService;
import org.sergei.parser.xmlparser.DocElemParsers;
import org.sergei.parser.xmlparser.XmlExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Sergei Visotsky, 2018
 * <p>
 * Class to call all document parser service functions
 */
@Component
public class ParsingWrapper {

    @Autowired
    private DocElemParsers docElemParsers;

    @Autowired
    private EmployeeService employeeService;

    public void documentServicesCaller(File localFile) {
        // Call parsing functions
        docElemParsers.setXmlExtractionService(new XmlExtraction(localFile));
        employeeService.read();
    }
}
