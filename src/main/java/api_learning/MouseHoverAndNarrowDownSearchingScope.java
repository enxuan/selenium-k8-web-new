package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import url.Urls;

import java.util.List;

public class MouseHoverAndNarrowDownSearchingScope implements Urls {

    private final static By figureSel = By.className("figure");
    private final static By profileNameSel = By.cssSelector(".figcaption h5");
    private final static By profileLinkSel = By.cssSelector(".figcaption a");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            // Navigate to target page
            driver.get(baseUrl.concat(hoverSlug));

            // Target parent elements
            List<WebElement> figureElems = driver.findElements(figureSel);
            if (figureElems.isEmpty()) {
                throw new RuntimeException("There is no profile image display");
            }

            // Define Actions object
            Actions actions = new Actions(driver);
            for (WebElement figureElem : figureElems) {
                WebElement profileNameElem = figureElem.findElement(profileNameSel);
                WebElement profileLinkElem = figureElem.findElement(profileLinkSel);

                // Before mouse hover
                System.out.println(profileNameElem.getText() + " is profile Name displaying: " + profileNameElem.isDisplayed());
                System.out.println(profileLinkElem.getText() + " Is profile link displaying: " + profileLinkElem.isDisplayed());

                // After mouse hover
                actions.moveToElement(figureElem).perform();
                System.out.println(profileNameElem.getText() + " is profile Name displaying: " + profileNameElem.isDisplayed());
                System.out.println(profileLinkElem.getText() + " Is profile link displaying: " + profileLinkElem.isDisplayed());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
