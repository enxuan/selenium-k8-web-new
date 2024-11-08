package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormInteraction {

    public static void main(String[] args) {
        // Get a chrome session
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            //Navigate to the target page
            driver.get("https://the-internet.herokuapp.com/login");

            // Define selector values
            By usernameSel = By.id("username");
            By passwordSel = By.cssSelector("#password");
            By loginBtnSel = By.cssSelector("[type='submit']");


            // Find elements
            WebElement userNameElem = driver.findElement(usernameSel);
            WebElement passwordElem = driver.findElement(passwordSel);
            WebElement loginButtonElem = driver.findElement(loginBtnSel);

            // Get attribute value
            System.out.println(loginButtonElem.getAttribute("type"));
            System.out.println(loginButtonElem.getCssValue("background-color"));
            // Interaction
            userNameElem.sendKeys("tomsmith");
            passwordElem.sendKeys("SuperSecretPassword!");
            loginButtonElem.click();

            // Go back to previous page
            driver.navigate().back();

            // Refresh page
            driver.navigate().refresh();

            // Re-interact with the element again
            userNameElem.sendKeys("fgdfg");
            passwordElem.sendKeys("fgfg");
            loginButtonElem.click();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close browser session
        driver.quit();
    }
}
