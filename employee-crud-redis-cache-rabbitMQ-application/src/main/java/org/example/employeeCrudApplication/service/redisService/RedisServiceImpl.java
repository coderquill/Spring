package org.example.employeeCrudApplication.service.redisService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.example.employeeCrudApplication.controller.rest.EmployeeRestController;
import org.example.employeeCrudApplication.dto.EmployeeDTOToEmployee;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.example.employeeCrudApplication.dto.EmployeeToEmployeeDTO;
import org.example.employeeCrudApplication.entity.Employee;
import org.example.employeeCrudApplication.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class RedisServiceImpl implements RedisService{
	
	Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
	
	private EmployeeToEmployeeDTO employeeToEmployeeDTO;
	
	@Autowired
    public void setEmployeeToEmployeeDTO(EmployeeToEmployeeDTO employeeToEmployeeDTO) {
        this.employeeToEmployeeDTO = employeeToEmployeeDTO;
    }
	
	private EmployeeDTOToEmployee employeeDTOToEmployee;
	
	@Autowired
    public void setEmployeeDTOToEmployee(EmployeeDTOToEmployee employeeDTOToEmployee) {
        this.employeeDTOToEmployee = employeeDTOToEmployee;
    }
	
	
	@Autowired
	private RedisTemplate<String, Employee> redisTemplate;
	
	private EmployeeService employeeService;
	
	@Autowired
    public RedisServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

	public EmployeeDto findEmployeeDtoById(UUID id) {
		String Id = id.toString();
		//logger.trace("id"+id + " Id(key as a string)"+Id);	
		
		if(redisTemplate.hasKey(Id)) {
			logger.trace("findEmployeeDtoById method accessed. Fetched  employee from cache with id: "+id);	
			logger.trace("remaining time to live for id: "+redisTemplate.getExpire(Id));
			return employeeToEmployeeDTO.convert(redisTemplate.opsForValue().get(Id));
		}
		else {
			logger.trace("getEmployee method accessed. Fetched employee from database with id: "+id +" and saved in cache");
			redisTemplate.opsForValue().set(Id, employeeDTOToEmployee.convert(employeeService.findEmployeeDtoById(id)));
			Boolean setTimeToLive =  redisTemplate.expire(Id,60,TimeUnit.SECONDS);
			logger.trace("remaining time to live for id: "+Id+" " +redisTemplate.getExpire(Id));
			return employeeService.findEmployeeDtoById(id);
		}
	}
    
	public void updateEmployee(EmployeeDto  employeeDto, UUID id) {
		String Id = id.toString();
		if(redisTemplate.hasKey(Id)) {
			Boolean deleted = redisTemplate.delete(Id);
			logger.trace("updateEmployee method accessed. deleting employee from cache with id: "+id);
		}
		employeeService.updateEmployee(employeeDto, id);	
    }

	public void deleteEmployeeById(UUID id) {
		String Id = id.toString();
		if(redisTemplate.hasKey(Id)) {
			Boolean deleted = redisTemplate.delete(Id);
			logger.trace("deleteEmployeeById method accessed. deleting employee from cache with id: "+id);
		}
		employeeService.deleteEmployeeById(id);	
    }
	
}
