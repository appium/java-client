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
public enum GeneralServerFlag implements ServerArgument {
    /**
     * Enter REPL mode.
     */
    SHELL("--shell"),
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
     * Configuration JSON file to register Appium with selenium grid. Sample:
     * --nodeconfig /abs/path/to/nodeconfig.json
     */
    CONFIGURATION_FILE("--nodeconfig"),
    /**
     * Show info about the Appium server configuration and exit. Default: false
     */
    SHOW_CONFIG("--show-config"),
    /**
     * Bypass Appium’s checks to ensure we can read/write necessary files. Default: false
     */
    NO_PERMS_CHECKS("--no-perms-check"),
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
     * Default: false
     */
    ASYNC_TRACE("--async-trace"),
    /**
     * Disable additional security checks. Only enable it if all the clients are in the trusted network.
     * Default: false
     */
    RELAXED_SECURITY("--relaxed-security"),
    /**
     * Enables NodeJS memory dumps collection feature.
     */
    ENABLE_HEAP_DUMP("--enable-heapdump"),
    /**
     * Allow a list of features which are considered insecure and must be turned on
     * explicitly by system administrators.
     * Default: []
     * Sample: --allow-insecure=foo,bar
     */
    ALLOW_INSECURE("--allow-insecure"),
    /**
     * Specify a list of features which will never be allowed to run, even if --relaxed-security
     * is turned on, and even if feature names are listed with --allow-insecure.
     * Default: []
     * Sample: --deny-insecure=foo,bar
     */
    DENY_INSECURE("--deny-insecure"),
    /**
     * Plugins are little programs which can be added to an Appium installation and activated, for the purpose of
     * extending or modifying the behavior of pretty much any aspect of Appium.
     * Plugins are available with Appium as of Appium 2.0.
     * To activate all plugins, you can use the single string "all" as the value (e.g --plugins=all)
     * Default: []
     * Sample: --use-plugins=device-farm,images
     */
    USE_PLUGINS("--use-plugins"),
    /**
     * A comma-separated list of installed driver names that should be active for this server.
     * All drivers will be active by default.
     * Default: []
     * Sample: --use-drivers=uiautomator2,xcuitest
     */
    USE_DRIVERS("--use-drivers"),
    /**
     * Base path to use as the prefix for all webdriver routes running on this server.
     * Sample: --base-path=/wd/hub
     */
    BASEPATH("--base-path");

    private final String arg;

    GeneralServerFlag(String arg) {
        this.arg = arg;
    }

    @Override public String getArgument() {
        return arg;
    }
}
