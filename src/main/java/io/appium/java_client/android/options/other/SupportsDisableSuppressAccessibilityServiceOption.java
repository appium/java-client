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

package io.appium.java_client.android.options.other;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsDisableSuppressAccessibilityServiceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DISABLE_SUPPRESS_ACCESSIBILITY_SERVICE_OPTION = "disableSuppressAccessibilityService";

    /**
     * Tells the instrumentation process to not suppress accessibility services
     * during the automated test.
     *
     * @return self instance for chaining.
     */
    default T disableSuppressAccessibilityService() {
        return amend(DISABLE_SUPPRESS_ACCESSIBILITY_SERVICE_OPTION, true);
    }

    /**
     * Being set to true tells the instrumentation process to not suppress
     * accessibility services during the automated test. This might be useful
     * if your automated test needs these services. false by default.
     *
     * @param value Set it to true in order to suppress accessibility services.
     * @return self instance for chaining.
     */
    default T setDisableSuppressAccessibilityService(boolean value) {
        return amend(DISABLE_SUPPRESS_ACCESSIBILITY_SERVICE_OPTION, value);
    }

    /**
     * Get whether to suppress accessibility services.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesDisableSuppressAccessibilityService() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(DISABLE_SUPPRESS_ACCESSIBILITY_SERVICE_OPTION))
        );
    }
}
