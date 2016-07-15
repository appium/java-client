package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.SearchingEventListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchingListener2 extends TestListener implements SearchingEventListener {

    @Override public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: Attempt to find something using "
            + by.toString() + ". The root element is "
            + String.valueOf(element));
    }

    @Override public void afterFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("Externally defined listener: The searching for something using "
            + by.toString() + " has beed finished. "
            + "The root element was "
            + String.valueOf(element));
    }

    @Override protected void add() {
        SingleListeners.listeners.put(SearchingListener2.class, this);
    }
}
