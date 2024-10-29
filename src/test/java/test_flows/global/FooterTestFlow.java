package test_flows.global;

import models.components.global.TopMenuComponent;
import static models.components.global.TopMenuComponent.MainCatItem;
import static models.components.global.TopMenuComponent.CatItemComponent;
import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.FooterComponent;
import models.pages.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FooterTestFlow {

    private WebDriver driver;


    public FooterTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyFooterComponent() {
        BasePage basePage = new BasePage(driver);
        FooterComponent footerComponent = basePage.footerComp();
        verifyInformationColumn(footerComponent.infomationColumnComp());
        verifyCustomerServiceColumn(footerComponent.customerServiceColumnComp());
        verifyAccountColumn(footerComponent.accountColumnComp());
        verifyFollowUsColumn(footerComponent.followUsColumnComp());
    }

    private void verifyInformationColumn(FooterColumnComponent footerColumnComp) {
        List<String> expectedLinkTexts = Arrays.asList("Sitemap",
                "Shipping & Returns", "Privacy Notice", "Conditions of Use",
                "About us", "Contact us");
        List<String> expectedHrefs = Arrays.asList("https://demowebshop.tricentis.com/sitemap",
                "https://demowebshop.tricentis.com/shipping-returns",
                "https://demowebshop.tricentis.com/privacy-policy",
                "https://demowebshop.tricentis.com/conditions-of-use",
                "https://demowebshop.tricentis.com/about-us",
                "https://demowebshop.tricentis.com/contactus");
        verifyFooterColumn(footerColumnComp, expectedLinkTexts, expectedHrefs);
    }

    private void verifyCustomerServiceColumn(FooterColumnComponent footerColumnComp) {
        List<String> expectedLinkTexts = Arrays.asList("Search",
                "News", "Blog", "Recently viewed products", "Compare products list", "New products");
        List<String> expectedHrefs = Arrays.asList(
                "https://demowebshop.tricentis.com/search",
                "https://demowebshop.tricentis.com/news",
                "https://demowebshop.tricentis.com/blog",
                "https://demowebshop.tricentis.com/recentlyviewedproducts",
                "https://demowebshop.tricentis.com/compareproducts",
                "https://demowebshop.tricentis.com/newproducts"
        );
        verifyFooterColumn(footerColumnComp, expectedLinkTexts, expectedHrefs);
    }

    private void verifyAccountColumn(FooterColumnComponent footerColumnComp) {
        List<String> expectedLinkTexts = new ArrayList<>();
        List<String> expectedHrefs = new ArrayList<>();
//        verifyFooterColumn(footerColumnComp, expectedLinkTexts, expectedHrefs);
    }

    private void verifyFollowUsColumn(FooterColumnComponent footerColumnComp) {
        List<String> expectedLinkTexts = new ArrayList<>();
        List<String> expectedHrefs = new ArrayList<>();
//        verifyFooterColumn(footerColumnComp, expectedLinkTexts, expectedHrefs);
    }

    public void verifyProductCatFooterComponent() {
        // Random pickup an item
        BasePage basePage = new BasePage(driver);
        TopMenuComponent topMenuComponent = basePage.topMenuComp();
        List<MainCatItem> mainCatElems = topMenuComponent.mainItemsElem();
        if (mainCatElems.isEmpty()) {
            Assert.fail("[ERR] There is no item on top menu!");
        }

        MainCatItem randomMainItemElem = mainCatElems.get(new SecureRandom().nextInt(mainCatElems.size()));
        String randomCatHref = randomMainItemElem.catItemLinkElem().getAttribute("href");

        // Get sublist
        List<CatItemComponent> catItemComps = randomMainItemElem.catItemComps();
        if (catItemComps.isEmpty()) {
            randomMainItemElem.catItemLinkElem().click();
        } else {
            int randomIndex = new SecureRandom().nextInt(catItemComps.size());
            CatItemComponent randomCatItemComp = catItemComps.get(randomIndex);
            randomCatHref = randomCatItemComp.getComponent().getAttribute("href");
            randomCatItemComp.getComponent().click();
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.urlContains(randomCatHref));
        } catch (TimeoutException e) {
            Assert.fail("[ERR] target page is not matched");
        }

        // Verify footer component
        verifyFooterComponent();
    }

    private static void verifyFooterColumn(FooterColumnComponent footerColumnComponent,
                                         List<String> expectedLinkTexts, List<String> expectedHrefs) {
        List<String> actualLinkTexts = new ArrayList<>();
        List<String> actualHrefs = new ArrayList<>();

        for (WebElement link : footerColumnComponent.linksElem()) {
            actualLinkTexts.add(link.getText().trim());
            actualHrefs.add(link.getAttribute("href"));
        }

        if (actualLinkTexts.isEmpty() || actualHrefs.isEmpty()) {
            Assert.fail("[Err] Texts of hyperlinks is empty in footer column");
        }

        // Verify link text
        Assert.assertEquals(actualLinkTexts, expectedLinkTexts, "[ERR] actual and expected link texts are differenct!");

        // Verify Hrefs
        Assert.assertEquals(actualHrefs, expectedHrefs, "[ERR] actual and expected link texts are differenct!");
    }
}
