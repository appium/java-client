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

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class MobileOptions<T extends MobileOptions<T>> extends MutableCapabilities {

    /**
     * Creates new instance with no preset capabilities.
     */
    public MobileOptions() {
    }

    /**
     * Creates new instance with provided capabilities capabilities.
     *
     * @param source is Capabilities instance to merge into new instance
     */
    public MobileOptions(Capabilities source) {
        merge(source);
    }

    /**
     * Set the kind of mobile device or emulator to use.
     *
     * @param platform the kind of mobile device or emulator to use.
     * @return this MobileOptions, for chaining.
     * @see org.openqa.selenium.remote.CapabilityType#PLATFORM_NAME
     */
    public T setPlatformName(String platform) {
        return amend(CapabilityType.PLATFORM_NAME, platform);
    }

    /**
     * Get the kind of mobile device or emulator to use.
     *
     * @return String representing the kind of mobile device or emulator to use.
     * @see org.openqa.selenium.remote.CapabilityType#PLATFORM_NAME
     */
    public String getPlatformName() {
        return (String) getCapability(CapabilityType.PLATFORM_NAME);
    }

    /**
     * Set the absolute local path for the location of the App.
     * The  or remote http URL to a {@code .ipa} file (IOS),
     *
     * @param path is a String representing the location of the App
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#APP
     */
    public T setApp(String path) {
        return amend(MobileCapabilityType.APP, path);
    }

    /**
     * Set the remote http URL for the location of the App.
     *
     * @param url is the URL representing the location of the App
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#APP
     */
    public T setApp(URL url) {
        return setApp(url.toString());
    }

    /**
     * Get the app location.
     *
     * @return String representing app location
     * @see MobileCapabilityType#APP
     */
    public String getApp() {
        return (String) getCapability(MobileCapabilityType.APP);
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
     * Set the app to move directly into Webview context.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#AUTO_WEBVIEW
     */
    public T setAutoWebview() {
        return setAutoWebview(true);
    }

    /**
     * Set whether the app moves directly into Webview context.
     *
     * @param bool is whether the app moves directly into Webview context.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#AUTO_WEBVIEW
     */
    public T setAutoWebview(boolean bool) {
        return amend(MobileCapabilityType.AUTO_WEBVIEW, bool);
    }

    /**
     * Get whether the app moves directly into Webview context.
     *
     * @return true if app moves directly into Webview context.
     * @see MobileCapabilityType#AUTO_WEBVIEW
     */
    public boolean doesAutoWebview() {
        return (boolean) getCapability(MobileCapabilityType.AUTO_WEBVIEW);
    }

    /**
     * Set the app to delete any generated files at the end of a session.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#CLEAR_SYSTEM_FILES
     */
    public T setClearSystemFiles() {
        return setClearSystemFiles(true);
    }

    /**
     * Set whether the app deletes generated files at the end of a session.
     *
     * @param bool is whether the app deletes generated files at the end of a session.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#CLEAR_SYSTEM_FILES
     */
    public T setClearSystemFiles(boolean bool) {
        return amend(MobileCapabilityType.CLEAR_SYSTEM_FILES, bool);
    }

    /**
     * Get whether the app deletes generated files at the end of a session.
     *
     * @return true if the app deletes generated files at the end of a session.
     * @see MobileCapabilityType#CLEAR_SYSTEM_FILES
     */
    public boolean doesClearSystemFiles() {
        return (boolean) getCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES);
    }

    /**
     * Set the name of the device.
     *
     * @param deviceName is the name of the device.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#DEVICE_NAME
     */
    public T setDeviceName(String deviceName) {
        return amend(MobileCapabilityType.DEVICE_NAME, deviceName);
    }

    /**
     * Get the name of the device.
     *
     * @return String representing the name of the device.
     * @see MobileCapabilityType#DEVICE_NAME
     */
    public String getDeviceName() {
        return (String) getCapability(MobileCapabilityType.DEVICE_NAME);
    }

    /**
     * Set the app to enable performance logging.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    public T setEnablePerformanceLogging() {
        return setEnablePerformanceLogging(true);
    }

    /**
     * Set whether the app logs performance.
     *
     * @param bool is whether the app logs performance.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    public T setEnablePerformanceLogging(boolean bool) {
        return amend(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING, bool);
    }

    /**
     * Get the app logs performance.
     *
     * @return true if the app logs performance.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    public boolean isEnablePerformanceLogging() {
        return (boolean) getCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING);
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
     * Set language abbreviation for use in session.
     *
     * @param language is the language abbreviation.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#LANGUAGE
     */
    public T setLanguage(String language) {
        return amend(MobileCapabilityType.LANGUAGE, language);
    }

    /**
     * Get language abbreviation for use in session.
     *
     * @return String representing the language abbreviation.
     * @see MobileCapabilityType#LANGUAGE
     */
    public String getLanguage() {
        return (String) getCapability(MobileCapabilityType.LANGUAGE);
    }

    /**
     * Set locale abbreviation for use in session.
     *
     * @param locale is the locale abbreviation.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#LOCALE
     */
    public T setLocale(String locale) {
        return amend(MobileCapabilityType.LOCALE, locale);
    }

    /**
     * Get locale abbreviation for use in session.
     *
     * @return String representing the locale abbreviation.
     * @see MobileCapabilityType#LOCALE
     */
    public String getLocale() {
        return (String) getCapability(MobileCapabilityType.LOCALE);
    }

    /**
     * Set the timeout for new commands.
     *
     * @param duration is the number of seconds to timeout before seeing a new command.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#NEW_COMMAND_TIMEOUT
     */
    public T setNewCommandTimeout(Duration duration) {
        return amend(MobileCapabilityType.NEW_COMMAND_TIMEOUT, duration.getSeconds());
    }

    /**
     * Get the timeout for new commands.
     *
     * @return timeout before seeing a new command in seconds.
     * @see MobileCapabilityType#NEW_COMMAND_TIMEOUT
     */
    public Duration getNewCommandTimeout() {
        Object duration = getCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT);
        if (duration.getClass().isAssignableFrom(Long.class)) {
            return Duration.ofSeconds((Long) duration);
        }
        Integer integer = (Integer) duration;
        Long value = Long.valueOf(integer);
        return Duration.ofSeconds(value);
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
     * Set the orientation of the screen.
     *
     * @param orientation is the screen orientation.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ORIENTATION
     */
    public T setOrientation(ScreenOrientation orientation) {
        return amend(MobileCapabilityType.ORIENTATION, orientation);
    }

    /**
     * Get the orientation of the screen.
     *
     * @return ScreenOrientation of the app.
     * @see MobileCapabilityType#ORIENTATION
     */
    public ScreenOrientation getOrientation() {
        return (ScreenOrientation) getCapability(MobileCapabilityType.ORIENTATION);
    }

    /**
     * Set the location of the app(s) to install before running a test.
     *
     * @param apps is the apps to install.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#OTHER_APPS
     */
    public T setOtherApps(List<String> apps) {
        return amend(MobileCapabilityType.OTHER_APPS, apps);
    }

    /**
     * Get the list of apps to install before running a test.
     *
     * @return apps to install.
     * @see MobileCapabilityType#OTHER_APPS
     */
    public Object getOtherApps() {
        return getCapability(MobileCapabilityType.OTHER_APPS);
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

    /**
     * Set the id of the device.
     *
     * @param id is the unique device identifier.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#UDID
     */
    public T setUdid(String id) {
        return amend(MobileCapabilityType.UDID, id);
    }

    /**
     * Get the id of the device.
     *
     * @return String representing the unique device identifier.
     * @see MobileCapabilityType#UDID
     */
    public String getUdid() {
        return (String) getCapability(MobileCapabilityType.UDID);
    }

    @Override
    public T merge(Capabilities extraCapabilities) {
        super.merge(extraCapabilities);
        return (T) this;
    }

    protected T amend(String optionName, Object value) {
        setCapability(optionName, value);
        return (T) this;
    }
}
