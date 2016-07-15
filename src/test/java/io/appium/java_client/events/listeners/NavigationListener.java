package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.NavigationEventListener;
import org.openqa.selenium.WebDriver;

public class NavigationListener extends TestListener implements NavigationEventListener {

    @Override public void beforeNavigateTo(String url, WebDriver driver) {
        messages.add("Attempt to navigate to " + url);
    }

    @Override public void afterNavigateTo(String url, WebDriver driver) {
        messages.add("Navigation to " + url + " was successful");
    }

    @Override public void beforeNavigateBack(WebDriver driver) {
        messages.add("Attempt to navigate back");
    }

    @Override public void afterNavigateBack(WebDriver driver) {
        messages.add("Navigation back was successful");
    }

    @Override public void beforeNavigateForward(WebDriver driver) {
        messages.add("Attempt to navigate forward");
    }

    @Override public void afterNavigateForward(WebDriver driver) {
        messages.add("Navigation forward was successful");
    }

    @Override public void beforeNavigateRefresh(WebDriver driver) {
        messages.add("Attempt to refresh");
    }

    @Override public void afterNavigateRefresh(WebDriver driver) {
        messages.add("The refreshing was successful");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(NavigationListener.class, this);
    }
}
