package app.model.jpa.dao.impls;

import app.model.jpa.dao.repos.EmployeeDao;
import app.model.jpa.entities.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("EmployeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = Logger.getLogger(EmployeeDaoImpl.class);

    private static final String SQL_ADD_RECORD = "INSERT INTO employee(first_name, last_name, age, posit, experience) " +
            "VALUES(?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addRecord(Employee employee) {
        try {
            jdbcTemplate.update(SQL_ADD_RECORD, employee.getFirstName(), employee.getLastName(),
                    employee.getAge(), employee.getPosition(), employee.getExperience());
            LOGGER.info("Employee entity was saved");
        } catch (DataAccessException e) {
            LOGGER.error(e);
        }
    }
}
