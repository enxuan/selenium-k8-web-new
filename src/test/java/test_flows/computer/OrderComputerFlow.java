package test_flows.computer;

import models.components.Component;
import models.components.cart.TotalComponent;
import models.components.order.ComputerEssentialComponent;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import test_data.computer.ComputerData;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private ComputerData computerData;
    private final int quantity;
    private double totalItemPrice;

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
        TotalComponent totalComp = shoppingCartPage.totalComponent();
        Map<String, Double> priceCategories = totalComp.priceCategories();
        for (String priceType : priceCategories.keySet()) {
            System.out.println(priceType + ": " + priceCategories.get(priceType));
        }
    }
}
