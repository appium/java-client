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

package io.appium.java_client.remote;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts element references in response values to elements. Adapted from Selenium's
 * {@code JsonToWebElementConverter} (Apache License 2.0).
 */
class JsonToWebElementConverter implements Function<Object, Object> {
    @Nullable
    private final AppiumRemoteWebDriver driver;

    JsonToWebElementConverter(@Nullable AppiumRemoteWebDriver driver) {
        this.driver = driver;
    }

    @Override
    public Object apply(Object result) {
        if (result instanceof Collection<?>) {
            return ((Collection<?>) result).stream().map(this).collect(Collectors.toList());
        }

        if (result instanceof Map<?, ?>) {
            Map<?, ?> resultAsMap = (Map<?, ?>) result;
            if (resultAsMap.containsKey(AppiumWebElement.ELEMENT_KEY)) {
                AppiumWebElement element = newElement();
                element.setId(String.valueOf(resultAsMap.get(AppiumWebElement.ELEMENT_KEY)));
                return element;
            }
            // Some values are converted to null, so Collectors.toMap cannot be used
            Map<Object, Object> converted = new LinkedHashMap<>();
            resultAsMap.forEach((k, v) -> converted.put(k, apply(v)));
            return converted;
        }

        if (result instanceof AppiumWebElement) {
            return setOwner((AppiumWebElement) result);
        }

        if (result instanceof Number) {
            if (result instanceof Float || result instanceof Double) {
                return ((Number) result).doubleValue();
            }
            return ((Number) result).longValue();
        }

        return result;
    }

    private AppiumWebElement newElement() {
        return setOwner(new AppiumWebElement());
    }

    private AppiumWebElement setOwner(AppiumWebElement element) {
        if (driver != null) {
            element.setParent(driver);
        }
        return element;
    }
}
