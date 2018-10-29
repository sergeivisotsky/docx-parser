/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.parser.repository;

import org.sergei.parser.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
