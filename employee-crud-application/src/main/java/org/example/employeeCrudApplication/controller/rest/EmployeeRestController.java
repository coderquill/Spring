package org.example.employeeCrudApplication.controller.rest;

import org.slf4j.LoggerFactory;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController 
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/employees")
    public List<EmployeeDto> findAll() {
    	return employeeService.findAllEmployeeDtos();
        
    }
    
    @GetMapping("/employee/{id}")
    @Cacheable(value = "employees", key="#id")
    public EmployeeDto getEmployee(@PathVariable UUID id) {
    	
    	return employeeService.findEmployeeDtoById(id);
    }
    

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    
    @PostMapping("/employee")
    public void addEmployee(@RequestBody EmployeeDto employeeDto) {
    	employeeService.saveEmployee(employeeDto);
        
    }
    
    @PutMapping("employee/{id}")
   // @CachePut(value = "employees", key="#id")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
    	employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("employee/{id}")
    @CacheEvict(value = "employees", allEntries=true)
    public void deleteEmployeeById(@PathVariable UUID id) {
    	employeeService.deleteEmployeeById(id);
    }
}