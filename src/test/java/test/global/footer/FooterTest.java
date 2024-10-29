package test.global.footer;

import driver.DriverFactory;
import models.components.global.footer.CustomerServiceColumnComponent;
import models.components.global.footer.FooterColumnComponent;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import test_flows.global.FooterTestFlow;
import url.Urls;

public class FooterTest {

    @Test
    public void testFooterHomePage() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(Urls.demeBaseUrl);
        try {
            HomePage homePagePage = new HomePage(driver);
//            InfomationColumnComponent infomationColumnComp = homePagePage.footerComp().infomationColumnComp();
//            testFooterColumn(infomationColumnComp);

            CustomerServiceColumnComponent customerServiceColumnComp = homePagePage.footerComp().customerServiceColumnComp();
            testFooterColumn(customerServiceColumnComp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    @Test
    public void testFooterCategoryPage() {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            driver.get(Urls.demeBaseUrl);
            FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
            footerTestFlow.verifyProductCatFooterComponent();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    @Test
    public void testFooterRegisterPage() {
    }

    @Test
    public void testFooterLoginPage() {
    }

    private static void testFooterColumn(FooterColumnComponent footerColumnComponent) {
        System.out.println(footerColumnComponent.headerElem().getText());
        footerColumnComponent.linksElem().forEach(link -> {
            System.out.println(link.getText());
            System.out.println(link.getAttribute("href"));
        });
    }
}
