package com.rupali.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rupali.model.Employee;

@Component
public class RabbitMQConsumer {
	
	@Autowired
    private EmployeeService service;

	@RabbitListener(queues = "${rupali.rabbitmq.queue}")
	public void saveEmployee(Employee employee) {
		System.out.println("saving employee: " + employee);
		service.save(employee);
	}
}