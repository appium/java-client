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
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileBy;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IOSAlertTest extends BaseIOSTest {

    @Test public void acceptAlertTest() {
        driver.findElement(MobileBy
            .IosUIAutomation(".elements().withName(\"show alert\")")).click();
        WebDriverWait wating = new WebDriverWait(driver, 10000);
        wating.until(alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @Test public void dismissAlertTest() {
        driver.findElement(MobileBy
            .IosUIAutomation(".elements().withName(\"show alert\")")).click();
        WebDriverWait wating = new WebDriverWait(driver, 10000);
        wating.until(alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    @Test public void getAlertTextTest() {
        driver.findElement(MobileBy
            .IosUIAutomation(".elements().withName(\"show alert\")")).click();
        WebDriverWait wating = new WebDriverWait(driver, 10000);
        wating.until(alertIsPresent());
        assertFalse(StringUtils.isBlank(driver.switchTo().alert().getText()));
    }
}
