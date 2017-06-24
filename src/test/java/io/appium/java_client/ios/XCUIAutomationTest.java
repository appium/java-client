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

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.DeviceRotation;

import java.time.Duration;

public class XCUIAutomationTest extends AppXCUITTest {

    @After public void afterMethod() {
        driver.rotate(new DeviceRotation(0, 0, 0));
    }

    @Test public void testLandscapeRightRotation() {
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 90);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test public void testLandscapeLeftRotation() {
        DeviceRotation landscapeLeftRotation = new DeviceRotation(0, 0, 270);
        driver.rotate(landscapeLeftRotation);
        assertEquals(driver.rotation(), landscapeLeftRotation);
    }

    @Test public void testTouchId() {
        try {
            driver.toggleTouchIDEnrollment();
            driver.performTouchID(true);
            driver.performTouchID(false);
            assertEquals(true, true);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test public void testPutIntoBackgroundAndRestore() {
        final long msStarted = System.currentTimeMillis();
        driver.runAppInBackground(Duration.ofSeconds(4));
        assertThat(System.currentTimeMillis() - msStarted, greaterThan(3000L));
    }

    @Test public void testPutIntoBackgroundWithoutRestore() {
        assertThat(driver.findElementsById("IntegerA"), is(not(empty())));
        driver.runAppInBackground(Duration.ofSeconds(-1));
        assertThat(driver.findElementsById("IntegerA"), is(empty()));
    }

    @Test public void doubleTapTest() {
        IOSElement firstField = driver.findElementById("IntegerA");
        firstField.sendKeys("2");

        IOSTouchAction iosTouchAction = new IOSTouchAction(driver);
        iosTouchAction.doubleTap(firstField);
        IOSElement editingMenu = driver.findElementByClassName("UIAEditingMenu");
        assertNotNull(editingMenu);
    }
}
