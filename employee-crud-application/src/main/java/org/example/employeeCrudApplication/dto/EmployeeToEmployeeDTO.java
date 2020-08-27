package org.example.employeeCrudApplication.dto;

import java.io.Serializable;

import org.example.employeeCrudApplication.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeDTO implements Serializable{

    public EmployeeDto convert(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setGender(employee.getGender());
        employeeDto.setDepartment(employee.getDepartment());

        return employeeDto;
    }
}
