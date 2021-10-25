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

public interface SupportsSuppressKillServerOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SUPPRESS_KILL_SERVER_OPTION = "suppressKillServer";

    /**
     * Prevents the driver from ever killing the ADB server explicitl.
     *
     * @return self instance for chaining.
     */
    default T suppressKillServer() {
        return amend(SUPPRESS_KILL_SERVER_OPTION, true);
    }

    /**
     * Being set to true prevents the driver from ever killing the ADB server explicitly.
     * Could be useful if ADB is connected wirelessly. false by default.
     *
     * @param value Whether to prevent the driver from ever killing the ADB server explicitly.
     * @return self instance for chaining.
     */
    default T setSuppressKillServer(boolean value) {
        return amend(SUPPRESS_KILL_SERVER_OPTION, value);
    }

    /**
     * Get whether to prevent the driver from ever killing the ADB server explicitly.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSuppressKillServer() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SUPPRESS_KILL_SERVER_OPTION)));
    }
}
