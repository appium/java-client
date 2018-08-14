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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.appium.java_client.appmanagement.ApplicationState;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AndroidDriverTest extends BaseAndroidTest {

    @Test public void sendSMSTest() {
        try {
            driver.sendSMS("11111111", "call");
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void gsmCallTest() {
        try {
            driver.makeGsmCall("11111111", GsmCallActions.CALL);
            driver.makeGsmCall("11111111", GsmCallActions.ACCEPT);
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void toggleWiFi() {
        try {
            driver.toggleWifi();
        } catch (Exception e) {
            fail("Not able to toggle wifi");
        }
    }

    @Test public void toggleAirplane() {
        try {
            driver.toggleAirplaneMode();
        } catch (Exception e) {
            fail("Not able to toggle airplane mode");
        }
    }

    @Test public void toggleData() {
        try {
            driver.toggleData();
        } catch (Exception e) {
            fail("Not able to toggle data");
        }
    }

    @Test public void gsmSignalStrengthTest() {
        try {
            driver.setGsmSignalStrength(GsmSignalStrength.GREAT);
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void gsmVoiceTest() {
        try {
            driver.setGsmVoice(GsmVoiceState.OFF);
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void networkSpeedTest() {
        try {
            driver.setNetworkSpeed(NetworkSpeed.EDGE);
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void powerTest() {
        try {
            driver.setPowerCapacity(100);
            driver.setPowerAC(PowerACState.OFF);
        } catch (Exception e) {
            fail("method works only in emulators");
        }
    }

    @Test public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertFalse(time.isEmpty());
    }

    @Test public void isAppInstalledTest() {
        assertTrue(driver.isAppInstalled("com.example.android.apis"));
    }

    @Test public void isAppNotInstalledTest() {
        assertFalse(driver.isAppInstalled("foo"));
    }

    @Test public void closeAppTest() {
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
        String returnDataDecoded = new String(returnData);
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
            String returnDataDecoded = new String(returnData);
            assertEquals(
                "The eventual code is no more than the deposit of "
                    + "your understanding. ~E. W. Dijkstra",
                returnDataDecoded);
        } finally {
            FileUtils.forceDelete(temp);
        }
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
        try {
            driver.lockDevice();
            assertTrue(driver.isDeviceLocked());
        } finally {
            driver.unlockDevice();
            assertFalse(driver.isDeviceLocked());
        }
    }

    @Test public void runAppInBackgroundTest() {
        long time = System.currentTimeMillis();
        driver.runAppInBackground(Duration.ofSeconds(4));
        long timeAfter = System.currentTimeMillis();
        assert (timeAfter - time > 3000);
    }

    @Test public void testApplicationsManagement() throws InterruptedException {
        String appId = driver.getCurrentPackage();
        assertThat(driver.queryAppState(appId), equalTo(ApplicationState.RUNNING_IN_FOREGROUND));
        Thread.sleep(500);
        driver.runAppInBackground(Duration.ofSeconds(-1));
        assertThat(driver.queryAppState(appId), lessThan(ApplicationState.RUNNING_IN_FOREGROUND));
        Thread.sleep(500);
        driver.activateApp(appId);
        assertThat(driver.queryAppState(appId), equalTo(ApplicationState.RUNNING_IN_FOREGROUND));
    }

    @Test public void pullFileTest() {
        byte[] data =
            driver.pullFile("/data/system/users/userlist.xml");
        assert (data.length > 0);
    }

    @Test public void resetTest() {
        driver.resetApp();
    }

    @Test public void endTestCoverage() {
        driver.endTestCoverage("android.intent.action.MAIN", "");
    }

    @Test public void getDeviceUDIDTest() {
        String deviceSerial = driver.getSessionDetail("deviceUDID").toString();
        assertNotNull(deviceSerial);
    }

    @Test public void getSessionMapData() {
        Map<?,?> map = (Map<?, ?>) driver.getSessionDetail("desired");
        assertNotEquals(map.size(), 0);
    }
    
    @Test public void deviceDetailsAndKeyboardTest() {
        assertFalse(driver.isKeyboardShown());
        assertNotNull(driver.getDisplayDensity());
        assertNotEquals(0, driver.getSystemBars().size());
    }

    @Test public void getSupportedPerformanceDataTypesTest() {
        driver.startActivity(new Activity(APP_ID, ".ApiDemos"));

        List<String> dataTypes = new ArrayList<>();
        dataTypes.add("cpuinfo");
        dataTypes.add("memoryinfo");
        dataTypes.add("batteryinfo");
        dataTypes.add("networkinfo");

        List<String> supportedPerformanceDataTypes = driver.getSupportedPerformanceDataTypes();

        assertEquals(4, supportedPerformanceDataTypes.size());

        for ( int i = 0 ; i < supportedPerformanceDataTypes.size() ;  ++i) {
            assertEquals(dataTypes.get(i), supportedPerformanceDataTypes.get(i));
        }


    }

    @Test public void getPerformanceDataTest() throws Exception {

        driver.startActivity(new Activity(APP_ID, ".ApiDemos"));

        List<String> supportedPerformanceDataTypes = driver.getSupportedPerformanceDataTypes();

        for (String dataType : supportedPerformanceDataTypes) {

            List<List<Object>> valueTable = driver.getPerformanceData("com.example.android.apis", dataType, 60000);

            for (int j = 1; j < valueTable.size(); ++j) {
                assertEquals(valueTable.subList(0, 0).size(), valueTable.subList(j, j).size());
            }
        }

    }

    @Test public void getCurrentPackageTest() {
        assertEquals(APP_ID, driver.getCurrentPackage());
    }

}
