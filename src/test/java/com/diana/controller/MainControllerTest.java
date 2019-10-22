package com.diana.controller;

import com.diana.config.ControllerTestConfig;
import com.diana.model.Employee;
import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = ControllerTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EmployeeService employeeService;

    private MockMvc mockMvc;
    private Employee testEmployee;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        testEmployee = new Employee();
        testEmployee.setName("Testy");
        testEmployee.setPhone("1234");
    }

    @Test
    public void testGetEmployees() throws Exception{

        Mockito.when(employeeService.getAll()).thenReturn(Arrays.asList(testEmployee));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/employees.jsp"))
                .andExpect(model().attribute("employees", hasSize(1)))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("name", is("Testy")),
                                hasProperty("phone", is("1234"))
                        )
                )));

        Mockito.verify(employeeService, Mockito.times(1)).getAll();

    }

    @Test
    public void testRootRedirect() throws Exception{
        mockMvc.perform(get("/")).andExpect(redirectedUrl("/employees")).andExpect(status().isFound());
    }

}
