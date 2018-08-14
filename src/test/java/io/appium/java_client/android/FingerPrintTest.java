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

import static io.appium.java_client.MobileBy.AndroidUIAutomator;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.By.id;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FingerPrintTest {
    private static AppiumDriverLocalService service;
    private static AndroidDriver<AndroidElement> driver;

    private static void initDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", "Settings");
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(15, SECONDS);
    }

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new ExceptionInInitializerError("An appium server node is not started!");
        }
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() {
        if (service != null) {
            service.stop();
        }
    }

    private AndroidElement findElementByText(String text) {
        return driver.findElements(id("android:id/title")).stream().filter(androidElement ->
                text.equals(androidElement.getText())).findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("There is no element with the text '%s'", text)));
    }

    private void clickNext() {
        driver.findElementById("com.android.settings:id/next_button").click();
    }

    private void clickFingerPrintNext() {
        driver.findElementById("com.android.settings:id/fingerprint_next_button").click();
    }

    private void clickOKInPopup() {
        driver.findElementById("android:id/button1").click();
    }

    private void enterPasswordAndContinue() {
        driver.findElementById("com.android.settings:id/password_entry")
                .sendKeys("1234\n");
    }

    private void clickOnSecurity() {
        driver.findElement(AndroidUIAutomator("new UiScrollable(new UiSelector()"
                + ".scrollable(true)).scrollIntoView("
                + "new UiSelector().text(\"Security & location\"));")).click();
    }

    /**
     * enable system security which is required for finger print activation.
     */
    @Before
    public void before() {
        initDriver();
        clickOnSecurity();
        findElementByText("Screen lock").click();
        findElementByText("PIN").click();
        enterPasswordAndContinue();
        enterPasswordAndContinue();
    }

    /**
     * add a new finger print to security.
     */
    @Test
    public void fingerPrintTest() {
        findElementByText("Fingerprint").click();
        clickFingerPrintNext();
        enterPasswordAndContinue();

        driver.fingerPrint(1234);
        driver.fingerPrint(1234);
        driver.fingerPrint(1234);
        try {
            clickNext();
        } catch (Exception e) {
            Assert.fail("fingerprint command fail to execute");
        }
    }

    /**
     * disabling pin lock mode.
     */
    @After
    public void after() {
        driver.quit();

        initDriver();
        clickOnSecurity();

        findElementByText("Screen lock").click();

        enterPasswordAndContinue();
        findElementByText("None").click();
        clickOKInPopup();
        driver.quit();
    }
}
