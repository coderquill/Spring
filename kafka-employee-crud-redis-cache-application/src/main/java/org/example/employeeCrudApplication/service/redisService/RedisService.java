package org.example.employeeCrudApplication.service.redisService;

import java.util.List;
import java.util.UUID;

import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.entity.Employee;

public interface RedisService {	

	EmployeeDto findEmployeeDtoById(UUID id);

    void updateEmployee(EmployeeDto  employeeDto, UUID id);

    void deleteEmployeeById(UUID id);
}
