package ru.stqa.addressbook.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Paths;

public class HelperBase {
    protected final ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void click(By locator) {
        manager.driver.findElement(locator).click();
    }

    protected void getUrl(String url) {
        manager.driver.get(url);
    }

    protected void moveTo(By locator) {
        Actions action = new Actions(manager.driver);
        action.moveToElement(manager.driver.findElement(locator)).perform();
    }

    protected void moveTo(WebElement webElement) {
        Actions action = new Actions(manager.driver);
        action.moveToElement(webElement).perform();
    }

    protected void type(By locator, String text) {
        click(locator);
        manager.driver.findElement(locator).clear();
        manager.driver.findElement(locator).sendKeys(text);
    }

    protected void selectType(By locator, String text) {
        click(locator);
        new Select(manager.driver.findElement(locator)).selectByValue(text);
    }

    protected void attach(By locator, String file) {
        manager.driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }
}
