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

package io.appium.java_client.android;

import io.appium.java_client.*;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test Mobile Driver features
 */
public class AndroidGestureTest {
    private static AppiumDriverLocalService service;
    private static AndroidDriver<AndroidElement> driver;

    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    @AfterClass public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null)
            service.stop();
    }

    @Test public void singleTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        Point point =
            driver.findElementById("io.appium.android.apis:id/button_toggle").getLocation();
        driver.tap(1, point.x + 20, point.y + 30, 1000);
        Assert.assertEquals("ON" ,driver.findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void singleElementTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        driver.tap(1, driver.findElementById("io.appium.android.apis:id/button_toggle"), 1000);
        Assert.assertEquals("ON" ,driver.findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void multiTapActionTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.ChronometerDemo");
        AndroidElement chronometer =
            driver.findElementById("io.appium.android.apis:id/chronometer");

        TouchAction startStop = new TouchAction(driver).
            tap(driver.findElementById("io.appium.android.apis:id/start")).waitAction(2000).
            tap(driver.findElementById("io.appium.android.apis:id/stop"));
        TouchAction reset = new TouchAction(driver).
            tap(driver.findElementById("io.appium.android.apis:id/reset"));

        MultiTouchAction m1 = new MultiTouchAction(driver).add(startStop);
        m1.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());

        MultiTouchAction m2 = new MultiTouchAction(driver).add(startStop).add(reset);
        m2.perform();

        assertEquals("Initial format: 00:00", chronometer.getText());
    }

    @Test public void dragNDropTest() throws Exception  {
        driver.startActivity("io.appium.android.apis", ".view.DragAndDropDemo");
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        TouchAction dragNDrop =
            new TouchAction(driver).longPress(dragDot1).moveTo(dragDot3).release();
        dragNDrop.perform();

        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void zoomAndPinchTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".ApiDemos");
        MobileElement e = driver.findElement(MobileBy.AccessibilityId("App"));
        e.zoom();
        e.pinch();
    }

    /*
    public void elementGestureTest() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        MobileElement e = driver.findElement(MobileBy.AccessibilityId("App"));
        e.tap(1, 1500);
        System.out.println("tap");
        MobileElement e2 = driver.findElementByClassName("android.widget.TextView");
        e2.zoom();
        System.out.println("zoom");
        e2.swipe(SwipeElementDirection.RIGHT, 1000);
        System.out.println("RIGHT");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.RIGHT, 1, 2, 1000);
        System.out.println("RIGHT Left border + 10 Right border - 20");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.LEFT, 1000);
        System.out.println("LEFT");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.LEFT, 1, 2, 1000);
        System.out.println("LEFT Right border - 10 Left border + 20");

        driver.pressKeyCode(AndroidKeyCode.BACK);
        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.DOWN, 1000);
        System.out.println("DOWN");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.DOWN, 1, 2, 1000);
        System.out.println("DOWN Top - 10 Bottom + 20");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.UP, 1000);
        System.out.println("UP");

        e2 = driver.findElementByClassName("android.widget.TextView");
        e2.swipe(SwipeElementDirection.UP, 1, 2, 1000);
        System.out.println("UP Bottom + 10 Top - 20");
    }
    */
}
