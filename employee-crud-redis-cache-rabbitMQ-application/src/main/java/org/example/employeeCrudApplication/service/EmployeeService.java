package org.example.employeeCrudApplication.service;

import java.util.*;

import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.entity.Employee;

public interface EmployeeService {

    List<Employee> findAllEmployees();
    
	List<EmployeeDto> findAllEmployeeDtos();
	
	Employee findEmployeeById(UUID id);

	EmployeeDto findEmployeeDtoById(UUID id);

    void saveEmployee(EmployeeDto employeeDto);
    
    void updateEmployee(EmployeeDto  employeeDto, UUID id);

    void deleteEmployeeById(UUID id);
}
