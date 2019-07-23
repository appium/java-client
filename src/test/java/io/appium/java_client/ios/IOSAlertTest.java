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
import static org.openqa.seleniumone.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileBy;
import org.apache.commons.lang3.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.seleniumone.support.ui.WebDriverWait;

import java.util.function.Supplier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSAlertTest extends AppIOSTest {

    private WebDriverWait waiting = new WebDriverWait(driver, 10000);
    private static final String iOSAutomationText = "show alert";

    @Test public void acceptAlertTest() {
        Supplier<Boolean> acceptAlert = () -> {
            driver.findElement(MobileBy.AccessibilityId(iOSAutomationText)).click();
            waiting.until(alertIsPresent());
            driver.switchTo().alert().accept();
            return true;
        };
        assertTrue(acceptAlert.get());
    }

    @Test public void dismissAlertTest() {
        Supplier<Boolean> dismissAlert = () -> {
            driver.findElement(MobileBy.AccessibilityId(iOSAutomationText)).click();
            waiting.until(alertIsPresent());
            driver.switchTo().alert().dismiss();
            return true;
        };
        assertTrue(dismissAlert.get());
    }

    @Test public void getAlertTextTest() {
        driver.findElement(MobileBy.AccessibilityId(iOSAutomationText)).click();
        waiting.until(alertIsPresent());
        assertFalse(StringUtils.isBlank(driver.switchTo().alert().getText()));
    }
}
