package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByClassName<T extends WebElement> {
    T findElementByClassName(String className);
    List<T> findElementsByClassName(String className);
}
