package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByTagName<T extends WebElement> {
    WebElement findElementByTagName(String tagName);
    List<WebElement> findElementsByTagName(String tagName);
}
