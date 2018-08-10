package org.sergei.parser.model.xmlparser.util;

import org.sergei.parser.model.service.EmployeeService;
import org.sergei.parser.model.xmlparser.parser.DocElemParsers;
import org.sergei.parser.model.xmlparser.parser.XmlExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Class to call all document parser service functions
 */
@Component
public class FileUploaderComponent {

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
