package org.sergei.parser.model.service;

import org.sergei.parser.model.jpa.dao.EmployeeDАO;
import org.sergei.parser.model.jpa.entities.Employee;
import org.sergei.parser.model.xmlparser.parser.DocElemParsers;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements CustomServiceRepository {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDАO employeeDАО;

    @Autowired
    private DocElemParsers docElemParsers;

    private static final String EXPR_FIRST_NAME = "/w:document/w:body/w:p[2]/w:r[2]";
    private static final String EXPR_LAST_LAME = "/w:document/w:body/w:p[3]/w:r[2]";
    private static final String EXPR_AGE = "/w:document/w:body/w:p[4]/w:r[2]";
    private static final String EXPR_POSITION = "/w:document/w:body/w:p[5]/w:r[2]";
    private static final String EXPR_EXPERIENCE = "/w:document/w:body/w:p[5]/w:r[2]";

    @Override
    public void read() {
        Employee employee = new Employee();

        try {
            employee.setFirstName((String) docElemParsers.xmlDataParser(EXPR_FIRST_NAME));
            employee.setLastName((String) docElemParsers.xmlDataParser(EXPR_LAST_LAME));
            employee.setAge(Integer.parseInt((String) docElemParsers.xmlDataParser(EXPR_AGE)));
            employee.setPosition((String) docElemParsers.xmlDataParser(EXPR_POSITION));
            employee.setExperience(Integer.parseInt((String) docElemParsers.xmlDataParser(EXPR_EXPERIENCE)));
        } catch (NumberFormatException e) {
            LOGGER.error(e);
        }

        employeeDАО.addRecord(employee);
    }
}
