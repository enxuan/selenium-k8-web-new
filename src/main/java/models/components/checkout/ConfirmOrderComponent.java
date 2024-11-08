package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ComponentCssSelector("#opc-confirm_order")
public class ConfirmOrderComponent extends Component {

    private static final By confirmBtnSel = By.cssSelector(".confirm-order-next-step-button");

    public ConfirmOrderComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void clickConfirmBtn() {
        WebElement confirmBtn = component.findElement(confirmBtnSel);
        confirmBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(confirmBtn));
    }
}
