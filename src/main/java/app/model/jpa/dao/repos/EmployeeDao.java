package app.model.jpa.dao.repos;

import app.model.jpa.entities.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao {
    void addRecord(Employee employee);
}
