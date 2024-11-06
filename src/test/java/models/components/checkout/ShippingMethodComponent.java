package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ComponentCssSelector("#opc-shipping_method")
public class ShippingMethodComponent extends Component {

    private static final By shippingMethodSel = By.id("shippingoption");
    private static final By continueBtnSel = By.cssSelector(".shipping-method-next-step-button");


    public ShippingMethodComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectShippingMethod(String shippingMethod) {
        component.findElement(
                By.xpath("//label[contains(text(), \"" + shippingMethod + "\")]"))
                .click();
    }

    public void clickContinueBtn() {
        WebElement continueBtn = component.findElement(continueBtnSel);
        continueBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtn));
    }
}
