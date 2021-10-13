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

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.JsonToWebElementConverter;

import java.lang.reflect.Constructor;

/**
 * Reconstitutes {@link org.openqa.selenium.WebElement}s from their JSON representation. Will recursively convert Lists
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
     */
    public JsonToMobileElementConverter(RemoteWebDriver driver) {
        super(driver);
        this.driver = driver;
        Capabilities caps = driver.getCapabilities();
        this.platform = CapabilityHelpers.getCapability(caps, CapabilityType.PLATFORM_NAME, String.class);
        this.automation = CapabilityHelpers.getCapability(caps, MobileCapabilityType.AUTOMATION_NAME, String.class);
    }

    @Override
    public Object apply(Object result) {
        Object toBeReturned = result;
        if (toBeReturned instanceof RemoteWebElement) {
            toBeReturned = newRemoteWebElement();
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
