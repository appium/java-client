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
 * The list of Android-specific capabilities. <br>
 * Read: <br>
 * <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md#android-only">
 * https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/caps.md#android-only</a>
 */
public interface AndroidMobileCapabilityType extends CapabilityType {

    /**
     * Activity name for the Android activity you want to launch from your package.
     * This often needs to be preceded by a {@code .} (e.g., {@code .MainActivity}
     * instead of {@code MainActivity}). By default this capability is received from the package
     * manifest (action: android.intent.action.MAIN , category: android.intent.category.LAUNCHER)
     */
    String APP_ACTIVITY = "appActivity";

    /**
     * Java package of the Android app you want to run. By default this capability is received
     * from the package manifest ({@literal @}package attribute value)
     */
    String APP_PACKAGE = "appPackage";

    /**
     * Activity name/names, comma separated, for the Android activity you want to wait for.
     * By default the value of this capability is the same as for {@code appActivity}.
     * You must set it to the very first focused application activity name in case it is different
     * from the one which is set as {@code appActivity} if your capability has {@code appActivity}
     * and {@code appPackage}. You can also use wildcards ({@code *}).
     */
    String APP_WAIT_ACTIVITY = "appWaitActivity";

    /**
     * Java package of the Android app you want to wait for.
     * By default the value of this capability is the same as for {@code appActivity}
     */
    String APP_WAIT_PACKAGE = "appWaitPackage";

    /**
     * Timeout in milliseconds used to wait for the appWaitActivity to launch (default 20000).
     * @since 1.6.0
     */
    String APP_WAIT_DURATION = "appWaitDuration";

    /**
     * Timeout in seconds while waiting for device to become ready.
     */
    String DEVICE_READY_TIMEOUT = "deviceReadyTimeout";

    /**
     * Allow to install a test package which has {@code android:testOnly="true"} in the manifest.
     * {@code false} by default
     */
    String ALLOW_TEST_PACKAGES = "allowTestPackages";

    /**
     * Fully qualified instrumentation class. Passed to -w in adb shell
     * am instrument -e coverage true -w.
     */
    String ANDROID_COVERAGE = "androidCoverage";

    /**
     * A broadcast action implemented by yourself which is used to dump coverage into file system.
     * Passed to -a in adb shell am broadcast -a
     */
    String ANDROID_COVERAGE_END_INTENT = "androidCoverageEndIntent";

    /**
     * (Chrome and webview only) Enable Chromedriver's performance logging (default false).
     *
     * @deprecated move to {@link MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING}
     */
    @Deprecated
    String ENABLE_PERFORMANCE_LOGGING = "enablePerformanceLogging";

    /**
     * Timeout in seconds used to wait for a device to become ready after booting.
     */
    String ANDROID_DEVICE_READY_TIMEOUT = "androidDeviceReadyTimeout";

    /**
     * Port used to connect to the ADB server (default 5037).
     */
    String ADB_PORT = "adbPort";

    /**
     * Devtools socket name. Needed only when tested app is a Chromium embedding browser.
     * The socket is open by the browser and Chromedriver connects to it as a devtools client.
     */
    String ANDROID_DEVICE_SOCKET = "androidDeviceSocket";

    /**
     * Timeout in milliseconds used to wait for an apk to install to the device. Defaults to `90000`.
     * @since 1.6.0
     */
    String ANDROID_INSTALL_TIMEOUT = "androidInstallTimeout";

    /**
     * The name of the directory on the device in which the apk will be push before install.
     * Defaults to {@code /data/local/tmp}
     * @since 1.6.5
     */
    String ANDROID_INSTALL_PATH = "androidInstallPath";

    /**
     * Name of avd to launch.
     */
    String AVD = "avd";

    /**
     * How long to wait in milliseconds for an avd to launch and connect to
     * ADB (default 120000).
     * @since 0.18.0
     */
    String AVD_LAUNCH_TIMEOUT = "avdLaunchTimeout";

    /**
     * How long to wait in milliseconds for an avd to finish its
     * boot animations (default 120000).
     * @since 0.18.0
     */
    String AVD_READY_TIMEOUT = "avdReadyTimeout";

    /**
     * Additional emulator arguments used when launching an avd.
     */
    String AVD_ARGS = "avdArgs";

    /**
     * Use a custom keystore to sign apks, default false.
     */
    String USE_KEYSTORE = "useKeystore";

    /**
     * Path to custom keystore, default ~/.android/debug.keystore.
     */
    String KEYSTORE_PATH = "keystorePath";

    /**
     * Password for custom keystore.
     */
    String KEYSTORE_PASSWORD = "keystorePassword";

    /**
     * Alias for key.
     */
    String KEY_ALIAS = "keyAlias";

    /**
     * Password for key.
     */
    String KEY_PASSWORD = "keyPassword";

    /**
     * The absolute local path to webdriver executable (if Chromium embedder provides
     * its own webdriver, it should be used instead of original chromedriver
     * bundled with Appium).
     */
    String CHROMEDRIVER_EXECUTABLE = "chromedriverExecutable";

    /**
     * An array of arguments to be passed to the chromedriver binary when it's run by Appium.
     * By default no CLI args are added beyond what Appium uses internally (such as {@code --url-base}, {@code --port},
     * {@code --adb-port}, and {@code --log-path}.
     * @since 1.12.0
     */
    String CHROMEDRIVER_ARGS = "chromedriverArgs";

    /**
     * The absolute path to a directory to look for Chromedriver executables in, for automatic discovery of compatible
     * Chromedrivers. Ignored if {@code chromedriverUseSystemExecutable} is {@code true}
     * @since 1.8.0
     */
    String CHROMEDRIVER_EXECUTABLE_DIR = "chromedriverExecutableDir";

    /**
     * The absolute path to a file which maps Chromedriver versions to the minimum Chrome that it supports.
     * Ignored if {@code chromedriverUseSystemExecutable} is {@code true}
     * @since 1.8.0
     */
    String CHROMEDRIVER_CHROME_MAPPING_FILE = "chromedriverChromeMappingFile";

    /**
     * If true, bypasses automatic Chromedriver configuration and uses the version that comes downloaded with Appium.
     * Ignored if {@code chromedriverExecutable} is set. Defaults to {@code false}
     * @since 1.9.0
     */
    String CHROMEDRIVER_USE_SYSTEM_EXECUTABLE = "chromedriverUseSystemExecutable";

    /**
     * Numeric port to start Chromedriver on. Note that use of this capability is discouraged as it will cause undefined
     * behavior in case there are multiple webviews present. By default Appium will find a free port.
     */
    String CHROMEDRIVER_PORT = "chromedriverPort";

    /**
     * A list of valid ports for Appium to use for communication with Chromedrivers. This capability supports multiple
     * webview scenarios. The form of this capability is an array of numeric ports, where array items can themselves be
     * arrays of length 2, where the first element is the start of an inclusive range and the second is the end.
     * By default, Appium will use any free port.
     * @since 1.13.0
     */
    String CHROMEDRIVER_PORTS = "chromedriverPorts";

    /**
     * Sets the chromedriver flag {@code --disable-build-check} for Chrome webview tests.
     * @since 1.11.0
     */
    String CHROMEDRIVER_DISABLE_BUILD_CHECK = "chromedriverDisableBuildCheck";

    /**
     * Amount of time to wait for Webview context to become active, in ms. Defaults to 2000.
     * @since 1.5.2
     */
    String AUTO_WEBVIEW_TIMEOUT = "autoWebviewTimeout";

    /**
     * Intent action which will be used to start activity
     * (default android.intent.action.MAIN).
     */
    String INTENT_ACTION = "intentAction";

    /**
     * Intent category which will be used to start
     * activity (default android.intent.category.LAUNCHER).
     */
    String INTENT_CATEGORY = "intentCategory";

    /**
     * Flags that will be used to start activity (default 0x10200000).
     */
    String INTENT_FLAGS = "intentFlags";

    /**
     * Additional intent arguments that will be used to start activity. See
     * <a href="https://developer.android.com/reference/android/content/Intent.html">
     * Intent arguments</a>.
     */
    String OPTIONAL_INTENT_ARGUMENTS = "optionalIntentArguments";

    /**
     * Doesn't stop the process of the app under test, before starting the app using adb.
     * If the app under test is created by another anchor app, setting this false,
     * allows the process of the anchor app to be still alive, during the start of
     * the test app using adb. In other words, with dontStopAppOnReset set to true,
     * we will not include the -S flag in the adb shell am start call.
     * With this capability omitted or set to false, we include the -S flag. Default false
     * @since 1.4.0
     */
    String DONT_STOP_APP_ON_RESET = "dontStopAppOnReset";

    /**
     * Enable Unicode input, default false.
     * @since 1.2.0
     */
    String UNICODE_KEYBOARD = "unicodeKeyboard";

    /**
     * Reset keyboard to its original state, after running Unicode tests with
     * unicodeKeyboard capability. Ignored if used alone. Default false
     */
    String RESET_KEYBOARD = "resetKeyboard";

    /**
     * Skip checking and signing of app with debug keys, will work only with
     * UiAutomator and not with selendroid, default false.
     * @since 1.2.2
     */
    String NO_SIGN = "noSign";

    /**
     * Calls the setCompressedLayoutHierarchy() uiautomator function.
     * This capability can speed up test execution, since Accessibility commands will run
     * faster ignoring some elements. The ignored elements will not be findable,
     * which is why this capability has also been implemented as a toggle-able
     * setting as well as a capability. Defaults to false.
     */
    String IGNORE_UNIMPORTANT_VIEWS = "ignoreUnimportantViews";

    /**
     * Disables android watchers that watch for application not responding and application crash,
     * this will reduce cpu usage on android device/emulator. This capability will work only with
     * UiAutomator and not with selendroid, default false.
     * @since 1.4.0
     */
    String DISABLE_ANDROID_WATCHERS = "disableAndroidWatchers";

    /**
     * Allows passing chromeOptions capability for ChromeDriver.
     * For more information see
     * <a href="https://sites.google.com/a/chromium.org/chromedriver/capabilities">
     * chromeOptions</a>.
     */
    String CHROME_OPTIONS = "chromeOptions";

    /**
     * Kill ChromeDriver session when moving to a non-ChromeDriver webview.
     * Defaults to false
     */
    String RECREATE_CHROME_DRIVER_SESSIONS = "recreateChromeDriverSessions";

    /**
     * In a web context, use native (adb) method for taking a screenshot, rather than proxying
     * to ChromeDriver, default false.
     * @since 1.5.3
     */
    String NATIVE_WEB_SCREENSHOT = "nativeWebScreenshot";

    /**
     * The name of the directory on the device in which the screenshot will be put.
     * Defaults to /data/local/tmp.
     * @since 1.6.0
     */
    String ANDROID_SCREENSHOT_PATH = "androidScreenshotPath";

    /**
     * Set the network speed emulation. Specify the maximum network upload and download speeds. Defaults to {@code full}
     */
    String NETWORK_SPEED = "networkSpeed";

    /**
     * Toggle gps location provider for emulators before starting the session. By default the emulator will have this
     * option enabled or not according to how it has been provisioned.
     */
    String GPS_ENABLED = "gpsEnabled";

    /**
     * Set this capability to {@code true} to run the Emulator headless when device display is not needed to be visible.
     * {@code false} is the default value. isHeadless is also support for iOS, check XCUITest-specific capabilities.
     */
    String IS_HEADLESS = "isHeadless";

    /**
     * Timeout in milliseconds used to wait for adb command execution. Defaults to {@code 20000}
     */
    String ADB_EXEC_TIMEOUT = "adbExecTimeout";

    /**
     * Sets the locale <a href="https://developer.android.com/reference/java/util/Locale">script</a>.
     * @since 1.10.0
     */
    String LOCALE_SCRIPT = "localeScript";

    /**
     * Skip device initialization which includes i.a.: installation and running of Settings app or setting of
     * permissions. Can be used to improve startup performance when the device was already used for automation and
     * it's prepared for the next automation. Defaults to {@code false}
     * @since 1.11.0
     */
    String SKIP_DEVICE_INITIALIZATION = "skipDeviceInitialization";

    /**
     * Have Appium automatically determine which permissions your app requires and
     * grant them to the app on install. Defaults to {@code false}. If noReset is {@code true}, this capability doesn't
     * work.
     */
    String AUTO_GRANT_PERMISSIONS = "autoGrantPermissions";

    /**
     * Allow for correct handling of orientation on landscape-oriented devices.
     * Set to {@code true} to basically flip the meaning of {@code PORTRAIT} and {@code LANDSCAPE}.
     * Defaults to {@code false}.
     * @since 1.6.4
     */
    String ANDROID_NATURAL_ORIENTATION = "androidNaturalOrientation";

    /**
     * {@code systemPort} used to connect to <a href="https://github.com/appium/appium-uiautomator2-server">
     * appium-uiautomator2-server</a> or
     * <a href="https://github.com/appium/appium-espresso-driver">appium-espresso-driver</a>.
     * The default is {@code 8200} in general and selects one port from {@code 8200} to {@code 8299}
     * for appium-uiautomator2-server, it is {@code 8300} from {@code 8300} to {@code 8399} for
     * appium-espresso-driver. When you run tests in parallel, you must adjust the port to avoid conflicts. Read
     * <a href="https://github.com/appium/appium/blob/master/docs/en/advanced-concepts/parallel-tests.md#parallel-android-tests">
     * Parallel Testing Setup Guide</a> for more details.
     */
    String SYSTEM_PORT = "systemPort";

    /**
     * Optional remote ADB server host.
     * @since 1.7.0
     */
    String REMOTE_ADB_HOST = "remoteAdbHost";

    /**
     * Skips unlock during session creation. Defaults to {@code false}
     */
    String SKIP_UNLOCK = "skipUnlock";

    /**
     * Unlock the target device with particular lock pattern instead of just waking up the device with a helper app.
     * It works with {@code unlockKey} capability. Defaults to undefined. {@code fingerprint} is available only for
     * Android 6.0+ and emulators.
     * Read <a href="https://github.com/appium/appium-android-driver/blob/master/docs/UNLOCK.md">unlock doc</a> in
     * android driver.
     */
    String UNLOCK_TYPE = "unlockType";

    /**
     * A key pattern to unlock used by {@code unlockType}.
     */
    String UNLOCK_KEY = "unlockKey";

    /**
     * Initializing the app under test automatically.
     * Appium does not launch the app under test if this is {@code false}. Defaults to {@code true}
     */
    String AUTO_LAUNCH = "autoLaunch";

    /**
     * Skips to start capturing logcat. It might improve performance such as network.
     * Log related commands will not work. Defaults to {@code false}.
     * @since 1.12.0
     */
    String SKIP_LOGCAT_CAPTURE = "skipLogcatCapture";

    /**
     * A package, list of packages or * to uninstall package/s before installing apks for test.
     * {@code '*'} uninstall all of thrid-party packages except for packages which is necessary for Appium to test such
     * as {@code io.appium.settings} or {@code io.appium.uiautomator2.server} since Appium already contains the logic to
     * manage them.
     * @since 1.12.0
     */
    String UNINSTALL_OTHER_PACKAGES = "uninstallOtherPackages";

    /**
     * Set device animation scale zero if the value is {@code true}. After session is complete, Appium restores the
     * animation scale to it's original value. Defaults to {@code false}
     * @since 1.9.0
     */
    String DISABLE_WINDOW_ANIMATION = "disableWindowAnimation";

    /**
     * Specify the Android build-tools version to be something different than the default, which is to use the most
     * recent version. It is helpful to use a non-default version if your environment uses alpha/beta build tools.
     * @since 1.14.0
     */
    String BUILD_TOOLS_VERSION  = "buildToolsVersion";

    /**
     * By default application installation is skipped if newer or the same version of this app is already present on
     * the device under test. Setting this option to {@code true} will enforce Appium to always install the current
     * application build independently of the currently installed version of it. Defaults to {@code false}.
     * @since 1.16.0
     */
    String ENFORCE_APP_INSTALL = "enforceAppInstall";

    /**
     * Whether or not Appium should augment its webview detection with page detection, guaranteeing that any
     * webview contexts which show up in the context list have active pages. This can prevent an error if a
     * context is selected where Chromedriver cannot find any pages. Defaults to {@code false}.
     * @since 1.15.0
     */
    String ENSURE_WEBVIEWS_HAVE_PAGES = "ensureWebviewsHavePages";

    /**
     * To support the `ensureWebviewsHavePages` feature, it is necessary to open a TCP port for communication with
     * the webview on the device under test. This capability allows overriding of the default port of {@code 9222},
     * in case multiple sessions are running simultaneously (to avoid port clash), or in case the default port
     * is not appropriate for your system.
     * @since 1.15.0
     */
    String WEBVIEW_DEVTOOLS_PORT = "webviewDevtoolsPort";

    /**
     * Set the maximum number of remote cached apks which are pushed to the device-under-test's
     * local storage. Caching apks remotely speeds up the execution of sequential test cases, when using the
     * same set of apks, by avoiding the need to be push an apk to the remote file system every time a
     * reinstall is needed. Set this capability to {@code 0} to disable caching. Defaults to {@code 10}.
     * @since 1.14.0
     */
    String REMOTE_APPS_CACHE_LIMIT = "remoteAppsCacheLimit";
}
