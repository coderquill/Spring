package org.example.employee.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.employee.crud.dao.EmployeeRepository;
import org.example.employee.crud.dto.EmployeeDto;
import org.example.employee.crud.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
    private EmployeeRepository employeeRepository;

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class); 
	
    @Override
    public List<Employee> findAllEmployees() {
    	logger.trace("accessed findAllEmployees method");
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeById(UUID id) {
        Optional<Employee> result = employeeRepository.findById(id);

        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
            logger.trace("accessed findEmployeeById method");
        }
        else {
            throw new RuntimeException("Did not find employee id: " + id);
        }

        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        logger.trace("accessed saveEmployee method. saved employee "+employee);
    }
    
    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
    	Employee EmployeeToUpdate = findEmployeeById(employeeDto.getId());
    	if(employeeDto.getName() != null ) {
    		EmployeeToUpdate.setName(employeeDto.getName());
    	}
    	
    	if(employeeDto.getGender() != null ) {
    		EmployeeToUpdate.setGender(employeeDto.getGender());
    	}
    	
    	if(employeeDto.getDepartment() != null ) {
    		EmployeeToUpdate.setDepartment(employeeDto.getDepartment());
    	}
    	
    	employeeRepository.save(EmployeeToUpdate);	
    	
    	logger.trace("Updated employee id :"+EmployeeToUpdate.getId()
		+" Updated name:"+EmployeeToUpdate.getName()
		+" Updated gender:"+EmployeeToUpdate.getGender()
		+" Updated department:"+EmployeeToUpdate.getDepartment());
    	
    }

    @Override
    public void deleteEmployeeById(UUID id) {
    	
        employeeRepository.deleteById(id);
        logger.trace("accessed deleteEmployee method. Deleted employee with id: "+id);
    }
}
