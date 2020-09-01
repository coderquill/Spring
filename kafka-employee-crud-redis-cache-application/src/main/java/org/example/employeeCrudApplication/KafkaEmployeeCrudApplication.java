package org.example.employeeCrudApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KafkaEmployeeCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaEmployeeCrudApplication.class, args);
	}

}