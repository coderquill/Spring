package org.example.employee.crud.KafkaConsumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.io.IOException;

import org.example.employee.crud.service.EmployeeService;
import org.example.employee.crud.dto.EmployeeDto;

@Component
public class Receiver {
    private EmployeeService employeeService;

    @Value("${kafka.topic.json}")
    private String jsonTopic;

    @Autowired
    public Receiver(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

   /* private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.json}")
   // @KafkaListener(topics = "json.t")
      public void receive(EmployeeDto employee) {
        LOGGER.trace("received employee='{}'", employee.toString());
        latch.countDown();
      }*/

    private final Logger LOGGER = LoggerFactory.getLogger(Receiver .class);

    @KafkaListener(topics = "json.t", groupId = "group_one", containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserMessage(@Payload EmployeeDto msg, @Headers MessageHeaders headers) throws IOException {
        LOGGER.trace("received data in Consumer One ="+ msg.toString());
        employeeService.updateEmployee(msg);
    }
}
