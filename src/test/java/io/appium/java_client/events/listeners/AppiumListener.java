package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppiumListener extends TestListener implements AppiumWebDriverEventListener {
    @Override protected void add() {
        SingleListeners.listeners.put(AppiumListener.class, this);
    }

    @Override public void beforeNavigateTo(String url, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to navigate to " + url);
    }

    @Override public void afterNavigateTo(String url, WebDriver driver) {
        messages.add("WebDriverEventListener: Navigation to " + url + " was successful");
    }

    @Override public void beforeNavigateBack(WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to navigate back");
    }

    @Override public void afterNavigateBack(WebDriver driver) {
        messages.add("WebDriverEventListener: Navigation back was successful");
    }

    @Override public void beforeNavigateForward(WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to navigate forward");
    }

    @Override public void afterNavigateForward(WebDriver driver) {
        messages.add("WebDriverEventListener: Navigation forward was successful");
    }

    @Override public void beforeNavigateRefresh(WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to refresh");
    }

    @Override public void afterNavigateRefresh(WebDriver driver) {
        messages.add("WebDriverEventListener: The refreshing was successful");
    }

    @Override public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to find something using " + by.toString()
            + ". The root element is "
            + String.valueOf(element));
    }

    @Override public void afterFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: The searching for something using "
            + by.toString() + " has beed finished. "
            + "The root element was "
            + String.valueOf(element));
    }

    @Override public void beforeClickOn(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to click on the element");
    }

    @Override public void afterClickOn(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Thee element was clicked");
    }

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to change value of the element");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: The value of the element was changed");
    }

    @Override public void beforeScript(String script, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to perform java script: " + script);
    }

    @Override public void afterScript(String script, WebDriver driver) {
        messages.add("WebDriverEventListener: Java script " + script + " was performed");
    }

    @Override public void onException(Throwable throwable, WebDriver driver) {
        messages.add("WebDriverEventListener: The exception was thrown: " + throwable.getClass());
    }
}
