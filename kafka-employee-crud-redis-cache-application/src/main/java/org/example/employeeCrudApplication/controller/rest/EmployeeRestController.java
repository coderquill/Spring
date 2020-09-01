package org.example.employeeCrudApplication.controller.rest;

import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.example.employeeCrudApplication.service.redisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController 
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private RedisService redisService;
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService, RedisService redisService) {
        this.employeeService = employeeService;
        this.redisService = redisService;
    }
    @GetMapping("/employees")
    public List<EmployeeDto> findAll() {
    	return employeeService.findAllEmployeeDtos();
        
    }

    @GetMapping("/employee/{id}")
    public EmployeeDto getEmployee(@PathVariable UUID id) {
    	return redisService.findEmployeeDtoById(id);
    }

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    
    @PostMapping("/employee")
    public void addEmployee(@RequestBody EmployeeDto employeeDto) {
    	employeeService.saveEmployee(employeeDto);
        
    }
    
    @PutMapping("employee/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
    	redisService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("employee/{id}")
    public void deleteEmployeeById(@PathVariable UUID id) {
    	redisService.deleteEmployeeById(id);
    }

}