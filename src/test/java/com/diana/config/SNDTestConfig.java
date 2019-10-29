package com.diana.config;

import com.diana.dao.*;
import com.diana.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfig.class})
public class SNDTestConfig {

    @Bean
    public AddressDAO addressDAO(){ return new AddressDAOImpl(); }

    @Bean
    public EmployeeDAO employeeDAO(){ return new EmployeeDAOImpl(); }

    @Bean
    public DepartmentDAO departmentDAO(){ return new DepartmentDAOImpl();}

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
