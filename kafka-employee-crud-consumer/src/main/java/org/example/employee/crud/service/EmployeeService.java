package org.example.employee.crud.service;
import java.util.List;
import java.util.UUID;

import org.example.employee.crud.dto.EmployeeDto;
import org.example.employee.crud.entity.Employee;

public interface EmployeeService {
	List<Employee> findAllEmployees();

    Employee findEmployeeById(UUID id);

    void saveEmployee(Employee employee);
    
    void updateEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(UUID id);
}
