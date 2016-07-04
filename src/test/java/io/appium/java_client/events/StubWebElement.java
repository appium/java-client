package io.appium.java_client.events;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosUIAutomation;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;

import java.util.List;

public class StubWebElement implements WebElement, FindsByClassName, FindsByCssSelector, FindsById,
    FindsByLinkText, FindsByTagName, FindsByXPath, FindsByAccessibilityId<StubWebElement>,
    FindsByAndroidUIAutomator<StubWebElement>, FindsByIosUIAutomation<StubWebElement> {
    @Override public StubWebElement findElementByAccessibilityId(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByAccessibilityId(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByAndroidUIAutomator(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByAndroidUIAutomator(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByClassName(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByClassName(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByCssSelector(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByCssSelector(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementById(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsById(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByIosUIAutomation(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByIosUIAutomation(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByLinkText(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByPartialLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByPartialLinkText(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByTagName(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByTagName(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByXPath(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByXPath(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public void click() {

    }

    @Override public void submit() {

    }

    @Override public void sendKeys(CharSequence... keysToSend) {

    }

    @Override public void clear() {

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

    @Override public List<StubWebElement> findElements(By by) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElement(By by) {
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
        return null;
    }

    @Override public String getCssValue(String propertyName) {
        return null;
    }

    @Override public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        throw new RuntimeException("Test web element exception");
    }

    @Override public String toString() {
        return this.getClass().getCanonicalName();
    }
}
