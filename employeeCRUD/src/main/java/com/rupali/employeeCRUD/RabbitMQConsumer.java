package com.rupali.employeeCRUD;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rupali.employeeCRUD.Employee;

@Component
public class RabbitMQConsumer {
	
	@Autowired
    private EmployeeService service;

	/*@RabbitListener(queues = "${rupali.rabbitmq.queue}")
	public void recievedMessage(Employee employee) {
		System.out.println("Recieved Message From RabbitMQ: " + employee);
	}*/
	@RabbitListener(queues = "${rupali.rabbitmq.queue}")
	public void saveEmployee(Employee employee) {
		System.out.println("saving employee: " + employee);
		service.save(employee);
	}
}