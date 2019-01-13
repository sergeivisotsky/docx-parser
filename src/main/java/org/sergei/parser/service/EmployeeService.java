/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.parser.service;

import org.sergei.parser.model.Employee;
import org.sergei.parser.repository.EmployeeRepository;
import org.sergei.parser.xmlparser.DocElemParsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    // xPath expressions to parse words document.xml file
    private static final String EXPR_FIRST_NAME = "/w:document/w:body/w:p[@w14:paraId='3DC39470']/w:r[2]";
    private static final String EXPR_LAST_LAME = "/w:document/w:body/w:p[@w14:paraId='18B9EE55']/w:r[2]";
    private static final String EXPR_AGE = "/w:document/w:body/w:p[@w14:paraId='04AE3ECF']/w:r[2]";
    private static final String EXPR_POSITION = "/w:document/w:body/w:p[@w14:paraId='7EA3CB2F']/w:r[3]";
    private static final String EXPR_EXPERIENCE = "/w:document/w:body/w:p[@w14:paraId='3C65B030']/w:r[2]";

    private final EmployeeRepository employeeRepository;
    private final DocElemParsers docElemParsers;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DocElemParsers docElemParsers) {
        this.employeeRepository = employeeRepository;
        this.docElemParsers = docElemParsers;
    }

    /**
     * Method to read employee data
     */
    public void read() {
        Employee employee = new Employee();

        try {
            employee.setFirstName((String) docElemParsers.xmlDataTextParser(EXPR_FIRST_NAME));
            employee.setLastName((String) docElemParsers.xmlDataTextParser(EXPR_LAST_LAME));
            employee.setAge(Integer.parseInt((String) docElemParsers.xmlDataTextParser(EXPR_AGE)));
            employee.setPosition((String) docElemParsers.xmlDataTextParser(EXPR_POSITION));
            employee.setExperience(Integer.parseInt((String) docElemParsers.xmlDataTextParser(EXPR_EXPERIENCE)));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
        }

        employeeRepository.save(employee);
    }
}
