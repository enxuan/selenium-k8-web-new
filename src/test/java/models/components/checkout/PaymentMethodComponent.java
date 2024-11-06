package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector("#opc-payment_method")
public class PaymentMethodComponent extends Component {

    By continueBtnSel = By.cssSelector(".payment-method-next-step-button");

    public PaymentMethodComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectPaymentByCash() {
        findElement(By.cssSelector("[value=\"Payments.CashOnDelivery\"]")).click();
    }

    public void clickContineBtn() {
        findElement(continueBtnSel).click();
    }
}
