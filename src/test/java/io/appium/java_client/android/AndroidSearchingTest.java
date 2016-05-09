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

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Before;
import org.junit.Test;

public class AndroidSearchingTest extends BaseAndroidTest {

    @Before
    public void setup() throws Exception {
        driver.startActivity("io.appium.android.apis", ".ApiDemos");
    }

    @Test  public void findByAccessibilityIdTest() {
        assertNotEquals(driver.findElement(MobileBy.AccessibilityId("Graphics")).getText(), null);
        assertEquals(driver.findElements(MobileBy.AccessibilityId("Graphics")).size(), 1);
    }

    @Test  public void findByAndroidUIAutomatorTest() {
        assertNotEquals(driver
                .findElement(MobileBy
                .AndroidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy
                .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        assertNotEquals(driver
                .findElements(MobileBy
                .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void findByXPathTest() {
        String byXPath = "//android.widget.TextView[contains(@text, 'Animat')]";
        assertNotNull(driver.findElementByXPath(byXPath).getText());
        assertEquals(driver.findElementsByXPath(byXPath).size(), 1);
    }

    @Test public void findScrollable() {
        driver.findElementByAccessibilityId("Views").click();
        MobileElement radioGroup = driver
                .findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
                + ".resourceId(\"android:id/list\")).scrollIntoView("
                + "new UiSelector().text(\"Radio Group\"));");
        assertNotNull(radioGroup.getLocation());
    }
}
