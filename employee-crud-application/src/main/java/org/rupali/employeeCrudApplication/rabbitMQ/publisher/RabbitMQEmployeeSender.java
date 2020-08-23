package org.rupali.employeeCrudApplication.rabbitMQ.publisher;

import org.rupali.employeeCrudApplication.controller.rest.EmployeeRestController;
import org.rupali.employeeCrudApplication.dto.EmployeeDto;
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
	
	@Value("${rupali.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rupali.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void sendEmployeeUpdateRequest(EmployeeDto  employeeDto) {
		rabbitTemp.convertAndSend(exchange, routingkey, employeeDto);
		logger.trace("message sent to the queue : "+employeeDto);
	}
}
