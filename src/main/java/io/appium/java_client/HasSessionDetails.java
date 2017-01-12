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

import static io.appium.java_client.MobileCommand.GET_SESSION;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.remote.Response;

import java.util.Map;

public interface HasSessionDetails extends ExecutesMethod {
    /**
     * @return a map with values that hold session details.
     *
     */
    @SuppressWarnings("unchecked")
    default Map<String, Object> getSessionDetails() {
        Response response = execute(GET_SESSION);
        return  ImmutableMap.<String, Object>builder()
                .putAll(Map.class.cast(response.getValue())).build();
    }

    default Object getSessionDetail(String detail) {
        return getSessionDetails().get(detail);
    }

    default String getPlatformName() {
        Object platformName = getSessionDetail("platformName");
        return ofNullable(platformName != null ? String.valueOf(platformName) : null).orElse(null);
    }

    default String  getAutomationName() {
        Object automationName = getSessionDetail("automationName");
        return ofNullable(automationName != null ? String.valueOf(automationName) : null).orElse(null);
    }

    default boolean isBrowser() {
        Object browserName = getSessionDetail("browserName");
        return ofNullable(browserName != null ? String
                .valueOf(browserName) : null).orElse(null) != null;
    }
}
