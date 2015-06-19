package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericFindsByLinkText<T extends WebElement> {
    T findElementByLinkText(String linkText);
    List<T> findElementsByLinkText(String linkText);
    WebElement findElementByPartialLinkText(String partialLinkText);
    List<T> findElementsByPartialLinkText(String partialLinkText);
}
