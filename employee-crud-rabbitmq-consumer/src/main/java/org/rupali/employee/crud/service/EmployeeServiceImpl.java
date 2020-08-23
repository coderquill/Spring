package org.rupali.employee.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.rupali.employee.crud.dao.EmployeeRepository;
import org.rupali.employee.crud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    	
    	System.out.println("Updated employee id :"+EmployeeToUpdate.getId()
    						+" Updated name:"+EmployeeToUpdate.getName()
    						+" Updated gender:"+EmployeeToUpdate.getGender()
    						+" Updated department:"+EmployeeToUpdate.getDepartment());
    	
    	employeeRepository.save(EmployeeToUpdate);	
    }
    
    
    @Override
    public void deleteEmployeeById(UUID id) {
        employeeRepository.deleteById(id);
    }
}
