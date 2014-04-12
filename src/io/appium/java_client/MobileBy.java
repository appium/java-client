package io.appium.java_client;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jonahss on 4/10/14.
 */
public abstract class MobileBy extends By {

  public static By IosUIAutomation(final String uiautomationText) {
    if (uiautomationText == null) {
      throw new IllegalArgumentException("Must supply an iOS UIAutomation string");
    }

    return new ByIosUIAutomation(uiautomationText);
  }

  public static By AndroidUIAutomator(final String uiautomatorText) {
    if (uiautomatorText == null) {
      throw new IllegalArgumentException("Must supply an Android UIAutomator string");
    }

    return new ByAndroidUIAutomator(uiautomatorText);
  }

  public static By AccessibilityId(final String id) {
    if (id == null) {
      throw new IllegalArgumentException("Must supply a uiautomationText");
    }

    return new ByAccessibilityId(id);
  }

  public static class ByIosUIAutomation extends By implements Serializable {

    private final String automationText;

    public ByIosUIAutomation(String uiautomationText)   {
      automationText = uiautomationText;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByIosUIAutomation) context).findElementsByIosUIAutomation(automationText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByIosUIAutomation) context).findElementByIosUIAutomation(automationText);
    }

    @Override
    public String toString() {
      return "By.IosUIAutomation: " + automationText;
    }
  }

  public static class ByAndroidUIAutomator extends By implements Serializable {

    private final String automatorText;

    public ByAndroidUIAutomator(String uiautomatorText) {
      automatorText = uiautomatorText;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByAndroidUIAutomator) context).findElementsByAndroidUIAutomator(automatorText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByAndroidUIAutomator) context).findElementByAndroidUIAutomator(automatorText);
    }

    @Override
    public String toString() { return "By.AndroidUIAutomator: " + automatorText; }
  }

  public static class ByAccessibilityId extends By implements Serializable {

    private final String id;

    public ByAccessibilityId(String id)   {
      this.id = id;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByAccessibilityId) context).findElementsByAccessibilityId(id);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByAccessibilityId) context).findElementByAccessibilityId(id);
    }

    @Override
    public String toString() {
      return "By.AccessibilityId: " + id;
    }
  }
}


