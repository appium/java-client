package io.appium.java_client;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindsByIosNsPredicate<T extends WebElement> {
    T findElementByIosNsPredicate(String using);

    List<T> findElementsByIosNsPredicate(String using);
}
