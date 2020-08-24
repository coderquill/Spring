package org.example.employeeCrudApplication.service;

import java.util.*;

import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.entity.Employee;

public interface EmployeeService {

   // List<Employee> findAllEmployees();
	List<EmployeeDto> findAllEmployees();

	 EmployeeDto findEmployeeById(UUID id);

    void saveEmployee(Employee employee);
    
    void updateEmployee(Employee  EmployeeToUpdateFrom, Employee EmployeeToUpdate);

    void deleteEmployeeById(UUID id);
}
