package org.example.employeeCrudApplication.rabbitMQ.publisher;

import org.example.employeeCrudApplication.controller.rest.EmployeeRestController;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEmployeeSender {
 
	Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
	
	@Autowired
	private AmqpTemplate rabbitTemp;
	
	@Value("${employee.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${employee.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void sendEmployeeUpdateRequest(EmployeeDto  employeeDto) {
		rabbitTemp.convertAndSend(exchange, routingkey, employeeDto);
		logger.trace("message sent to the queue : "+employeeDto);
	}
}
