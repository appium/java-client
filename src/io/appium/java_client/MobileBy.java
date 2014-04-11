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
      throw new IllegalArgumentException("Must supply a uiautomationText");
    }

    return new ByIosUIAutomation(uiautomationText);
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
}


