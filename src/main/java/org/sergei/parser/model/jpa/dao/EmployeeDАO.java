package org.sergei.parser.model.jpa.dao;

import org.sergei.parser.model.jpa.entities.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("EmployeeDao")
public class EmployeeDАO implements CustomCrudRepository<Employee> {

    private static final Logger LOGGER = Logger.getLogger(EmployeeDАO.class);

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
