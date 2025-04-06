package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class HelperBase {
    protected final ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void click(By locator) {
        manager.driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        manager.driver.findElement(locator).clear();
        manager.driver.findElement(locator).sendKeys(text);
    }

    protected void selectType(By locator, String text) {
        click(locator);
        new Select(manager.driver.findElement(locator)).selectByVisibleText(text);
    }
}
