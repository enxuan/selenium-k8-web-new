package api_learning.testng;

import org.testng.annotations.*;

public class TestNGHook03 {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("before suite 3");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("\t---> before test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\t\t---> before class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\t\t\t---> before method");
    }

    @Test
    public void testSth() {
        System.out.println("\t\t\t\t ---> TEST");
    }

    @Test
    public void testSthElse() {
        System.out.println("\t\t\t\t ---> TEST STH ELSE");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("\t\t\t---> after method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("\t\t---> after class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("\t---> after test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("after suite");
    }
}
