package io.appium.java_client.events.listeners;

import static java.lang.String.format;

import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppiumListener extends TestListener implements AppiumWebDriverEventListener {
    @Override protected void add() {
        SingleListeners.listeners.put(AppiumListener.class, this);
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to accept alert");
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        messages.add("WebDriverEventListener: The alert was accepted");
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to dismiss alert");
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        messages.add("WebDriverEventListener: The alert was dismissed");
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

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver,
        CharSequence[] keysToSend) {
        messages.add("WebDriverEventListener: Attempt to click on the element");
    }

    @Override public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to change value of the element");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: The value of the element was changed");
    }

    @Override public void afterChangeValueOf(WebElement element, WebDriver driver,
        CharSequence[] keysToSend) {
        messages.add("WebDriverEventListener: Thee element was clicked");
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

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {
        messages.add(format("WebDriverEventListener: Attempt to take screen shot. Target type is %s", target));
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
        messages.add(format("WebDriverEventListener: Screen shot was taken successfully. "
                        + "Target type is %s, result is %s", target, screenshot));
    }

    @Override public void beforeGetText(WebElement element, WebDriver driver) {
        messages.add("WebDriverEventListener: Attempt to get text of the element");
    }

    @Override public void afterGetText(WebElement element, WebDriver driver, String text) {
        messages.add("WebDriverEventListener: Got the text of an element");
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        messages.add(format("WebDriverEventListener: Attempt to switch to window %s", windowName));
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        messages.add(format("WebDriverEventListener: driver is switched to window %s", windowName));
    }
}
