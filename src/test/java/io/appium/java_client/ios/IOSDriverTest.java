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

import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Test Mobile Driver features
 */
public class IOSDriverTest {

    private static AppiumDriverLocalService service;
    private IOSDriver<MobileElement> driver;

    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @AfterClass public static void afterClass() {
        if (service != null)
            service.stop();
    }

    @Before public void setup() throws Exception {
        if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.4");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<MobileElement>(service.getUrl(), capabilities);
    }

    @After public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * @Test TODO There is no ability to check this function usibg simulators. When CI will have been set up then this test will be returned
     */
    public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertTrue(time.length() == 28);
    }

    @Test public void getStringsTest() {
        Map<String, String> strings = driver.getAppStringMap();
        assertTrue(strings.size() > 10);
    }

    @Test public void getStringsWithLanguageTest() {
        Map<String, String> strings = driver.getAppStringMap("en");
        assertTrue(strings.size() > 10);
    }

    @Test public void getStringsWithLanguageAndStringFileTest() {
        Map<String, String> strings = driver.getAppStringMap("en", "Localizable.strings");
        assertTrue(strings.size() > 10);
    }

    @Test public void getStringsWithUnknownStringFileTest() {
        Map<String, String> strings = driver.getAppStringMap("en", "Unknown.strings");
        assertTrue(strings.size() > 10);
    }

    @Test public void resetTest() {
        driver.resetApp();
    }

    @Test public void namedTextFieldTest() {
        MobileElement element =
            driver.findElementByAccessibilityId("Text Fields, AAPLTextFieldViewController");
        element.click();
        element = driver.getNamedTextField("DEFAULT");
        ((IOSElement) element).setValue("Grace Hopper");
        assertEquals("Grace Hopper", element.getText());
    }

    @Test public void hideKeyboardWithParametersTest() {
        MobileElement element =
            driver.findElementByAccessibilityId("Text Fields, AAPLTextFieldViewController");
        element.click();
        element = driver.findElementByAccessibilityId("DEFAULT");
        element.click();
        driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
    }

    @Test public void scrollToTest() {
        MobileElement searchBar = driver.findElementByName("Search Bars");
        Point before = searchBar.getLocation();
        driver.scrollTo("Search Ba");
        Point after = searchBar.getLocation();
        assertNotEquals(before, after);
    }

    @Test public void scrollToExactTest() {
        MobileElement searchBar = driver.findElementByName("Search Bars");
        Point before = searchBar.getLocation();
        driver.scrollToExact("Search Bars");
        Point after = searchBar.getLocation();
        assertNotEquals(before, after);
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
