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

import static org.junit.Assert.assertEquals;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Test;

public class IOSScrollingSearchingTest extends UICatalogIOSTest {

    @Test public void scrollByDriver() {
        MobileElement slider = driver
                .findElement(MobileBy
                        .IosUIAutomation(".tableViews()[0]"
                                + ".scrollToElementWithPredicate(\"name CONTAINS 'Slider'\")"));
        assertEquals(slider.getAttribute("name"), "Sliders");
    }

    @Test public void scrollByElement() {
        MobileElement table = driver.findElement(MobileBy
                .IosUIAutomation(".tableViews()[0]"));
        MobileElement slider = table.findElement(MobileBy
                .IosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS 'Slider'\")"));
        assertEquals(slider.getAttribute("name"), "Sliders");
    }
}
