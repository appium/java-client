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

public interface SupportsEventTimingsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String EVENT_TIMINGS_OPTION = "eventTimings";

    /**
     * Set the app to report the timings for various Appium-internal events.
     *
     * @return self instance for chaining.
     */
    default T eventTimings() {
        return setEventTimings(true);
    }

    /**
     * Set whether the app reports the timings for various Appium-internal events.
     *
     * @param bool is whether the app enables event timings.
     * @return self instance for chaining.
     */
    default T setEventTimings(boolean bool) {
        return amend(EVENT_TIMINGS_OPTION, bool);
    }

    /**
     * Get whether the app reports the timings for various Appium-internal events.
     *
     * @return true if the app reports event timings.
     */
    default Optional<Boolean> doesEventTimings() {
        return Optional.ofNullable(toSafeBoolean(getCapability(EVENT_TIMINGS_OPTION)));
    }
}
