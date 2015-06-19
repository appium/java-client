package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByXPath<T extends WebElement> {
    T findElementByXPath(String xPath);
    List<T> findElementsByXPath(String xPath);
}
