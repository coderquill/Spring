package org.example.employeeCrudApplication.dto;

import org.example.employeeCrudApplication.entity.Employee;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDTOToEmployee {

    // DTO data transfer object

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee convert(EmployeeDto employeeDto) {

        Employee employee = (employeeDto.getId() != null ? employeeService.findEmployeeById(employeeDto.getId()) : new Employee());

        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setDepartment(employeeDto.getDepartment());

        return employee;
    }
}