package models.components.global.footer;

import models.components.ComponentCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".column.information")
public class InfomationColumnComponent extends FooterColumnComponent{

    public InfomationColumnComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }
}
