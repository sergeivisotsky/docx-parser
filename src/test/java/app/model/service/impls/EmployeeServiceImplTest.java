package app.model.service.impls;

import app.model.jpa.dao.repos.EmployeeDАО;
import app.model.jpa.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.testng.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration("/test-context.xml")
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeDАО employeeDАО;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDataFlow() {
        final String SQL_SELECT_NAME = "SELECT first_name FROM employee WHERE emp_id = ?";
        Employee employee = new Employee("John", "Smith", 25, "manager", 5);
        employeeDАО.addRecord(employee);
//        assertEquals((long) 1, (long) employee.getEmpId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals(25, employee.getAge());
        assertEquals("manager", employee.getPosition());
        assertEquals(5, employee.getExperience());
        String name = jdbcTemplate.queryForObject(SQL_SELECT_NAME, String.class, employee.getEmpId());
        assertEquals("John", name);
    }
}