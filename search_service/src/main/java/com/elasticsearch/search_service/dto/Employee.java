package com.elasticsearch.search_service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    @JsonProperty("FirstName")
    private String FirstName;

    @JsonProperty("LastName")
    private String LastName;

    @JsonProperty("Designation")
    private String Designation;

    @JsonProperty("Salary")
    private float Salary;

    @JsonProperty("DateOfJoining")
    private String DateOfJoining;

    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Gender")
    private String Gender;

    @JsonProperty("Age")
    private Integer Age;

    @JsonProperty("MaritalStatus")
    private String MaritalStatus;

    @JsonProperty("Interests")
    private String Interests;

    @JsonProperty("id")
    private Integer id;

}

