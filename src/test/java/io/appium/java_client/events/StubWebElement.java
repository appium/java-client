package io.appium.java_client.events;

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
    FindsByLinkText, FindsByTagName, FindsByXPath, FindsByAccessibilityId,
    FindsByAndroidUIAutomator, FindsByIosUIAutomation {
    @Override public WebElement findElementByAccessibilityId(String using) {
        return null;
    }

    @Override public List findElementsByAccessibilityId(String using) {
        return null;
    }

    @Override public WebElement findElementByAndroidUIAutomator(String using) {
        return null;
    }

    @Override public List findElementsByAndroidUIAutomator(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByClassName(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByClassName(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByCssSelector(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByCssSelector(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementById(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsById(String using) {
        return null;
    }

    @Override public WebElement findElementByIosUIAutomation(String using) {
        return null;
    }

    @Override public List findElementsByIosUIAutomation(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByLinkText(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByLinkText(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByPartialLinkText(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByPartialLinkText(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByTagName(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByTagName(String using) {
        return null;
    }

    @Override public <T extends WebElement> T findElementByXPath(String using) {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElementsByXPath(String using) {
        return null;
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

    @Override public <T extends WebElement> List<T> findElements(By by) {
        return null;
    }

    @Override public <T extends WebElement> T findElement(By by) {
        return null;
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
        return null;
    }

    @Override public String toString() {
        return this.getClass().getCanonicalName();
    }
}
