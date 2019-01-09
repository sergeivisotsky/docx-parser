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

package org.sergei.parser.xmlparser.util;

import org.sergei.parser.service.EmployeeService;
import org.sergei.parser.xmlparser.DocElemParsers;
import org.sergei.parser.xmlparser.XmlExtraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Class to call all document parser service functions
 *
 * @author Sergei Visotsky
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
