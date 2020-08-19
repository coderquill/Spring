package com.rupali.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;

import com.rupali.model.Employee;
import com.rupali.service.EmployeeService;
import com.rupali.service.RabbitMQConsumer;
import com.rupali.service.RabbitMQSender;


@RestController
public class EmployeeController {
	
	@Autowired
    private EmployeeService service;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@Autowired
	RabbitMQConsumer rabbitMQConsumer;
     
    //REST API for retrieval
	@GetMapping("/employee")
	public List<Employee> list() {
		return service.listAll();
	}
	
	
	//REST API for retrieval by id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> get(@PathVariable Integer id) {
	    try {
	    	Employee employee = service.get(id);
	        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	
	//REST API for create new record
	//curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"xyz\",\"department\":\"sales\"}" http://localhost:8080/employee
    
	@PostMapping("/employee")
	public void add(@RequestBody Employee employee) {
	    service.save(employee);
	}
	
	
	//REST API for update record
	//curl -X PUT -H "Content-Type: application/json" -d "{\"id\":3,\"name\":\"lmn\",\"department\":\"hr\"}" http://localhost:8080/employee/3
 
	@PutMapping("/employee/{id}")
	public ResponseEntity<?> update(@RequestBody Employee employee, @PathVariable Integer id) {
	    try {
	    	
	    	Employee existEmployee = service.get(id);
	        rabbitMQSender.send(employee);
	        rabbitMQConsumer.saveEmployee(employee);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	
	//REST API for deletion
	//curl -X DELETE http://localhost:8080/employee/3
 
	@DeleteMapping("/employee/{id}")
	public void delete(@PathVariable Integer id) {
	    service.delete(id);
	}
    

}
