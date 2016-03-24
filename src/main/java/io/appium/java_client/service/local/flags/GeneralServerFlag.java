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

package io.appium.java_client.service.local.flags;


/**
 * Here is the list of common Appium server arguments.
 */
public enum GeneralServerFlag implements ServerArgument{
    /**
     * Enter REPL mode
     */
    SHELL("--shell"),

    @Deprecated
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * IOS: abs path to simulator-compiled .app file or the bundle_id of the desired target on device; Android: abs path to .apk file.
     * Sample: --app /abs/path/to/my.app
     */
    APP("--app"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Unique device identifier of the connected physical device.
     * Sample:
     * --udid 1adsf-sdfas-asdf-123sdf
     */
    @Deprecated
    UIID("--udid"),
    /**
     * callback IP Address (default: same as address).
     * Sample: --callback-address 127.0.0.1
     */
    CALLBACK_ADDRESS("--callback-address"),
    /**
     * callback port (default: same as port).
     * Sample: --callback-port 4723
     */
    CALLBACK_PORT("--callback-port"),
    /**
     * Enables session override (clobbering). Default: false
     */
    SESSION_OVERRIDE("--session-override"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Don’t reset app state between sessions (IOS: don’t delete app plist files; Android: don’t uninstall app before new session).
     * Default: false
     */
    @Deprecated
    NO_RESET("--no-reset"),
    /**
     * Pre-launch the application before allowing the first session (Requires –app and, for Android, –app-pkg and –app-activity).
     * Default: false
     */
    PRE_LAUNCH("--pre-launch"),
    /**
     * The message log level to be shown.
     * Sample: --log-level debug
     */
    LOG_LEVEL("--log-level"),
    /**
     * Show timestamps in console output.
     * Default: false
     */
    LOG_TIMESTAMP("--log-timestamp"),
    /**
     * Use local timezone for timestamps.
     * Default: false
     */
    LOCAL_TIMEZONE("--local-timezone"),
    /**
     * Don’t use colors in console output.
     * Default: false
     */
    LOG_NO_COLORS("--log-no-colors"),
    /**
     * Also send log output to this HTTP listener.
     * Sample: --webhook localhost:9876
     */
    WEB_HOOK("--webhook"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Name of the mobile device to use.
     * Sample: --device-name iPhone Retina (4-inch), Android Emulator
     */
    @Deprecated
    DEVICE_NAME("--device-name"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Name of the mobile platform: iOS, Android, or FirefoxOS
     * Sample: --platform-name iOS
     */
    @Deprecated
    PLATFORM_NAME("--platform-name"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Version of the mobile platform. Sample: --platform-version 7.1
     */
    @Deprecated
    PLATFORM_VERSION("--platform-version"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Name of the automation tool: Appium or Selendroid. Sample: --automation-name Appium
     */
    @Deprecated
    AUTOMATION_NAME("--automation-name"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Name of the mobile browser: Safari or Chrome. Sample: --browser-name Safari
     */
    @Deprecated
    BROWSER_NAME("--browser-name"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Language for the iOS simulator / Android Emulator. Sample: --language en
     */
    @Deprecated
    LANGUAGE("--language"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * Locale for the iOS simulator / Android Emulator. Sample: --locale en_US
     */
    @Deprecated
    LOCALE("--locale"),
    /**
     * Configuration JSON file to register Appium with selenium grid. Sample:
     * --nodeconfig /abs/path/to/nodeconfig.json
     */
    CONFIGURATION_FILE("--nodeconfig"),
    /**
     * IP Address of robot. Sample: --robot-address 0.0.0.0
     */
    ROBOT_ADDRESS("--robot-address"),
    /**
     * Port for robot. Sample: --robot-port 4242
     */
    ROBOT_PORT("--robot-port"),

    @Deprecated
    /**
     * This flag IS deprecated because it is moved to
     * {@link AndroidServerFlag#CHROME_DRIVER_PORT}
     *
     * Port upon which ChromeDriver will run. Sample: --chromedriver-port 9515
     */
    CHROME_DRIVER_PORT("--chromedriver-port"),
    @Deprecated
    /**
     * This flag IS deprecated because it is moved to
     * {@link AndroidServerFlag#CHROME_DRIVER_EXECUTABLE}
     *
     * ChromeDriver executable full path
     */
    CHROME_DRIVER_EXECUTABLE("--chromedriver-executable"),
    /**
     * Show info about the Appium server configuration and exit. Default: false
     */
    SHOW_CONFIG("--show-config"),
    /**
     * Bypass Appium’s checks to ensure we can read/write necessary files. Default: false
     */
    NO_PERMS_CHECKS( "--no-perms-check"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     *
     * The default command timeout for the server to use for all sessions. Will
     * still be overridden by newCommandTimeout cap. Default: 60
     */
    @Deprecated
    COMMAND_TIMEOUT("--command-timeout"),
    /**
     * Cause sessions to fail if desired caps are sent in that Appium does not
     * recognize as valid for the selected device. Default: false
     */
    STRICT_CAPS("--strict-caps"),
    /**
     * Absolute path to directory Appium can use to manage temporary files, like
     * built-in iOS apps it needs to move around. On *nix/Mac defaults to /tmp,
     * on Windows defaults to C:\Windows\Temp.
     */
    TEMP_DIRECTORY("--tmp"),
    /**
     * Add exaggerated spacing in logs to help with visual inspection. Default: false
     */
    DEBUG_LOG_SPACING("--debug-log-spacing"),

    /**
     * Add long stack traces to log entries. Recommended for debugging only.
     *
     * Default: false
     */
    ASYNC_TRACE("--async-trace");

    private final String arg;

    GeneralServerFlag(String arg) {
        this.arg = arg;
    }

    @Override
    public String getArgument() {
        return arg;
    }
}
