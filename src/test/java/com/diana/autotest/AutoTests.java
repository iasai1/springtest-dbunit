package com.diana.autotest;


import org.junit.Assert;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.lang.Thread.sleep;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutoTests {

    private static final int PORT = 8953;

    private static final Logger LOG = LoggerFactory.getLogger(AutoTests.class);

    private WebDriver webDriver = null;

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\DevTools\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().setSize(new Dimension(800, 600));
    }

    @AfterEach
    public void tearDown() throws Exception{
        webDriver.quit();
    }

    @Test
    @Order(1)
    public void testEmployeeRender(){
        webDriver.get("http://localhost:" + PORT +"/employees");

        Assert.assertNotSame(webDriver.findElements(By.id("newEmp")).size(), 0);
        Assert.assertNotSame(webDriver.findElements(By.id("deps")).size(), 0);
    }

    @Test
    @Order(2)
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
        webDriver.findElement(By.id("name")).sendKeys("selenium");

        Assert.assertNotSame(webDriver.findElements(By.id("add")).size(), 0);
        webDriver.findElement(By.id("add")).click();

        sleep(500);

        Assert.assertEquals("http://localhost:"+ PORT +"/departments", webDriver.getCurrentUrl());

    }

    @Test
    @Order(3)
    public void test_goToNewEmp_thenCreateEmp_thenGoBack() throws  Exception{
        webDriver.get("http://localhost:"+ PORT +"/employees");

        Integer n = webDriver.findElements(By.className("btnInfo")).size();

        Assert.assertNotSame(n, 0);

        webDriver.findElement(By.id("newEmp")).click();

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/newEmployee", webDriver.getCurrentUrl());

        Assert.assertNotSame(webDriver.findElements(By.id("name")).size(), 0);
        webDriver.findElement(By.id("name")).sendKeys("Celenium");

        Assert.assertNotSame(webDriver.findElements(By.id("phone")).size(), 0);
        webDriver.findElement(By.id("phone")).sendKeys("12345180");

        Assert.assertNotSame(webDriver.findElements(By.id("city")).size(), 0);
        webDriver.findElement(By.id("city")).sendKeys("City");

        Assert.assertNotSame(webDriver.findElements(By.id("street")).size(), 0);
        webDriver.findElement(By.id("street")).sendKeys("S. St.");

        Assert.assertNotSame(webDriver.findElements(By.id("depName")).size(), 0);
        Select depName = new Select(webDriver.findElement(By.id("depName")));
        depName.selectByVisibleText("selenium");

        Assert.assertNotSame(webDriver.findElements(By.id("add")).size(), 0);
        webDriver.findElement(By.id("add")).click();

        sleep(500);

        Assert.assertEquals("http://localhost:"+ PORT +"/employees", webDriver.getCurrentUrl());

        Assert.assertEquals(webDriver.findElements(By.className("btnInfo")).size(), n + 1);

        webDriver.findElements(By.className("btnInfo")).get(1).click();
        sleep(1000);
        Alert alert = webDriver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("S. St."));
        Assert.assertTrue(alert.getText().contains("selenium"));
        alert.accept();
    }

    @Test
    public void testRenameDep_expectAlreadyExists() throws Exception {
        webDriver.get("http://localhost:"+ PORT +"/");

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/employees", webDriver.getCurrentUrl());

        webDriver.findElement(By.id("deps")).click();

        sleep(100);

        Assert.assertEquals("http://localhost:"+ PORT +"/departments", webDriver.getCurrentUrl());

        webDriver.findElements(By.className("btnRename")).get(0).click();

        webDriver.switchTo().activeElement();

        sleep(100);

        webDriver.findElement(By.id("txtInput")).sendKeys("selenium");
        webDriver.findElement(By.id("save")).click();

        sleep(100);

        Alert alert = webDriver.switchTo().alert();
        Assert.assertEquals("Department with such name already exists", alert.getText());

    }

}
