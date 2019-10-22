package com.diana.config;

import com.diana.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(PersistenceConfig.class)
public class DAOTestConfig {

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
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.diana.model");
        sessionFactoryBean.setHibernateProperties((new Properties() {{
            this.put("hibernate.show_sql", true);
            this.put("hibernate.format_sql", true);
            this.put("hibernate.cache.use_second_level_cache", true);
            this.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
            this.put("hibernate.generate_statistics", true);
            this.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        }}));
        return sessionFactoryBean;
    }

    @Bean
    public AddressDAO addressDAO(){ return new AddressDAOImpl(); }

    @Bean
    public EmployeeDAO employeeDAO(){ return new EmployeeDAOImpl(); }

    @Bean
    public DepartmentDAO departmentDAO(){ return new DepartmentDAOImpl();}
}
