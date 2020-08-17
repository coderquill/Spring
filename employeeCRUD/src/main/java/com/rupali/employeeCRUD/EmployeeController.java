package com.rupali.employeeCRUD;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
 
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
	
	@Autowired
    private EmployeeService service;
     
    // RESTful API methods for Retrieval operations
	@GetMapping("/employee")
	public List<Employee> list() {
		return service.listAll();
	}
     
    

}
