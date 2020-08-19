package com.rupali.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupali.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
