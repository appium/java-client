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
 * The list of common capabilities.
 * Read:
 * https://github.com/appium/appium/blob/1.5/docs/en/
 * writing-running-appium/caps.md#appium-server-capabilities
 */
public interface MobileCapabilityType extends CapabilityType {

    /**
     * Which automation engine to use.
     */
    String AUTOMATION_NAME = "automationName";

    /**
     * Which mobile OS platform to use.
     */
    String PLATFORM_NAME = "platformName";

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
     * The absolute local path or remote http URL to an .ipa or .apk file,
     * or a .zip containing one of these. Appium will attempt to install this app
     * binary on the appropriate device first. Note that this capability is not required for
     * Android if you specify appPackage and appActivity capabilities (see below).
     * Incompatible with browserName.
     */
    String APP = "app";

    /**
     * Name of mobile web browser to automate.
     * Should be an empty string if automating an app instead.
     */
    String BROWSER_NAME = "browserName";

    /**
     * Unique device identifier of the connected physical device.
     */
    String UDID = "udid";

    /**
     * Sauce-specific.
     */
    String APPIUM_VERSION = "appiumVersion";

    /**
     * (Sim/Emu-only) Language to set for the simulator / emulator.
     */
    String LANGUAGE = "language";

    /**
     * (Sim/Emu-only) Locale to set for the simulator / emulator.
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
     * Don't reset app state before this session. Default false.
     */
    String NO_RESET = "noReset";

    /**
     * (iOS) Delete the entire simulator folder.
     * (Android) Reset app state by uninstalling app instead of clearing app data.
     * On Android, this will also remove the app after the session is complete. Default false.
     */
    String FULL_RESET = "fullReset";
}
