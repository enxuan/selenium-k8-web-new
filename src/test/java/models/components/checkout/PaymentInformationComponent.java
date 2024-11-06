package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector("#opc-payment_info")
public class PaymentInformationComponent extends Component {

    By continueBtnSel = By.cssSelector(".payment-info-next-step-button");

    public PaymentInformationComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void clickContinueBtn() {
        findElement(continueBtnSel).click();
    }
}
