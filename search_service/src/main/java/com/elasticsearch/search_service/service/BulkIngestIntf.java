package com.elasticsearch.search_service.service;

import com.elasticsearch.search_service.dto.Employee;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public interface BulkIngestIntf {
    HttpStatus ingestdata(List<Employee> employee) throws InterruptedException, IOException;
}
