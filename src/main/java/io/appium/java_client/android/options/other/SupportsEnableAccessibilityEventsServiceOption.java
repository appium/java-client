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

/**
 * Provides getters and setters for the {@code enableAccessibilityEventsService} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsEnableAccessibilityEventsServiceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code enableAccessibilityEventsService} capability.
     */
    String ENABLE_ACCESSIBILITY_EVENTS_SERVICE_OPTION = "enableAccessibilityEventsService";

    /**
     * Enables the Appium Settings accessibility events service for the session.
     * Enabling this also stops suppressing accessibility services unless
     * {@code disableSuppressAccessibilityService} is explicitly set to {@code false}.
     * Supported since {@code appium-uiautomator2-driver} 9.1.0.
     *
     * @return self instance for chaining.
     */
    default T enableAccessibilityEventsService() {
        return amend(ENABLE_ACCESSIBILITY_EVENTS_SERVICE_OPTION, true);
    }

    /**
     * Whether to enable the Appium Settings no-op accessibility service for the
     * duration of the session so Chrome emits accessibility events for web content.
     * Enabling this also stops suppressing accessibility services unless
     * {@code disableSuppressAccessibilityService} is explicitly set to {@code false}.
     * {@code false} by default. Supported since {@code appium-uiautomator2-driver} 9.1.0.
     *
     * @param value True to enable the accessibility events service.
     * @return self instance for chaining.
     */
    default T setEnableAccessibilityEventsService(boolean value) {
        return amend(ENABLE_ACCESSIBILITY_EVENTS_SERVICE_OPTION, value);
    }

    /**
     * Get whether the accessibility events service is enabled.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesEnableAccessibilityEventsService() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(ENABLE_ACCESSIBILITY_EVENTS_SERVICE_OPTION))
        );
    }
}
