package com.diana.inner_integration;

import com.diana.config.SNDTestConfig;
import com.diana.model.Address;
import com.diana.model.Employee;
import com.diana.service.AddressService;
import com.diana.service.EmployeeService;
import com.diana.util.dto.EmployeeDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SNDTestConfig.class})
@WebAppConfiguration
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ServiceAndDAOIntegration {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    @Before
    public void setUp() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DatabaseSetup("SNDTestDB.xml")
    public void test_FindById(){

        Employee e = employeeService.findById(2L);
        Assert.assertEquals("123", e.getPhone());
    }

    @Test
    @DatabaseSetup("SNDTestDB.xml")
    public void test_findALL(){

        List<Employee> e = employeeService.getAll();
        Assert.assertEquals(1, e.size());
        Assert.assertEquals("Testy", e.get(0).getName());
    }

    @Test
    @DatabaseSetup("SNDTestDB.xml")
    public void testCreateEmployee(){

        EmployeeDTO employeeDTO = new EmployeeDTO("Toosty", "1234", "city", "tstreet", "coast");
        employeeService.create(employeeDTO);

        Employee e = employeeService.findByName("Toosty");

        Assert.assertEquals(addressService.findById(e.getId()).getStreet(), "tstreet");
    }


}
