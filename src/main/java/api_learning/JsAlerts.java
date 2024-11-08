package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import url.Urls;

import java.time.Duration;
import java.util.List;

public class JsAlerts implements Urls {

    private final static By jsAlertSel = By.cssSelector("[onclick=\"jsAlert()\"]");
    private final static By jsAlertConfirmSel = By.cssSelector("[onclick=\"jsConfirm()\"]");
    private final static By jsAlertPrompSel = By.cssSelector("[onclick=\"jsPrompt()\"]");
    private final static By resultSel = By.cssSelector("#result");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            // Navigate to target page
            driver.get(baseUrl.concat(alertsSlug));
           handleAlert(driver, jsAlertSel, true);
            System.out.println("Result" + driver.findElement(resultSel).getText());


            // JS_CONFIRM
             handleAlert(driver, jsAlertConfirmSel, false);
            System.out.println("Result" + driver.findElement(resultSel).getText());

            // JS_PROMPT
            handleAlert(driver, jsAlertPrompSel, "hello, good day, right?");
            System.out.println("Result" + driver.findElement(resultSel).getText());

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    public static void handleAlert(WebDriver driver, By triggerAlertSel, boolean isAcepting) {
        Alert alert = getAlert(driver, triggerAlertSel);

        System.out.println("Alert content" + alert.getText());
        if (isAcepting) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    public static void handleAlert(WebDriver driver, By alertTriggerSel, String contentToEnter) {
        Alert alert = getAlert(driver, alertTriggerSel);
        System.out.println("Alert content" + alert.getText());
        alert.sendKeys(contentToEnter);
        alert.accept();
    }

    private static Alert getAlert(WebDriver driver, By alertTriggerSel) {
        WebElement triggerJsAlertBtnElem;
        Alert alert;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // JS_ALERT
        triggerJsAlertBtnElem = driver.findElement(alertTriggerSel);
        triggerJsAlertBtnElem.click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        return alert;
    }
}
