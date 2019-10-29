package com.diana.integration;

import com.diana.config.SNDTestConfig;
import com.diana.model.Employee;
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
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SNDTestConfig.class})
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ServiceAndDAOIntegration {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp() throws Exception{
        resetAutoIncrement(applicationContext, "employees", "departments");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DatabaseSetup("SNDTestDB.xml")
    public void test_FindById(){

        Employee e = employeeService.findById(2L);
        Assert.assertEquals("1234", e.getPhone());
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
    @ExpectedDatabase("SNDTestDB-AddEmp-expected.xml")
    public void testCreateEmployee(){

        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "coast");
        employeeService.create(employeeDTO);
    }

    private void resetAutoIncrement(ApplicationContext applicationContext, String... tables) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String sqlTemplate = applicationContext.getBean(Environment.class).getRequiredProperty("test.reset.sql.template");
        try(Connection connection = dataSource.getConnection()){
            for(String table: tables){
                try(Statement statement = connection.createStatement()){
                    String resetSql = String.format(sqlTemplate, table);
                    statement.execute(resetSql);
                }
            }
        }
    }

}
