package pom_test;

import driver.DriverFactory;
import models.pages.LoginPageMod01;
import models.pages.LoginPageMod02;
import org.openqa.selenium.WebDriver;

public class LoginTestMod02 {
    public static void main(String[] args) {
        // Get a chrome session
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            //Navigate to the target page
            driver.get("https://the-internet.herokuapp.com/login");

            LoginPageMod02 loginPage = new LoginPageMod02(driver);
            // Interaction
            loginPage.inputUsername("tomsmith");
            loginPage.inputPassword("SuperSecretPassword!");
            loginPage.clickLoginBtn();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close browser session
        driver.quit();
    }
}
