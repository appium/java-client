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

package io.appium.java_client;

import static java.util.Optional.ofNullable;

import java.util.Map;
import javax.annotation.Nullable;

public interface HasSessionDetails extends ExecutesMethod {

    Map<String, Object> getSessionDetails();

    /**
     * Get the value of the corresponding session detail.
     *
     * @param detail The name of the details to get in the map.
     * @return The value of the detail.
     */
    default @Nullable Object getSessionDetail(String detail) {
        return getSessionDetails().get(detail);
    }

    /**
     * @return name of the current mobile platform.
     */
    default @Nullable String getPlatformName() {
        Object platformName = ofNullable(getSessionDetail("platformName"))
                .orElseGet(() -> getSessionDetail("platform"));
        return ofNullable(platformName).map(String::valueOf).orElse(null);
    }

    /**
     * @return current automation name.
     */
    default @Nullable String getAutomationName() {
        return ofNullable(getSessionDetail("automationName"))
                .map(String::valueOf).orElse(null);
    }

    /**
     * @return is focus on browser or on native content.
     */
    default boolean isBrowser() {
        return ofNullable(getSessionDetail("browserName"))
                .orElse(null) != null;
    }
}
