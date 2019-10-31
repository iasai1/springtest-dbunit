package com.diana.integration;

import com.diana.TestUtil;
import com.diana.model.Department;
import com.diana.util.dto.DepRenameDTO;

import com.diana.util.dto.EmployeeDTO;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    private static final int PORT = 8953;

    private RestTemplate restTemplate;
    private ResponseEntity<String> response;

    @BeforeEach
    public void classSetUp(){
        restTemplate = new RestTemplate();
    }

    @Test
    @Order(1)
    public void testPostDep() throws  Exception{
        Department d = new Department("testDep");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(d), headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + PORT +"/newDepartment", request, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());
        try {
            response = restTemplate.postForEntity("http://localhost:" + PORT +"/newDepartment", request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Department with such name already exists", response.getBody());

    }

    @Test
    @Order(2)
    public void testEditDep_expectEntityNotExists() throws Exception {
        DepRenameDTO d = new DepRenameDTO("2", "test");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(d), headers);

        try {
            response =  restTemplate.exchange("http://localhost:" + PORT +"/renameDep", HttpMethod.PUT, request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Department with such id does not exist", response.getBody());
    }

    @Test
    @Order(3)
    public void testEditDep_expectEntityExists() throws Exception {
        DepRenameDTO d = new DepRenameDTO("1", "testDep");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(d), headers);

        try {
            response =  restTemplate.exchange("http://localhost:" + PORT +"/renameDep", HttpMethod.PUT, request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Department with such name already exists", response.getBody());
    }

    @Test
    @Order(4)
    public void testEditDep_expectSuccess() throws Exception {
        DepRenameDTO d = new DepRenameDTO("1", "testDep2");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(d), headers);

        response =  restTemplate.exchange("http://localhost:" + PORT +"/renameDep", HttpMethod.PUT, request, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void testCreateEmp_expectMalformedInput() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy1234", "1234", "city", "street", "coast");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(employeeDTO), headers);

        try {
            response =  restTemplate.exchange("http://localhost:" + PORT +"/newEmployee", HttpMethod.POST, request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.NOT_ACCEPTABLE);
        }
        Assert.assertEquals(406, response.getStatusCodeValue());
        Assert.assertEquals("Employee's name should only contain letters", response.getBody());
    }

    @Test
    @Order(6)
    public void testCreateEmp_expectDepNotExists() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "testDep");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(employeeDTO), headers);

        try {
            response =  restTemplate.exchange("http://localhost:" + PORT +"/newEmployee", HttpMethod.POST, request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.NOT_ACCEPTABLE);
        }
        Assert.assertEquals(406, response.getStatusCodeValue());
        Assert.assertEquals("Department with such name doesn't exist", response.getBody());
    }

    @Test
    @Order(7)
    public void testCreateEmp_expectSuccess() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "testDep2");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(TestUtil.convertObjectToJsonString(employeeDTO), headers);

        response =  restTemplate.exchange("http://localhost:" + PORT +"/newEmployee", HttpMethod.POST, request, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    @Order(8)
    public void testGetEmpInfo_expectEntityNotExists() {

        String id = "1234";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            response =  restTemplate.exchange("http://localhost:" + PORT +"/employeeInfo" + id, HttpMethod.POST, request, String.class);
        }catch(HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals("Employee with such id does not exist", response.getBody());

    }

    @Test
    @Order(9)
    public void testGetEmpInfo_expectSuccess() {

        String id = "2";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        response =  restTemplate.exchange("http://localhost:" + PORT +"/employeeInfo" + id, HttpMethod.POST, request, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertFalse(response.getBody().isEmpty());

    }

}