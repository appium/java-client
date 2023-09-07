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

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClipboardTest extends BaseAndroidTest {

    @BeforeEach public void setUp() {
        driver.executeScript("mobile: terminateApp", ImmutableMap.of("appId", APP_ID));
        driver.executeScript("mobile: activateApp", ImmutableMap.of("appId", APP_ID));
    }

    @Test public void verifySetAndGetClipboardText() {
        final String text = "Happy testing";
        driver.setClipboardText(text);
        assertEquals(driver.getClipboardText(), text);
    }
}
