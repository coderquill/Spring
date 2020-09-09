package com.elasticsearch.search_service.service;

import com.elasticsearch.search_service.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.http.client.methods.RequestBuilder.put;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/*@Service
public class ServiceImplementation implements ServiceInterface{
    @Override
    public HttpStatus createIndex() throws IOException, InterruptedException{
        CreateIndexRequest request = new CreateIndexRequest("companyDatabase");
        return HttpStatus.OK;
    }

   // @Override
   // public HttpStatus ingest_data() throws IOException, InterruptedException {
}*/
@Service
public class ServiceImplementation implements ServiceInterface {


    @Autowired
    private RestHighLevelClient client;

    @Autowired
    BulkIngestImplementation blk;

    @Autowired
    private ObjectMapper objectMapper;

   /* @Override
    public HttpStatus createIndex() throws IOException, InterruptedException{
        CreateIndexRequest request = new CreateIndexRequest("companyDatabase");

        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
                .loadFromSource(Strings.toString(jsonBuilder()
                        .startObject()
                        .startObject("analysis")
                        .startObject("analyzer")
                        .startObject("englishAnalyzer")
                        .field("tokenizer", "standard")
                        .field("char_filter", "html_strip")
                        .field("filter", new String[]{"snowball", "standard", "lowercase"})
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject()), XContentType.JSON)
        );
        request.mapping("{\n" +
                        "  \"companydatabase\" : {\n" +
                        "    \"mappings\" : {\n" +
                        "      \"dynamic\" : \"true\",\n" +
                        "      \"properties\" : {\n" +
                        "        \"Address\" : {\n" +
                        "          \"type\" : \"text\",\n" +
                        "          \"fields\" : {\n" +
                        "            \"keyword\" : {\n" +
                        "              \"type\" : \"keyword\",\n" +
                        "              \"ignore_above\" : 256\n" +
                        "            }\n" +
                        "          }\n" +
                        "        },\n" +
                        "        \"Age\" : {\n" +
                        "          \"type\" : \"float\"\n" +
                        "        },\n" +
                        "        \"DateOfJoining\" : {\n" +
                        "          \"type\" : \"date\",\n" +
                        "          \"format\" : \"yyyy-MM-dd\"\n" +
                        "        },\n" +
                        "        \"Designation\" : {\n" +
                        "          \"type\" : \"keyword\"\n" +
                        "        },\n" +
                        "        \"FirstName\" : {\n" +
                        "          \"type\" : \"text\",\n" +
                        "          \"analyzer\" : \"my_analyzer\",\n" +
                        "          \"search_analyzer\" : \"my_analyzer_2\"\n" +
                        "        },\n" +
                        "        \"Gender\" : {\n" +
                        "          \"type\" : \"keyword\"\n" +
                        "        },\n" +
                        "        \"Interests\" : {\n" +
                        "          \"type\" : \"text\",\n" +
                        "          \"analyzer\" : \"my_analyzer_1\",\n" +
                        "          \"search_analyzer\" : \"my_analyzer_2\"\n" +
                        "        },\n" +
                        "        \"LastName\" : {\n" +
                        "          \"type\" : \"text\",\n" +
                        "          \"analyzer\" : \"my_analyzer\",\n" +
                        "          \"search_analyzer\" : \"my_analyzer_2\"\n" +
                        "        },\n" +
                        "        \"MaritalStatus\" : {\n" +
                        "          \"type\" : \"keyword\"\n" +
                        "        },\n" +
                        "        \"Salary\" : {\n" +
                        "          \"type\" : \"float\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n",
                XContentType.JSON);



        request.alias(
                new Alias("twitter_alias")
        );
        /*request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");
        request.waitForActiveShards(2);
        request.waitForActiveShards(ActiveShardCount.DEFAULT);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

        return HttpStatus.OK;
    }*/


    @Override
    public HttpStatus ingestData() throws IOException, InterruptedException {
        File jsonFile = new File("Employees50K.json");
        FileReader fr = new FileReader(jsonFile);   //reads the file
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<Employee> employeeList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Integer count = 1;
        while ((line = br.readLine()) != null) { //read line and add employee object to  list
            Employee employee = mapper.readValue(line, Employee.class);
            employee.setId(count);
            employeeList.add(employee);
            count++;
        }
        blk.ingestdata(employeeList); //adds data from list to index
        return HttpStatus.OK;
    }


    @Override
    public List<Employee> getAllEmployees() throws IOException {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery(); //A Query that matches documents matching boolean combinations of other queries.
        boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        SearchRequest searchRequest = new SearchRequest(); //A request to execute search against one or more indices
        searchRequest.indices("companydatabase");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0); //index to start the search from
        searchSourceBuilder.size(25); //The number of search hits to return
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHit = searchResponse.getHits().getHits();
        List<Employee> employeeList = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            employeeList.add(objectMapper.convertValue(hit.getSourceAsMap(), Employee.class));
        }
        return employeeList;
    }

    @Override
    public List<Employee> getSpecificEmployee(String search_string) throws IOException {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.should(QueryBuilders.termQuery("Designation", search_string).boost(1.2f)); //matches documents containing a term
        boolQueryBuilder.should(QueryBuilders.termQuery("MaritalStatus", search_string).boost(0.9f));
        boolQueryBuilder.should(QueryBuilders.matchQuery("FirstName", search_string).boost(3.0f).fuzziness(Fuzziness.ONE)); //Sets the boost for query,Sets the fuzziness used, fuzziness: edit distance allowed
        boolQueryBuilder.should(QueryBuilders.matchQuery("LastName", search_string).boost(1.5f).fuzziness(Fuzziness.TWO));
        boolQueryBuilder.should(QueryBuilders.matchQuery("Interests", search_string).boost(0.5f).fuzziness(Fuzziness.AUTO));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("companydatabase");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0); //index to start the search from
        searchSourceBuilder.size(5);
        //searchSourceBuilder.query(boolQueryBuilder).sort(new FieldSortBuilder("id").order(SortOrder.ASC)).sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchSourceBuilder.minScore(50);
        searchSourceBuilder.query(boolQueryBuilder).sort("id",(SortOrder.ASC)).sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //System.out.println(searchResponse);
        SearchHit[] searchHit = searchResponse.getHits().getHits();
        List<Employee> employeeList = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            //System.out.println(hit);
            employeeList.add(objectMapper.convertValue(hit.getSourceAsMap(), Employee.class));
        }
        return employeeList;
    }

    @Override
    public Employee getById(Integer id) throws IOException{
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("id", id));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("companydatabase");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //System.out.println(searchResponse);
        SearchHit[] searchHit = searchResponse.getHits().getHits();
        //System.out.println(objectMapper.convertValue(searchHit[0].getSourceAsMap(), Employee.class));
        return objectMapper.convertValue(searchHit[0].getSourceAsMap(), Employee.class);
    }

    public HttpStatus updateEmployee(String id, String updateValue) throws IOException{

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("companydatabase");

        updateRequest.id(id);
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("Designation", updateValue)
                .endObject());
        client.update(updateRequest, RequestOptions.DEFAULT).getGetResult();
        System.out.println("updated a document");

        return HttpStatus.OK;
    }


}

