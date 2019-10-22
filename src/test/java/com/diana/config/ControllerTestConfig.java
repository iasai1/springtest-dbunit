package com.diana.config;

import com.diana.service.AddressService;
import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import org.h2.server.web.WebApp;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebConfig.class)
public class ControllerTestConfig {

    @Bean
    public EmployeeService employeeService(){
        return Mockito.mock(EmployeeService.class);
    }

    @Bean
    public AddressService addressService(){
        return Mockito.mock(AddressService.class);
    }

    @Bean
    public DepartmentService departmentService(){
        return Mockito.mock(DepartmentService.class);
    }

}
