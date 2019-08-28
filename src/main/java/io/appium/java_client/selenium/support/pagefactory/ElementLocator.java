package io.appium.java_client.selenium.support.pagefactory;

import io.appium.java_client.selenium.WebElement;

import java.util.List;

public interface ElementLocator {
    WebElement findElement();

    List<WebElement> findElements();
}
