package org.rupali.employeeCrudApplication.rabbitMQ.publisher;

import org.rupali.employeeCrudApplication.dto.EmployeeDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEmployeeSender {
 
	
	@Autowired
	private AmqpTemplate rabbitTemp;
	
	@Value("${rupali.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rupali.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void sendEmployeeUpdateRequest(EmployeeDto  employeeDto) {
		rabbitTemp.convertAndSend(exchange, routingkey, employeeDto);
		System.out.println("Sent msg to the queue = " + employeeDto);   
	}
}
