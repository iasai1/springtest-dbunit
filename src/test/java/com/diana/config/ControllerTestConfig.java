package com.diana.config;

import com.diana.service.AddressService;
import com.diana.service.DepartmentService;
import com.diana.service.EmployeeService;
import org.h2.server.web.WebApp;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Import(WebConfig.class)
public class ControllerTestConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

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
