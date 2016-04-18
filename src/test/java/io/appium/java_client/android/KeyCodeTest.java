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

import org.junit.Before;
import org.junit.Test;

public class KeyCodeTest extends BaseAndroidTest {

    @Before public void setup() throws Exception {
        driver.resetApp();
    }

    @Test public void pressKeyCodeTest() {
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }

    @Test public void pressKeyCodeWithMetastateTest() {
        driver.pressKeyCode(AndroidKeyCode.SPACE, AndroidKeyMetastate.META_SHIFT_ON);
    }

    @Test public void longPressKeyCodeTest() {
        driver.longPressKeyCode(AndroidKeyCode.HOME);
    }

    @Test public void longPressKeyCodeWithMetastateTest() {
        driver.longPressKeyCode(AndroidKeyCode.HOME, AndroidKeyMetastate.META_SHIFT_ON);
    }
}
