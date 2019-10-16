package com.diana.config;

import com.diana.service.*;
import org.h2.server.web.WebApp;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DAOTestConfig.class})
public class IntTestConfig {

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
