package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsById<T extends WebElement> {
    T findElementById(String id);
    List<T> findElementsById(String id);
}
