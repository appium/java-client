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

import static java.time.Duration.ofSeconds;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.appium.java_client.MobileElement;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.remote.MobileCapabilityType;
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
        new WebDriverWait(driver, ofSeconds(30))
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
        byte[] data = driver.pullFile("@io.appium.TestApp/TestApp");
        assert (data.length > 0);
    }

    @Test public void keyboardTest() {
        MobileElement element = driver.findElementById("IntegerA");
        element.click();
        assertTrue(driver.isKeyboardShown());
    }

    @Test public void putAppIntoBackgroundAndRestoreTest() {
        final long msStarted = System.currentTimeMillis();
        driver.runAppInBackground(ofSeconds(4));
        assertThat(System.currentTimeMillis() - msStarted, greaterThan(3000L));
    }

    @Test public void applicationsManagementTest() throws InterruptedException {
        // This only works since Xcode9
        try {
            if (Double.parseDouble(
                    (String) driver.getCapabilities()
                            .getCapability(MobileCapabilityType.PLATFORM_VERSION)) < 11) {
                return;
            }
        } catch (NumberFormatException | NullPointerException e) {
            return;
        }
        assertThat(driver.queryAppState(BUNDLE_ID), equalTo(ApplicationState.RUNNING_IN_FOREGROUND));
        Thread.sleep(500);
        driver.runAppInBackground(ofSeconds(-1));
        assertThat(driver.queryAppState(BUNDLE_ID), lessThan(ApplicationState.RUNNING_IN_FOREGROUND));
        Thread.sleep(500);
        driver.activateApp(BUNDLE_ID);
        assertThat(driver.queryAppState(BUNDLE_ID), equalTo(ApplicationState.RUNNING_IN_FOREGROUND));
    }

    @Test public void putAIntoBackgroundWithoutRestoreTest() {
        assertThat(driver.findElementsById("IntegerA"), is(not(empty())));
        driver.runAppInBackground(ofSeconds(-1));
        assertThat(driver.findElementsById("IntegerA"), is(empty()));
        driver.launchApp();
    }

    @Ignore
    @Test public void touchIdTest() {
        driver.toggleTouchIDEnrollment(true);
        driver.performTouchID(true);
        driver.performTouchID(false);
        assertEquals(true, true);
    }
}
