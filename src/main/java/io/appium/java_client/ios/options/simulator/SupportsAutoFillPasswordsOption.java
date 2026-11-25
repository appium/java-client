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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code autoFillPasswords} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAutoFillPasswordsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code autoFillPasswords} capability.
     */
    String AUTO_FILL_PASSWORDS_OPTION = "autoFillPasswords";

    /**
     * Allows turning on or off the AutoFill Passwords feature when a text field is focused.
     * Works only with iOS 16.4+ simulators.
     *
     * @param value Whether to enable AutoFill Passwords.
     * @return self instance for chaining.
     */
    default T setAutoFillPasswords(boolean value) {
        return amend(AUTO_FILL_PASSWORDS_OPTION, value);
    }

    /**
     * Get whether AutoFill Passwords is enabled on simulators.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAutoFillPasswords() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTO_FILL_PASSWORDS_OPTION)));
    }
}
