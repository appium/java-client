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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.openqa.selenium.By.className;

public class IOSElementTest extends AppIOSTest {
    private static final By SLIDER_CLASS = className("XCUIElementTypeSlider");

    @Test
    public void setValueTest() {
        driver.findElement(LOGIN_LINK_ID).click();
        driver.findElement(SLIDER_MENU_ITEM_PREDICATE).click();

        var slider = driver.findElement(SLIDER_CLASS);
        var previousValue = slider.getAttribute("value");
        slider.sendKeys("0.5");
        assertNotEquals(slider.getAttribute("value"), previousValue);
    }
}
