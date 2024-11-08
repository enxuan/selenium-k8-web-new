package models.components.order;

import models.components.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class ComputerEssentialComponent extends BaseItemDetailsComponent {

    private static By allOptionSel = By.cssSelector(".option-list input");

    public ComputerEssentialComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public abstract String selectProcessorType(String type);
    public abstract String selectRAMType(String type);

    public void unselectDefaultOptions() {
        List<WebElement> allOptionsElem = component.findElements(allOptionSel);
        allOptionsElem.forEach(option -> {
            if (option.getAttribute("checked") != null) {
                option.click();
            }
        });
    }

    public String selectHDD(String hddType) {
        return selectComputerOption(hddType);
    }

    public String selectSoftware(String hddType) {
        return selectComputerOption(hddType);
    }

    public String selectOs(String type) {
        return selectComputerOption(type);
    }


    protected String selectComputerOption(String type) {
        String selectorString = "//label[contains(text(), \"" + type + "\")]";
        By optionSel = By.xpath(selectorString);
        WebElement optionElem = null;

        try {
            optionElem = component.findElement(optionSel);
        } catch (Exception ignored) {

        }
        if (optionElem != null) {
            optionElem.click();
            return optionElem.getText();
        } else {
            throw new RuntimeException("The option: " + type + " is not existing to select");
        }
    }

}
