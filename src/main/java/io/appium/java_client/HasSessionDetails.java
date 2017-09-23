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
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
        Map<String, Object> resultMap = Map.class.cast(response.getValue());

        return  ImmutableMap.<String, Object>builder()
                .putAll(resultMap.entrySet()
                        .stream().filter(entry -> {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            return !isBlank(key)
                                && value != null
                                && !isBlank(String.valueOf(value));
                        }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue))).build();
    }

    default Object getSessionDetail(String detail) {
        return getSessionDetails().get(detail);
    }

    /**
     * @return name of the current mobile platform.
     */
    default String getPlatformName() {
        Object platformName = ofNullable(getSessionDetail("platformName"))
                .orElseGet(() -> getSessionDetail("platform"));
        return ofNullable(platformName).map(Object::toString).orElse(null);
    }

    /**
     * @return current automation name.
     */
    default String  getAutomationName() {
        return ofNullable(getSessionDetail("automationName"))
                .map(Object::toString).orElse(null);
    }

    /**
     * @return is focus on browser or on native content.
     */
    default boolean isBrowser() {
        return ofNullable(getSessionDetail("browserName"))
                .map(browserName -> browserName.toString())
                .orElse(null) != null;
    }
}
