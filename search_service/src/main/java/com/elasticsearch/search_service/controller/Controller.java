package com.elasticsearch.search_service.controller;

import com.elasticsearch.search_service.dto.Employee;
import com.elasticsearch.search_service.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    public ServiceInterface service;

    @GetMapping("/id/{id}")
    public Employee getById(@PathVariable Integer id) throws IOException {
        return service.getById(id);
    }

    @PostMapping("/ingest")
    public HttpStatus ingest_data() throws IOException, InterruptedException {
        return service.ingestData();
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() throws IOException {
        return service.getAllEmployees();
    }

    @GetMapping("/search/{searchString}")
    public List<Employee> getSpecificDoc(@PathVariable String searchString) throws IOException {
        return service.getSpecificEmployee(searchString);
    }

    @PutMapping("/update/{id}/{updateString}")
    public HttpStatus updateSpecificDoc(@PathVariable String id, @PathVariable String updateString) throws IOException {
        return service.updateEmployee(id,updateString);
    }



}
