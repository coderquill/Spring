package com.rupali.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rupali.model.Employee;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemp;
	
	@Value("${rupali.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rupali.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Employee company) {
		rabbitTemp.convertAndSend(exchange, routingkey, company);
		System.out.println("Send msg = " + company);
	    
	}
}