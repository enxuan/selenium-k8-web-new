package models.components.order;

import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@ComponentCssSelector(value = ".product-essential")
public class StandardComputerComponent extends ComputerEssentialComponent{

    private static final By productAttributeSel = By.cssSelector("select[id^=\"product_attribute\"]");

    public StandardComputerComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Override
    public String selectProcessorType(String type) {
        final int PROCESSOR_DROPDOWN_INDEX = 0;
        WebElement processorDropDownElem =
                component.findElements(productAttributeSel).get(PROCESSOR_DROPDOWN_INDEX);
        return selectOption(processorDropDownElem, type);
    }

    @Override
    public String selectRAMType(String type) {
        final int RAM_DROPDOWN_INDEX = 1;
        WebElement ramDropDownElem =
                component.findElements(productAttributeSel).get(RAM_DROPDOWN_INDEX);
        return selectOption(ramDropDownElem, type);
    }

    private String selectOption(WebElement dropdownElem, String type) {
        Select select = new Select(dropdownElem);
        List<WebElement> allOptions = select.getOptions();
        String fullStrOption = null;
        for (WebElement option : allOptions) {
            String currentOptionText = option.getText();
            String optionTextWithoutSpace = currentOptionText.trim().replace(" ", "");
            if (optionTextWithoutSpace.startsWith(type)) {
                fullStrOption = currentOptionText;
                break;
            }
        }

        if (fullStrOption == null) {
            throw new RuntimeException("[ERR] The option " + type + " is not existing");
        }

        select.selectByVisibleText(fullStrOption);
        return fullStrOption;
    }
}