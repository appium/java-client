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

import java.io.File;
import java.net.URL;

/**
 * Test Mobile Driver features
 */
public class MobileDriverAndroidTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("test/io/appium/java_client");
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
  public void getStrings() {
    String strings = driver.getAppStrings();
    assert(strings.length() > 100);
  }

  @Test
  public void keyEvent() {
    driver.sendKeyEvent(AndroidKeyCode.HOME);
  }

  @Test
  public void keyEventWithMetastate() {
    driver.sendKeyEvent(AndroidKeyCode.SPACE, AndroidKeyMetastate.META_SHIFT_ON);
  }
}
