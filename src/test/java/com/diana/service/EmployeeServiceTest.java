package com.diana.service;


import com.diana.config.ServiceTestConfig;
import com.diana.dao.AddressDAO;
import com.diana.dao.DepartmentDAO;
import com.diana.dao.EmployeeDAO;
import com.diana.model.Address;
import com.diana.model.Department;
import com.diana.model.Employee;
import com.diana.util.dto.EmployeeDTO;
import org.junit.Assert;
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
import java.util.List;

@ContextConfiguration(classes = ServiceTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EmployeeServiceTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private AddressDAO addressDAO;

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
    public void test_FindById(){
        Mockito.when(employeeDAO.findById(2L)).thenReturn(testEmployee);

        Employee e = employeeService.findById(2L);
        Assert.assertEquals("1234", e.getPhone());

    }

    @Test
    public void test_findALL(){
        Mockito.when(employeeDAO.getAll()).thenReturn(Arrays.asList(testEmployee));

        List<Employee> e = employeeDAO.getAll();
        Assert.assertEquals(1, e.size());
        Assert.assertEquals("Testy", e.get(0).getName());
    }

    @Test
    public void testCreateEmployee(){
        EmployeeDTO employeeDTO = new EmployeeDTO("Testy", "1234", "city", "street", "coast");

        Mockito.when(departmentDAO.findByName("coast")).thenReturn(testDepartment);

        employeeService.create(employeeDTO);

        Mockito.verify(departmentDAO, Mockito.times(1)).add(testDepartment);
        Mockito.verify(addressDAO, Mockito.times(1)).add(Mockito.any(Address.class));

    }

}
