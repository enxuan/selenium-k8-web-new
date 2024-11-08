package pom_test;

import driver.DriverFactory;
import models.pages.LoginPageMod01;
import org.openqa.selenium.WebDriver;

public class LoginTestMod01 {
    public static void main(String[] args) {
        // Get a chrome session
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            //Navigate to the target page
            driver.get("https://the-internet.herokuapp.com/login");

            LoginPageMod01 loginPage = new LoginPageMod01(driver);
            // Interaction
            loginPage.username().sendKeys("tomsmith");
            loginPage.password().sendKeys("SuperSecretPassword!");
            loginPage.loginBtn().click();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close browser session
        driver.quit();
    }
}
