package com.diana.dao;

import com.diana.config.DAOTestConfig;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.transaction.Transactional;

@ContextConfiguration(classes = DAOTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("daoTestDB.xml")
public class EmployeeDAOTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    private MockMvc mockMvc;


    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional
    @ExpectedDatabase("daoTestDB-addEmp-expected.xml")
    public void testAddEmployee(){

        Employee testEmployee = new Employee();
        testEmployee.setName("Toosty");
        testEmployee.setPhone("1234");

        Department d = departmentDAO.findByName("coast");
        d.addEmployee(testEmployee);
        departmentDAO.add(d);
        Address testAddress = new Address();
        testAddress.setCity("asd");
        testAddress.setStreet("ggg");
        testAddress.setEmployee(testEmployee);
        addressDAO.add(testAddress);

        Assert.assertEquals("1234", employeeDAO.findByName("Toosty").getPhone());
        Employee e = employeeDAO.findByName("Toosty");
        Assert.assertEquals("asd", addressDAO.findById(e.getId()).getCity());

    }

    @Test
    @Transactional
    @ExpectedDatabase("daoTestDB-delEmp-expected.xml")
    public void testDeleteEmployee(){

        Employee employee = employeeDAO.findByName("Testy");
        Long id = employee.getId();
        employeeDAO.delete(employee);

        Assert.assertNull(employeeDAO.findByName("Testy"));
        Assert.assertNull(addressDAO.findById(id));



    }

}
