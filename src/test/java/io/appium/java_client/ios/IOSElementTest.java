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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IOSElementTest extends BaseIOSTest {

    @Test public void findByAccessibilityIdTest() {
        assertNotEquals(driver.findElementsByClassName("UIAWindow").
            get(0).
            findElementByAccessibilityId("ComputeSumButton").getText(), null);
        assertNotEquals(driver.findElementsByClassName("UIAWindow").get(0)
            .findElementsByAccessibilityId("ComputeSumButton").size(), 0);
    }

    @Test public void findByByIosUIAutomationTest() {
        assertNotEquals(((IOSElement) driver.findElementsByClassName("UIAWindow").
            get(0)).
            findElementByIosUIAutomation(".elements().withName(\"Answer\")").getText(), null);
        assertNotEquals(((IOSElement) driver.findElementsByClassName("UIAWindow").
            get(0)).
            findElementsByIosUIAutomation(".elements().withName(\"Answer\")").size(), 0);
    }

    @Test public void setValueTest() {
        IOSElement slider = (IOSElement) driver.findElementByClassName("UIASlider");
        slider.setValue("0%");
        assertEquals("0%", slider.getAttribute("value"));
    }
}
