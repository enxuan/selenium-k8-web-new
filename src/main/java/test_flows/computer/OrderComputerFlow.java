package test_flows.computer;

import models.components.cart.CartItemRowComponent;
import models.components.cart.TotalComponent;
import models.components.checkout.*;
import models.components.order.ComputerEssentialComponent;
import models.pages.CheckOutPage;
import models.pages.CheckoutOptionPage;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.CreditCardType;
import test_data.DataObjectBuilder;
import test_data.PaymentMethod;
import test_data.computer.ComputerData;
import test_data.user.UserDataObject;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static models.components.checkout.ConfirmOrderComponent.ConfirmBillingInfo;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private ComputerData computerData;
    private final int quantity;
    private double totalItemPrice;
    private UserDataObject defaultCheckoutUser;
    private PaymentMethod paymentMethod;
    private CreditCardType creditCardType;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComp, ComputerData computerData) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComp;
        this.computerData = computerData;
        this.quantity = 1;
    }

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComp, ComputerData computerData, int quantity) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComp;
        this.computerData = computerData;
        this.quantity = quantity;
    }

    public void buildComputerSpecAndAddToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        T computerEssentialComp = computerItemDetailsPage.computerComp(computerEssentialComponent);

        // Unselect all default options
        computerEssentialComp.unselectDefaultOptions();

        String processorFullStr = computerEssentialComp.selectProcessorType(computerData.getProcessorType());
        double processorAdditionalPrice = extractAdditionalPrice(processorFullStr);
        String ramFullStr = computerEssentialComp.selectRAMType(computerData.getRam());
        double ramAdditionalPrice = extractAdditionalPrice(ramFullStr);
        String hddFullStr = computerEssentialComp.selectHDD(computerData.getHdd());
        double additionalHddPrice = extractAdditionalPrice(hddFullStr);

        double additionalOsPrice = 0;
        if (computerData.getOs() != null) {
            String fullOsStr = computerEssentialComp.selectOs(computerData.getOs());
            additionalOsPrice = extractAdditionalPrice(fullOsStr);
        }

        String softwareFullStr = computerEssentialComp.selectSoftware(computerData.getSoftware());
        double additionalSoftwarePrice = extractAdditionalPrice(softwareFullStr);

        // Calculate item price and add to card
        double basePrice = computerEssentialComp.productPrice();
        double allAdditionalPrices = processorAdditionalPrice + ramAdditionalPrice +
                additionalHddPrice + additionalOsPrice + additionalSoftwarePrice;
        totalItemPrice = (basePrice + allAdditionalPrices) * quantity;

        // Click add to cart button
        computerEssentialComp.clickAddtoCardBtn();
        computerEssentialComp.waitUntilItemAddedToCart();
        computerItemDetailsPage.headerComp().clickOnShoppingCartLink();
    }

    private double extractAdditionalPrice(String itemString) {
        double price = 0;
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(itemString);
        if (matcher.find()) {
            price = Double.parseDouble(matcher.group(1).replaceAll("[-+]", ""));

        }
        return price;
    }


    public void verifyShoppingCartPage() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        List<CartItemRowComponent> cartItemRowComps = shoppingCartPage.cartItemRowComponents();
        if (cartItemRowComps.isEmpty()) {
            Assert.fail("[ERR] there is no item displayed in shopping cart");
        }

        double currentSubtotal = 0;
        double currentTotalUnitPrices = 0;
        for (CartItemRowComponent cartItemRowComp : cartItemRowComps) {
            currentSubtotal = currentSubtotal + cartItemRowComp.subTotal();
            currentTotalUnitPrices = currentTotalUnitPrices + (cartItemRowComp.unitPrice() * cartItemRowComp.quantity());
        }

        Assert.assertEquals(currentSubtotal, currentTotalUnitPrices,
                "[ERR] Shopping cart's subtotal is incorrect");

        TotalComponent totalComp = shoppingCartPage.totalComponent();
        Map<String, Double> priceCategories = totalComp.priceCategories();
        double checkoutSubtotal = 0;
        double checkoutOtherFeesTotal = 0;
        double checkoutTotal = 0;
        for (String priceType : priceCategories.keySet()) {
            double priceValue = priceCategories.get(priceType);
            if (priceType.startsWith("Sub-Total")) {
                checkoutSubtotal = priceValue;
            } else if (priceType.startsWith("Total")) {
                checkoutTotal = priceValue;
            } else {
                checkoutOtherFeesTotal += priceValue;
            }
            System.out.println(priceType + ": " + priceCategories.get(priceType));
        }

        Assert.assertEquals(checkoutSubtotal, currentSubtotal, "[ERR] ...");
        Assert.assertEquals(checkoutTotal, checkoutSubtotal + checkoutOtherFeesTotal, "[ERR] ...");
    }

    public void agreeTOSAndCheckout() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.totalComponent().agreeTOS();
        shoppingCartPage.totalComponent().clickOnCheckOutBtn();
        new CheckoutOptionPage(driver).checkoutAsGuess();
    }

    public void inputBillingAddress() {
        String defaultCheckoutUserJSONLoc = "/src/main/java/test_data/DefaultCheckoutUser.json";
        defaultCheckoutUser = DataObjectBuilder.buildDataObjectFrom(defaultCheckoutUserJSONLoc, UserDataObject.class);
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        BillingAddressComponent billingAddressComp = checkOutPage.billingAddressComp();
        billingAddressComp.selectInputNewAddress();
        billingAddressComp.inputFirstName(defaultCheckoutUser.getFirstName());
        billingAddressComp.inputLastName(defaultCheckoutUser.getLastName());
        billingAddressComp.inputEmail(defaultCheckoutUser.getEmail());
        billingAddressComp.selectCountry(defaultCheckoutUser.getCountry());
        billingAddressComp.selectState(defaultCheckoutUser.getState());
        billingAddressComp.inputCity(defaultCheckoutUser.getCity());
        billingAddressComp.inputAdd1(defaultCheckoutUser.getAdd1());
        billingAddressComp.inputZipCode(defaultCheckoutUser.getZipCode());
        billingAddressComp.inputPhoneNumber(defaultCheckoutUser.getPhoneNum());
        billingAddressComp.clickContinueBtn();
    }

    public void inputShippingAddress() {
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.shippingAddressComp().clickContinueBtn();
    }

    public void selectShippingMethod() {
        List<String> shippingMethods = Arrays.asList("Ground", "Next Day Air", "2nd Day Air");
        String ramdomShippingMethod = shippingMethods.get(new SecureRandom().nextInt(shippingMethods.size()));
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        ShippingMethodComponent shippingMethodComponent = checkOutPage.shippingMethodComp();
        shippingMethodComponent.selectShippingMethod(ramdomShippingMethod);
        shippingMethodComponent.clickContinueBtn();
    }

    public void selectPaymentMethod() {
        this.paymentMethod = PaymentMethod.COD;

    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null ) {
            throw new IllegalArgumentException("[ERR] Payment method can't be null");
        }
        this.paymentMethod = paymentMethod;

        CheckOutPage checkOutPage = new CheckOutPage(driver);
        PaymentMethodComponent paymentMethodComp = checkOutPage.paymentMethodComp();
        switch (paymentMethod) {
            case CHECK_MONEY_ORDER:
                paymentMethodComp.selectCheckMoneyOrderMethod();
                break;
            case CREDIT_CARD:
                paymentMethodComp.selectCreditCardMethod();
                break;
            case PURCHASE_ORDER:
                paymentMethodComp.selectPurchaseOrderMethod();
                break;
            default:
                paymentMethodComp.selectCODMethod();
        }

        paymentMethodComp.clickContinueBtn();
    }

    // Test Card Number: https://www.paypalobjects.com/en_GB/vhelp/paypalmanager_help/credit_card_numbers.htm
    public void inputPaymentInfo(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        PaymentInformationComponent paymentInformationComp = checkOutPage.paymentInformationComp();

        if (this.paymentMethod.equals(PaymentMethod.PURCHASE_ORDER)) {
            // it can be dynamic as well
            paymentInformationComp.inputPurchaseNumber("12345");
        } else if (this.paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            paymentInformationComp.selectCardType(creditCardType);
            String cardHolderFirstName = this.defaultCheckoutUser.getFirstName();
            String cardHolderLastName = defaultCheckoutUser.getLastName();
            paymentInformationComp.inputCardHolderName(cardHolderFirstName + " " + cardHolderLastName);
            // Visa: 4111111111111111
            // Discover: 6011000990139424
            String cardNumber = creditCardType.equals(CreditCardType.VISA) ? "4111111111111111" : "6011000990139424";
            paymentInformationComp.inputCardNumber(cardNumber);

            // Select current month and next year
            Calendar calendar = new GregorianCalendar();
            paymentInformationComp.selectExpireMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
            paymentInformationComp.selectExpireYear(String.valueOf(calendar.get(Calendar.YEAR) + 1));
            paymentInformationComp.inputCardCode("123");
        } else if (this.paymentMethod.equals(PaymentMethod.COD)) {
            paymentInformationComp.confirmCODText();
        } else {
            paymentInformationComp.confirmCheckText();
        }
        paymentInformationComp.clickContinueBtn();
    }

    public void confirmOrder() {
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        ConfirmOrderComponent confirmOrderComponent = checkOutPage.confirmOrderComp();

        // get Billing info
        ConfirmBillingInfo confirmBillingInfo = confirmOrderComponent.getConfirmBillingInfo();
        String billingName = confirmBillingInfo.getBillingName();
        String billingEmail = confirmBillingInfo.getBillingEmail();
        String billingAdd1 = confirmBillingInfo.getBillingAdd1();
        String billingPhone = confirmBillingInfo.getBillingPhone();
        String paymentMethod = confirmBillingInfo.getBillingPaymentMethod();
        System.out.println(billingName);
        System.out.println(billingEmail);
        System.out.println(billingAdd1);
        System.out.println(billingPhone);
        System.out.println(paymentMethod);

        // confirm billing info
        String defaultUsername = defaultCheckoutUser.getFirstName() +  " " + defaultCheckoutUser.getLastName();
        Assert.assertEquals(billingName, defaultUsername, "[ERR] billing name didn't correct.");
        Assert.assertTrue(billingEmail.contains(defaultCheckoutUser.getEmail()), "[ERR] Billing email didn't correct");
        Assert.assertTrue(billingAdd1.contains(defaultCheckoutUser.getAdd1()), "[ERR] Billing Address1 didn't correct");
        Assert.assertTrue(billingPhone.contains(defaultCheckoutUser.getPhoneNum()), "[ERR] Billing phone number didn't correct");

        confirmOrderComponent.clickConfirmBtn();
    }
}
