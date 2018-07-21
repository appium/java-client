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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class KeyCodeTest extends BaseAndroidTest {
    private static final By PRESS_RESULT_VIEW = By.id("io.appium.android.apis:id/text");

    @Before
    public void setUp() {
        //Activity not found error
        final Activity activity = new Activity(driver.getCurrentPackage(), ".text.KeyEventText");
        driver.startActivity(activity);
    }

    @Test
    public void pressKeyCodeTest() {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        assertThat(driver.findElement(PRESS_RESULT_VIEW).getText(),
                containsString(String.format("KEYCODE_%s", AndroidKey.ENTER.name())));
    }

    @Test
    public void pressKeyCodeWithMetastateTest() {
        driver.pressKey(new KeyEvent(AndroidKey.SPACE)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        final String state = driver.findElement(PRESS_RESULT_VIEW).getText();
        assertThat(state, containsString(String.format("KEYCODE_%s", AndroidKey.SPACE.name())));
        assertThat(state, containsString(String.format("META_%s", KeyEventMetaModifier.SHIFT_ON.name())));
    }

    @Test
    public void pressKeyAndGenerateIMEActionTest() {
        driver.pressKey(new KeyEvent()
                .withKey(AndroidKey.ENTER)
                .withFlag(KeyEventFlag.SOFT_KEYBOARD)
                .withFlag(KeyEventFlag.KEEP_TOUCH_MODE)
                .withFlag(KeyEventFlag.EDITOR_ACTION));
        final String state = driver.findElement(PRESS_RESULT_VIEW).getText();
        // This event won't update the view
        assertTrue(state.isEmpty());
    }

    @Test
    public void longPressKeyCodeTest() {
        driver.longPressKey(new KeyEvent(AndroidKey.SPACE));
        final String state = driver.findElement(PRESS_RESULT_VIEW).getText();
        assertThat(state, containsString(String.format("KEYCODE_%s", AndroidKey.SPACE.name())));
        assertThat(state, containsString(String.format("flags=0x%s",
                Integer.toHexString(KeyEventFlag.LONG_PRESS.getValue()))));
    }

    @Test
    public void longPressKeyCodeWithMetastateTest() {
        driver.longPressKey(new KeyEvent(AndroidKey.SPACE)
                .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
        final String state = driver.findElement(PRESS_RESULT_VIEW).getText();
        assertThat(state, containsString(String.format("KEYCODE_%s", AndroidKey.SPACE.name())));
        assertThat(state, containsString(String.format("META_%s", KeyEventMetaModifier.SHIFT_ON.name())));
        assertThat(state, containsString(String.format("flags=0x%s",
                Integer.toHexString(KeyEventFlag.LONG_PRESS.getValue()))));
    }
}
