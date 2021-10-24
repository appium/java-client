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

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.AcceptedW3CCapabilityKeys;
import org.openqa.selenium.remote.CapabilityType;

import java.time.Duration;
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
@SuppressWarnings("unused")
public class BaseOptions<T extends BaseOptions<T>> extends MutableCapabilities
    implements CanSetCapability<T> {
    private static final AcceptedW3CCapabilityKeys W3C_KEY_PATTERNS =
            new AcceptedW3CCapabilityKeys();


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

    /**
     * Set the automation engine to use.
     *
     * @param name is the name of the automation engine
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#AUTOMATION_NAME
     */
    public T setAutomationName(String name) {
        return amend(MobileCapabilityType.AUTOMATION_NAME, name);
    }

    /**
     * Get the automation engine to use.
     *
     * @return String representing the name of the automation engine
     * @see MobileCapabilityType#AUTOMATION_NAME
     */
    public String getAutomationName() {
        return (String) getCapability(MobileCapabilityType.AUTOMATION_NAME);
    }

    /**
     * Set the app to report the timings for various Appium-internal events.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#EVENT_TIMINGS
     */
    public T setEventTimings() {
        return setEventTimings(true);
    }

    /**
     * Set whether the app reports the timings for various Appium-internal events.
     *
     * @param bool is whether the app enables event timings.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#EVENT_TIMINGS
     */
    public T setEventTimings(boolean bool) {
        return amend(MobileCapabilityType.EVENT_TIMINGS, bool);
    }

    /**
     * Get whether the app reports the timings for various Appium-internal events.
     *
     * @return true if the app reports event timings.
     * @see MobileCapabilityType#EVENT_TIMINGS
     */
    public boolean doesEventTimings() {
        return (boolean) getCapability(MobileCapabilityType.EVENT_TIMINGS);
    }

    /**
     * Set the app to do a full reset.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#FULL_RESET
     */
    public T setFullReset() {
        return setFullReset(true);
    }

    /**
     * Set whether the app does a full reset.
     *
     * @param bool is whether the app does a full reset.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#FULL_RESET
     */
    public T setFullReset(boolean bool) {
        return amend(MobileCapabilityType.FULL_RESET, bool);
    }

    /**
     * Get whether the app does a full reset.
     *
     * @return true if the app does a full reset.
     * @see MobileCapabilityType#FULL_RESET
     */
    public boolean doesFullReset() {
        return (boolean) getCapability(MobileCapabilityType.FULL_RESET);
    }

    /**
     * Set the timeout for new commands.
     *
     * @param duration is the allowed time before seeing a new command.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#NEW_COMMAND_TIMEOUT
     */
    public T setNewCommandTimeout(Duration duration) {
        return amend(MobileCapabilityType.NEW_COMMAND_TIMEOUT, duration.getSeconds());
    }

    /**
     * Get the timeout for new commands.
     *
     * @return allowed time before seeing a new command.
     * @see MobileCapabilityType#NEW_COMMAND_TIMEOUT
     */
    public Duration getNewCommandTimeout() {
        Object duration = getCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT);
        return Duration.ofSeconds(Long.parseLong("" + duration));
    }

    /**
     * Set the app not to do a reset.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#NO_RESET
     */
    public T setNoReset() {
        return setNoReset(true);
    }

    /**
     * Set whether the app does not do a reset.
     *
     * @param bool is whether the app does not do a reset.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#NO_RESET
     */
    public T setNoReset(boolean bool) {
        return amend(MobileCapabilityType.NO_RESET, bool);
    }

    /**
     * Get whether the app does not do a reset.
     *
     * @return true if the app does not do a reset.
     * @see MobileCapabilityType#NO_RESET
     */
    public boolean doesNoReset() {
        return (boolean) getCapability(MobileCapabilityType.NO_RESET);
    }

    /**
     * Set the version of the platform.
     *
     * @param version is the platform version.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#PLATFORM_VERSION
     */
    public T setPlatformVersion(String version) {
        return amend(MobileCapabilityType.PLATFORM_VERSION, version);
    }

    /**
     * Get the version of the platform.
     *
     * @return String representing the platform version.
     * @see MobileCapabilityType#PLATFORM_VERSION
     */
    public String getPlatformVersion() {
        return (String) getCapability(MobileCapabilityType.PLATFORM_VERSION);
    }

    /**
     * Set the app to print page source when a find operation fails.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#PRINT_PAGE_SOURCE_ON_FIND_FAILURE
     */
    public T setPrintPageSourceOnFindFailure() {
        return setPrintPageSourceOnFindFailure(true);
    }

    /**
     * Set whether the app to print page source when a find operation fails.
     *
     * @param bool is whether to print page source.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#PRINT_PAGE_SOURCE_ON_FIND_FAILURE
     */
    public T setPrintPageSourceOnFindFailure(boolean bool) {
        return amend(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE, bool);
    }

    /**
     * Get whether the app to print page source when a find operation fails.
     *
     * @return true if app prints page source.
     * @see MobileCapabilityType#PRINT_PAGE_SOURCE_ON_FIND_FAILURE
     */
    public boolean doesPrintPageSourceOnFindFailure() {
        return (boolean) getCapability(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE);
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
}
