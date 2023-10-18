package io.appium.java_client.pagefactory_tests.widget.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class StubWebElement implements WebElement, WrapsDriver {
    private final WebDriver driver;
    private final By by;

    public StubWebElement(WebDriver driver, By by) {
        this.driver = requireNonNull(driver);
        this.by = requireNonNull(by);
    }

    @Override
    public void click() {
        //this is just stub and it does nothing.
    }

    @Override
    public void submit() {
        //this is just stub and it does nothing.
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        //this is just stub and it does nothing.
    }

    @Override
    public void clear() {
        //this is just stub and it does nothing.
    }

    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public String getAttribute(String name) {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of(new StubWebElement(driver, by), new StubWebElement(driver, by));
    }

    @Override
    public StubWebElement findElement(By by) {
        return new StubWebElement(driver, by);
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return by.toString();
    }
}
