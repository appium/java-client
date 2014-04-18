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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.net.URL;

/**
 * Test Mobile Driver features
 */
public class MobileDriverIOSTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("test/io/appium/java_client");
    File app = new File(appDir, "UICatalog.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(CapabilityType.VERSION, "7.1");
    capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
    capabilities.setCapability("device", "iPhone Simulator");
    capabilities.setCapability("app", app.getAbsolutePath());
    driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
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
  public void setValueTest() {
    MobileElement element = new MobileElement((RemoteWebElement)driver.findElementByAccessibilityId("TextFields, Uses of UITextField"), driver);
    element.click();
    element = new MobileElement((RemoteWebElement)driver.findElementByAccessibilityId("Normal"), driver);
    element.setValue("Grace Hopper");
  }

  @Test
  public void pullFileTest() {
    byte[] data = driver.pullFile("Library/AddressBook/AddressBook.sqlitedb");
    assert(data.length > 0);
  }

  @Test
  public void hideKeyboardTest() {
    MobileElement element = new MobileElement((RemoteWebElement)driver.findElementByAccessibilityId("TextFields, Uses of UITextField"), driver);
    element.click();
    element = new MobileElement((RemoteWebElement)driver.findElementByAccessibilityId("Normal"), driver);
    element.click();
    driver.hideKeyboard();
  }

  @Test
  public void runAppInBackgroundTest() {
    long time = System.currentTimeMillis();
    driver.runAppInBackground(4);
    long timeAfter = System.currentTimeMillis();
    assert(timeAfter - time > 3000);
  }

}
