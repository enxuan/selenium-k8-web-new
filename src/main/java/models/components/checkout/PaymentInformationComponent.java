package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import test_data.CreditCardType;

@ComponentCssSelector("#opc-payment_info")
public class PaymentInformationComponent extends Component {

    private static final By creditCardTypeSel = By.cssSelector("#CreditCardType");
    private static final By cardHolderNameSel = By.cssSelector("#CardholderName");
    private static final By cardNumberSel = By.cssSelector("#CardNumber");
    private static final By cardExpireMonthSel = By.cssSelector("#ExpireMonth");
    private static final By cardExpireYearSel = By.cssSelector("#ExpireYear");
    private static final By cardCodeSel = By.cssSelector("#CardCode");
    private static final By purchaseNumberSel = By.cssSelector("#PurchaseOrderNumber");
    private static final By codConfirmTextSel = By.xpath("//p[contains(text(), \"You will pay by COD\")]");
    private static final String checkConfirmText = "Mail Personal or Business Check, Cashier's Check or money order to:";
    private static final By checkConfirmSel = By.xpath("//p[contains(text(), \"" + checkConfirmText + "\")]");
    By continueBtnSel = By.cssSelector(".payment-info-next-step-button");

    public PaymentInformationComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void inputPurchaseNumber(String purchaseNum) {
        component.findElement(purchaseNumberSel).sendKeys(purchaseNum);
    }

    public void selectCardType(CreditCardType type) {
        if (type == null) {
            throw new IllegalArgumentException("[ERR] Credit card type can't be null");
        }
        Select select = new Select(component.findElement(creditCardTypeSel));
        switch (type) {
            case VISA:
                select.selectByVisibleText("Visa");
                break;
            case AMEX:
                select.selectByVisibleText("Amex");
                break;
            case MASTER_CARD:
                select.selectByVisibleText("Master card");
                break;
            case DISCOVER:
                select.selectByVisibleText("Discover");
        }
    }

    public void inputCardHolderName(String name) {
        component.findElement(cardHolderNameSel).sendKeys(name);
    }

    public void inputCardNumber(String number) {
        component.findElement(cardNumberSel).sendKeys(number);
    }

    public void selectExpireMonth(String month) {
        Select select = new Select(component.findElement(cardExpireMonthSel));
        select.selectByVisibleText(month);
    }

    public void selectExpireYear(String year) {
        Select select = new Select(component.findElement(cardExpireYearSel));
        select.selectByVisibleText(year);
    }

    public void inputCardCode(String code) {
        component.findElement(cardCodeSel).sendKeys(code);
    }

    public void confirmCODText() {
        if (component.findElements(codConfirmTextSel).isEmpty()) {
            Assert.fail("[ERR] the COD text confirmation \"You will pay by COD\" should be showed!");
        }
    }

    public void confirmCheckText() {
        if (component.findElements(checkConfirmSel).isEmpty()) {
            Assert.fail("[ERR] check confirm text shouldn't be empty");
        }
    }

    public void clickContinueBtn() {
        WebElement continueBtn = component.findElement(continueBtnSel);
        continueBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtn));
    }
}
