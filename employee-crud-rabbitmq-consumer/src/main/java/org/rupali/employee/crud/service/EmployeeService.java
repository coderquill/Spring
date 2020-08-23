package org.rupali.employee.crud.service;
import java.util.List;
import java.util.UUID;

import org.rupali.employee.crud.entity.Employee;

public interface EmployeeService {
	List<Employee> findAllEmployees();

    Employee findEmployeeById(UUID id);

    void saveEmployee(Employee employee);
    
    void updateEmployee(Employee  EmployeeToUpdateFrom, Employee EmployeeToUpdate);

    void deleteEmployeeById(UUID id);
}
