package org.example.employeeCrudApplication.service;

import org.example.employeeCrudApplication.controller.rest.EmployeeRestController;
import org.example.employeeCrudApplication.dao.EmployeeRepository;
import org.example.employeeCrudApplication.dto.EmployeeDTOToEmployee;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.dto.EmployeeToEmployeeDTO;
import org.example.employeeCrudApplication.entity.Employee;
import org.example.employeeCrudApplication.rabbitMQ.publisher.RabbitMQEmployeeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Autowired
    private RabbitMQEmployeeSender rabbitMQSender;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> findAllEmployeeDtos() {
    	List<EmployeeDto> employeeDtos = new ArrayList<>();

        for(Employee employee : employeeRepository.findAll()) {
            employeeDtos.add(employeeToEmployeeDTO.convert(employee));
        }
        logger.trace("findAll method accessed. Returning all employees");
        
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
            logger.trace("getEmployee method accessed. Returning employee with id: "+id);
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
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    
    @Override
    public void updateEmployee(Employee EmployeeToUpdateFrom, Employee EmployeeToUpdate) {
    	
    	
    	
    	if(EmployeeToUpdateFrom.getName() != null ) {
    		EmployeeToUpdate.setName(EmployeeToUpdateFrom.getName());
    	}
    	
    	if(EmployeeToUpdateFrom.getGender() != null ) {
    		EmployeeToUpdate.setGender(EmployeeToUpdateFrom.getGender());
    	}
    	
    	if(EmployeeToUpdateFrom.getDepartment() != null ) {
    		EmployeeToUpdate.setDepartment(EmployeeToUpdateFrom.getDepartment());
    	}
    	
    	employeeRepository.save(EmployeeToUpdate);
    	
    }
    
    
    @Override
    public void deleteEmployeeById(UUID id) {
        employeeRepository.deleteById(id);
    }
}