package test.global.footer;

import driver.DriverFactory;
import models.components.global.footer.CustomerServiceColumnComponent;
import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.InfomationColumnComponent;
import models.pages.BasePage;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import support.verification.Verifier;
import test_flows.global.FooterTestFlow;
import url.Urls;

public class FooterTest {

//    @Test
    public void testFooterHomePage() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(Urls.demeBaseUrl);
        try {
            HomePage homePagePage = new HomePage(driver);
            InfomationColumnComponent infomationColumnComp = homePagePage.footerComp().infomationColumnComp();
            testFooterColumn(infomationColumnComp);

            CustomerServiceColumnComponent customerServiceColumnComp = homePagePage.footerComp().customerServiceColumnComp();
            testFooterColumn(customerServiceColumnComp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test
    public void testFooterCategoryPage() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(Urls.demeBaseUrl);
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyFooterComponent();
    }

    @Test
    public void testFooterRegisterPage() {
        String actualResult = "Teo";
        String expectedResult = "Ti";

//        Verifier.verifyEquals(actualResult, expectedResult);

        // Hard assertion
        Assert.assertEquals(actualResult, expectedResult, ".....");
        Assert.assertTrue(actualResult.equals(expectedResult));
        Assert.fail();
        Assert.fail(".....");
    }

    @Test
    public void testFooterLoginPage() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(1, 2);
        softAssert.assertEquals(true, true);
        softAssert.assertEquals(4, 5);
        softAssert.assertAll();
        System.out.println("end command");
    }

    private static void testFooterColumn(FooterColumnComponent footerColumnComponent) {
        System.out.println(footerColumnComponent.headerElem().getText());
        footerColumnComponent.linksElem().forEach(link -> {
            System.out.println(link.getText());
            System.out.println(link.getAttribute("href"));
        });
    }
}
