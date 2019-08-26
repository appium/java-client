package io.appium.java_client.selenium;

/**
 * Indicates that there is an underlying element that can be used
 */
public interface WrapsElement {
    WebElement getWrappedElement();
}
