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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsRemoteAppsCacheLimitOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String REMOTE_APPS_CACHE_LIMIT_OPTION = "remoteAppsCacheLimit";

    /**
     * Sets the maximum amount of application packages to be cached on the device under test.
     * This is needed for devices that don't support streamed installs (Android 7 and below),
     * because ADB must push app packages to the device first in order to install them,
     * which takes some time. Setting this capability to zero disables apps caching.
     * 10 by default.
     *
     * @param limit The maximum amount of cached apps.
     * @return self instance for chaining.
     */
    default T setRemoteAppsCacheLimit(int limit) {
        return amend(REMOTE_APPS_CACHE_LIMIT_OPTION, limit);
    }

    /**
     * Get the maximum amount of apps that could be cached on the remote device.
     *
     * @return The maximum amount of cached apps.
     */
    default Optional<Integer> getRemoteAppsCacheLimit() {
        return Optional.ofNullable(toInteger(getCapability(REMOTE_APPS_CACHE_LIMIT_OPTION)));
    }
}
