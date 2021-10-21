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

import org.junit.Test;

import io.appium.java_client.MobileBy;

public class IOSSearchingTest extends AppIOSTest {

    @Test public void findByAccessibilityIdTest() {
        assertNotEquals(driver
                .findElement(MobileBy.AccessibilityId("ComputeSumButton"))
                .getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy.AccessibilityId("ComputeSumButton"))
                .size(), 0);
    }

    @Test public void findByByIosPredicatesTest() {
        assertNotEquals(driver
                .findElement(MobileBy.iOSNsPredicateString("name like 'Answer'"))
                .getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy.iOSNsPredicateString("name like 'Answer'"))
                .size(), 0);
    }

    @Test public void findByByIosClassChainTest() {
        assertNotEquals(driver
                .findElement(MobileBy.iOSClassChain("**/XCUIElementTypeButton"))
                .getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy.iOSClassChain("**/XCUIElementTypeButton"))
                .size(), 0);
    }
}
