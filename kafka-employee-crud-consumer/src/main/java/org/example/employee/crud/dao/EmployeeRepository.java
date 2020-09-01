package org.example.employee.crud.dao;

import java.util.*;

import org.example.employee.crud.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    
}