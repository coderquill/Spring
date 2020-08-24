package org.example.employeeCrudApplication.dao;

import java.util.*;

import org.example.employeeCrudApplication.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    
}