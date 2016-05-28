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

import static org.junit.Assert.assertEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.junit.Test;

public class IOSGesturesTest extends BaseIOSTest {


    @Test public void tapTest() {
        ((IOSElement) driver.findElementById("IntegerA")).sendKeys("2");
        ((IOSElement) driver.findElementById("IntegerB")).sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        driver.tap(2, e, 2000);
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
    }

    @Test public void zoomTest() {
        MobileElement e = driver.findElementById("IntegerA");
        driver.zoom(e);
    }

    @Test public void pinchTest() {
        MobileElement e = driver.findElementById("IntegerA");
        driver.pinch(e);
    }

    @Test public void horizontalSwipingTest() {
        MobileElement slider = driver.findElementByClassName("UIASlider");
        slider.swipe(SwipeElementDirection.LEFT, slider.getSize().getWidth() / 2, 0, 3000);
        assertEquals("1%", slider.getAttribute("value"));
        slider.swipe(SwipeElementDirection.RIGHT, 2, 0, 3000);
        assertEquals("100%", slider.getAttribute("value"));
    }
}

