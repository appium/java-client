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

import static org.junit.Assert.assertNotNull;

import io.appium.java_client.MobileElement;
import org.junit.Before;
import org.junit.Test;

public class ScrollTest extends BaseAndroidTest {

    @Before public void setup() throws Exception {
        driver.resetApp();
    }

    @Test public void scrollToTest() {
        driver.findElementByAccessibilityId("Views").click();
        driver.scrollTo("Radio G");
        MobileElement radioGroup = driver.findElementByAccessibilityId("Radio Group");
        assertNotNull(radioGroup.getLocation());
    }

    @Test public void scrollToExactTest() {
        driver.findElementByAccessibilityId("Views").click();
        driver.scrollToExact("Radio Group");
        MobileElement radioGroup = driver.findElementByAccessibilityId("Radio Group");
        assertNotNull(radioGroup.getLocation());
    }
}
