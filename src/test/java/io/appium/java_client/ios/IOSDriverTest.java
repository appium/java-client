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

import static io.appium.java_client.TestUtils.waitUntilTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.appium.java_client.MobileElement;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.remote.HideKeyboardStrategy;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IOSDriverTest extends AppIOSTest {

    @Test
    public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertFalse(time.isEmpty());
    }

    @Test public void resetTest() {
        driver.resetApp();
    }

    @Test public void hideKeyboardWithParametersTest() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("IntegerA")))
                .click();
        driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
    }

    @Ignore
    @Test public void geolocationTest() {
        Location location = new Location(45, 45, 100);
        try {
            driver.setLocation(location);
        } catch (Exception e) {
            fail("Not able to set location");
        }
    }

    @Test public void orientationTest() {
        assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
        driver.rotate(ScreenOrientation.LANDSCAPE);
        assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test public void lockTest() {
        try {
            driver.lockDevice();
            assertTrue(driver.isDeviceLocked());
        } finally {
            driver.unlockDevice();
            assertFalse(driver.isDeviceLocked());
        }
    }

    @Test public void pullFileTest() {
        byte[] data = driver.pullFile(String.format("@%s/TestApp", BUNDLE_ID));
        assertThat(data.length, greaterThan(0));
    }

    @Test public void keyboardTest() {
        MobileElement element = driver.findElementById("IntegerA");
        element.click();
        assertTrue(driver.isKeyboardShown());
    }

    @Test public void putAppIntoBackgroundAndRestoreTest() {
        final long msStarted = System.currentTimeMillis();
        driver.runAppInBackground(Duration.ofSeconds(4));
        assertThat(System.currentTimeMillis() - msStarted, greaterThan(3000L));
    }

    @Test public void applicationsManagementTest() {
        assertThat(driver.queryAppState(BUNDLE_ID), equalTo(ApplicationState.RUNNING_IN_FOREGROUND));
        driver.runAppInBackground(Duration.ofSeconds(-1));
        waitUntilTrue(
                () -> driver.queryAppState(BUNDLE_ID).ordinal() < ApplicationState.RUNNING_IN_FOREGROUND.ordinal(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.activateApp(BUNDLE_ID);
        waitUntilTrue(
                () -> driver.queryAppState(BUNDLE_ID) == ApplicationState.RUNNING_IN_FOREGROUND,
                Duration.ofSeconds(10), Duration.ofSeconds(1));
    }

    @Test public void putAIntoBackgroundWithoutRestoreTest() {
        waitUntilTrue(() -> !driver.findElementsById("IntegerA").isEmpty(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.runAppInBackground(Duration.ofSeconds(-1));
        waitUntilTrue(() -> driver.findElementsById("IntegerA").isEmpty(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.activateApp(BUNDLE_ID);
    }

    @Ignore
    @Test public void touchIdTest() {
        driver.toggleTouchIDEnrollment(true);
        driver.performTouchID(true);
        driver.performTouchID(false);
        //noinspection SimplifiableAssertion
        assertEquals(true, true);
    }
}
