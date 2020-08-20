package com.rupali.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rupali.model.Employee;

@Component
public class RabbitMQConsumer {

	@RabbitListener(queues = "${rupali.rabbitmq.queue}")
	public void recievedMessage(Employee employee) {
		System.out.println("Recieved Message From RabbitMQ: " + employee);
	}
}