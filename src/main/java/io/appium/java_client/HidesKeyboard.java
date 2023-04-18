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

import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.MobileCommand.HIDE_KEYBOARD;

public interface HidesKeyboard extends ExecutesMethod {

    /**
     * Hides the keyboard if it is showing.
     * If the on-screen keyboard does not have any dedicated button that
     * hides it then an error is going to be thrown. In such case you must emulate
     * same actions an app user would do to hide the keyboard.
     * See the documentation for 'mobile: hideKeyboard' extension for more details.
     */
    default void hideKeyboard() {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: hideKeyboard");
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, HIDE_KEYBOARD);
        }
    }
}
