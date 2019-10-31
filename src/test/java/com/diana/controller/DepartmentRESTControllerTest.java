package com.diana.controller;


import com.diana.TestUtil;
import com.diana.config.ControllerTestConfig;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ControllerTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class DepartmentRESTControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

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
    public void testAddExistingDep() throws Exception{

        Department d = new Department("coast");

        Mockito.when(departmentService.findByName("coast")).thenReturn(testDepartment);

        mockMvc.perform(post("/newDepartment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonString(d)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
        Mockito.verify(departmentService, Mockito.times(1)).findByName("coast");
        Mockito.verifyNoMoreInteractions(departmentService);
    }


}
