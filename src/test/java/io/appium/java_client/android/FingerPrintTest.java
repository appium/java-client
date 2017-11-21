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

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class FingerPrintTest {

    private static final String PASSWORD_INPUT_ID = "com.android.settings:id/password_entry";
    private static final String FIRST_IN_LIST_XPATH = "//android.widget.ListView[1]/android.widget.LinearLayout[1]";
    private static AppiumDriverLocalService service;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new ExceptionInInitializerError("An appium server node is not started!");
        }
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() {
        if (service != null) {
            service.stop();
        }
    }

    /**
     * enable system security which is required for finger print activation.
     */
    @Before public void before() throws Exception {
        final AndroidDriver driver = getAndroidDriver("ChooseLockGeneric");
        TimeUnit.SECONDS.sleep(2);
        // clicking the pin lock mode
        driver.findElement(By.xpath("//android.widget.LinearLayout[4]")).click();
        TimeUnit.SECONDS.sleep(2);
        try {
            // line below will throw exception if secure startup window is popped up
            driver.findElementById(PASSWORD_INPUT_ID);
        } catch (NoSuchElementException e) {
            // in secure startup window
            driver.findElementById("com.android.settings:id/encrypt_require_password").click();
            TimeUnit.SECONDS.sleep(2);
            clickOKInPopup(driver);
            clickNext(driver);
        }
        enterPasswordAndContinue(driver);
        enterPasswordAndContinue(driver);
        clickNext(driver);
        driver.quit();
    }

    /**
     * add a new finger print to security.
     */
    @Test public void pressKeyCodeTest() throws InterruptedException {
        final AndroidDriver driver = getAndroidDriver(".fingerprint.FingerprintSettings");
        TimeUnit.SECONDS.sleep(2);
        enterPasswordAndContinue(driver);
        // click add fingerprint
        driver.findElementByXPath(FIRST_IN_LIST_XPATH).click();
        TimeUnit.SECONDS.sleep(2);
        driver.fingerPrint(2);
        TimeUnit.SECONDS.sleep(2);
        try {
            clickNext(driver);
        } catch (Exception e) {
            Assert.fail("fingerprint command fail to execute");
        } finally {
            driver.quit();
        }
    }

    /**
     * disabling pin lock mode.
     */
    @After public void after() throws InterruptedException {
        final AndroidDriver driver = getAndroidDriver("ChooseLockGeneric");
        TimeUnit.SECONDS.sleep(2);
        enterPasswordAndContinue(driver);
        driver.findElementByXPath(FIRST_IN_LIST_XPATH).click();
        TimeUnit.SECONDS.sleep(2);
        clickOKInPopup(driver);
        driver.quit();
    }

    private AndroidDriver getAndroidDriver(String activity) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", activity);
        return new AndroidDriver<AndroidElement>(service.getUrl(), capabilities);
    }

    private void enterPasswordAndContinue(AndroidDriver driver) throws InterruptedException {
        driver.findElementById(PASSWORD_INPUT_ID).sendKeys("1234\n");
        TimeUnit.SECONDS.sleep(2);
    }

    private void clickNext(AndroidDriver driver) throws InterruptedException {
        driver.findElementById("com.android.settings:id/next_button").click();
        TimeUnit.SECONDS.sleep(2);
    }

    private void clickOKInPopup(AndroidDriver driver) throws InterruptedException {
        driver.findElementById("android:id/button1").click();
        TimeUnit.SECONDS.sleep(2);
    }
}
