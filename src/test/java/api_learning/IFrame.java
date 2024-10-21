package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.ui.SelectEx;
import url.Urls;

public class IFrame implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            // Navigate to target page
            driver.get(baseUrl.concat(iframeSlug));

            Thread.sleep(5000);
            By iFrameSel = By.cssSelector("[id$='ifr']");
            WebElement iFrameElem = driver.findElement(iFrameSel);

            // Switch to the iframe
            driver.switchTo().frame(iFrameElem);

            // Locate element inside iframe
            WebElement editorInputElem = driver.findElement(By.cssSelector("#tinymce p"));
//            editorInputElem.click();
//            editorInputElem.clear();
//            editorInElementNotInteractableExceptionputElem.sendKeys("this is the new text ...");
//            Thread.sleep(3000);

            // Switch back to parent frame
            driver.switchTo().defaultContent();
            driver.findElement(By.linkText("Elemental Selenium")).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
