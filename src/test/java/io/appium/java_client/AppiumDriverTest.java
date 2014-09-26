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

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Test Mobile Driver features
 */
public class AppiumDriverTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "UICatalog.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
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
  public void setValueTest() {
    MobileElement element = (MobileElement)driver.findElementByAccessibilityId("Text Fields, AAPLTextFieldViewController");
    element.click();
    element = (MobileElement)driver.findElementByAccessibilityId("DEFAULT");
    element.setValue("Grace Hopper");
  }

  @Test
  public void pullFileTest() {
    byte[] data = driver.pullFile("Library/AddressBook/AddressBook.sqlitedb");
    assert(data.length > 0);
  }

  //TODO hideKeyboard() test

  @Test
  public void runAppInBackgroundTest() {
    long time = System.currentTimeMillis();
    driver.runAppInBackground(4);
    long timeAfter = System.currentTimeMillis();
    assert(timeAfter - time > 3000);
  }

  @Test
  public void lockTest() {
    driver.lockScreen(3);
  }

  @Test
  public void orientationTest() {
    assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
    driver.rotate(ScreenOrientation.LANDSCAPE);
    assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
  }

  @Test
  public void geolocationTest() {
    Location location = new Location(45, 45, 100);
    driver.setLocation(location);
  }

}
