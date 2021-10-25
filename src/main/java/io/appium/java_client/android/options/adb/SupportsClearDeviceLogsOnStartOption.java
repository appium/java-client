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

public interface SupportsClearDeviceLogsOnStartOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CLEAR_DEVICE_LOGS_ON_START_OPTION = "clearDeviceLogsOnStart";

    /**
     * Makes UiAutomator2 to delete all the existing logs in the
     * device buffer before starting a new test.
     *
     * @return self instance for chaining.
     */
    default T clearDeviceLogsOnStart() {
        return amend(CLEAR_DEVICE_LOGS_ON_START_OPTION, true);
    }


    /**
     * If set to true then UiAutomator2 deletes all the existing logs in the
     * device buffer before starting a new test.
     *
     * @param value Set to false if you don't want to wait for the app to finish its launch.
     * @return self instance for chaining.
     */
    default T setClearDeviceLogsOnStart(boolean value) {
        return amend(CLEAR_DEVICE_LOGS_ON_START_OPTION, value);
    }

    /**
     * Get whether to delete all the existing logs in the
     * device buffer before starting a new test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesClearDeviceLogsOnStart() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CLEAR_DEVICE_LOGS_ON_START_OPTION)));
    }
}
