package com.elasticsearch.search_service.service;

import com.elasticsearch.search_service.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder; //JSON builder

@Service
@Slf4j
public class BulkIngestImplementation implements BulkIngestIntf {

    @Autowired
    private ObjectMapper objectMapper;//JSON serialize-deserialize objects

    @Autowired
    private RestHighLevelClient client;

    @Override
    public HttpStatus ingestdata(List<Employee> employee) throws InterruptedException, IOException {

        BulkProcessor.Listener listener = new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                log.info("start updation");
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                if (bulkResponse.hasFailures()) {
                    for (BulkItemResponse bulkItemResponse : bulkResponse) {
                        if (bulkItemResponse.isFailed()) {
                            BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                            log.info("found error ", failure.getCause());
                        }
                    }
                }
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                log.error("found errror", throwable);
            }
        };
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener).build();

        try {
            BulkRequest bulkRequest = new BulkRequest();

            employee.forEach(emp -> {
                Map<String, Object> map = objectMapper.convertValue(emp, HashMap.class);
                map.values().removeAll(Collections.singleton(null));
                Set<String> keys1 = map.keySet();
                XContentBuilder builder = null;
                try {
                    builder = jsonBuilder().startObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String keys : keys1) {
                    try {
                        builder.field(keys, map.get(keys));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    builder.endObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                IndexRequest indexRequest = new IndexRequest("companydatabase", "_doc", emp.getId().toString()).
                        source(builder);
                UpdateRequest updateRequest = new UpdateRequest("companydatabase", "_doc", emp.getId().toString());
                updateRequest.doc(builder);
                updateRequest.upsert(indexRequest);

                bulkProcessor.add(updateRequest);
            });

        } catch (Exception e) {
            log.error("found errror", e);
            throw e;
        }

        try {
            boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
            log.info("Update complete");
        } catch (InterruptedException e) {
            throw e;
        }
        return HttpStatus.OK;
    }
}

