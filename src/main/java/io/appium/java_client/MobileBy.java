/*
 * Copyright 2015 Appium Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jonahss on 4/10/14.
 */
@SuppressWarnings("serial")
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

    @SuppressWarnings("unchecked")
	@Override
    public List<WebElement> findElements(SearchContext context) {
      return (List<WebElement>) ((FindsByIosUIAutomation<?>) context).
    		  findElementsByIosUIAutomation(automationText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByIosUIAutomation<?>) context).findElementByIosUIAutomation(automationText);
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

    @SuppressWarnings("unchecked")
	@Override
    public List<WebElement> findElements(SearchContext context) {
      return (List<WebElement>) ((FindsByAndroidUIAutomator<?>) context).
    		  findElementsByAndroidUIAutomator(automatorText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByAndroidUIAutomator<?>) context).findElementByAndroidUIAutomator(automatorText);
    }

    @Override
    public String toString() { return "By.AndroidUIAutomator: " + automatorText; }
  }

  
public static class ByAccessibilityId extends By implements Serializable {

    private final String id;

    public ByAccessibilityId(String id)   {
      this.id = id;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<WebElement> findElements(SearchContext context) {
      return (List<WebElement>) 
    		  ((FindsByAccessibilityId<?>) context).findElementsByAccessibilityId(id);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByAccessibilityId<?>) context).findElementByAccessibilityId(id);
    }

    @Override
    public String toString() {
      return "By.AccessibilityId: " + id;
    }
  }
}


