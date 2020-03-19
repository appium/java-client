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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileBy;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSAlertTest extends AppIOSTest {

    private static final long ALERT_TIMEOUT_SECONDS = 5;
    private static final int CLICK_RETRIES = 2;

    private final WebDriverWait waiter = new WebDriverWait(driver, ALERT_TIMEOUT_SECONDS);
    private static final String iOSAutomationText = "show alert";

    private void ensureAlertPresence() {
        int retry = 0;
        // CI might not be performant enough, so we need to retry
        while (true) {
            try {
                driver.findElement(MobileBy.AccessibilityId(iOSAutomationText)).click();
            } catch (WebDriverException e) {
                // ignore
            }
            try {
                waiter.until(alertIsPresent());
                return;
            } catch (TimeoutException e) {
                retry++;
                if (retry >= CLICK_RETRIES) {
                    throw e;
                }
            }
        }
    }

    @After
    public void afterEach() {
        try {
            driver.switchTo().alert().accept();
        } catch (WebDriverException e) {
            // ignore
        }
    }

    @Test
    public void acceptAlertTest() {
        Supplier<Boolean> acceptAlert = () -> {
            ensureAlertPresence();
            driver.switchTo().alert().accept();
            return true;
        };
        assertTrue(acceptAlert.get());
    }

    @Test
    public void dismissAlertTest() {
        Supplier<Boolean> dismissAlert = () -> {
            ensureAlertPresence();
            driver.switchTo().alert().dismiss();
            return true;
        };
        assertTrue(dismissAlert.get());
    }

    @Test
    public void getAlertTextTest() {
        ensureAlertPresence();
        assertFalse(StringUtils.isBlank(driver.switchTo().alert().getText()));
    }
}
