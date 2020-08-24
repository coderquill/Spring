package org.example.employeeCrudApplication.controller.rest;

import org.slf4j.LoggerFactory;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<EmployeeDto>> findAll() {
    	return new ResponseEntity<>(employeeService.findAllEmployeeDtos(), HttpStatus.OK);
        
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable UUID id) {
    	
    	return new ResponseEntity<>(employeeService.findEmployeeDtoById(id), HttpStatus.OK);
    }

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
    	employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }
    
    @PutMapping("employee/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
    	employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("employee/{id}")
    public void deleteEmployeeById(@PathVariable UUID id) {
    	employeeService.deleteEmployeeById(id);
    }
}