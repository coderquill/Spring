package com.rupali.employeeCRUD;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
	
	@Autowired
    private EmployeeService service;
     
    
	@GetMapping("/employee")
	public List<Employee> list() {
		return service.listAll();
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> get(@PathVariable Integer id) {
	    try {
	    	Employee employee = service.get(id);
	        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	    }      
	}
     
    

}
