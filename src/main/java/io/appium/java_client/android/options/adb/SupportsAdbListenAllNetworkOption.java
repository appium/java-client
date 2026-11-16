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

/**
 * Provides getters and setters for the {@code adbListenAllNetwork} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAdbListenAllNetworkOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code adbListenAllNetwork} capability.
     */
    String ADB_LISTEN_ALL_NETWORK_OPTION = "adbListenAllNetwork";

    /**
     * Enables listening on all network interfaces for ADB instead of only localhost.
     *
     * @return self instance for chaining.
     */
    default T adbListenAllNetwork() {
        return amend(ADB_LISTEN_ALL_NETWORK_OPTION, true);
    }

    /**
     * Whether to listen on all network interfaces for ADB rather than only localhost.
     *
     * @param value True to listen on all network interfaces.
     * @return self instance for chaining.
     */
    default T setAdbListenAllNetwork(boolean value) {
        return amend(ADB_LISTEN_ALL_NETWORK_OPTION, value);
    }

    /**
     * Get whether ADB listens on all network interfaces.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAdbListenAllNetwork() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ADB_LISTEN_ALL_NETWORK_OPTION)));
    }
}
