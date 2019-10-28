package com.diana.config;
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile("test")
public class IntTestConfig {

    static final Logger LOG = LoggerFactory.getLogger(IntTestConfig.class);

    @Profile("test")
    @Bean
    public DataSource dataSource(){
        LOG.warn("Initializing test datasource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Profile("test")
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

    @Profile("test")
    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.diana.model");
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    @Profile("test")
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return  transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public Validator localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();

    }

    /*

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
 */


