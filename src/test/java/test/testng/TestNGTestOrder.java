package test.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNGTestOrder {

    @Test(priority = 2)
    public void test01() {
        System.out.println("test 01");
//        Assert.fail();
    }

    @Test(priority = 1, dependsOnMethods = {"test01"})
    public void test02() {
        System.out.println("test 02");
    }
}
