package models.components.global.footer;

import models.components.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FooterColumnComponent extends Component {

    public final static By headerSel = By.tagName("h3");
    public final static By linkSel = By.cssSelector("li a");

    public FooterColumnComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public WebElement headerElem() {
        return component.findElement(headerSel);
    }

    public List<WebElement> linksElem() {
        return component.findElements(linkSel);
    }
}
