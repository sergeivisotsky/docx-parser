package app.model.service.impls;

import app.model.jpa.dao.repos.EmployeeDao;
import app.model.jpa.entities.Employee;
import app.model.service.repos.EmployeeService;
import app.model.xmlparser.parser.Parser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Parser parser;

    private static final String EXPR_FIRST_NAME = "/w:document/w:body/w:p[2]/w:r[2]";
    private static final String EXPR_LAST_LAME = "/w:document/w:body/w:p[3]/w:r[2]";
    private static final String EXPR_AGE = "/w:document/w:body/w:p[4]/w:r[2]";
    private static final String EXPR_POSITION = "/w:document/w:body/w:p[5]/w:r[2]";
    private static final String EXPR_EXPERIENCE = "/w:document/w:body/w:p[5]/w:r[2]";

    @Override
    public void readEmployeeData() {
        Employee employee = new Employee();

        try {
            employee.setFirstName((String) parser.xmlDataParser(EXPR_FIRST_NAME));
            employee.setLastName((String) parser.xmlDataParser(EXPR_LAST_LAME));
            employee.setAge(Integer.parseInt((String) parser.xmlDataParser(EXPR_AGE)));
            employee.setPosition((String) parser.xmlDataParser(EXPR_POSITION));
            employee.setExperience(Integer.parseInt((String) parser.xmlDataParser(EXPR_EXPERIENCE)));
        } catch (NumberFormatException e) {
            LOGGER.error(e);
        }

        employeeDao.addRecord(employee);
    }
}
