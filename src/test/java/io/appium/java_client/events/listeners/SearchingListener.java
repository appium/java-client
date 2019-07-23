package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.SearchingEventListener;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.WebDriver;
import org.openqa.seleniumone.WebElement;

public class SearchingListener extends TestListener implements SearchingEventListener {

    @Override public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("Attempt to find something using " + by.toString() + ". The root element is "
            + String.valueOf(element));
    }

    @Override public void afterFindBy(By by, WebElement element, WebDriver driver) {
        messages.add("The searching for something using " + by.toString() + " has beed finished. "
            + "The root element was "
            + String.valueOf(element));
    }

    @Override protected void add() {
        SingleListeners.listeners.put(SearchingListener.class, this);
    }
}
