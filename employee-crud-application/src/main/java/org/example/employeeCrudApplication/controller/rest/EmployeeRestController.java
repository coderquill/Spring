package org.example.employeeCrudApplication.controller.rest;

import org.slf4j.LoggerFactory;
import org.example.employeeCrudApplication.dto.EmployeeDTOToEmployee;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.dto.EmployeeToEmployeeDTO;
import org.example.employeeCrudApplication.entity.Employee;
import org.example.employeeCrudApplication.rabbitMQ.publisher.RabbitMQEmployeeSender;
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
    private EmployeeDTOToEmployee employeeDTOToEmployee;
    private EmployeeToEmployeeDTO employeeToEmployeeDTO;
    
    Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEmployeeDTOToEmployee(EmployeeDTOToEmployee employeeDTOToEmployee) {
        this.employeeDTOToEmployee = employeeDTOToEmployee;
    }

    @Autowired
    public void setEmployeeToEmployeeDTO(EmployeeToEmployeeDTO employeeToEmployeeDTO) {
        this.employeeToEmployeeDTO = employeeToEmployeeDTO;
    }
    
    @Autowired
    private RabbitMQEmployeeSender rabbitMQSender;

    
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> findAll() {
    	return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
        
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable UUID id) {
    	/*
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
        	logger.error("No such employee with given id: "+id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.trace("getEmployee method accessed. Returning employee with id: "+id);
        return new ResponseEntity<>(employeeToEmployeeDTO.convert(employee), HttpStatus.OK);*/
    	return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDTOToEmployee.convert(employeeDto));
        logger.trace("addEmployee method accessed. New employee added: "+employeeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }
    
    @PutMapping("employee/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
    	/*Employee ExistingEmployee = employeeService.findEmployeeById(id);
    	
        if (ExistingEmployee == null) {
        	logger.error("No such employee with given id: "+id);
        }else {
        	employeeDto.setId(id);
        	logger.trace("updateEmployee method accessed.");
        	rabbitMQSender.sendEmployeeUpdateRequest(employeeDto);
        }*/

    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable UUID id) {

        try {
            employeeService.deleteEmployeeById(id);
            logger.trace("deletetEmployee method accessed. Employee deleted with id: "+id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception ex) {
        	logger.error("No such employee with given id: "+id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}