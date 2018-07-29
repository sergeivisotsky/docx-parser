package app.model.service.impls;

import app.model.jpa.dao.repos.EmployeeDao;
import app.model.jpa.entities.Employee;
import app.model.service.repos.EmployeeService;
import app.model.xmlparser.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Parser parser;

    private static final String EXPR_FIRST_NAME = "/w:document/w:body/w:p[2]/w:r[2]/w:t";

    private static final String EXPR_LAST_LAME = "";

    private static final String EXPR_AGE = "";

    private static final String EXPR_POSITION = "";

    private static final String EXPR_EXPERIENCE = "";

    @Override
    public void readEmployeeData() {
        Employee employee = new Employee();

        employee.setFirstName((String) parser.xmlDataParser(EXPR_FIRST_NAME));
        employee.setLastName((String) parser.xmlDataParser(EXPR_LAST_LAME));
        employee.setAge((Integer) parser.xmlDataParser(EXPR_AGE));
        employee.setPosition((String) parser.xmlDataParser(EXPR_POSITION));
        employee.setExperience((Integer) parser.xmlDataParser(EXPR_EXPERIENCE));

        employeeDao.addRecord(employee);
    }
}
