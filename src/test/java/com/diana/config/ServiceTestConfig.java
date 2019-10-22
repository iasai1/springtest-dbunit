package com.diana.config;

import com.diana.dao.*;
import com.diana.service.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfig {

    @Bean
    public AddressDAO addressDAO(){ return Mockito.mock(AddressDAO.class); }

    @Bean
    public EmployeeDAO employeeDAO(){ return Mockito.mock(EmployeeDAO.class); }

    @Bean
    public DepartmentDAO departmentDAO(){ return Mockito.mock(DepartmentDAO.class); }

    @Bean
    public AddressService addressService(){
        return new AddressServiceImpl();
    }

    @Bean
    public DepartmentService departmentService(){
        return new DepartmentServiceImpl();
    }

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeServiceImpl();
    }


}
