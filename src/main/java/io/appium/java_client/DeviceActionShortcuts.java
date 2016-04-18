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

package io.appium.java_client;


public interface DeviceActionShortcuts {

    /**
     * Hides the keyboard if it is showing.
     * On iOS, there are multiple strategies for hiding the keyboard.
     * Defaults to the "tapOutside" strategy (taps outside the keyboard).
     * Switch to using hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done") if this doesn't work.
     */
    void hideKeyboard();

    /*
        Gets device date and time for both iOS(Supports only real device) and Android devices
     */
    String getDeviceTime();

}
