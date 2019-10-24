package com.diana.integration;

import com.diana.config.IntTestConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;


import static java.lang.Thread.sleep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntTestConfig.class})
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("intTestDB.xml")
public class IntTest {

    private static final int PORT = 8953;

    private static final Logger LOG = LoggerFactory.getLogger(IntTest.class);

    private WebDriver webDriver = null;


    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "C:\\DevTools\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().setSize(new Dimension(800, 600));

    }

    @After
    public void tearDown() throws Exception{
        webDriver.quit();
    }

    @Test
    public void testEmployeeRender(){
        webDriver.get("http://localhost:" + PORT +"/employees");

        Assert.assertNotSame(webDriver.findElements(By.className("btnInfo")).size(), 0);
        Assert.assertNotSame(webDriver.findElements(By.id("newEmp")).size(), 0);
        Assert.assertNotSame(webDriver.findElements(By.id("deps")).size(), 0);
    }



    @Test
    @ExpectedDatabase("initTestDB-addDep-expected.xml")
    public void test_goToNewDep_thenCreateDep_thenGoBack() throws  Exception{

        webDriver.get("http://localhost:"+ PORT +"/");

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/employees", webDriver.getCurrentUrl());

        webDriver.findElement(By.id("deps")).click();

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/departments", webDriver.getCurrentUrl());

        webDriver.findElement(By.id("newDep")).click();

        sleep(100);

        Assert.assertEquals("http://localhost:" + PORT +"/newDepartment", webDriver.getCurrentUrl());

        Assert.assertNotSame(webDriver.findElements(By.id("name")).size(), 0);
        webDriver.findElement(By.id("name")).sendKeys("city");

        Assert.assertNotSame(webDriver.findElements(By.id("add")).size(), 0);
        webDriver.findElement(By.id("add")).click();

        sleep(500);

        Assert.assertEquals("http://localhost:"+ PORT +"/employees", webDriver.getCurrentUrl());

    }


    @Test
    @ExpectedDatabase("initTestDB-addEmp-expected.xml")
    public void test_goToNewEmp_thenCreateEmp_thenGoBack() throws  Exception{
        webDriver.get("http://localhost:"+ PORT +"/employees");

        Integer n = webDriver.findElements(By.className("btnInfo")).size();

        Assert.assertNotSame(n, 0);

        webDriver.findElement(By.id("newEmp")).click();

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/newEmployee", webDriver.getCurrentUrl());

        Assert.assertNotSame(webDriver.findElements(By.id("name")).size(), 0);
        webDriver.findElement(By.id("name")).sendKeys("Toosty");

        Assert.assertNotSame(webDriver.findElements(By.id("phone")).size(), 0);
        webDriver.findElement(By.id("phone")).sendKeys("1234");

        Assert.assertNotSame(webDriver.findElements(By.id("city")).size(), 0);
        webDriver.findElement(By.id("city")).sendKeys("asd");

        Assert.assertNotSame(webDriver.findElements(By.id("street")).size(), 0);
        webDriver.findElement(By.id("street")).sendKeys("ggg");

        Assert.assertNotSame(webDriver.findElements(By.id("depName")).size(), 0);
        Select depName = new Select(webDriver.findElement(By.id("depName")));
        depName.selectByVisibleText("coast");

        Assert.assertNotSame(webDriver.findElements(By.id("add")).size(), 0);
        webDriver.findElement(By.id("add")).click();

        sleep(500);

        Assert.assertEquals("http://localhost:"+ PORT +"/employees", webDriver.getCurrentUrl());

        Assert.assertEquals(webDriver.findElements(By.className("btnInfo")).size(), n + 1);

        webDriver.findElements(By.className("btnInfo")).get(1).click();
        sleep(1000);
        Alert alert = webDriver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
    }

}
