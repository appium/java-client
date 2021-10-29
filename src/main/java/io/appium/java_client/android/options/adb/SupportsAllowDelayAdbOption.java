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

package io.appium.java_client.android.options.adb;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAllowDelayAdbOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ALLOW_DELAY_ADB_OPTION = "allowDelayAdb";

    /**
     * Being set to false prevents emulator to use -delay-adb feature to detect its startup.
     * See https://github.com/appium/appium/issues/14773 for more details.
     *
     * @param value Set it to false in order to prevent the emulator to use -delay-adb feature.
     * @return self instance for chaining.
     */
    default T setAllowDelayAdb(boolean value) {
        return amend(ALLOW_DELAY_ADB_OPTION, value);
    }

    /**
     * Get whether to prevent the emulator to use -delay-adb feature.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAllowDelayAdb() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ALLOW_DELAY_ADB_OPTION)));
    }
}
