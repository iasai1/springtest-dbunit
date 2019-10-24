package com.diana.controller;


import com.diana.config.ControllerTestConfig;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.service.AddressService;
import com.diana.service.EmployeeService;
import com.diana.util.dto.EmployeeDTO;
import com.diana.util.error.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = ControllerTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EmployeeRESTControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    private MockMvc mockMvc;

    private Department testDepartment;
    private Address testAddress;
    private Employee testEmployee;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        testDepartment = new Department();
        testDepartment.setName("coast");
        testDepartment.setId(1L);

        testEmployee = new Employee();
        testEmployee.setName("Testy");
        testEmployee.setPhone("1234");
        testEmployee.setId(2L);
        testEmployee.setDepartment(testDepartment);

        testAddress = new Address();
        testAddress.setStreet("street");
        testAddress.setCity("city");
        testAddress.setEmployee(testEmployee);
    }

    @Test
    public void testCreateEmp() throws Exception{
        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "coast");

        Mockito.when(employeeService.findByName("Testy")).thenReturn(null);
        Mockito.when(employeeService.findByPhone("1234")).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/newEmployee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonString(employeeDTO)))
                .andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isOk());

        ArgumentCaptor<EmployeeDTO> dtoCaptor = ArgumentCaptor.forClass(EmployeeDTO.class);
        Mockito.verify(employeeService, Mockito.times(1)).create(dtoCaptor.capture());

        EmployeeDTO eDTO = dtoCaptor.getValue();
        Assert.assertEquals("1234", eDTO.getPhone());

    }

    @Test
    public void testGetInfo() throws Exception{

        Mockito.when(employeeService.findFetchedById(2L)).thenReturn(testEmployee);
        Mockito.when(addressService.findById(2L)).thenReturn(testAddress);

        mockMvc.perform(post("/employeeInfo{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.city").value("city"));


        Mockito.verify(employeeService, Mockito.times(1)).findFetchedById(2L);
        Mockito.verifyNoMoreInteractions(employeeService);
        Mockito.verify(addressService, Mockito.times(1)).findById(2L);
        Mockito.verifyNoMoreInteractions(addressService);
    }

    @Test
    public void testGetInfo_notExisting() throws Exception{
        Mockito.when(employeeService.findFetchedById(1L)).thenThrow(new EntityNotFoundException("Employee with such id does not exist"));

        mockMvc.perform(post("/employeeInfo{id}", 1L))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(employeeService, Mockito.times(1)).findFetchedById(1L);
        Mockito.verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testAddExistingEmployee() throws Exception{
        Mockito.when(employeeService.findByName("Testy")).thenReturn(testEmployee);

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "coast");

        mockMvc.perform(post("/newEmployee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonString(employeeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

        Mockito.verify(employeeService, Mockito.times(1)).findByName("Testy");
        Mockito.verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testAddEmployee_MalformedName() throws Exception{

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy1234", "1234", "city", "street", "coast");

        mockMvc.perform(post("/newEmployee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonString(employeeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotAcceptable());
    }

    public static String convertObjectToJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

}
