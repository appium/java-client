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

import org.openqa.selenium.remote.CapabilityType;

/**
 * The list of common capabilities. <br>
 * Read: <br>
 * <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md#general-capabilities">
 * https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md#general-capabilities</a>
 *
 * @deprecated This interface will be removed. Use Options instead.
 */
@Deprecated
public interface MobileCapabilityType extends CapabilityType {

    /**
     * Which automation engine to use.
     */
    String AUTOMATION_NAME = "automationName";

    /**
     * Mobile OS version.
     */
    String PLATFORM_VERSION = "platformVersion";

    /**
     * The kind of mobile device or emulator to use.
     */
    String DEVICE_NAME = "deviceName";

    /**
     * How long (in seconds) Appium will wait for a new command from the
     * client before assuming the client quit and ending the session.
     */
    String NEW_COMMAND_TIMEOUT = "newCommandTimeout";

    /**
     * The absolute local path or remote http URL to a {@code .ipa} file (IOS),
     * {@code .app} folder (IOS Simulator), {@code .apk} file (Android) or {@code .apks} file (Android App Bundle),
     * or a {@code .zip} file containing one of these (for .app, the .app folder must be the root of the zip file).
     * Appium will attempt to install this app binary on the appropriate device first.
     * Note that this capability is not required for Android if you specify {@code appPackage}
     * and {@code appActivity} capabilities (see below). Incompatible with {@code browserName}. See
     * <a href="https://github.com/appium/appium/blob/v1.13.0/docs/en/writing-running-appium/android/android-appbundle.md">
     * here</a>
     * about {@code .apks} file.
     */
    String APP = "app";

    /**
     * Unique device identifier of the connected physical device.
     */
    String UDID = "udid";


    /**
     * Language to set for iOS (XCUITest driver only) and Android.
     */
    String LANGUAGE = "language";

    /**
     * Locale to set for iOS (XCUITest driver only) and Android.
     * {@code fr_CA} format for iOS. {@code CA} format (country name abbreviation) for Android
     */
    String LOCALE = "locale";

    /**
     * (Sim/Emu-only) start in a certain orientation.
     */
    String ORIENTATION = "orientation";

    /**
     * Move directly into Webview context. Default false.
     */
    String AUTO_WEBVIEW = "autoWebview";

    /**
     * Don't reset app state before this session. See
     * <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/other/reset-strategies.md">
     * here</a>
     * for more detail.
     */
    String NO_RESET = "noReset";

    /**
     * Perform a complete reset. See
     * <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/other/reset-strategies.md">
     * here</a>
     * for more detail.
     */
    String FULL_RESET = "fullReset";

    /**
     * The desired capability which specifies whether to delete any generated files at
     * the end of a session (see iOS and Android entries for particulars).
     */
    String CLEAR_SYSTEM_FILES = "clearSystemFiles";

    /**
     * Enable or disable the reporting of the timings for various Appium-internal events
     * (e.g., the start and end of each command, etc.). Defaults to {@code false}.
     * To enable, use {@code true}. The timings are then reported as {@code events} property on response
     * to querying the current session. See the
     * <a href="https://github.com/appium/appium/blob/master/docs/en/advanced-concepts/event-timings.md">
     * event timing docs</a> for the the structure of this response.
     */
    String EVENT_TIMINGS = "eventTimings";

    /**
     * (Web and webview only) Enable ChromeDriver's (on Android)
     * or Safari's (on iOS) performance logging (default {@code false}).
     */
    String ENABLE_PERFORMANCE_LOGGING = "enablePerformanceLogging";


    /**
     * App or list of apps (as a JSON array) to install prior to running tests. Note that it will not work with
     * automationName of Espresso and iOS real devices.
     */
    String OTHER_APPS = "otherApps";

    /**
     * When a find operation fails, print the current page source. Defaults to false.
     */
    String PRINT_PAGE_SOURCE_ON_FIND_FAILURE = "printPageSourceOnFindFailure";
}
