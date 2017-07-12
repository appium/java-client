package io.appium.java_client.pagefactory_tests.widget.tests;

import static com.google.common.collect.ImmutableList.of;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

import java.util.List;

public class StubWebElement implements WebElement, WrapsDriver {
    private final WebDriver driver;
    private final By by;

    public StubWebElement(WebDriver driver, By by) {
        this.driver = driver;
        this.by = by;
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {

    }

    @Override
    public void clear() {

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
