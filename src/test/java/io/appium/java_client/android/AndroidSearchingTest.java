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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class AndroidSearchingTest extends BaseAndroidTest {

    @Before
    public void setup() {
        Activity activity = new Activity("io.appium.android.apis", ".ApiDemos");
        driver.startActivity(activity);
    }

    @Test  public void findByAccessibilityIdTest() {
        assertNotEquals(driver.findElement(AppiumBy.accessibilityId("Graphics")).getText(), null);
        assertEquals(driver.findElements(AppiumBy.accessibilityId("Graphics")).size(), 1);
    }

    @Test  public void findByAndroidUIAutomatorTest() {
        assertNotEquals(driver
                .findElement(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        assertNotEquals(driver
                .findElements(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        assertNotEquals(driver
                .findElements(AppiumBy
                .androidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void findByXPathTest() {
        By byXPath = By.xpath("//android.widget.TextView[contains(@text, 'Animat')]");
        assertNotNull(driver.findElement(byXPath).getText());
        assertEquals(driver.findElements(byXPath).size(), 1);
    }

    @Test public void findScrollable() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        MobileElement radioGroup = driver
                .findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()"
                + ".resourceId(\"android:id/list\")).scrollIntoView("
                + "new UiSelector().text(\"Radio Group\"));"));
        assertNotNull(radioGroup.getLocation());
    }
}
