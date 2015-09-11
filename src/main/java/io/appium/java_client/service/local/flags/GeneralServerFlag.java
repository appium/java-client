package io.appium.java_client.service.local.flags;

/**
 * Here is the list of common Appium server arguments.
 * All flags are optional, but some are required in conjunction with certain others.
 * The full list is available here: {@link http://appium.io/slate/en/master/?ruby#appium-server-arguments}
 */
public enum GeneralServerFlag implements ServerArgument{
    /**
     * Enter REPL mode
     */
    SHELL("--shell"),
    /**
     * IOS: abs path to simulator-compiled .app file or the bundle_id of the desired target on device; Android: abs path to .apk file<br/>
     * Sample <br/>
     * --app /abs/path/to/my.app
     */
    APP("--app"),
    /**
     * Unique device identifier of the connected physical device<br/>
     * Sample <br/>
     * --udid 1adsf-sdfas-asdf-123sdf
     */
    UIID("--udid"),
    /**
     * callback IP Address (default: same as address) <br/>
     * Sample <br/>
     * --callback-address 127.0.0.1
     */
    CALLBACK_ADDRESS("--callback-address"),
    /**
     * callback port (default: same as port)<br/>
     * Sample <br/>
     * --callback-port 4723
     */
    CALLBACK_PORT("--callback-port"),
    /**
     * Enables session override (clobbering) <br/>
     * Default: false
     */
    SESSION_OVERRIDE("--session-override"),
    /**
     * Don’t reset app state between sessions (IOS: don’t delete app plist files; Android: don’t uninstall app before new session)<br/>
     * Default: false
     */
    NO_RESET("--no-reset"),
    /**
     * Pre-launch the application before allowing the first session (Requires –app and, for Android, –app-pkg and –app-activity) <br/>
     * Default: false
     */
    PRE_LAUNCH("--pre-launch"),
    /**
     * The message log level to be shown <br/>
     * Sample:<br/>
     * --log-level debug
     */
    LOG_LEVEL("--log-level"),
    /**
     * Show timestamps in console output <br/>
     * Default: false
     */
    LOG_TIMESTAMP("log-timestamp"),
    /**
     * Use local timezone for timestamps <br/>
     * Default: false
     */
    LOCAL_TIMEZONE("--local-timezone"),
    /**
     * Don’t use colors in console output <br/>
     * Default: false
     */
    LOG_NO_COLORS("--log-no-colors"),
    /**
     * Also send log output to this HTTP listener <br/>
     * Sample: <br/>
     * --webhook localhost:9876
     */
    WEB_HOOK("--webhook"),
    /**
     * Name of the mobile device to use<br/>
     * Sample: <br/>
     * --device-name iPhone Retina (4-inch), Android Emulator
     */
    DEVICE_NAME("--device-name"),
    /**
     * Name of the mobile platform: iOS, Android, or FirefoxOS <br/>
     * Sample:<br/>
     * --platform-name iOS
     */
    PLATFORM_NAME("--platform-name"),
    /**
     * Version of the mobile platform <br/>
     * Sample:<br/>
     * --platform-version 7.1
     */
    PLATFORM_VERSION("--platform-version"),
    /**
     * Name of the automation tool: Appium or Selendroid<br/>
     * Sample:<br/>
     * --automation-name Appium
     */
    AUTOMATION_NAME("--automation-name"),
    /**
     * Name of the mobile browser: Safari or Chrome<br/>
     * Sample: <br/>
     * --browser-name Safari
     */
    BROWSER_NAME("--browser-name"),
    /**
     * Language for the iOS simulator / Android Emulator <br/>
     * Sample:<br/>
     * --language en
     */
    LANGUAGE("--language"),
    /**
     * Locale for the iOS simulator / Android Emulator<br/>
     * Sample:<br/>
     * --locale en_US
     */
    LOCALE("--locale"),
    /**
     * Configuration JSON file to register Appium with selenium grid<br/>
     * Sample:<br/>
     * --nodeconfig /abs/path/to/nodeconfig.json
     */
    CONFIGURATION_FILE("--nodeconfig"),
    /**
     * IP Address of robot<br/>
     * Sample:<br/>
     * --robot-address 0.0.0.0
     */
    ROBOT_ADDRESS("--robot-address"),
    /**
     * Port for robot<br/>
     * Sample: <br/>
     * --robot-port 4242
     */
    ROBOT_PORT("--robot-port"),
    /**
     * Port upon which ChromeDriver will run<br/>
     * Sample:<br/>
     * --chromedriver-port 9515
     */
    CHROME_DRIVER_PORT("--chromedriver-port"),
    /**
     * ChromeDriver executable full path
     */
    CHROME_DRIVER_EXECUTABLE("--chromedriver-executable"),
    /**
     * Show info about the Appium server configuration and exit<br/>
     * Default: false
     */
    SHOW_CONFIG("--show-config"),
    /**
     * Bypass Appium’s checks to ensure we can read/write necessary files<br/>
     * Default: false
     */
    NO_PERMS_CHECKS( "--no-perms-check"),
    /**
     * The default command timeout for the server to use for all sessions. Will
     * still be overridden by newCommandTimeout cap<br/>
     * Default: 60
     */
    COMMAND_TIMEOUT("--command-timeout"),
    /**
     * Cause sessions to fail if desired caps are sent in that Appium does not
     * recognize as valid for the selected device<br/>
     * Default: false
     */
    STRICT_CAPS("--strict-caps"),
    /**
     * Absolute path to directory Appium can use to manage temporary files, like
     * built-in iOS apps it needs to move around. On *nix/Mac defaults to /tmp,
     * on Windows defaults to C:\Windows\Temp<br/>
     */
    TEMP_DIRECTORY("--tmp"),
    /**
     * Add exaggerated spacing in logs to help with visual inspection<br/>
     * Default: false
     */
    DEBUG_LOG_SPACING("--debug-log-spacing");
    ;

    private final String arg;

    GeneralServerFlag(String arg) {
        this.arg = arg;
    }

    @Override
    public String getArgument() {
        return arg;
    }
}
