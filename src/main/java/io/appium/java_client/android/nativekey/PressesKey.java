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

package io.appium.java_client.android.nativekey;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static io.appium.java_client.MobileCommand.LONG_PRESS_KEY_CODE;
import static io.appium.java_client.MobileCommand.PRESS_KEY_CODE;

public interface PressesKey extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Send a key event to the device under test.
     *
     * @param keyEvent The generated native key event
     */
    default void pressKey(KeyEvent keyEvent) {
        final String extName = "mobile: pressKey";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, keyEvent.build());
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    Map.entry(PRESS_KEY_CODE, keyEvent.build())
            );
        }
    }

    /**
     * Send a long press key event to the device under test.
     *
     * @param keyEvent The generated native key event
     */
    default void longPressKey(KeyEvent keyEvent) {
        final String extName = "mobile: pressKey";
        try {
            Map<String, Object> args = ImmutableMap.<String, Object>builder()
                    .putAll(keyEvent.build())
                    .put("isLongPress", true)
                    .build();
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    Map.entry(LONG_PRESS_KEY_CODE, keyEvent.build())
            );
        }
    }
}
