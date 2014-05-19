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

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test Mobile Driver features
 */
public class MobileDriverAndroidTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "ApiDemos-debug.apk");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
    capabilities.setCapability("device", "Android");
    capabilities.setCapability("app", app.getAbsolutePath());
    driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void getStringsTest() {
    String strings = driver.getAppStrings();
    assert(strings.length() > 100);
  }

  @Test
  public void getStringsWithLanguageTest() {
    String strings = driver.getAppStrings("en");
    assert(strings.length() > 100);
  }

  @Test
  public void keyEventTest() {
    driver.sendKeyEvent(AndroidKeyCode.HOME);
  }

  @Test
  public void keyEventWithMetastateTest() {
    driver.sendKeyEvent(AndroidKeyCode.SPACE, AndroidKeyMetastate.META_SHIFT_ON);
  }

  @Test
  public void currentActivityTest() {
    String activity = driver.currentActivity();
    assertEquals(".ApiDemos", activity);
  }

  @Test
  public void isAppInstalledTest() {
    assertTrue(driver.isAppInstalled("com.example.android.apis"));
  }

  @Test
  public void isAppNotInstalledTest() {
    assertFalse(driver.isAppInstalled("foo"));
  }

  @Test
  public void closeAppTest() throws InterruptedException {
    driver.closeApp();
    driver.launchApp();
    assertEquals(".ApiDemos", driver.currentActivity());
  }

  @Test
  public void pushFileTest() {
    byte[] data = Base64.encodeBase64("The eventual code is no more than the deposit of your understanding. ~E. W. Dijkstra".getBytes());
    driver.pushFile("/data/local/tmp/remote.txt", data);
    byte[] returnData = driver.pullFile("/data/local/tmp/remote.txt");
    String returnDataDecoded = new String(Base64.decodeBase64(returnData));
    assertEquals("The eventual code is no more than the deposit of your understanding. ~E. W. Dijkstra", returnDataDecoded);
  }
}
