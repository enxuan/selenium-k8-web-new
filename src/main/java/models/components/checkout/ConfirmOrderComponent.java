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

    public ConfirmBillingInfo getConfirmBillingInfo() {
        return findComponent(ConfirmBillingInfo.class, driver);
    }

    public void clickConfirmBtn() {
        WebElement confirmBtn = component.findElement(confirmBtnSel);
        confirmBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(confirmBtn));
    }

    @ComponentCssSelector("#opc-confirm_order .billing-info")
    public static class ConfirmBillingInfo extends Component {

        private final static By nameSel = By.cssSelector(".name");
        private final static By emailSel = By.cssSelector(".email");
        private final static By phoneSel = By.cssSelector(".phone");
        private final static By add1Sel = By.cssSelector(".address1");
        private final static By cityStateSel = By.cssSelector(".city-state-zip");
        private final static By countrySel = By.cssSelector(".country");
        private final static By paymentMethodSel = By.cssSelector(".payment-method");
        public ConfirmBillingInfo(WebDriver driver, WebElement component) {
            super(driver, component);
        }

        public String getBillingName() {
            return component.findElement(nameSel).getText().trim();
        }

        public String getBillingEmail() {
            return component.findElement(emailSel).getText().trim();
        }

        public String getBillingPhone() {
            return component.findElement(phoneSel).getText().trim();
        }

        public String getBillingAdd1() {
            return component.findElement(add1Sel).getText().trim();
        }

        public String getBillingCityState() {
            return component.findElement(cityStateSel).getText().trim();
        }

        public String getBillingCountry() {
            return component.findElement(countrySel).getText().trim();
        }

        public String getBillingPaymentMethod() {
            return component.findElement(paymentMethodSel).getText().trim();
        }
    }
}
