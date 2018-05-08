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

import static org.junit.Assert.fail;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriverException;

public class KeyCodeTest extends BaseAndroidTest {

    @Before
    public void setUp() {
        driver.resetApp();
    }

    @Test
    public void pressKeyCodeTest() {
        try {
            driver.pressKey(new KeyEvent(AndroidKey.HOME));
        } catch (WebDriverException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void pressKeyCodeWithMetastateTest() {
        try {
            driver.pressKey(new KeyEvent(AndroidKey.SPACE)
                    .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        } catch (WebDriverException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void pressKeyAndGenerateIMEActionTest() {
        try {
            driver.pressKey(new KeyEvent()
                    .withKey(AndroidKey.ENTER)
                    .withFlag(KeyEventFlag.SOFT_KEYBOARD)
                    .withFlag(KeyEventFlag.KEEP_TOUCH_MODE)
                    .withFlag(KeyEventFlag.EDITOR_ACTION));
        } catch (WebDriverException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void longPressKeyCodeTest() {
        try {
            driver.longPressKey(new KeyEvent(AndroidKey.SPACE));
        } catch (WebDriverException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void longPressKeyCodeWithMetastateTest() {
        try {
            driver.longPressKey(new KeyEvent(AndroidKey.SPACE)
                    .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        } catch (WebDriverException e) {
            fail(e.getMessage());
        }
    }
}
