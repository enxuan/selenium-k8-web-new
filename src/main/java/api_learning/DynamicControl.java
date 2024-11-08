package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ui.WaitForElementEnabled;
import url.Urls;

import java.time.Duration;

public class DynamicControl implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            // Navigate to target page
            driver.get(baseUrl.concat(dynamicControlSlug));

            // Define parent locator | 2 forms | checkbox form + input form
            By checkboxFormSel = By.id("checkbox-example");
            By inputFormSel = By.id("input-example");

            // Checkbox form interaction
            WebElement checkboxFromElem = driver.findElement(checkboxFormSel);
            // debug purpose only
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            boolean isLocal = System.getenv("isLocal") != null && System.getenv("isLocal").equals("true");
            if (isLocal) {
                javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: blue; order: 4px solid red;');", checkboxFromElem);
            }
            Thread.sleep(2000);

            WebElement checkboxElem = checkboxFromElem.findElement(By.tagName("input"));

            if (!checkboxElem.isSelected()) {
                checkboxElem.click();
            }
            Thread.sleep(1000);


            // Input form interaction
            WebElement inputFromElem = driver.findElement(inputFormSel);
            WebElement inputFieldElem = inputFromElem.findElement(By.tagName("input"));
            WebElement inputBtnElem = inputFromElem.findElement(By.tagName("button"));

            if (!inputFieldElem.isEnabled()) {
                inputBtnElem.click();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
//            wait.until(new WaitForElementEnabled(By.cssSelector("#input-example input")));

            inputFieldElem.sendKeys("Hello, have a good day!");
            Thread.sleep(1000);

            // Debug purpose only
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
