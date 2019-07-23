package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.ElementEventListener;
import org.openqa.seleniumone.WebDriver;
import org.openqa.seleniumone.WebElement;

public class ElementListener2 extends TestListener implements ElementEventListener {

    @Override public void beforeClickOn(WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: Attempt to click on the element");
    }

    @Override public void afterClickOn(WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: Thee element was clicked");
    }

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: Attempt to change value of the element");
    }

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver,
        CharSequence[] keysToSend) {
        messages.add("Externally defined listener: Attempt to change value of the element");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: The value of the element was changed");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver,
        CharSequence[] keysToSend) {
        messages.add("Externally defined listener: The value of the element was changed");
    }

    @Override public void beforeGetText(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to get text of the element");
    }

    @Override public void afterGetText(WebElement element, WebDriver driver, String text) {
        messages.add("WebDriverEventListener: Got the text of an element");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(ElementListener2.class, this);
    }
}
