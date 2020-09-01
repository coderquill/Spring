package org.example.employeeCrudApplication.service;

import org.example.employeeCrudApplication.controller.rest.EmployeeRestController;
import org.example.employeeCrudApplication.dao.EmployeeRepository;
import org.example.employeeCrudApplication.dto.EmployeeDTOToEmployee;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.dto.EmployeeToEmployeeDTO;
import org.example.employeeCrudApplication.entity.Employee;
//import org.example.employeeCrudApplication.rabbitMQ.publisher.RabbitMQEmployeeSender;
import org.example.employeeCrudApplication.KafkaSender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
    private EmployeeDTOToEmployee employeeDTOToEmployee;
    private EmployeeToEmployeeDTO employeeToEmployeeDTO;
    
    Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
    

    @Autowired
    public void setEmployeeDTOToEmployee(EmployeeDTOToEmployee employeeDTOToEmployee) {
        this.employeeDTOToEmployee = employeeDTOToEmployee;
    }

    @Autowired
    public void setEmployeeToEmployeeDTO(EmployeeToEmployeeDTO employeeToEmployeeDTO) {
        this.employeeToEmployeeDTO = employeeToEmployeeDTO;
    }
    
   // @Autowired
    //private RabbitMQEmployeeSender rabbitMQSender;

    @Autowired
    private Sender sender;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> findAllEmployeeDtos() {
    	List<EmployeeDto> employeeDtos = new ArrayList<>();

        for(Employee employee : employeeRepository.findAll()) {
            employeeDtos.add(employeeToEmployeeDTO.convert(employee));
        }
        logger.trace("findAll method accessed. Fetched all employees from database");
        
        return employeeDtos;
    }
    
    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
	
    @Override
    public EmployeeDto findEmployeeDtoById(UUID id) {
        
        Optional<Employee> result = employeeRepository.findById(id);

        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id: " + id);
        }
        
        return employeeToEmployeeDTO.convert(employee);
      
    }
    
    @Override
    public Employee findEmployeeById(UUID id) {
        
       Optional<Employee> result = employeeRepository.findById(id);

        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id: " + id);
        }

        return employee;
    }


    @Override
    public void saveEmployee(EmployeeDto employeeDto) {
    	
        employeeRepository.save(employeeDTOToEmployee.convert(employeeDto));
    }
    
    
    @Override
    public void updateEmployee(EmployeeDto  employeeDto, UUID id) {
    		
    	
        if (findEmployeeById(id) == null) {
        	logger.error("No such employee with given id: "+id);
        }else {
        	employeeDto.setId(id);
        	logger.trace("updateEmployee method accessed.");
        	//rabbitMQSender.sendEmployeeUpdateRequest(employeeDto);
            sender.send(employeeDto);
        }
    	
    }
    
    @Override
    public void deleteEmployeeById(UUID id) {
    	
        if (findEmployeeById(id) == null) {
        	logger.error("No such employee with given id: "+id);
        }else {
        	 employeeRepository.deleteById(id);
        	logger.trace("deleteEmployeeById method accessed. Employee with id: "+id +" deleted.");	
        }	
       
    }
}