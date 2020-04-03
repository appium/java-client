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

public class MobileOptions<T extends MobileOptions<T>> extends MutableCapabilities {

    public MobileOptions() {
    }

    public MobileOptions(Capabilities source) {
        merge(source);
    }

    public T setPlatformName(String platform) {
        return amend(CapabilityType.PLATFORM_NAME, platform);
    }

    public String getPlatformName() {
        return (String) getCapability(CapabilityType.PLATFORM_NAME);
    }

    public T setApp(String path) {
        return amend(MobileCapabilityType.APP, path);
    }

    public T setApp(URL url) {
        return setApp(url.toString());
    }

    public String getApp() {
        return (String) getCapability(MobileCapabilityType.APP);
    }

    public T setAutomationName(String name) {
        return amend(MobileCapabilityType.AUTOMATION_NAME, name);
    }

    public String getAutomationName() {
        return (String) getCapability(MobileCapabilityType.AUTOMATION_NAME);
    }

    public T setAutoWebview() {
        return setAutoWebview(true);
    }

    public T setAutoWebview(boolean bool) {
        return amend(MobileCapabilityType.AUTO_WEBVIEW, bool);
    }

    public boolean doesAutoWebview() {
        return (boolean) getCapability(MobileCapabilityType.AUTO_WEBVIEW);
    }

    public T setClearSystemFiles() {
        return setClearSystemFiles(true);
    }

    public T setClearSystemFiles(boolean bool) {
        return amend(MobileCapabilityType.CLEAR_SYSTEM_FILES, bool);
    }

    public boolean doesClearSystemFiles() {
        return (boolean) getCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES);
    }

    public T setDeviceName(String deviceName) {
        return amend(MobileCapabilityType.DEVICE_NAME, deviceName);
    }

    public String getDeviceName() {
        return (String) getCapability(MobileCapabilityType.DEVICE_NAME);
    }

    public T setEnablePerformanceLogging() {
        return setEnablePerformanceLogging(true);
    }

    public T setEnablePerformanceLogging(boolean bool) {
        return amend(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING, bool);
    }

    public boolean isEnablePerformanceLogging() {
        return (boolean) getCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING);
    }

    public T setEventTimings() {
        return setEventTimings(true);
    }

    public T setEventTimings(boolean bool) {
        return amend(MobileCapabilityType.EVENT_TIMINGS, bool);
    }

    public boolean doesEventTimings() {
        return (boolean) getCapability(MobileCapabilityType.EVENT_TIMINGS);
    }

    public T setFullReset() {
        return setFullReset(true);
    }

    public T setFullReset(boolean bool) {
        return amend(MobileCapabilityType.FULL_RESET, bool);
    }

    public boolean doesFullReset() {
        return (boolean) getCapability(MobileCapabilityType.FULL_RESET);
    }

    public T setLanguage(String language) {
        return amend(MobileCapabilityType.LANGUAGE, language);
    }

    public String getLanguage() {
        return (String) getCapability(MobileCapabilityType.LANGUAGE);
    }

    public T setLocale(String locale) {
        return amend(MobileCapabilityType.LOCALE, locale);
    }

    public String getLocale() {
        return (String) getCapability(MobileCapabilityType.LOCALE);
    }

    public T setNewCommandTimeout(Duration duration) {
        return amend(MobileCapabilityType.NEW_COMMAND_TIMEOUT, duration.getSeconds());
    }

    public Duration getNewCommandTimeout() {
        Object duration = getCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT);
        return Duration.ofSeconds(Long.parseLong("" + duration));
    }

    public T setNoReset() {
        return setNoReset(true);
    }

    public T setNoReset(boolean bool) {
        return amend(MobileCapabilityType.NO_RESET, bool);
    }

    public boolean doesNoReset() {
        return (boolean) getCapability(MobileCapabilityType.NO_RESET);
    }

    public T setOrientation(ScreenOrientation orientation) {
        return amend(MobileCapabilityType.ORIENTATION, orientation);
    }

    public ScreenOrientation getOrientation() {
        return (ScreenOrientation) getCapability(MobileCapabilityType.ORIENTATION);
    }

    public T setOtherApps(String apps) {
        return amend(MobileCapabilityType.OTHER_APPS, apps);
    }

    public String getOtherApps() {
        return (String) getCapability(MobileCapabilityType.OTHER_APPS);
    }

    public T setPlatformVersion(String version) {
        return amend(MobileCapabilityType.PLATFORM_VERSION, version);
    }

    public String getPlatformVersion() {
        return (String) getCapability(MobileCapabilityType.PLATFORM_VERSION);
    }

    public T setPrintPageSourceOnFindFailure() {
        return setPrintPageSourceOnFindFailure(true);
    }

    public T setPrintPageSourceOnFindFailure(boolean bool) {
        return amend(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE, bool);
    }

    public boolean doesPrintPageSourceOnFindFailure() {
        return (boolean) getCapability(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE);
    }

    public T setUdid(String id) {
        return amend(MobileCapabilityType.UDID, id);
    }

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
