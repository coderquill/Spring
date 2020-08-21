package springbootemployee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmployeeApplication.class, args);
	}

}

/*
 
CREATE TABLE employee (
  id binary(16) NOT NULL,
  name VARCHAR(255),
  gender VARCHAR(255),
  department VARCHAR(255),
  dob DATE,
  created timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
 */
