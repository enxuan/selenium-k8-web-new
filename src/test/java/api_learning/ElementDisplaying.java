package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementDisplaying {

    public static void main(String[] args) {
        // Get a chrome session
        WebDriver driver = DriverFactory.getChromeDriver();


        //Navigate to the target page
        driver.get("https://the-internet.herokuapp.com/login");

        // Define selector values
        By usernameSel = By.id("username_ssfgfdg");

        // Find Element
        List<WebElement> elemList = driver.findElements(usernameSel);
        if (!elemList.isEmpty()) {
//            Assert.fail("reason");
        }

        // Close browser session
        driver.quit();
    }
}
