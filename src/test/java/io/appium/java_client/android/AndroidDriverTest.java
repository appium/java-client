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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.AppiumSetting;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;

import java.io.File;
import java.util.Map;

public class AndroidDriverTest extends BaseAndroidTest {

    @Test public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertTrue(time.length() == 28);
    }

    @Test public void isAppInstalledTest() {
        assertTrue(driver.isAppInstalled("com.example.android.apis"));
    }

    @Test public void isAppNotInstalledTest() {
        assertFalse(driver.isAppInstalled("foo"));
    }

    @Test public void closeAppTest() throws InterruptedException {
        driver.closeApp();
        driver.launchApp();
        assertEquals(".ApiDemos", driver.currentActivity());
    }

    @Test public void pushFileTest() {
        byte[] data = Base64.encodeBase64(
            "The eventual code is no more than the deposit of your understanding. ~E. W. Dijkstra"
                .getBytes());
        driver.pushFile("/data/local/tmp/remote.txt", data);
        byte[] returnData = driver.pullFile("/data/local/tmp/remote.txt");
        String returnDataDecoded = new String(Base64.decodeBase64(returnData));
        assertEquals(
            "The eventual code is no more than the deposit of your understanding. ~E. W. Dijkstra",
            returnDataDecoded);
    }

    @Test public void pushTempFileTest() throws Exception {
        File temp = File.createTempFile("Temp_", "_test");
        try {
            FileUtils.writeStringToFile(temp, "The eventual code is no "
                + "more than the deposit of your understanding. ~E. W. Dijkstra", "UTF-8", true);
            driver.pushFile("/data/local/tmp/remote2.txt", temp);
            byte[] returnData = driver.pullFile("/data/local/tmp/remote2.txt");
            String returnDataDecoded = new String(Base64.decodeBase64(returnData));
            assertEquals(
                "The eventual code is no more than the deposit of "
                    + "your understanding. ~E. W. Dijkstra",
                returnDataDecoded);
        } finally {
            FileUtils.forceDelete(temp);
        }
    }

    @Test public void ignoreUnimportantViews() {
        driver.ignoreUnimportantViews(true);
        boolean ignoreViews =
            driver.getSettings().get(AppiumSetting.IGNORE_UNIMPORTANT_VIEWS.toString())
                .getAsBoolean();
        assertTrue(ignoreViews);
        driver.ignoreUnimportantViews(false);
        ignoreViews = driver.getSettings().get(AppiumSetting.IGNORE_UNIMPORTANT_VIEWS.toString())
            .getAsBoolean();
        assertFalse(ignoreViews);
    }

    @Test public void toggleLocationServicesTest() {
        driver.toggleLocationServices();
    }

    @Test public void geolocationTest() {
        Location location = new Location(45, 45, 100);
        driver.setLocation(location);
    }

    @Test public void orientationTest() {
        assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
        driver.rotate(ScreenOrientation.LANDSCAPE);
        assertEquals(ScreenOrientation.LANDSCAPE, driver.getOrientation());
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test public void lockTest() {
        driver.lockDevice();
        assertEquals(true, driver.isLocked());
        driver.unlockDevice();
        assertEquals(false, driver.isLocked());
    }

    @Test public void runAppInBackgroundTest() {
        long time = System.currentTimeMillis();
        driver.runAppInBackground(4);
        long timeAfter = System.currentTimeMillis();
        assert (timeAfter - time > 3000);
    }

    @Test public void pullFileTest() {
        byte[] data =
            driver.pullFile("data/system/registered_services/android.content.SyncAdapter.xml");
        assert (data.length > 0);
    }

    @Test public void resetTest() {
        driver.resetApp();
    }

    @Test public void endTestCoverage() {
        driver.endTestCoverage("android.intent.action.MAIN", "");
    }

    @Test public void getDeviceUDIDTest() {
        String deviceSerial = driver.getSessionDetails().get("deviceUDID").toString();
        assertNotNull(deviceSerial);
    }

    @Test public void getSessionMapData() {
        Map<?,?> map = (Map<?, ?>) driver.getSessionDetails().get("desired");
        assertNotEquals(map.size(), 0);
    }
}
