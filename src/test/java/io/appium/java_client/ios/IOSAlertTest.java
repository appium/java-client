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

import io.appium.java_client.AppiumBy;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class IOSAlertTest extends AppIOSTest {

    private static final Duration ALERT_TIMEOUT = Duration.ofSeconds(5);
    private static final int CLICK_RETRIES = 2;
    private static final String IOS_AUTOMATION_TEXT = "show alert";

    private final WebDriverWait waiter = new WebDriverWait(driver, ALERT_TIMEOUT);

    private void ensureAlertPresence() {
        int retry = 0;
        // CI might not be performant enough, so we need to retry
        while (true) {
            try {
                driver.findElement(AppiumBy.accessibilityId(IOS_AUTOMATION_TEXT)).click();
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

    @AfterEach
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
