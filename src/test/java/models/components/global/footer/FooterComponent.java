package models.components.global.footer;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".footer")
public class FooterComponent extends Component {

    public FooterComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public InfomationColumnComponent infomationColumnComp() {
        return findComponent(InfomationColumnComponent.class, driver);
    }

    public CustomerServiceColumnComponent customerServiceColumnComp() {
        return findComponent(CustomerServiceColumnComponent.class, driver);
    }

    public AccountColumnComponent accountColumnComp() {
        return findComponent(AccountColumnComponent.class, driver);
    }

    public FollowUsColumnComponent followUsColumnComp() {
        return findComponent(FollowUsColumnComponent.class, driver);
    }
}
