package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.NavigationEventListener;
import org.openqa.selenium.WebDriver;

public class NavigationListener2 extends TestListener implements NavigationEventListener {

    @Override public void beforeNavigateTo(String url, WebDriver driver) {
        messages.add("Externally defined listener: Attempt to navigate to " + url);
    }

    @Override public void afterNavigateTo(String url, WebDriver driver) {
        messages.add("Externally defined listener: Navigation to " + url + " was successful");
    }

    @Override public void beforeNavigateBack(WebDriver driver) {
        messages.add("Externally defined listener: Attempt to navigate back");
    }

    @Override public void afterNavigateBack(WebDriver driver) {
        messages.add("Externally defined listener: Navigation back was successful");
    }

    @Override public void beforeNavigateForward(WebDriver driver) {
        messages.add("Externally defined listener: Attempt to navigate forward");
    }

    @Override public void afterNavigateForward(WebDriver driver) {
        messages.add("Externally defined listener: Navigation forward was successful");
    }

    @Override public void beforeNavigateRefresh(WebDriver driver) {
        messages.add("Externally defined listener: Attempt to refresh");
    }

    @Override public void afterNavigateRefresh(WebDriver driver) {
        messages.add("Externally defined listener: The refreshing was successful");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(NavigationListener2.class, this);
    }
}
