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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileCommand;

import java.util.AbstractMap;
import java.util.Map;

@Deprecated
public class IOSMobileCommandHelper extends MobileCommand {

    /**
     * This method forms a {@link Map} of parameters for the device shaking.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated this helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>>  shakeCommand() {
        return new AbstractMap.SimpleEntry<>(SHAKE, ImmutableMap.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the touchId simulator.
     *
     * @param match If true, simulates a successful fingerprint scan. If false, simulates a failed fingerprint scan.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated this helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> touchIdCommand(boolean match) {
        return new AbstractMap.SimpleEntry<>(
            TOUCH_ID, prepareArguments("match", match));
    }

    /**
     * This method forms a {@link Map} of parameters for the toggling touchId
     * enrollment in simulator.
     *
     * @param enabled Whether to enable or disable Touch ID Enrollment for Simulator.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated this helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> toggleTouchIdEnrollmentCommand(boolean enabled) {
        return new AbstractMap.SimpleEntry<>(
                TOUCH_ID_ENROLLMENT, prepareArguments("enabled", enabled));
    }
}
