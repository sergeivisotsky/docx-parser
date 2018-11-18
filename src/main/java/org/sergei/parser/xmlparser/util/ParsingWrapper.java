package org.sergei.parser.xmlparser.util;

import org.sergei.parser.service.EmployeeService;
import org.sergei.parser.xmlparser.DocElemParsers;
import org.sergei.parser.xmlparser.XmlExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Sergei Visotsky, 2018
 * <pre>
 *    Class to call all document parser service functions
 * </pre>
 */
@Component
public class ParsingWrapper {

    private final DocElemParsers docElemParsers;
    private final EmployeeService employeeService;

    @Autowired
    public ParsingWrapper(DocElemParsers docElemParsers, EmployeeService employeeService) {
        this.docElemParsers = docElemParsers;
        this.employeeService = employeeService;
    }

    public void documentServicesCaller(File localFile) {
        // Call parsing functions
        docElemParsers.setXmlExtractionService(new XmlExtraction(localFile));
        employeeService.read();
    }
}
