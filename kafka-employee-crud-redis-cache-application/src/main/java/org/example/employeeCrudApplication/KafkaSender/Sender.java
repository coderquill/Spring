package org.example.employeeCrudApplication.KafkaSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.example.employeeCrudApplication.dto.EmployeeDto;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topic.json}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, EmployeeDto> kafkaTemplate;

    public void send(EmployeeDto msg) {
        LOGGER.trace(String.format("\n ===== Producing message in JSON ===== \n"+msg));
        Message<EmployeeDto> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, jsonTopic)
                .build();
        this.kafkaTemplate.send(message);
    }
}