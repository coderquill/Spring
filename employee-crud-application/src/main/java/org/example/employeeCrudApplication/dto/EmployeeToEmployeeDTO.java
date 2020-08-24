package org.example.employeeCrudApplication.dto;

import org.example.employeeCrudApplication.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeDTO {

    // DTO data transfer object

    public EmployeeDto convert(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setGender(employee.getGender());
        employeeDto.setDepartment(employee.getDepartment());

        return employeeDto;
    }
}
