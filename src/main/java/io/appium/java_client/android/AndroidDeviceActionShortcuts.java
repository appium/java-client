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

import io.appium.java_client.DeviceActionShortcuts;

public interface AndroidDeviceActionShortcuts extends DeviceActionShortcuts {

    /**
     * Send a key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    void pressKeyCode(int key);

    /**
     * Send a key event along with an Android metastate to an Android device.
     * Metastates are things like *shift* to get uppercase characters.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    void pressKeyCode(int key, Integer metastate);

    /**
     * Send a long key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    void longPressKeyCode(int key);

    /**
     * Send a long key event along with an Android metastate to an Android device.
     * Metastates are things like *shift* to get uppercase characters.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     */
    void longPressKeyCode(int key, Integer metastate);
}
