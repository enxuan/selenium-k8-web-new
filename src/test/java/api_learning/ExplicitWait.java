package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ui.SelectEx;
import support.ui.WaitMoreThanOneTab;
import support.ui.WaitUntilSth;
import url.Urls;

import java.time.Duration;

public class ExplicitWait {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            // Navigate to target page
            driver.get(Urls.baseUrl.concat(Urls.loginSlug));

            By taolaoSel = By.cssSelector("#linhtinh");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(taolaoSel));;
//            wait.until(ExpectedConditions.visibilityOf(driver.findElement(taolaoSel)));;

//            driver.findElement(By.cssSelector("linhtinh"));

            // Windows/tabs --> waitUntil tabs > 1
            wait.until(new WaitMoreThanOneTab());

//            wait.until(new WaitUntilSth(By.cssSelector("#....")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
