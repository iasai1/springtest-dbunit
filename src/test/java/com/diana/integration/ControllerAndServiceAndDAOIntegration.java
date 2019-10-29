package com.diana.integration;

import com.diana.config.PersistenceConfig;
import com.diana.model.Employee;
import com.diana.service.EmployeeService;
import com.diana.util.dto.EmployeeDTO;
import com.diana.util.error.EntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ControllerAndServiceAndDAOIntegration {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DatabaseSetup("CNSNDTestDB.xml")
    public void testGetEmployees() throws Exception{

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
    }

    @Test
    public void testRootRedirect() throws Exception{
        mockMvc.perform(get("/")).andExpect(redirectedUrl("/employees")).andExpect(status().isFound());
    }

    @Test
    @DatabaseSetup("CNSNDTestDB.xml")
    @ExpectedDatabase("CNSNDTestDB-addEmp-expected.xml")
    public void testCreateEmp() throws Exception{
        EmployeeDTO employeeDTO = new EmployeeDTO("Toosty", "1234", "city", "tstreet", "coast");

        ResultActions result = mockMvc.perform(post("/newEmployee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonString(employeeDTO)))
                .andDo(MockMvcResultHandlers.print());
        result.andExpect(status().isOk());

        ArgumentCaptor<EmployeeDTO> dtoCaptor = ArgumentCaptor.forClass(EmployeeDTO.class);
        EmployeeDTO eDTO = dtoCaptor.getValue();
        Assert.assertEquals("1234", eDTO.getPhone());

    }

    @Test
    @DatabaseSetup("CNSNDTestDB.xml")
    public void testGetInfo() throws Exception{

        mockMvc.perform(post("/employeeInfo{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.city").value("city"));
    }

    @Test
    @DatabaseSetup("CNSNDTestDB.xml")
    public void testGetInfo_notExisting() throws Exception{

        mockMvc.perform(post("/employeeInfo{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DatabaseSetup("CNSNDTestDB.xml")
    public void testAddExistingEmployee() throws Exception{

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "123", "city", "street", "coast");

        mockMvc.perform(post("/newEmployee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonString(employeeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

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
