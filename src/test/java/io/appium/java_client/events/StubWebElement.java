package io.appium.java_client.events;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByFluentSelector;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.Dimension;
import org.openqa.seleniumone.NoSuchElementException;
import org.openqa.seleniumone.OutputType;
import org.openqa.seleniumone.Point;
import org.openqa.seleniumone.Rectangle;
import org.openqa.seleniumone.WebDriverException;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.internal.FindsByClassName;
import org.openqa.seleniumone.internal.FindsByCssSelector;
import org.openqa.seleniumone.internal.FindsById;
import org.openqa.seleniumone.internal.FindsByLinkText;
import org.openqa.seleniumone.internal.FindsByTagName;
import org.openqa.seleniumone.internal.FindsByXPath;

import java.util.ArrayList;
import java.util.List;

public class StubWebElement implements WebElement, FindsByClassName, FindsByCssSelector, FindsById,
    FindsByLinkText, FindsByTagName, FindsByXPath, FindsByAccessibilityId<WebElement>,
    FindsByAndroidUIAutomator<WebElement>, FindsByFluentSelector<WebElement> {

    private static List<WebElement> createStubSubElementList() {
        return new ArrayList<>(ImmutableList.of(new StubWebElement(), new StubWebElement()));
    }


    @Override public WebElement findElementByAccessibilityId(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByAccessibilityId(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByAndroidUIAutomator(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByAndroidUIAutomator(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByClassName(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByClassName(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByCssSelector(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByCssSelector(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementById(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsById(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByLinkText(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByPartialLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByPartialLinkText(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByTagName(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByTagName(String using) {
        return createStubSubElementList();
    }

    @Override public WebElement findElementByXPath(String using) {
        return new StubWebElement();
    }

    @Override public List<WebElement> findElementsByXPath(String using) {
        return createStubSubElementList();
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

    @Override
    public List<WebElement> findElements(String by, String using) throws WebDriverException {
        return createStubSubElementList();
    }

    @Override public WebElement findElement(By by) {
        return new StubWebElement();
    }

    @Override
    public WebElement findElement(String by, String using) throws WebDriverException, NoSuchElementException {
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
