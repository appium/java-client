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

package io.appium.java_client.wincore.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code ms:forcequit} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsMsForceQuitOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code ms:forcequit} capability.
     */
    String MS_FORCE_QUIT_OPTION = "ms:forcequit";

    /**
     * Whether the application process is force-killed at the end of the session. Only applies if
     * {@code shouldCloseApp} is enabled.
     *
     * @param value True to enable, false to disable.
     * @return self instance for chaining.
     */
    default T setForceQuit(boolean value) {
        return amend(MS_FORCE_QUIT_OPTION, value);
    }

    /**
     * Get whether the application process is force-killed at the end of the session.
     *
     * @return The capability value.
     */
    default Optional<Boolean> isForceQuit() {
        return Optional.ofNullable(toSafeBoolean(getCapability(MS_FORCE_QUIT_OPTION)));
    }
}
