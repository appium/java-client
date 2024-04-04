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

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static io.appium.java_client.AppiumBy.androidUIAutomator;

public class FingerPrintTest {
    private static AppiumDriverLocalService service;
    private static AndroidDriver driver;

    private static void initDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setAppPackage("com.android.settings")
                .setAppActivity("Settings");
        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    /**
     * initialization.
     */
    @BeforeAll public static void beforeClass() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new ExceptionInInitializerError("An appium server node is not started!");
        }
    }

    /**
     * finishing.
     */
    @AfterAll public static void afterClass() {
        if (service != null) {
            service.stop();
        }
    }

    private WebElement findElementByText(String text) {
        return driver.findElements(By.id("android:id/title")).stream().filter(androidElement ->
                text.equals(androidElement.getText())).findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("There is no element with the text '%s'", text)));
    }

    private void clickNext() {
        driver.findElement(By.id("com.android.settings:id/next_button")).click();
    }

    private void clickFingerPrintNext() {
        driver.findElement(By.id("com.android.settings:id/fingerprint_next_button")).click();
    }

    private void clickOKInPopup() {
        driver.findElement(By.id("android:id/button1")).click();
    }

    private void enterPasswordAndContinue() {
        driver.findElement(By.id("com.android.settings:id/password_entry")).sendKeys("1234\n");
    }

    private void clickOnSecurity() {
        driver.findElement(androidUIAutomator("new UiScrollable(new UiSelector()"
                + ".scrollable(true)).scrollIntoView("
                + "new UiSelector().text(\"Security & location\"));")).click();
    }

    /**
     * enable system security which is required for finger print activation.
     */
    @BeforeEach
    public void before() {
        initDriver();
        clickOnSecurity();
        findElementByText("Screen lock").click();
        findElementByText("PIN").click();
        enterPasswordAndContinue();
        enterPasswordAndContinue();
        clickNext();
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
            Assertions.fail("fingerprint command fail to execute");
        }
    }

    /**
     * disabling pin lock mode.
     */
    @AfterEach
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
