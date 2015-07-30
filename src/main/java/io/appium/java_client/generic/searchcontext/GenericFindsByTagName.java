package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByTagName<T extends WebElement> {
    T findElementByTagName(String tagName);
    List<T> findElementsByTagName(String tagName);
}
