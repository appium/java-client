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

import org.junit.Before;
import org.junit.Test;

public class AndroidActivityTest extends BaseAndroidTest {

    @Before public void setUp() throws Exception {
        driver.startActivity("io.appium.android.apis", ".ApiDemos");
    }

    @Test public void startActivityInThisAppTestCase() {
        driver.startActivity("io.appium.android.apis",
            ".accessibility.AccessibilityNodeProviderActivity");
        assertEquals(driver.currentActivity(),
            ".accessibility.AccessibilityNodeProviderActivity");
    }

    @Test public void startActivityWithWaitingAppTestCase() {
        driver.startActivity("io.appium.android.apis",
            ".accessibility.AccessibilityNodeProviderActivity",
            "io.appium.android.apis", ".accessibility.AccessibilityNodeProviderActivity");
        assertEquals(driver.currentActivity(),
            ".accessibility.AccessibilityNodeProviderActivity");
    }

    @Test public void startActivityInNewAppTestCase() {
        driver.startActivity("com.android.contacts", ".ContactsListActivity");
        assertEquals(driver.currentActivity(), ".ContactsListActivity");
        driver.pressKeyCode(AndroidKeyCode.BACK);
        assertEquals(driver.currentActivity(), ".ContactsListActivity");
    }

    @Test public void startActivityInNewAppTestCaseWithoutClosingApp() {
        driver.startActivity("io.appium.android.apis",
            ".accessibility.AccessibilityNodeProviderActivity");
        assertEquals(driver.currentActivity(), ".accessibility.AccessibilityNodeProviderActivity");
        driver.startActivity("com.android.contacts", ".ContactsListActivity",
            "com.android.contacts", ".ContactsListActivity", false);
        assertEquals(driver.currentActivity(), ".ContactsListActivity");
        driver.pressKeyCode(AndroidKeyCode.BACK);
        assertEquals(driver.currentActivity(),
            ".accessibility.AccessibilityNodeProviderActivity");
    }
}
