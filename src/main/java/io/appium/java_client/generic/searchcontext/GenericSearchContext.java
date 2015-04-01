package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericSearchContext<T extends WebElement> {
    List<T> findElements(By by);
    T findElement(By by);
}
