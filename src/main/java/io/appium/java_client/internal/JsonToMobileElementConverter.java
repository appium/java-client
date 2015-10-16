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

package io.appium.java_client.internal;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.appium.java_client.MobileElement;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;

import java.util.Collection;
import java.util.Map;

/**
 * Reconstitutes {@link WebElement}s from their JSON representation. Will recursively convert Lists
 * and Maps to catch nested references. All other values pass through the converter unchanged.
 */
public abstract class JsonToMobileElementConverter extends JsonToWebElementConverter {
    protected RemoteWebDriver driver;

    public JsonToMobileElementConverter(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This method converts a command result.
     *
     * @param result is the result of a command execution.
     * @return the result
     */
    public Object apply(Object result) {
        if (result instanceof Collection<?>) {
            Collection<?> results = (Collection<?>) result;
            return Lists.newArrayList(Iterables.transform(results, this));
        }

        if (result instanceof Map<?, ?>) {
            Map<?, ?> resultAsMap = (Map<?, ?>) result;
            if (resultAsMap.containsKey("ELEMENT")) {
                MobileElement element = newMobileElement();
                element.setId(String.valueOf(resultAsMap.get("ELEMENT")));
                element.setFileDetector(driver.getFileDetector());
                return element;
            } else {
                return Maps.transformValues(resultAsMap, this);
            }
        }

        if (result instanceof Number) {
            if (result instanceof Float || result instanceof Double) {
                return ((Number) result).doubleValue();
            }
            return ((Number) result).longValue();
        }

        return result;
    }

    protected abstract MobileElement newMobileElement();
}
