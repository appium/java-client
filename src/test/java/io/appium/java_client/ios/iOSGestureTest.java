/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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

package io.appium.java_client.ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test Mobile Driver features
 */
public class iOSGestureTest {

  private AppiumDriver<WebElement> driver;
  private static AppiumDriverLocalService service;

  @BeforeClass
  public static void beforeClass() throws Exception{
     service = AppiumDriverLocalService.buildDefaultService();
     service.start();
  }

  @Before
  public void setup() throws Exception {
    if (service == null || !service.isRunning())
      throw new RuntimeException("An appium server node is not started!");

    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "TestApp.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    driver = new IOSDriver<WebElement>(service.getUrl(), capabilities);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void TouchActionTest() throws InterruptedException {
    WebElement button = driver.findElementsByClassName("UIAButton").get(3);
    TouchAction action = new TouchAction(driver);
    action.press(button).perform();
    Thread.sleep(2000);
  }

  @Test
  public void TouchActionChainTest() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, 2);

    WebElement button = driver.findElementsByClassName("UIAButton").get(5);
    TouchAction action = new TouchAction(driver);
    action.press(button).perform();

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    WebElement mapview = driver.findElementByXPath("//UIAWindow[1]/UIAMapView[1]");
    action = new TouchAction(driver);
    action.press(mapview).moveTo(mapview, 0, 100).release().perform();
    Thread.sleep(2000);
  }

  @Test
  public void MultiGestureTest() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, 2);

    WebElement button = driver.findElementsByClassName("UIAButton").get(5);
    TouchAction action = new TouchAction(driver);
    action.press(button).perform();

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    WebElement mapview = driver.findElementByXPath("//UIAWindow[1]/UIAMapView[1]");

    MultiTouchAction multiTouch = new MultiTouchAction(driver);
    TouchAction action0 = new TouchAction(driver).press(mapview, 100, 0).moveTo(mapview, 0,-80).release();
    TouchAction action1 = new TouchAction(driver).press(mapview, 100, 50).moveTo(mapview, 0,80).release();
    multiTouch.add(action0).add(action1).perform();
    Thread.sleep(2000);
  }

  @Test
  public void ZoomTest() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, 2);

    WebElement button = driver.findElementsByClassName("UIAButton").get(5);
    TouchAction action = new TouchAction(driver);
    action.press(button).perform();

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    WebElement mapview = driver.findElementByXPath("//UIAWindow[1]/UIAMapView[1]");

    driver.zoom(mapview);
    Thread.sleep(2000);
  }

  @Test
  public void TapSingleFingerTest() {
    driver.tap(1,100,200,1000);
  }

  @Test
  public void elementGestureTest(){
	  MobileElement e = (MobileElement) driver.findElementByName("TextField1");
	  e.tap(1, 1500);
	  e.zoom();
	  e.pinch();
	  e.swipe(SwipeElementDirection.UP,2000);
	  e.swipe(SwipeElementDirection.UP, 5, 5, 2000);
	  e.swipe(SwipeElementDirection.DOWN,2000);
	  e.swipe(SwipeElementDirection.DOWN, 5, 5, 2000);
	  e.swipe(SwipeElementDirection.LEFT,2000);
	  e.swipe(SwipeElementDirection.LEFT, 5, 5, 2000);
	  e.swipe(SwipeElementDirection.RIGHT,2000);
	  e.swipe(SwipeElementDirection.RIGHT, 5, 5, 2000);
  }

  @AfterClass
  public static void afterClass(){
    if (service != null)
      service.stop();
  }
}
