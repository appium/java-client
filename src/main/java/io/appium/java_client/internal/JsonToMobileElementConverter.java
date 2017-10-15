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

import static io.appium.java_client.internal.ElementMap.getElementClass;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import io.appium.java_client.HasSessionDetails;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;

import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * Reconstitutes {@link org.openqa.selenium.WebElement}s from their JSON representation. Will recursively convert Lists
 * and Maps to catch nested references. All other values pass through the converter unchanged.
 */
public class JsonToMobileElementConverter extends JsonToWebElementConverter {

    protected final RemoteWebDriver driver;
    private final HasSessionDetails hasSessionDetails;

    /**
     * @param driver an instance of {@link org.openqa.selenium.remote.RemoteWebDriver} subclass
     * @param hasSessionDetails object that has session details
     */
    public JsonToMobileElementConverter(RemoteWebDriver driver, HasSessionDetails hasSessionDetails) {
        super(driver);
        this.driver = driver;
        this.hasSessionDetails = hasSessionDetails;
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

        if (result instanceof RemoteWebElement) {
            RemoteWebElement resultElement = RemoteWebElement.class.cast(result);
            RemoteWebElement element = newMobileElement();
            element.setParent(driver);
            element.setId(resultElement.getId());
            element.setFileDetector(driver.getFileDetector());
            return element;
        }

        if (result instanceof Number) {
            if (result instanceof Float || result instanceof Double) {
                return ((Number) result).doubleValue();
            }
            return ((Number) result).longValue();
        }

        return result;
    }

    protected RemoteWebElement newMobileElement() {
        Class<? extends RemoteWebElement> target;
        target = getElementClass(hasSessionDetails);
        try {
            Constructor<? extends RemoteWebElement> constructor = target.getDeclaredConstructor();
            constructor.setAccessible(true);
            RemoteWebElement result = constructor.newInstance();
            result.setParent(driver);
            return result;
        } catch (Exception e) {
            throw new WebDriverException(e);
        }
    }
}
