package io.appium.java_client.pagefactory_tests.widget.tests;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.of;

import org.openqa.seleniumone.By;
import org.openqa.seleniumone.Dimension;
import org.openqa.seleniumone.OutputType;
import org.openqa.seleniumone.Point;
import org.openqa.seleniumone.Rectangle;
import org.openqa.seleniumone.WebDriver;
import org.openqa.seleniumone.WebDriverException;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.WrapsDriver;

import java.util.List;

public class StubWebElement implements WebElement, WrapsDriver {
    private final WebDriver driver;
    private final By by;

    public StubWebElement(WebDriver driver, By by) {
        this.driver = checkNotNull(driver);
        this.by = checkNotNull(by);
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
    public List<StubWebElement> findElements(By by) {
        return of(new StubWebElement(driver, by), new StubWebElement(driver, by));
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
