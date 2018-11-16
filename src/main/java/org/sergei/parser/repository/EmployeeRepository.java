package org.sergei.parser.repository;

import org.sergei.parser.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
