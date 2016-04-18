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
import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.HideKeyboardStrategy;

import org.junit.Test;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;

public class IOSDriverTest extends BaseIOSTest {

    //TODO There is no ability to check this function usibg simulators.
    // When CI will have been set up then this test will be returned
    public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertTrue(time.length() == 28);
    }

    @Test public void resetTest() {
        driver.resetApp();
    }

    @Test public void hideKeyboardWithParametersTest() {
        MobileElement element = driver.findElementById("IntegerA");
        element.click();
        driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
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
        driver.lockDevice(20);
    }

    @Test public void pullFileTest() {
        byte[] data = driver.pullFile("Library/AddressBook/AddressBook.sqlitedb");
        assert (data.length > 0);
    }
}
