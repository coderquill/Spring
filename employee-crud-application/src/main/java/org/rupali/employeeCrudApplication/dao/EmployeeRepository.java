package org.rupali.employeeCrudApplication.dao;

import java.util.*;

import org.rupali.employeeCrudApplication.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    
}