package com.elasticsearch.search_service.service;

import com.elasticsearch.search_service.dto.Employee;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public interface ServiceInterface {


    HttpStatus ingestData() throws IOException, InterruptedException;

   // HttpStatus updateEmployee() throws IOException, InterruptedException;

    Employee getById(Integer id) throws IOException;

    List<Employee> getAllEmployees() throws IOException;

    List<Employee> getSpecificEmployee(String search_string) throws IOException;

    HttpStatus updateEmployee(String id, String updateValue) throws IOException;
}

