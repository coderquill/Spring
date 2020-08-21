package org.rupali.employeeCrudApplication.controller.rest;

import org.rupali.employeeCrudApplication.dto.EmployeeDTOToEmployee;
import org.rupali.employeeCrudApplication.dto.EmployeeDto;
import org.rupali.employeeCrudApplication.dto.EmployeeToEmployeeDTO;
import org.rupali.employeeCrudApplication.entity.Employee;
import org.rupali.employeeCrudApplication.service.EmployeeService;
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

    //set up constructor injection
        // constructor injection (automatically created by spring boot)
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

    //expose "/employees" and return list of employees
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> findAll() {

        List<EmployeeDto> employeeDtos = new ArrayList<>();

        for(Employee employee : employeeService.findAllEmployees()) {
            employeeDtos.add(employeeToEmployeeDTO.convert(employee));
        }

        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
        //return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable UUID id) {

        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeToEmployeeDTO.convert(employee), HttpStatus.OK);
    }

  //curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"rbk\",\"gender\":\"female\",\"department\":\"Development\"}" http://localhost:8080/api/employee
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
    	
        employeeService.saveEmployee(employeeDTOToEmployee.convert(employeeDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    @PutMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        //employeeDto.setId(id);
       // employeeService.saveEmployee(employeeDTOToEmployee.convert(employeeDto));
       // return new ResponseEntity<>(HttpStatus.ACCEPTED);
    	Employee ExistingEmployee = employeeService.findEmployeeById(id);
    	System.out.println(ExistingEmployee);
        if (ExistingEmployee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
        	employeeService.updateEmployee(employeeDTOToEmployee.convert(employeeDto), ExistingEmployee);
        	return new ResponseEntity<>(employeeToEmployeeDTO.convert(ExistingEmployee), HttpStatus.OK);
        }
        
    	/*try {
    		employeeService.saveEmployee(employeeDTOToEmployee.convert(employeeDto));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable UUID id) {

        try {
            employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}