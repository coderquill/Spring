package org.rupali.employee.crud.dto;

import org.rupali.employee.crud.dto.EmployeeDto;
import org.rupali.employee.crud.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeDTO {
	public EmployeeDto convert(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setGender(employee.getGender());
        employeeDto.setDepartment(employee.getDepartment());

        return employeeDto;
    }
}
