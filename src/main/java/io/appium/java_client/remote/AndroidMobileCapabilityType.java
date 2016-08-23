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
 * The list of Android-specific capabilities.
 * Read:
 * https://github.com/appium/appium/blob/1.5/docs/en/writing-running-appium/caps.md#android-only
 */
public interface AndroidMobileCapabilityType extends CapabilityType {
    /**
     * Activity name for the Android activity you want to launch from your package.
     * This often needs to be preceded by a . (e.g., .MainActivity instead of MainActivity).
     */
    String APP_ACTIVITY = "appActivity";

    /**
     * Java package of the Android app you want to run.
     */
    String APP_PACKAGE = "appPackage";

    /**
     * Activity name for the Android activity you want to wait for.
     */
    String APP_WAIT_ACTIVITY = "appWaitActivity";

    /**
     * Java package of the Android app you want to wait for.
     */
    String APP_WAIT_PACKAGE = "appWaitPackage";

    /**
     * Timeout in milliseconds used to wait for the appWaitActivity to launch (default 20000).
     */
    String APP_WAIT_DURATION = "appWaitDuration";

    /**
     * Timeout in seconds while waiting for device to become ready.
     */
    String DEVICE_READY_TIMEOUT = "deviceReadyTimeout";

    /**
     * Fully qualified instrumentation class. Passed to -w in adb shell
     * am instrument -e coverage true -w.
     */
    String ANDROID_COVERAGE = "androidCoverage";

    /**
     * (Chrome and webview only) Enable Chromedriver's performance logging (default false).
     */
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
     */
    String ANDROID_INSTALL_TIMEOUT = "androidInstallTimeout";

    /**
     * Name of avd to launch.
     */
    String AVD = "avd";

    /**
     * How long to wait in milliseconds for an avd to launch and connect to
     * ADB (default 120000).
     */
    String AVD_LAUNCH_TIMEOUT = "avdLaunchTimeout";

    /**
     * How long to wait in milliseconds for an avd to finish its
     * boot animations (default 120000).
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
     * Amount of time to wait for Webview context to become active, in ms. Defaults to 2000.
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
     * Additional intent arguments that will be used to start activity. See Intent arguments:
     * http://developer.android.com/reference/android/content/Intent.html
     */
    String OPTIONAL_INTENT_ARGUMENTS = "optionalIntentArguments";

    /**
     * Doesn't stop the process of the app under test, before starting the app using adb.
     * If the app under test is created by another anchor app, setting this false,
     * allows the process of the anchor app to be still alive, during the start of
     * the test app using adb. In other words, with dontStopAppOnReset set to true,
     * we will not include the -S flag in the adb shell am start call.
     * With this capability omitted or set to false, we include the -S flag. Default false
     */
    String DONT_STOP_APP_ON_RESET = "dontStopAppOnReset";

    /**
     * Enable Unicode input, default false.
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
     */
    String DISABLE_ANDROID_WATCHERS = "disableAndroidWatchers";

    /**
     * Allows passing chromeOptions capability for ChromeDriver.
     * For more information see chromeOptions:
     * https://sites.google.com/a/chromium.org/chromedriver/capabilities
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
     */
    String NATIVE_WEB_SCREENSHOT = "nativeWebScreenshot";

    /**
     * The name of the directory on the device in which the screenshot will be put.
     * Defaults to /data/local/tmp.
     */
    String ANDROID_SCREENSHOT_PATH = "androidScreenshotPath";

    String SELENDROID_PORT = "selendroidPort";
}
