package com.diana.util;

import com.diana.config.DAOTestConfig;
import com.diana.model.Department;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Assert;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOTestConfig.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("utilTestDB.xml")
public class HibernateCacheTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testSecondLvlCache(){
        Session session = sessionFactory.openSession();
        Department department = session.createQuery("from Department where id = 1L", Department.class).stream().findAny().orElse(null);

        Assert.assertTrue(sessionFactory.getCache().containsEntity(Department.class, department.getId()));
        session.close();

        Session _session = sessionFactory.openSession();
        Department department1 = (Department) _session.load(Department.class, 1L);
        _session.close();

        Statistics statistics = sessionFactory.getStatistics();
        Assert.assertEquals(1L, statistics.getQueryExecutionCount());

    }

}



