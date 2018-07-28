package app.model.service.document.impls;

import app.model.jpa.dao.repos.EmployeeDao;
import app.model.jpa.entities.Employee;
import app.model.service.document.repos.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void readEmployeeData() {
        Employee employee = new Employee();

        employeeDao.addRecord(employee);
    }
}
