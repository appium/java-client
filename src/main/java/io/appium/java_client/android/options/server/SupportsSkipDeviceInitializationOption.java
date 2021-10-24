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

package io.appium.java_client.android.options.server;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSkipDeviceInitializationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SKIP_DEVICE_INITIALIZATION_OPTION = "skipDeviceInitialization";

    /**
     * Disables initial device startup checks by the server.
     *
     * @return self instance for chaining.
     */
    default T setSkipDeviceInitialization() {
        return amend(SKIP_DEVICE_INITIALIZATION_OPTION, true);
    }

    /**
     * If set to true then device startup checks (whether it is ready and whether
     * Settings app is installed) will be canceled on session creation.
     * Could speed up the session creation if you know what you are doing. false by default
     *
     * @param value True to skip device initialization.
     * @return self instance for chaining.
     */
    default T setSkipDeviceInitialization(boolean value) {
        return amend(SKIP_DEVICE_INITIALIZATION_OPTION, value);
    }

    /**
     * Get whether initial device startup checks by the server are disabled.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipDeviceInitialization() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_DEVICE_INITIALIZATION_OPTION)));
    }
}
