package org.example.employee.crud.rabbitMQ.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.employee.crud.dto.EmployeeDTOToEmployee;
import org.example.employee.crud.dto.EmployeeDto;
import org.example.employee.crud.entity.Employee;
import org.example.employee.crud.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class RabbitMQConsumer {
	private EmployeeService employeeService;
    private EmployeeDTOToEmployee employeeDTOToEmployee;
	
    @Autowired
    public RabbitMQConsumer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
   
    @Autowired
    public void setEmployeeDTOToEmployee(EmployeeDTOToEmployee employeeDTOToEmployee) {
        this.employeeDTOToEmployee = employeeDTOToEmployee;
    }

    
    Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class); 
	
	@RabbitListener(queues = "${employee.rabbitmq.queue}")
	public void recievedMessage(EmployeeDto employeeDto) {
		logger.trace("Recieved Message From RabbitMQ: " + employeeDto);
		employeeService.updateEmployee(employeeDto);
    } 
		
}

