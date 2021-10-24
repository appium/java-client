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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.AcceptedW3CCapabilityKeys;
import org.openqa.selenium.remote.CapabilityType;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static java.util.Collections.unmodifiableMap;

/**
 * This class represents capabilities that are available in the base driver,
 * e.g. are acceptable by any Appium driver
 *
 * @param <T> The child class for a proper chaining.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BaseOptions<T extends BaseOptions<T>> extends MutableCapabilities
    implements CanSetCapability<T>, SupportsAutomationNameOption<T>,
        SupportsEventTimingsOption<T>, SupportsPrintPageSourceOnFindFailureOption<T>,
        SupportsNoResetOption<T>, SupportsFullResetOption<T>, SupportsNewCommandTimeoutOption<T>,
        SupportsPlatformVersionOption<T> {
    private static final AcceptedW3CCapabilityKeys W3C_KEY_PATTERNS = new AcceptedW3CCapabilityKeys();

    /**
     * Creates new instance with no preset capabilities.
     */
    public BaseOptions() {
    }

    /**
     * Creates new instance with provided capabilities.
     *
     * @param source is Capabilities instance to merge into new instance
     */
    public BaseOptions(Capabilities source) {
        super(source);
    }

    /**
     * Set the kind of mobile device or emulator to use.
     *
     * @param platform the kind of mobile device or emulator to use.
     * @return this MobileOptions, for chaining.
     * @see CapabilityType#PLATFORM_NAME
     */
    public T setPlatformName(String platform) {
        return amend(CapabilityType.PLATFORM_NAME, platform);
    }

    @Override
    public Map<String, Object> asMap() {
        return unmodifiableMap(super.asMap().entrySet().stream()
                .collect(Collectors.toMap(entry -> W3C_KEY_PATTERNS.test(entry.getKey())
                        ? entry.getKey() : APPIUM_PREFIX + entry.getKey(), Map.Entry::getValue)
                ));
    }

    @Override
    public T merge(Capabilities extraCapabilities) {
        super.merge(extraCapabilities);
        //noinspection unchecked
        return (T) this;
    }

    @Override
    public void setCapability(String key, @Nullable Object value) {
        Require.nonNull("Capability name", key);
        super.setCapability(W3C_KEY_PATTERNS.test(key) ? key : APPIUM_PREFIX + key, value);
    }

    @Override
    @Nullable
    public Object getCapability(String capabilityName) {
        Object value = super.getCapability(capabilityName);
        if (value == null) {
            value = super.getCapability(APPIUM_PREFIX + capabilityName);
        }
        return value;
    }
}