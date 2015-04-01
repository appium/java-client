package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByCssSelector<T extends WebElement> {
    T findElementByCssSelector(String cssSelector);
    List<T> findElementsByCssSelector(String cssSelector);
}
