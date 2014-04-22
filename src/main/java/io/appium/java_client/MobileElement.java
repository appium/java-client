/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.*;

import java.util.List;
import java.util.Map;

public class MobileElement extends RemoteWebElement {

  private String foundBy;
  protected FileDetector fileDetector;

  private WebElement webElement;
  private MobileDriver parent;

  public MobileElement(RemoteWebElement originalElement, MobileDriver parentDriver) {

    webElement = originalElement;
    this.id = originalElement.getId();
    this.parent = parentDriver;
    //The super doesn't need the parent object at ALL, it's terrible that it asks for one, when it only needs it for setFoundBy()
    super.setParent(new FakeRemoteWebDriver());
  }

  public List<WebElement> findElements(By by) {
    return by.findElements(this);
  }

  public WebElement findElement(By by) {
    return by.findElement(this);
  }

  public WebElement findElementByIosUIAutomation(String using) { return findElement("-ios uiautomation", using); }

  public List<WebElement> findElementsByIosUIAutomation(String using) { return findElements("-ios uiautomation", using); }

  public WebElement findElementByAndroidUIAutomator(String using) { return findElement("-android uiautomator", using); }

  public List<WebElement> findElementsByAndroidUIAutomator(String using) { return findElements("-android uiautomator", using); }

  public WebElement findElementByAccessibilityId(String using) { return findElement("accessibility id", using); }

  public List<WebElement> findElementsByAccessibilityId(String using) { return findElements("accessibility id", using); }

  public void setValue(String value) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("id", id).put("value", value);
    execute(MobileCommand.SET_VALUE, builder.build());
  }

  public Point getCenter() {
    Point upperLeft = this.getLocation();
    Dimension dimensions = this.getSize();
    return new Point(upperLeft.getX() + dimensions.getWidth()/2, upperLeft.getY() + dimensions.getHeight()/2);
  }

  protected Response execute(String command, Map<String, ?> parameters) {
    return parent.execute(command, parameters);
  }

  private class FakeRemoteWebDriver extends RemoteWebDriver {
    public FakeRemoteWebDriver() {
      //hahaha do NOTHING!
    }
  }
}
