package org.example.employeeCrudApplication.controller.rest;

import org.slf4j.LoggerFactory;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.example.employeeCrudApplication.service.redisService.RedisService;
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
    private RedisService redisService;
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService, RedisService redisService) {
        this.employeeService = employeeService;
        this.redisService = redisService;
    }
    
   /* private RedisService redisService;
    
    @Autowired
    public EmployeeRestController(RedisService redisService) {
        this.redisService = redisService;
    }*/
    
    @GetMapping("/employees")
    public List<EmployeeDto> findAll() {
    	return employeeService.findAllEmployeeDtos();
        
    }
    
   /* rest controller  using annotations and before adding redis service 
    * 
    * 
    * 
    * @GetMapping("/employee/{id}")
    //@Cacheable(value = "employees", key="#id")
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
    //@CacheEvict(cacheManager ="cacheManager", value = "employees", allEntries=true)
    public void deleteEmployeeById(@PathVariable UUID id) {
    	employeeService.deleteEmployeeById(id);
    }*/
    
    
    
    @GetMapping("/employee/{id}")
    //@Cacheable(value = "employees", key="#id")
    public EmployeeDto getEmployee(@PathVariable UUID id) {
    	return redisService.findEmployeeDtoById(id);
    	
    	//return employeeService.findEmployeeDtoById(id);
    }
    

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    
    @PostMapping("/employee")
    public void addEmployee(@RequestBody EmployeeDto employeeDto) {
    	employeeService.saveEmployee(employeeDto);
        
    }
    
    @PutMapping("employee/{id}")
   // @CachePut(value = "employees", key="#id")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
    	redisService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("employee/{id}")
    //@CacheEvict(cacheManager ="cacheManager", value = "employees", allEntries=true)
    public void deleteEmployeeById(@PathVariable UUID id) {
    	redisService.deleteEmployeeById(id);
    }
    
    
}