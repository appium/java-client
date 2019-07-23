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

import io.appium.java_client.HasSessionDetails;
import org.openqa.seleniumone.WebDriverException;
import org.openqa.seleniumone.remote.RemoteWebDriver;
import org.openqa.seleniumone.remote.RemoteWebElement;
import org.openqa.seleniumone.remote.internal.JsonToWebElementConverter;

import java.lang.reflect.Constructor;

/**
 * Reconstitutes {@link org.openqa.seleniumone.WebElement}s from their JSON representation. Will recursively convert Lists
 * and Maps to catch nested references. All other values pass through the converter unchanged.
 */
public class JsonToMobileElementConverter extends JsonToWebElementConverter {

    protected final RemoteWebDriver driver;

    private final String platform;
    private final String automation;

    /**
     * Creates a new instance based on {@code driver} and object with session details.
     *
     * @param driver an instance of {@link RemoteWebDriver} subclass
     * @param hasSessionDetails object that has session details
     */
    public JsonToMobileElementConverter(RemoteWebDriver driver, HasSessionDetails hasSessionDetails) {
        super(driver);
        this.driver = driver;
        this.platform = hasSessionDetails.getPlatformName();
        this.automation = hasSessionDetails.getAutomationName();
    }

    @Override
    public Object apply(Object result) {
        Object toBeReturned = result;
        if (toBeReturned instanceof RemoteWebElement) {
            toBeReturned =  newRemoteWebElement();
            ((RemoteWebElement) toBeReturned).setId(((RemoteWebElement) result).getId());
        }

        return super.apply(toBeReturned);
    }

    @Override
    protected RemoteWebElement newRemoteWebElement() {
        Class<? extends RemoteWebElement> target;
        target = getElementClass(platform, automation);

        try {
            Constructor<? extends RemoteWebElement> constructor = target.getDeclaredConstructor();
            constructor.setAccessible(true);
            RemoteWebElement result = constructor.newInstance();

            result.setParent(driver);
            result.setFileDetector(driver.getFileDetector());

            return result;
        } catch (Exception e) {
            throw new WebDriverException(e);
        }
    }
}
