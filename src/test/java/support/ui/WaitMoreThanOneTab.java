package support.ui;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitMoreThanOneTab implements ExpectedCondition<Boolean> {

    @Override
    public Boolean apply(WebDriver input) {
        return input.getWindowHandles().size() > 1;
    }

    @Override
    public String toString() {
        return "Tab numbers to be bigger than 2";
    }
}
