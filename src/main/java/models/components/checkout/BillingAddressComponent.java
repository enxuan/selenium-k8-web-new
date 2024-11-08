package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.security.SecureRandom;
import java.util.List;

@ComponentCssSelector("#opc-billing")
public class BillingAddressComponent extends Component {

    private final static By billingAddressSelectSel = By.id("billing-address-select");
    private final static By firstNameSel = By.id("BillingNewAddress_FirstName");
    private final static By lastNameSel = By.id("BillingNewAddress_LastName");
    private final static By emailSel = By.id("BillingNewAddress_Email");
    private final static By selectCountryDropdownSel = By.id("BillingNewAddress_CountryId");
    private final static By selectStateDropdownSel = By.id("BillingNewAddress_StateProvinceId");
    private final static By loadingStateProgressBarSel = By.id("states-loading-progress");
    private final static By citySel = By.id("BillingNewAddress_City");
    private final static By add1Sel = By.id("BillingNewAddress_Address1");
    private final static By zipCodeSel = By.id("BillingNewAddress_ZipPostalCode");
    private final static By phoneNumberSel = By.id("BillingNewAddress_PhoneNumber");
    private final static By continueBtnSel = By.cssSelector(".new-address-next-step-button");

    public BillingAddressComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectInputNewAddress() {
        List<WebElement> selectNewAddElem = component.findElements(billingAddressSelectSel);
        if (!selectNewAddElem.isEmpty()) {
            Select select = new Select(selectNewAddElem.get(0));
            select.selectByVisibleText("New Address");
        }
    }

    public void inputFirstName(String firstName) {
        component.findElement(firstNameSel).sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        component.findElement(lastNameSel).sendKeys(lastName);
    }

    public void inputEmail(String email) {
       component.findElement(emailSel).sendKeys(email);
    }

    public void selectCountry(String country) {
        WebElement selectElem = component.findElement(selectCountryDropdownSel);
        Select select = new Select(selectElem);
        select.selectByVisibleText(country);
        wait.until(ExpectedConditions.invisibilityOf(component.findElement(loadingStateProgressBarSel)));
    }

    public void selectState(String state) {
        WebElement selectElem = component.findElement(selectStateDropdownSel);
        Select select = new Select(selectElem);
        select.selectByVisibleText(state);
    }

    public void inputCity(String city) {
        component.findElement(citySel).sendKeys(city);
    }

    public void inputAdd1(String add) {
        component.findElement(add1Sel).sendKeys(add);
    }

    public void inputZipCode(String zipCode) {
        component.findElement(zipCodeSel).sendKeys(zipCode);
    }

    public void inputPhoneNumber(String phoneNumber) {
        component.findElement(phoneNumberSel).sendKeys(phoneNumber);
    }

    public void clickContinueBtn() {
        WebElement continueBtn = component.findElement(continueBtnSel);
        continueBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtn));
    }
}
