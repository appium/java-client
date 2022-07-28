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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class AndroidElementTest extends BaseAndroidTest {

    @BeforeEach public void setup() {
        Activity activity = new Activity("io.appium.android.apis", ".ApiDemos");
        driver.startActivity(activity);
    }


    @Test public void findByAccessibilityIdTest() {
        assertNotEquals(driver.findElement(By.id("android:id/content"))
            .findElement(AppiumBy.accessibilityId("Graphics")).getText(), null);
        assertEquals(driver.findElement(By.id("android:id/content"))
            .findElements(AppiumBy.accessibilityId("Graphics")).size(), 1);
    }

    @Test public void findByAndroidUIAutomatorTest() {
        assertNotEquals(driver.findElement(By.id("android:id/content"))
            .findElement(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        assertNotEquals(driver.findElement(By.id("android:id/content"))
            .findElements(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        assertNotEquals(driver.findElement(By.id("android:id/content"))
            .findElements(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void replaceValueTest() {
        String originalValue = "original value";

        Activity activity = new Activity("io.appium.android.apis", ".view.Controls1");
        driver.startActivity(activity);
        WebElement editElement = driver
            .findElement(AppiumBy.androidUIAutomator("resourceId(\"io.appium.android.apis:id/edit\")"));
        editElement.sendKeys(originalValue);
        assertEquals(originalValue, editElement.getText());
        String replacedValue = "replaced value";
        driver.replaceElementValue((RemoteWebElement) editElement, replacedValue);
        assertEquals(replacedValue, editElement.getText());
    }

    @Test public void scrollingToSubElement() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        WebElement list = driver.findElement(By.id("android:id/list"));
        WebElement radioGroup = list
                .findElement(AppiumBy
                        .androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                        + "new UiSelector().text(\"Radio Group\"));"));
        assertNotNull(radioGroup.getLocation());
    }

    @Test public void setValueTest() {
        String value = "new value";

        Activity activity = new Activity("io.appium.android.apis", ".view.Controls1");
        driver.startActivity(activity);
        WebElement editElement = driver
            .findElement(AppiumBy.androidUIAutomator("resourceId(\"io.appium.android.apis:id/edit\")"));
        editElement.sendKeys(value);
        assertEquals(value, editElement.getText());
    }
}
