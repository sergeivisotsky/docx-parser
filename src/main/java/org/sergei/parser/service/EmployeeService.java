package org.sergei.parser.service;

import org.sergei.parser.model.Employee;
import org.sergei.parser.jpa.EmployeeRepository;
import org.sergei.parser.xmlparser.parser.DocElemParsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DocElemParsers docElemParsers;

    private static final String EXPR_FIRST_NAME = "/w:document/w:body/w:p[@w14:paraId='3DC39470']/w:r[2]";
    private static final String EXPR_LAST_LAME = "/w:document/w:body/w:p[@w14:paraId='18B9EE55']/w:r[2]";
    private static final String EXPR_AGE = "/w:document/w:body/w:p[@w14:paraId='04AE3ECF']/w:r[2]";
    private static final String EXPR_POSITION = "/w:document/w:body/w:p[@w14:paraId='7EA3CB2F']/w:r[3]";
    private static final String EXPR_EXPERIENCE = "/w:document/w:body/w:p[@w14:paraId='3C65B030']/w:r[2]";

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
