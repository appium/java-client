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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class AndroidGestureTest extends BaseAndroidTest {

    @Test public void singleTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        Point point =
            driver.findElementById("io.appium.android.apis:id/button_toggle").getLocation();
        driver.tap(1, point.x + 20, point.y + 30, 1000);
        assertEquals("ON" ,driver
            .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void singleElementTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        driver.tap(1, driver.findElementById("io.appium.android.apis:id/button_toggle"), 1000);
        assertEquals("ON" ,driver
            .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void multiTapActionTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.ChronometerDemo");
        AndroidElement chronometer =
            driver.findElementById("io.appium.android.apis:id/chronometer");

        TouchAction startStop = new TouchAction(driver)
            .tap(driver.findElementById("io.appium.android.apis:id/start")).waitAction(2000)
            .tap(driver.findElementById("io.appium.android.apis:id/stop"));

        MultiTouchAction m1 = new MultiTouchAction(driver).add(startStop);
        m1.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());

        TouchAction reset = new TouchAction(driver)
            .tap(driver.findElementById("io.appium.android.apis:id/reset"));
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

    @Test public void reusableTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        AndroidElement element = driver.findElementById("io.appium.android.apis:id/button_toggle");
        TouchAction tap = new TouchAction(driver).tap(element);

        driver.performTouchAction(tap);
        assertEquals("ON" ,driver
            .findElementById("io.appium.android.apis:id/button_toggle").getText());

        driver.performTouchAction(tap);
        assertEquals("OFF" ,driver
            .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void verticalSwipingTest() throws Exception {
        driver.findElementByAccessibilityId("Views").click();
        AndroidElement listView = driver.findElementByClassName("android.widget.ListView");
        MobileElement textView = driver.findElementById("android:id/text1");

        String originalText = textView.getText();

        listView.swipe(SwipeElementDirection.UP, 20, 15, 1000);
        assertNotEquals(originalText, textView.getText());

        listView.swipe(SwipeElementDirection.DOWN, 20, 15, 1000);
        assertEquals(originalText, textView.getText());
    }

    @Test public void horizontalSwipingTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Gallery1");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        int originalImageCount = gallery
            .findElementsByClassName("android.widget.ImageView").size();

        gallery.swipe(SwipeElementDirection.LEFT, 5, 5, 2000);
        assertNotEquals(originalImageCount, gallery
            .findElementsByClassName("android.widget.ImageView").size());

        gallery.swipe(SwipeElementDirection.RIGHT, 5, 5, 2000);
        assertEquals(originalImageCount, gallery
            .findElementsByClassName("android.widget.ImageView").size());
    }
}
