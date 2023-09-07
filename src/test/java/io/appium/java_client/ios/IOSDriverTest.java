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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.appmanagement.ApplicationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpMethod;

import java.time.Duration;

import static io.appium.java_client.TestUtils.waitUntilTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class IOSDriverTest extends AppIOSTest {
    @BeforeEach
    public void setupEach() {
        if (driver.queryAppState(BUNDLE_ID).ordinal() < ApplicationState.RUNNING_IN_FOREGROUND.ordinal()) {
            driver.activateApp(BUNDLE_ID);
        }
    }

    @Test
    public void addCustomCommandTest() {
        driver.addCommand(HttpMethod.GET, "/sessions", "getSessions");
        final Response getSessions = driver.execute("getSessions");
        assertNotNull(getSessions.getSessionId());
    }

    @Test
    public void addCustomCommandWithSessionIdTest() {
        driver.addCommand(HttpMethod.POST, "/session/" + driver.getSessionId() + "/appium/app/launch",
                "launchApplication");
        final Response launchApplication = driver.execute("launchApplication");
        assertNotNull(launchApplication.getSessionId());
    }

    @Test
    public void addCustomCommandWithElementIdTest() {
        WebElement intA = driver.findElement(By.id("IntegerA"));
        intA.clear();
        driver.addCommand(HttpMethod.POST,
                String.format("/session/%s/appium/element/%s/value", driver.getSessionId(),
                        ((RemoteWebElement) intA).getId()), "setNewValue");
        final Response setNewValue = driver.execute("setNewValue",
                ImmutableMap.of("id", ((RemoteWebElement) intA).getId(), "text", "8"));
        assertNotNull(setNewValue.getSessionId());
    }

    @Test
    public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertFalse(time.isEmpty());
    }

    @Test public void resetTest() {
        driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", BUNDLE_ID));
        driver.executeScript("mobile: activateApp", ImmutableMap.of("bundleId", BUNDLE_ID));
    }

    @Disabled
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
        WebElement element = driver.findElement(By.id("IntegerA"));
        element.click();
        assertTrue(driver.isKeyboardShown());
    }

    @Disabled("The app crashes when restored from the background")
    @Test
    public void putAppIntoBackgroundAndRestoreTest() {
        final long msStarted = System.currentTimeMillis();
        driver.runAppInBackground(Duration.ofSeconds(4));
        assertThat(System.currentTimeMillis() - msStarted, greaterThan(3000L));
    }

    @Disabled("The app crashes when restored from the background")
    @Test
    public void applicationsManagementTest() {
        driver.runAppInBackground(Duration.ofSeconds(-1));
        waitUntilTrue(
                () -> driver.queryAppState(BUNDLE_ID).ordinal() < ApplicationState.RUNNING_IN_FOREGROUND.ordinal(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.activateApp(BUNDLE_ID);
        waitUntilTrue(
                () -> driver.queryAppState(BUNDLE_ID) == ApplicationState.RUNNING_IN_FOREGROUND,
                Duration.ofSeconds(10), Duration.ofSeconds(1));
    }

    @Disabled("The app crashes when restored from the background")
    @Test
    public void putAIntoBackgroundWithoutRestoreTest() {
        waitUntilTrue(() -> !driver.findElements(By.id("IntegerA")).isEmpty(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.runAppInBackground(Duration.ofSeconds(-1));
        waitUntilTrue(() -> driver.findElements(By.id("IntegerA")).isEmpty(),
                Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.activateApp(BUNDLE_ID);
    }

    @Disabled
    @Test public void touchIdTest() {
        driver.toggleTouchIDEnrollment(true);
        driver.performTouchID(true);
        driver.performTouchID(false);
        //noinspection SimplifiableAssertion,EqualsWithItself
        assertEquals(true, true);
    }
}
