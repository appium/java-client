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

import static io.appium.java_client.ios.IOSMobileCommandHelper.hideKeyboardCommand;
import static io.appium.java_client.ios.IOSMobileCommandHelper.shakeCommand;
import static io.appium.java_client.ios.IOSMobileCommandHelper.touchIdCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.DeviceActionShortcuts;

public interface IOSDeviceActionShortcuts extends DeviceActionShortcuts {

    /**
     * Hides the keyboard by pressing the button specified by keyName if it is
     * showing.
     *
     * @param keyName The button pressed by the mobile driver to attempt hiding the
     *                keyboard.
     */
    default void hideKeyboard(String keyName) {
        CommandExecutionHelper.execute(this, hideKeyboardCommand(keyName));
    }

    /**
     * Hides the keyboard if it is showing. Available strategies are PRESS_KEY
     * and TAP_OUTSIDE. One taps outside the keyboard, the other presses a key
     * of your choosing (probably the 'Done' key). Hiding the keyboard often
     * depends on the way an app is implemented, no single strategy always
     * works.
     *
     * @param strategy HideKeyboardStrategy.
     * @param keyName  a String, representing the text displayed on the button of the
     *                 keyboard you want to press. For example: "Done".
     */
    default void hideKeyboard(String strategy, String keyName) {
        CommandExecutionHelper.execute(this, hideKeyboardCommand(strategy, keyName));
    }

    /**
     * Simulate shaking the device.
     */
    default void shake() {
        CommandExecutionHelper.execute(this, shakeCommand());
    }
    
    /**
     * Simulate touchId event
     *
     * @param match Are we simulating a successful fingerprint scan?
     */
    default void touchId(boolean match) {
    	CommandExecutionHelper.execute(this, touchIdCommand(match));
    }

}
