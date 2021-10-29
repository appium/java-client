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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSkipLogCaptureOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SKIP_LOG_CAPTURE_OPTION = "skipLogCapture";

    /**
     * Skips capturing system logs.
     *
     * @return self instance for chaining.
     */
    default T skipLogCapture() {
        return amend(SKIP_LOG_CAPTURE_OPTION, true);
    }

    /**
     * Skips to start capturing system logs. It might improve network performance.
     * Log-related commands won't work if the capability is enabled. Defaults to false.
     *
     * @param value Set it to true in order to skip logcat capture.
     * @return self instance for chaining.
     */
    default T setSkipLogCapture(boolean value) {
        return amend(SKIP_LOG_CAPTURE_OPTION, value);
    }

    /**
     * Get whether to skip capturing system logs.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipLogCapture() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_LOG_CAPTURE_OPTION)));
    }
}
