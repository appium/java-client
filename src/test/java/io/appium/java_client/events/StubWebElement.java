package io.appium.java_client.events;

import com.google.common.collect.ImmutableList;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class StubWebElement implements WebElement {

    private static List<WebElement> createStubSubElementList() {
        return new ArrayList<>(ImmutableList.of(new StubWebElement(), new StubWebElement()));
    }

    @Override public void click() {
        //There is no clicking. It is STUB.
    }

    @Override public void submit() {
        //No submitting
    }

    @Override public void sendKeys(CharSequence... keysToSend) {
        //There is no the sending keys.
    }

    @Override public void clear() {
        //It doesn't clearing anything.
    }

    @Override public String getTagName() {
        return null;
    }

    @Override public String getAttribute(String name) {
        return null;
    }

    @Override public boolean isSelected() {
        return false;
    }

    @Override public boolean isEnabled() {
        return false;
    }

    @Override public String getText() {
        return null;
    }

    @Override public List<WebElement> findElements(By by) {
        return createStubSubElementList();
    }

    @Override public WebElement findElement(By by) {
        return new StubWebElement();
    }

    @Override public boolean isDisplayed() {
        return false;
    }

    @Override public Point getLocation() {
        return null;
    }

    @Override public Dimension getSize() {
        return null;
    }

    @Override public Rectangle getRect() {
        throw new WebDriverException();
    }

    @Override public String getCssValue(String propertyName) {
        return null;
    }

    @Override public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return target.convertFromPngBytes(new byte[]{1,2});
    }

    @Override public String toString() {
        return this.getClass().getCanonicalName();
    }
}
