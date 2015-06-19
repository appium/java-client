package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByName<T extends WebElement> {
    T findElementByName(String name);
    List<T> findElementsByName(String name);
}
