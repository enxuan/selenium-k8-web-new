package pom_test;

import driver.DriverFactory;
import models.components.LoginFormComponent;
import models.pages.LoginPageMod02;
import models.pages.LoginPageMod03;
import org.openqa.selenium.WebDriver;

public class LoginTestMod03 {
    public static void main(String[] args) {
        // Get a chrome session
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            //Navigate to the target page
            driver.get("https://the-internet.herokuapp.com/login");

            LoginPageMod03 loginPage = new LoginPageMod03(driver);
            LoginFormComponent loginFormComp = loginPage.loginFormComp();
            // Interaction
            loginFormComp.inputUsername("tomsmith");
            loginFormComp.inputPassword("SuperSecretPassword!");
            loginFormComp.clickLoginBtn();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close browser session
        driver.quit();
    }
}
