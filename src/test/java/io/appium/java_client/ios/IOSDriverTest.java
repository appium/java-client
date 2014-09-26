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

package io.appium.java_client.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.remote.MobileCapabilityType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.Point;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;


/**
 * Test Mobile Driver features
 */
public class IOSDriverTest {

  private IOSDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "UICatalog.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void resetTest() {
    driver.resetApp();
  }

  @Test
  public void namedTextFieldTest() {
    MobileElement element = (MobileElement)driver.findElementByAccessibilityId("Text Fields, AAPLTextFieldViewController");
    element.click();
    element = (MobileElement)driver.getNamedTextField("DEFAULT");
    element.setValue("Grace Hopper");
    assertEquals("Grace Hopper", element.getText());
  }

  @Test
  public void hideKeyboardWithParametersTest() {
    MobileElement element = (MobileElement)driver.findElementByAccessibilityId("Text Fields, AAPLTextFieldViewController");
    element.click();
    element = (MobileElement)driver.findElementByAccessibilityId("DEFAULT");
    element.click();
    driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
  }

  @Test
  public void scrollToTest() {
    MobileElement searchBar = (MobileElement) driver.findElementByName("Search Bars");
    Point before = searchBar.getLocation();
    driver.scrollTo("Search Ba");
    Point after = searchBar.getLocation();
    assertNotEquals(before, after);
  }

  @Test
  public void scrollToExactTest() {
    MobileElement searchBar = (MobileElement) driver.findElementByName("Search Bars");
    Point before = searchBar.getLocation();
    driver.scrollToExact("Search Bars");
    Point after = searchBar.getLocation();
    assertNotEquals(before, after);
  }
}
