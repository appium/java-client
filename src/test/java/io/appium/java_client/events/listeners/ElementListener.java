package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.ElementEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementListener extends TestListener implements ElementEventListener {

    @Override public void beforeClickOn(WebElement element, WebDriver driver) {
        messages.add("Attempt to click on the element");
    }

    @Override public void afterClickOn(WebElement element, WebDriver driver) {
        messages.add("Thee element was clicked");
    }

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("Attempt to change value of the element");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("The value of the element was changed");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(ElementListener.class, this);
    }
}
