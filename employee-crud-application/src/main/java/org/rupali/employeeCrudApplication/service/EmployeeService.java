package org.rupali.employeeCrudApplication.service;

import java.util.*;

import org.rupali.employeeCrudApplication.entity.Employee;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findEmployeeById(UUID id);

    void saveEmployee(Employee employee);
    
    void updateEmployee(Employee  EmployeeToUpdateFrom, Employee EmployeeToUpdate);

    void deleteEmployeeById(UUID id);
}
