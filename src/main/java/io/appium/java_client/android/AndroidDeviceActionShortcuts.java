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

import static io.appium.java_client.android.AndroidMobileCommandHelper.longPressKeyCodeCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.pressKeyCodeCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.DeviceActionShortcuts;

public interface AndroidDeviceActionShortcuts extends DeviceActionShortcuts {

    /**
     * Send a key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    default void pressKeyCode(int key) {
        CommandExecutionHelper.execute(this, pressKeyCodeCommand(key));
    }

    /**
     * Send a key event along with an Android metastate to an Android device.
     * Metastates are things like *shift* to get uppercase characters.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    default void pressKeyCode(int key, Integer metastate) {
        CommandExecutionHelper.execute(this, pressKeyCodeCommand(key, metastate));
    }

    /**
     * Send a long key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    default void longPressKeyCode(int key) {
        CommandExecutionHelper.execute(this, longPressKeyCodeCommand(key));
    }

    /**
     * Send a long key event along with an Android metastate to an Android device.
     * Metastates are things like *shift* to get uppercase characters.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    default void longPressKeyCode(int key, Integer metastate) {
        CommandExecutionHelper.execute(this, longPressKeyCodeCommand(key, metastate));
    }
}
