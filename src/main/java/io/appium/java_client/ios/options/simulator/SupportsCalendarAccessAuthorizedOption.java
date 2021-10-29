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

public interface SupportsCalendarAccessAuthorizedOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CALENDAR_ACCESS_AUTHORIZED_OPTION = "calendarAccessAuthorized";

    /**
     * Enable calendar access on IOS Simulator.
     *
     * @return self instance for chaining.
     */
    default T calendarAccessAuthorized() {
        return amend(CALENDAR_ACCESS_AUTHORIZED_OPTION, true);
    }

    /**
     * Set this to true if you want to enable calendar access on IOS Simulator
     * with given bundleId. Set to false, if you want to disable calendar access
     * on IOS Simulator with given bundleId. If not set, the calendar
     * authorization status will not be set.
     *
     * @param value Whether to enable calendar access on IOS Simulator.
     * @return self instance for chaining.
     */
    default T setCalendarAccessAuthorized(boolean value) {
        return amend(CALENDAR_ACCESS_AUTHORIZED_OPTION, value);
    }

    /**
     * Get whether to enable calendar access on IOS Simulator.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesCalendarAccessAuthorized() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CALENDAR_ACCESS_AUTHORIZED_OPTION)));
    }
}
