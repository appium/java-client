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

package io.appium.java_client.ios.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code appTimeZone} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAppTimeZoneOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code appTimeZone} capability.
     */
    String APP_TIME_ZONE_OPTION = "appTimeZone";

    /**
     * Defines the custom time zone override for the application under test, such as
     * {@code UTC} or a tz database name like {@code America/Los_Angeles}.
     * The application must be (re)launched for the capability to take effect.
     *
     * @param timeZone Time zone identifier.
     * @return self instance for chaining.
     */
    default T setAppTimeZone(String timeZone) {
        return amend(APP_TIME_ZONE_OPTION, timeZone);
    }

    /**
     * Get the custom time zone override for the application under test.
     *
     * @return Time zone identifier.
     */
    default Optional<String> getAppTimeZone() {
        return Optional.ofNullable((String) getCapability(APP_TIME_ZONE_OPTION));
    }
}
