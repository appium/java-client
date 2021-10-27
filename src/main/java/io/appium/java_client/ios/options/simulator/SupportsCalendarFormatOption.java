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

public interface SupportsCalendarFormatOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CALENDAR_FORMAT_OPTION = "calendarFormat";

    /**
     * Set calendar format for the iOS Simulator.
     *
     * @param format Calendar format to set for the iOS Simulator.
     * @return self instance for chaining.
     */
    default T setCalendarFormat(String format) {
        return amend(CALENDAR_FORMAT_OPTION, format);
    }

    /**
     * Get calendar format to set for the iOS Simulator.
     *
     * @return Calendar format.
     */
    default Optional<String> getCalendarFormat() {
        return Optional.ofNullable((String) getCapability(CALENDAR_FORMAT_OPTION));
    }
}
