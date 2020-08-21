package org.rupali.employeeCrudApplication.service;

import org.rupali.employeeCrudApplication.dao.EmployeeRepository;
import org.rupali.employeeCrudApplication.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
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