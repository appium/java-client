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

package io.appium.java_client.android;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileOptions;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class AndroidMobileOptions extends MobileOptions<AndroidMobileOptions> {

    /**
     * Creates new instance with platformName specified for Android.
     */
    public AndroidMobileOptions() {
        setPlatformName(MobilePlatform.ANDROID);
    }

    /**
     * Creates new instance with provided capabilities capabilities.
     *
     * @param source is Capabilities instance to merge into new instance
     */
    public AndroidMobileOptions(Capabilities source) {
        this();
        merge(source);
    }

    /**
     * Set the Timeout in milliseconds used to wait for adb command execution.
     *
     * @param duration is the number of milliseconds to wait for adb command execution.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ADB_EXEC_TIMEOUT
     */
    public AndroidMobileOptions setAdbExecTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT, duration.toMillis());
    }

    /**
     * Get the Timeout in milliseconds used to wait for adb command execution.
     *
     * @return Duration to wait for adb command execution.
     * @see AndroidMobileCapabilityType#ADB_EXEC_TIMEOUT
     */
    public Duration getAdbExecTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the port used to connect to the ADB server.
     *
     * @param port is the port to connect to the ADB server.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ADB_PORT
     */
    public AndroidMobileOptions setAdbPort(String port) {
        return amend(AndroidMobileCapabilityType.ADB_PORT, port);
    }

    /**
     * Get the port used to connect to the ADB server.
     *
     * @return String of the port used to connect to the ADB server.
     * @see AndroidMobileCapabilityType#ADB_PORT
     */
    public String getAdbPort() {
        return (String) getCapability(AndroidMobileCapabilityType.ADB_PORT);
    }

    /**
     * Set the app to allow installation of a test package which has android:testOnly="true" in the manifest.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ALLOW_TEST_PACKAGES
     */
    public AndroidMobileOptions setAllowTestPackages() {
        return setAllowTestPackages(true);
    }

    /**
     * Set whether to allow installation of a test package which has android:testOnly="true" in the manifest.
     *
     * @param bool when true will be able install a test package marked testOnly.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ALLOW_TEST_PACKAGES
     */
    public AndroidMobileOptions setAllowTestPackages(boolean bool) {
        return amend(AndroidMobileCapabilityType.ALLOW_TEST_PACKAGES, bool);
    }

    /**
     * Get whether installation of a test package which has android:testOnly="true" in the manifest is allowed.
     *
     * @return true if able to install a test package marked testOnly.
     * @see AndroidMobileCapabilityType#ALLOW_TEST_PACKAGES
     */
    public boolean doesAllowTestPackages() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ALLOW_TEST_PACKAGES);
    }

    /**
     * Set the fully qualified instrumentation class.
     *
     * @param coverage is the fully qualified instrumentation class.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_COVERAGE
     */
    public AndroidMobileOptions setAndroidCoverage(String coverage) {
        return amend(AndroidMobileCapabilityType.ANDROID_COVERAGE, coverage);
    }

    /**
     * Get the fully qualified instrumentation class.
     *
     * @return String of the fully qualified instrumentation class.
     * @see AndroidMobileCapabilityType#ANDROID_COVERAGE
     */
    public String getAndroidCoverage() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_COVERAGE);
    }

    /**
     * Set the broadcast action which is used to dump coverage into file system.
     *
     * @param coverage is the action to dump coverage into file system.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_COVERAGE_END_INTENT
     */
    public AndroidMobileOptions setAndroidCoverageEndIntent(String coverage) {
        return amend(AndroidMobileCapabilityType.ANDROID_COVERAGE_END_INTENT, coverage);
    }

    /**
     * Get the broadcast action which is used to dump coverage into file system.
     *
     * @return String of the broadcast action which is used to dump coverage into file system.
     * @see AndroidMobileCapabilityType#ANDROID_COVERAGE_END_INTENT
     */
    public String getAndroidCoverageEndIntent() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_COVERAGE_END_INTENT);
    }

    /**
     * Set the timeout to wait for a device to become ready after booting.
     *
     * @param duration is the number of seconds to wait for a device to become ready after booting.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_DEVICE_READY_TIMEOUT
     */
    public AndroidMobileOptions setAndroidDeviceReadyTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, duration.getSeconds());
    }

    /**
     * Get the timeout to wait for a device to become ready after booting.
     *
     * @return Duration to wait for a device to become ready after booting.
     * @see AndroidMobileCapabilityType#ANDROID_DEVICE_READY_TIMEOUT
     */
    public Duration getAndroidDeviceReadyTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT);
        return Duration.ofSeconds(duration);
    }

    /**
     * Set the devtools socket name.
     *
     * @param activity is the devtools socket name.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_DEVICE_SOCKET
     */
    public AndroidMobileOptions setAndroidDeviceSocket(String activity) {
        return amend(AndroidMobileCapabilityType.ANDROID_DEVICE_SOCKET, activity);
    }

    /**
     * Get the devtools socket name.
     *
     * @return String of the devtools socket name.
     * @see AndroidMobileCapabilityType#ANDROID_DEVICE_SOCKET
     */
    public String getAndroidDeviceSocket() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_SOCKET);
    }

    /**
     * Set the name of the directory on the device in which the apk will be pushed before install.
     *
     * @param path is the directory on the device in which the apk will be pushed before install.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_INSTALL_PATH
     */
    public AndroidMobileOptions setAndroidInstallPath(String path) {
        return amend(AndroidMobileCapabilityType.ANDROID_INSTALL_PATH, path);
    }

    /**
     * Get the name of the directory on the device in which the apk will be pushed before install.
     *
     * @return String of the name of the directory on the device in which the apk will be pushed before install.
     * @see AndroidMobileCapabilityType#ANDROID_INSTALL_PATH
     */
    public String getAndroidInstallPath() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_PATH);
    }

    /**
     * Set the timeout to wait for an apk to install to the device.
     *
     * @param duration is the number of milliseconds to wait for an apk to install to the device.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_INSTALL_TIMEOUT
     */
    public AndroidMobileOptions setAndroidInstallTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT, duration.toMillis());
    }

    /**
     * Get the timeout to wait for an apk to install to the device.
     *
     * @return Duration to wait for an apk to install to the device.
     * @see AndroidMobileCapabilityType#ANDROID_INSTALL_TIMEOUT
     */
    public Duration getAndroidInstallTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the app to allow for correct handling of orientation on landscape-oriented devices.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_NATURAL_ORIENTATION
     */
    public AndroidMobileOptions setAndroidNaturalOrientation() {
        return setAndroidNaturalOrientation(true);
    }

    /**
     * Set whether to allow for correct handling of orientation on landscape-oriented devices.
     *
     * @param bool when true allows for correct handling of orientation on landscape-oriented devices.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_NATURAL_ORIENTATION
     */
    public AndroidMobileOptions setAndroidNaturalOrientation(boolean bool) {
        return amend(AndroidMobileCapabilityType.ANDROID_NATURAL_ORIENTATION, bool);
    }

    /**
     * Get whether correct handling of orientation on landscape-oriented devices is allowed.
     *
     * @return true if correct handling of orientation on landscape-oriented devices is allowed.
     * @see AndroidMobileCapabilityType#ANDROID_NATURAL_ORIENTATION
     */
    public boolean isAndroidNaturalOrientation() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ANDROID_NATURAL_ORIENTATION);
    }

    /**
     * Set the name of the directory on the device in which the screenshot will be put.
     *
     * @param path is the directory on the device in which the screenshot will be put.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ANDROID_SCREENSHOT_PATH
     */
    public AndroidMobileOptions setAndroidScreenshotPath(String path) {
        return amend(AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH, path);
    }

    /**
     * Get the name of the directory on the device in which the screenshot will be put.
     *
     * @return String of the name of the directory on the device in which the screenshot will be put.
     * @see AndroidMobileCapabilityType#ANDROID_SCREENSHOT_PATH
     */
    public String getAndroidScreenshotPath() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH);
    }

    /**
     * Set the activity name for the Android activity you want to launch from your package.
     *
     * @param activity is the name of the activity to launch from the package.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#APP_ACTIVITY
     */
    public AndroidMobileOptions setAppActivity(String activity) {
        return amend(AndroidMobileCapabilityType.APP_ACTIVITY, activity);
    }

    /**
     * Get the activity name for the Android activity you want to launch from your package.
     *
     * @return String of the activity name for the Android activity you want to launch from your package.
     * @see AndroidMobileCapabilityType#APP_ACTIVITY
     */
    public String getAppActivity() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_ACTIVITY);
    }

    /**
     * Set the Java package of the Android app you want to run.
     *
     * @param pkg is the Android app you want to run.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#APP_PACKAGE
     */
    public AndroidMobileOptions setAppPackage(String pkg) {
        return amend(AndroidMobileCapabilityType.APP_PACKAGE, pkg);
    }

    /**
     * Get the Java package of the Android app you want to run.
     *
     * @return String of the Java package of the Android app you want to run.
     * @see AndroidMobileCapabilityType#APP_PACKAGE
     */
    public String getAppPackage() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_PACKAGE);
    }

    /**
     * Set the activity name/names, comma separated, for the Android activity you want to wait for.
     *
     * @param activity is the comma separated activity names for activities to wait for.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#APP_WAIT_ACTIVITY
     */
    public AndroidMobileOptions setAppWaitActivity(String activity) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, activity);
    }

    /**
     * Get the activity name/names, comma separated, for the Android activity to wait for.
     *
     * @return String of comma separated activity names for the Android activity to wait for.
     * @see AndroidMobileCapabilityType#APP_WAIT_ACTIVITY
     */
    public String getAppWaitActivity() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY);
    }

    /**
     * Set the Timeout used to wait for the appWaitActivity to launch.
     *
     * @param duration is the number of milliseconds to wait for the appWaitActivity to launch.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#APP_WAIT_DURATION
     */
    public AndroidMobileOptions setAppWaitDuration(Duration duration) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_DURATION, duration.toMillis());
    }

    /**
     * Get the timeout used to wait for the appWaitActivity to launch.
     *
     * @return Duration to wait for the appWaitActivity to launch.
     * @see AndroidMobileCapabilityType#APP_WAIT_DURATION
     */
    public Duration getAppWaitDuration() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the Java package of the Android app to wait for.
     *
     * @param pkg is the package of the app to wait for.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#APP_WAIT_PACKAGE
     */
    public AndroidMobileOptions setAppWaitPackage(String pkg) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, pkg);
    }

    /**
     * Get the Java package of the Android app to wait for.
     *
     * @return String of the Java package of the Android app to wait for.
     * @see AndroidMobileCapabilityType#APP_WAIT_PACKAGE
     */
    public String getAppWaitPackage() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE);
    }

    /**
     * Set Appium to automatically determine which permissions the app requires and grant them to the app on install.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AUTO_GRANT_PERMISSIONS
     */
    public AndroidMobileOptions setAutoGrantPermissions() {
        return setAutoGrantPermissions(true);
    }

    /**
     * Set whether required permissions are determined and granted automatically.
     *
     * @param bool is true if required app permissions are automatically determined and granted.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AUTO_GRANT_PERMISSIONS
     */
    public AndroidMobileOptions setAutoGrantPermissions(boolean bool) {
        return amend(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, bool);
    }

    /**
     * Get whether required permissions are determined and granted automatically.
     *
     * @return true if required app permissions are automatically determined and granted.
     * @see AndroidMobileCapabilityType#AUTO_GRANT_PERMISSIONS
     */
    public boolean doesAutoGrantPermissions() {
        return (boolean) getCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS);
    }

    /**
     * Set the app to initialize automatically.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AUTO_LAUNCH
     */
    public AndroidMobileOptions setAutoLaunch() {
        return setAutoLaunch(true);
    }

    /**
     * Set whether the app will initialize automatically.
     *
     * @param bool is true if the app automatically initializes.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AUTO_LAUNCH
     */
    public AndroidMobileOptions setAutoLaunch(boolean bool) {
        return amend(AndroidMobileCapabilityType.AUTO_LAUNCH, bool);
    }

    /**
     * Get whether the app will initialize automatically.
     *
     * @return true if the app automatically initializes.
     * @see AndroidMobileCapabilityType#AUTO_LAUNCH
     */
    public boolean doesAutoLaunch() {
        return (boolean) getCapability(AndroidMobileCapabilityType.AUTO_LAUNCH);
    }

    /**
     * Set the amount of time to wait for Webview context to become active.
     *
     * @param duration is the number of milliseconds to wait for Webview context to become active.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AUTO_WEBVIEW_TIMEOUT
     */
    public AndroidMobileOptions setAutoWebviewTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.AUTO_WEBVIEW_TIMEOUT, duration.toMillis());
    }

    /**
     * Get the amount of time to wait for Webview context to become active.
     *
     * @return Duration to wait for Webview context to become active.
     * @see AndroidMobileCapabilityType#AUTO_WEBVIEW_TIMEOUT
     */
    public Duration getAutoWebviewTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.AUTO_WEBVIEW_TIMEOUT);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the name of avd to launch.
     *
     * @param name is the name of avd to launch.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AVD
     */
    public AndroidMobileOptions setAvd(String name) {
        return amend(AndroidMobileCapabilityType.AVD, name);
    }

    /**
     * Get the name of avd to launch.
     *
     * @return String of the name of avd to launch.
     * @see AndroidMobileCapabilityType#AVD
     */
    public String getAvd() {
        return (String) getCapability(AndroidMobileCapabilityType.AVD);
    }

    /**
     * Set the additional emulator arguments used when launching an avd.
     *
     * @param args is the additional arguments for launching avd on emulator.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AVD_ARGS
     */
    public AndroidMobileOptions setAvdArgs(String args) {
        return amend(AndroidMobileCapabilityType.AVD_ARGS, args);
    }

    /**
     * Get the additional emulator arguments used when launching an avd.
     *
     * @return String of the additional emulator arguments used when launching an avd.
     * @see AndroidMobileCapabilityType#AVD_ARGS
     */
    public String getAvdArgs() {
        return (String) getCapability(AndroidMobileCapabilityType.AVD_ARGS);
    }

    /**
     * Set the wait time for an avd to launch and connect to ADB.
     *
     * @param duration is the number of milliseconds to wait for an avd to launch and connect to ADB.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AVD_LAUNCH_TIMEOUT
     */
    public AndroidMobileOptions setAvdLaunchTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, duration.toMillis());
    }

    /**
     * Get the wait time for an avd to launch and connect to ADB.
     *
     * @return Duration to wait for an avd to launch and connect to ADB.
     * @see AndroidMobileCapabilityType#AVD_LAUNCH_TIMEOUT
     */
    public Duration getAvdLaunchTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the wait time for an avd to finish its boot animations.
     *
     * @param duration is the number of milliseconds to wait for an avd to finish its boot animations.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#AVD_READY_TIMEOUT
     */
    public AndroidMobileOptions setAvdReadyTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.AVD_READY_TIMEOUT, duration.toMillis());
    }

    /**
     * Get the the wait time for an avd to finish its boot animations.
     *
     * @return Duration to wait for an avd to finish its boot animations.
     * @see AndroidMobileCapabilityType#AVD_READY_TIMEOUT
     */
    public Duration getAvdReadyTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.AVD_READY_TIMEOUT);
        return Duration.ofMillis(duration);
    }

    /**
     * Set the Android build-tools version to be something different than the default.
     *
     * @param version is the version of Android build-tools to use.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#BUILD_TOOLS_VERSION
     */
    public AndroidMobileOptions setBuildToolsVersion(String version) {
        return amend(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION, version);
    }

    /**
     * Get the Android build-tools version to be something different than the default.
     *
     * @return String of the Android build-tools version to be something different than the default.
     * @see AndroidMobileCapabilityType#BUILD_TOOLS_VERSION
     */
    public String getBuildToolsVersion() {
        return (String) getCapability(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION);
    }

    /**
     * Set the arguments to be passed to the chromedriver binary when it's run by Appium..
     *
     * @param args is the List of arguments to be passed to the chromedriver.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_ARGS
     */
    public AndroidMobileOptions setChromedriverArgs(List<String> args) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_ARGS, args);
    }

    /**
     * Get the arguments to be passed to the chromedriver binary when it's run by Appium..
     *
     * @return list of String arguments to be passed to the chromedriver binary when it's run by Appium.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_ARGS
     */
    public Object getChromedriverArgs() {
        return getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_ARGS);
    }

    /**
     * Set the file location which maps Chromedriver versions to the minimum Chrome version that it supports.
     *
     * @param path is the absolute path to a file mapping Chromedriver versions to Chrome versions.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_CHROME_MAPPING_FILE
     */
    public AndroidMobileOptions setChromedriverChromeMappingFile(String path) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_CHROME_MAPPING_FILE, path);
    }

    /**
     * Get the file location which maps Chromedriver versions to the minimum Chrome version that it supports.
     *
     * @return String of the file location which maps Chromedriver versions to the minimum supported Chrome version.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_CHROME_MAPPING_FILE
     */
    public String getChromedriverChromeMappingFile() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_CHROME_MAPPING_FILE);
    }

    /**
     * Set the chromedriver flag --disable-build-check for Chrome webview tests.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public AndroidMobileOptions setChromedriverDisableBuildCheck() {
        return setChromedriverDisableBuildCheck(true);
    }

    /**
     * Set whether chromedriver flag --disable-build-check is added for Chrome webview tests.
     *
     * @param bool when true adds --disable-build-check for Chrome webview.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public AndroidMobileOptions setChromedriverDisableBuildCheck(boolean bool) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK, bool);
    }

    /**
     * Get whether chromedriver flag --disable-build-check is added for Chrome webview tests.
     *
     * @return true if --disable-build-check for Chrome webview is added.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public boolean doesChromedriverDisableBuildCheck() {
        return (boolean) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK);
    }

    /**
     * Set the path to webdriver executable.
     *
     * @param path is the absolute local path to webdriver executable.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE
     */
    public AndroidMobileOptions setChromedriverExecutable(String path) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, path);
    }

    /**
     * Get the path to webdriver executable.
     *
     * @return String of the path to webdriver executable.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE
     */
    public String getChromedriverExecutable() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE);
    }

    /**
     * Set the directory to look for Chromedriver executables in, for automatic discovery of compatible Chromedrivers.
     *
     * @param dir is the absolute path of the directory to automatically discover compatible Chromedriver executable.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE_DIR
     */
    public AndroidMobileOptions setChromedriverExecutableDir(String dir) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, dir);
    }

    /**
     * Get the directory to look for Chromedriver executables in, for automatic discovery of compatible Chromedrivers.
     *
     * @return String of the directory of Chromedriver executables.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE_DIR
     */
    public String getChromedriverExecutableDir() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR);
    }

    /**
     * Set the port to start Chromedriver on.
     *
     * @param port is the number of the port to use for Chromedriver.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_PORT
     */
    public AndroidMobileOptions setChromedriverPort(int port) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_PORT, port);
    }

    /**
     * Get the port Chromedriver starts on.
     *
     * @return Integer of the port Chromedriver starts on.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_PORT
     */
    public int getChromedriverPort() {
        return (int) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_PORT);
    }

    /**
     * Set the valid ports for Appium to use for communication with Chromedrivers.
     *
     * @param ports is the list of ports for Appium to use with Chromedrivers.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#BUILD_TOOLS_VERSION
     */
    public AndroidMobileOptions setChromedriverPorts(List<Integer> ports) {
        return amend(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION, ports);
    }

    /**
     * Get the valid ports for Appium to use for communication with Chromedrivers.
     *
     * @return list of Integers for the valid ports for Appium to use for communication with Chromedrivers.
     * @see AndroidMobileCapabilityType#BUILD_TOOLS_VERSION
     */
    public Object getChromedriverPorts() {
        return getCapability(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION);
    }

    /**
     * Set the app to bypass automatic Chromedriver configuration and use the version that comes downloaded with Appium.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public AndroidMobileOptions setChromedriverUseSystemExecutable() {
        return setChromedriverUseSystemExecutable(true);
    }

    /**
     * Set whether to bypass automatic Chromedriver configuration and use the version that comes downloaded with Appium.
     *
     * @param bool when true uses the version of Chromedriver that comes with Appium.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public AndroidMobileOptions setChromedriverUseSystemExecutable(boolean bool) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK, bool);
    }

    /**
     * Get whether to bypass automatic Chromedriver configuration and use the version that comes downloaded with Appium.
     *
     * @return true if using the version of Chromedriver that comes with Appium.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_DISABLE_BUILD_CHECK
     */
    public boolean doesChromedriverUseSystemExecutable() {
        return (boolean) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK);
    }

    /**
     * Set the chromeOptions capability for ChromeDriver.
     *
     * @param opts is the chromeoptions for ChromeDriver.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE_DIR
     */
    public AndroidMobileOptions setChromeOptions(ChromeOptions opts) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, opts);
    }

    /**
     * Get the chromeOptions capability for ChromeDriver.
     *
     * @return ChromeOptions set for use with ChromeDriver.
     * @see AndroidMobileCapabilityType#CHROMEDRIVER_EXECUTABLE_DIR
     */
    public Object getChromeOptions() {
        return getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR);
    }

    /**
     * Set the timeout for device to become ready.
     *
     * @param duration is the number of seconds to wait for the device to become ready.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DEVICE_READY_TIMEOUT
     */
    public AndroidMobileOptions setDeviceReadyTimeout(Duration duration) {
        return amend(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, duration.getSeconds());
    }

    /**
     * Get the timeout for device to become ready.
     *
     * @return Duration to wait for device to become ready.
     * @see AndroidMobileCapabilityType#DEVICE_READY_TIMEOUT
     */
    public Duration getDeviceReadyTimeout() {
        Integer duration = (Integer) getCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT);
        return Duration.ofSeconds(duration);
    }

    /**
     * Set the application to disable Android watchers for when application is not responding or crashes.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DISABLE_ANDROID_WATCHERS
     */
    public AndroidMobileOptions setDisableAndroidWatchers() {
        return setDisableAndroidWatchers(true);
    }

    /**
     * Set whether the application disables Android watchers for when application is not responding or crashes.
     *
     * @param bool when true disables Android watchers for when application is not responding or crashes.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DISABLE_ANDROID_WATCHERS
     */
    public AndroidMobileOptions setDisableAndroidWatchers(boolean bool) {
        return amend(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS, bool);
    }

    /**
     * Get whether the application disables Android watchers for when application is not responding or crashes.
     *
     * @return true if Android watchers for when application is not responding or crashes are disabled.
     * @see AndroidMobileCapabilityType#DISABLE_ANDROID_WATCHERS
     */
    public boolean doesDisableAndroidWatchers() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS);
    }

    /**
     * Set the device animation scale to zero.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DISABLE_WINDOW_ANIMATION
     */
    public AndroidMobileOptions setDisableWindowAnimation() {
        return setDisableWindowAnimation(true);
    }

    /**
     * Set whether the application has device animation scale of zero.
     *
     * @param bool when true has device animation scale of zero.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DISABLE_WINDOW_ANIMATION
     */
    public AndroidMobileOptions setDisableWindowAnimation(boolean bool) {
        return amend(AndroidMobileCapabilityType.DISABLE_WINDOW_ANIMATION, bool);
    }

    /**
     * Get whether the application has device animation scale of zero.
     *
     * @return true if device animation scale is zero.
     * @see AndroidMobileCapabilityType#DISABLE_WINDOW_ANIMATION
     */
    public boolean doesDisableWindowAnimation() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DISABLE_WINDOW_ANIMATION);
    }

    /**
     * Set the app not to stop the process of the app under test, before starting the app using adb.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DONT_STOP_APP_ON_RESET
     */
    public AndroidMobileOptions setDontStopAppOnReset() {
        return setDontStopAppOnReset(true);
    }

    /**
     * Set whether the app process is not stopped before starting using adb.
     *
     * @param bool when true does not stop the process of the app under test before starting the app using adb.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#DONT_STOP_APP_ON_RESET
     */
    public AndroidMobileOptions setDontStopAppOnReset(boolean bool) {
        return amend(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, bool);
    }

    /**
     * Get whether the app process is not stopped before starting using adb.
     *
     * @return true if the process of the app under test does not stop before starting the app using adb.
     * @see AndroidMobileCapabilityType#DONT_STOP_APP_ON_RESET
     */
    public boolean isDontStopAppOnReset() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET);
    }

    /**
     * Set to always install the current application build independently of the currently installed version of it.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ENFORCE_APP_INSTALL
     */
    public AndroidMobileOptions setEnforceAppInstall() {
        return setEnforceAppInstall(true);
    }

    /**
     * Set whether to always install the current application build independently of the currently installed version.
     *
     * @param bool when true will install the current application build independently of the installed version.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ENFORCE_APP_INSTALL
     */
    public AndroidMobileOptions setEnforceAppInstall(boolean bool) {
        return amend(AndroidMobileCapabilityType.ENFORCE_APP_INSTALL, bool);
    }

    /**
     * Get whether the current application build is always isntalled independently of the currently installed version.
     *
     * @return true if the current application build is installed independently of the currently installed version.
     * @see AndroidMobileCapabilityType#ENFORCE_APP_INSTALL
     */
    public boolean doesEnforceAppInstall() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ENFORCE_APP_INSTALL);
    }

    /**
     * Set the app to augment its webview detection with page detection.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ENSURE_WEBVIEWS_HAVE_PAGES
     */
    public AndroidMobileOptions setEnsureWebviewsHavePages() {
        return setEnsureWebviewsHavePages(true);
    }

    /**
     * Set whether to augment webview detection with page detection.
     *
     * @param bool when true augments webview detection with page detection.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#ENSURE_WEBVIEWS_HAVE_PAGES
     */
    public AndroidMobileOptions setEnsureWebviewsHavePages(boolean bool) {
        return amend(AndroidMobileCapabilityType.ENSURE_WEBVIEWS_HAVE_PAGES, bool);
    }

    /**
     * Get whether to augment webview detection with page detection.
     *
     * @return true if webview detection is augmented with page detection.
     * @see AndroidMobileCapabilityType#ENSURE_WEBVIEWS_HAVE_PAGES
     */
    public boolean doesEnsureWebviewsHavePages() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ENSURE_WEBVIEWS_HAVE_PAGES);
    }

    /**
     * Set the app to use GPS location for emulators.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#GPS_ENABLED
     */
    public AndroidMobileOptions setGpsEnabled() {
        return setGpsEnabled(true);
    }

    /**
     * Set whether to use GPS location for emulators.
     *
     * @param bool when true will enable GPS location for emulators.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#GPS_ENABLED
     */
    public AndroidMobileOptions setGpsEnabled(boolean bool) {
        return amend(AndroidMobileCapabilityType.GPS_ENABLED, bool);
    }

    /**
     * Get whether to use GPS location for emulators.
     *
     * @return true if GPS location for emulators is enabled.
     * @see AndroidMobileCapabilityType#GPS_ENABLED
     */
    public boolean isGpsEnabled() {
        return (boolean) getCapability(AndroidMobileCapabilityType.GPS_ENABLED);
    }

    /**
     * Set the accessibility commands to run faster by ignoring some elements.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#IGNORE_UNIMPORTANT_VIEWS
     */
    public AndroidMobileOptions setIgnoreUnimportantViews() {
        return setIgnoreUnimportantViews(true);
    }

    /**
     * Set whether accessibility commands run faster by ignoring some elements.
     *
     * @param bool when true will ignore some elements to improve performance.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#IGNORE_UNIMPORTANT_VIEWS
     */
    public AndroidMobileOptions setIgnoreUnimportantViews(boolean bool) {
        return amend(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, bool);
    }

    /**
     * Get whether accessibility commands run faster by ignoring some elements.
     *
     * @return true if some elements are ignored to improve performance.
     * @see AndroidMobileCapabilityType#IGNORE_UNIMPORTANT_VIEWS
     */
    public boolean doesIgnoreUnimportantViews() {
        return (boolean) getCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS);
    }

    /**
     * Set the intent action which will be used to start activity.
     *
     * @param action is the action used to start the activity.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#INTENT_ACTION
     */
    public AndroidMobileOptions setIntentAction(String action) {
        return amend(AndroidMobileCapabilityType.INTENT_ACTION, action);
    }

    /**
     * Get the intent action which will be used to start activity.
     *
     * @return String of the intent action which will be used to start activity.
     * @see AndroidMobileCapabilityType#INTENT_ACTION
     */
    public String getIntentAction() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_ACTION);
    }

    /**
     * Set the intent category which will be used to start activity.
     *
     * @param category is the category used to start activity.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#INTENT_CATEGORY
     */
    public AndroidMobileOptions setIntentCategory(String category) {
        return amend(AndroidMobileCapabilityType.INTENT_CATEGORY, category);
    }

    /**
     * Get the intent category which will be used to start activity.
     *
     * @return String of the intent category which will be used to start activity.
     * @see AndroidMobileCapabilityType#INTENT_CATEGORY
     */
    public String getIntentCategory() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_CATEGORY);
    }

    /**
     * Set the flags that will be used to start activity.
     *
     * @param flags is the flags used to start activity.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#INTENT_FLAGS
     */
    public AndroidMobileOptions setIntentFlags(String flags) {
        return amend(AndroidMobileCapabilityType.INTENT_FLAGS, flags);
    }

    /**
     * Get the flags that will be used to start activity.
     *
     * @return String of the flags that will be used to start activity.
     * @see AndroidMobileCapabilityType#INTENT_FLAGS
     */
    public String getIntentFlags() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_FLAGS);
    }

    /**
     * Set the emulator to not display the screen.
     *
     * @param bool when true will not display the emulator screen.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#IS_HEADLESS
     */
    public AndroidMobileOptions setIsHeadless(boolean bool) {
        return amend(AndroidMobileCapabilityType.IS_HEADLESS, bool);
    }

    /**
     * Get the emulator to not display the screen.
     *
     * @return true if emulator is not to display the screen.
     * @see AndroidMobileCapabilityType#IS_HEADLESS
     */
    public boolean isHeadless() {
        return (boolean) getCapability(AndroidMobileCapabilityType.IS_HEADLESS);
    }

    /**
     * Set the alias for the key.
     *
     * @param alias is the key alias.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#KEY_ALIAS
     */
    public AndroidMobileOptions setKeyAlias(String alias) {
        return amend(AndroidMobileCapabilityType.KEY_ALIAS, alias);
    }

    /**
     * Get the alias for the key.
     *
     * @return String of the key alias.
     * @see AndroidMobileCapabilityType#KEY_ALIAS
     */
    public String getKeyAlias() {
        return (String) getCapability(AndroidMobileCapabilityType.KEY_ALIAS);
    }

    /**
     * Set the password for the key.
     *
     * @param password is the key password.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#KEY_PASSWORD
     */
    public AndroidMobileOptions setKeyPassword(String password) {
        return amend(AndroidMobileCapabilityType.KEY_PASSWORD, password);
    }

    /**
     * Get the password for the key.
     *
     * @return String of the key password.
     * @see AndroidMobileCapabilityType#KEY_PASSWORD
     */
    public String getKeyPassword() {
        return (String) getCapability(AndroidMobileCapabilityType.KEY_PASSWORD);
    }

    /**
     * Set the password for custom keystore.
     *
     * @param password is the password for the custom keystore.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#KEYSTORE_PASSWORD
     */
    public AndroidMobileOptions setKeystorePassword(String password) {
        return amend(AndroidMobileCapabilityType.KEYSTORE_PASSWORD, password);
    }

    /**
     * Get the password for custom keystore.
     *
     * @return String of the custom keystore password.
     * @see AndroidMobileCapabilityType#KEYSTORE_PASSWORD
     */
    public String getKeystorePassword() {
        return (String) getCapability(AndroidMobileCapabilityType.KEYSTORE_PASSWORD);
    }

    /**
     * Set the path to custom keystore.
     *
     * @param path is the path to the custom keystore.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#KEYSTORE_PATH
     */
    public AndroidMobileOptions setKeystorePath(String path) {
        return amend(AndroidMobileCapabilityType.KEYSTORE_PATH, path);
    }

    /**
     * Get the path to custom keystore.
     *
     * @return String of the custom keystore path.
     * @see AndroidMobileCapabilityType#KEYSTORE_PATH
     */
    public String getKeystorePath() {
        return (String) getCapability(AndroidMobileCapabilityType.KEYSTORE_PATH);
    }

    /**
     * Set the locale script.
     *
     * @param script is the locale script.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#LOCALE_SCRIPT
     */
    public AndroidMobileOptions setLocaleScript(String script) {
        return amend(AndroidMobileCapabilityType.LOCALE_SCRIPT, script);
    }

    /**
     * Get the locale script.
     *
     * @return String of the locale script.
     * @see AndroidMobileCapabilityType#LOCALE_SCRIPT
     */
    public String getLocaleScript() {
        return (String) getCapability(AndroidMobileCapabilityType.LOCALE_SCRIPT);
    }

    /**
     * Set the web context to use native (adb) method for taking a screenshot.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NATIVE_WEB_SCREENSHOT
     */
    public AndroidMobileOptions setNativeWebScreenshot() {
        return setNativeWebScreenshot(true);
    }

    /**
     * Set whether the web context uses native (adb) method for taking a screenshot or proxies to ChromeDriver.
     *
     * @param bool when true uses the native method for taking a screenshot.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NATIVE_WEB_SCREENSHOT
     */
    public AndroidMobileOptions setNativeWebScreenshot(boolean bool) {
        return amend(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, bool);
    }

    /**
     * Get whether the web context uses native (adb) method for taking a screenshot or proxies to ChromeDriver.
     *
     * @return true if using the native method for taking a screenshot.
     * @see AndroidMobileCapabilityType#NATIVE_WEB_SCREENSHOT
     */
    public boolean doesNativeWebScreenshot() {
        return (boolean) getCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT);
    }

    /**
     * Set the network speed emulation.
     *
     * @param speed is the network speed emulation.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NETWORK_SPEED
     */
    public AndroidMobileOptions setNetworkSpeed(String speed) {
        return amend(AndroidMobileCapabilityType.NETWORK_SPEED, speed);
    }

    /**
     * Set the network speed emulation.
     *
     * @param speed is the network speed emulation.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NETWORK_SPEED
     */
    public AndroidMobileOptions setNetworkSpeed(NetworkSpeed speed) {
        return amend(AndroidMobileCapabilityType.NETWORK_SPEED, speed.name().toLowerCase());
    }

    /**
     * Get the network speed emulation.
     *
     * @return String of the network speed emulation.
     * @see AndroidMobileCapabilityType#NETWORK_SPEED
     */
    public String getNetworkSpeed() {
        return (String) getCapability(AndroidMobileCapabilityType.NETWORK_SPEED);
    }

    /**
     * Set the app to skip checking and signing with debug keys.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NO_SIGN
     */
    public AndroidMobileOptions setNoSign() {
        return setNoSign(true);
    }

    /**
     * Set whether to skip checking and signing the app with debug keys.
     *
     * @param bool when true skips checking and signing the app with debug keys.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#NO_SIGN
     */
    public AndroidMobileOptions setNoSign(boolean bool) {
        return amend(AndroidMobileCapabilityType.NO_SIGN, bool);
    }

    /**
     * Get whether to skip checking and signing the app with debug keys.
     *
     * @return true if checking and signing the app with debug keys is skipped
     * @see AndroidMobileCapabilityType#NO_SIGN
     */
    public boolean isNoSign() {
        return (boolean) getCapability(AndroidMobileCapabilityType.NO_SIGN);
    }

    /**
     * Set the additional intent arguments that will be used to start activity.
     *
     * @param speed is the additional intent arguments that will be used to start activity.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#OPTIONAL_INTENT_ARGUMENTS
     */
    public AndroidMobileOptions setOptionalIntentArguments(String speed) {
        return amend(AndroidMobileCapabilityType.OPTIONAL_INTENT_ARGUMENTS, speed);
    }

    /**
     * Get the additional intent arguments that will be used to start activity.
     *
     * @return String of the additional intent arguments that will be used to start activity.
     * @see AndroidMobileCapabilityType#OPTIONAL_INTENT_ARGUMENTS
     */
    public String getOptionalIntentArguments() {
        return (String) getCapability(AndroidMobileCapabilityType.OPTIONAL_INTENT_ARGUMENTS);
    }

    /**
     * Set the app to kill ChromeDriver session when moving to a non-ChromeDriver webview.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#RECREATE_CHROME_DRIVER_SESSIONS
     */
    public AndroidMobileOptions setRecreateChromeDriverSessions() {
        return setRecreateChromeDriverSessions(true);
    }

    /**
     * Set whether to kill ChromeDriver session when moving to a non-ChromeDriver webview.
     *
     * @param bool when true kills ChromeDriver session when moving to a non-ChromeDriver webview.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#RECREATE_CHROME_DRIVER_SESSIONS
     */
    public AndroidMobileOptions setRecreateChromeDriverSessions(boolean bool) {
        return amend(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, bool);
    }

    /**
     * Get whether to kill ChromeDriver session when moving to a non-ChromeDriver webview.
     *
     * @return true if kills ChromeDriver session when moving to a non-ChromeDriver webview.
     * @see AndroidMobileCapabilityType#RECREATE_CHROME_DRIVER_SESSIONS
     */
    public boolean doesRecreateChromeDriverSessions() {
        return (boolean) getCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS);
    }

    /**
     * Set the optional remote ADB server host.
     *
     * @param host is the remote ADB server host.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#REMOTE_ADB_HOST
     */
    public AndroidMobileOptions setRemoteAdbHost(String host) {
        return amend(AndroidMobileCapabilityType.REMOTE_ADB_HOST, host);
    }

    /**
     * Get the optional remote ADB server host.
     *
     * @return String of .
     * @see AndroidMobileCapabilityType#REMOTE_ADB_HOST
     */
    public String getRemoteAdbHost() {
        return (String) getCapability(AndroidMobileCapabilityType.REMOTE_ADB_HOST);
    }

    /**
     * Set the maximum number of remote cached apks which are pushed to the device-under-test's local storage.
     *
     * @param num is the maximum number of remote cached apks pushed to local storage.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#REMOTE_APPS_CACHE_LIMIT
     */
    public AndroidMobileOptions setRemoteAppsCacheLimit(int num) {
        return amend(AndroidMobileCapabilityType.REMOTE_APPS_CACHE_LIMIT, num);
    }

    /**
     * Get the maximum number of remote cached apks which are pushed to the device-under-test's local storage.
     *
     * @return Integer of the max number of remote cached apks pushed to local storage.
     * @see AndroidMobileCapabilityType#REMOTE_APPS_CACHE_LIMIT
     */
    public int getRemoteAppsCacheLimit() {
        return (int) getCapability(AndroidMobileCapabilityType.REMOTE_APPS_CACHE_LIMIT);
    }

    /**
     * Set the keyboard to reset to its original state after running Unicode tests with unicodeKeyboard capability.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#RESET_KEYBOARD
     */
    public AndroidMobileOptions setResetKeyboard() {
        return setResetKeyboard(true);
    }

    /**
     * Set whether the keyboard resets to original state after running Unicode tests with unicodeKeyboard capability.
     *
     * @param bool when true resets the keyboard to its original state after running with unicodeKeyboard.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#RESET_KEYBOARD
     */
    public AndroidMobileOptions setResetKeyboard(boolean bool) {
        return amend(AndroidMobileCapabilityType.RESET_KEYBOARD, bool);
    }

    /**
     * Get whether the keyboard resets to original state after running Unicode tests with unicodeKeyboard capability.
     *
     * @return true if the keyboard is reset to its original state after running with unicodeKeyboard.
     * @see AndroidMobileCapabilityType#RESET_KEYBOARD
     */
    public boolean doesResetKeyboard() {
        return (boolean) getCapability(AndroidMobileCapabilityType.RESET_KEYBOARD);
    }

    /**
     * Set the device to skip initialization including installation and running of Settings app and permissions.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_DEVICE_INITIALIZATION
     */
    public AndroidMobileOptions setSkipDeviceInitialization() {
        return setSkipDeviceInitialization(true);
    }

    /**
     * Set whether to skip device initialization including installation and running of Settings app and permissions.
     *
     * @param bool when true skips device initialization.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_DEVICE_INITIALIZATION
     */
    public AndroidMobileOptions setSkipDeviceInitialization(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_DEVICE_INITIALIZATION, bool);
    }

    /**
     * Get whether to skip device initialization including installation and running of Settings app and permissions.
     *
     * @return true if device initialization is skipped.
     * @see AndroidMobileCapabilityType#SKIP_DEVICE_INITIALIZATION
     */
    public boolean doesSkipDeviceInitialization() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_DEVICE_INITIALIZATION);
    }

    /**
     * Set the App to skip capturing logcat.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_LOGCAT_CAPTURE
     */
    public AndroidMobileOptions setSkipLogcatCapture() {
        return setSkipLogcatCapture(true);
    }

    /**
     * Set whether to skip capturing logcat.
     *
     * @param bool when true does not capture logcat.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_LOGCAT_CAPTURE
     */
    public AndroidMobileOptions setSkipLogcatCapture(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_LOGCAT_CAPTURE, bool);
    }

    /**
     * Get whether to skip capturing logcat.
     *
     * @return true if not capturing logcat.
     * @see AndroidMobileCapabilityType#SKIP_LOGCAT_CAPTURE
     */
    public boolean doesSkipLogcatCapture() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_LOGCAT_CAPTURE);
    }

    /**
     * Set the app to skip unlock during session creation.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_UNLOCK
     */
    public AndroidMobileOptions setSkipUnlock() {
        return setSkipUnlock(true);
    }

    /**
     * Set whether to skip unlock during session creation.
     *
     * @param bool when true skips unlock during session creation.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SKIP_UNLOCK
     */
    public AndroidMobileOptions setSkipUnlock(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_UNLOCK, bool);
    }

    /**
     * Get whether to skip unlock during session creation.
     *
     * @return true if unlock during session creation is skipped.
     * @see AndroidMobileCapabilityType#SKIP_UNLOCK
     */
    public boolean doesSkipUnlock() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_UNLOCK);
    }

    /**
     * Set the port used to connect to the driver.
     *
     * @param port is the port used to connect to the driver.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#SYSTEM_PORT
     */
    public AndroidMobileOptions setSystemPort(String port) {
        return amend(AndroidMobileCapabilityType.SYSTEM_PORT, port);
    }

    /**
     * Get the port used to connect to the driver.
     *
     * @return String of the port used to connect to the driver.
     * @see AndroidMobileCapabilityType#SYSTEM_PORT
     */
    public String getSystemPort() {
        return (String) getCapability(AndroidMobileCapabilityType.SYSTEM_PORT);
    }

    /**
     * Set the app to enable Unicode input.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNICODE_KEYBOARD
     */
    public AndroidMobileOptions setUnicodeKeyboard() {
        return setUnicodeKeyboard(true);
    }

    /**
     * Set whether to enable Unicode input.
     *
     * @param bool when true enables Unicode input.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNICODE_KEYBOARD
     */
    public AndroidMobileOptions setUnicodeKeyboard(boolean bool) {
        return amend(AndroidMobileCapabilityType.UNICODE_KEYBOARD, bool);
    }

    /**
     * Get whether to enable Unicode input.
     *
     * @return true if Unicode input is enabled.
     * @see AndroidMobileCapabilityType#UNICODE_KEYBOARD
     */
    public boolean isUnicodeKeyboard() {
        return (boolean) getCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD);
    }

    /**
     * Set the list of packages to uninstall before installing apks.
     *
     * @param packages is the list of packages to uninstall before installing apks.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNINSTALL_OTHER_PACKAGES
     */
    public AndroidMobileOptions setUninstallOtherPackages(List<String> packages) {
        return amend(AndroidMobileCapabilityType.UNINSTALL_OTHER_PACKAGES, packages);
    }

    /**
     * Get the list of packages to uninstall before installing apks.
     *
     * @return list of Strings for packages to uninstall before installing apks.
     * @see AndroidMobileCapabilityType#UNINSTALL_OTHER_PACKAGES
     */
    public Object getUninstallOtherPackages() {
        return getCapability(AndroidMobileCapabilityType.UNINSTALL_OTHER_PACKAGES);
    }

    /**
     * Set the key pattern to unlock used by unlockType.
     *
     * @param key is the key pattern to unlock.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNLOCK_KEY
     */
    public AndroidMobileOptions setUnlockKey(String key) {
        return amend(AndroidMobileCapabilityType.UNLOCK_KEY, key);
    }

    /**
     * Get the key pattern to unlock used by unlockType.
     *
     * @return String of the key pattern to unlock used by unlockType.
     * @see AndroidMobileCapabilityType#UNLOCK_KEY
     */
    public String getUnlockKey() {
        return (String) getCapability(AndroidMobileCapabilityType.UNLOCK_KEY);
    }

    /**
     * Set the device to unlock with particular lock pattern instead of just waking up the device with a helper app.
     *
     * @param type is the pattern to unlock the device.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNLOCK_TYPE
     */
    public AndroidMobileOptions setUnlockType(String type) {
        return amend(AndroidMobileCapabilityType.UNLOCK_TYPE, type);
    }

    /**
     * Set the device to unlock with particular lock pattern instead of just waking up the device with a helper app.
     *
     * @param type is the pattern to unlock the device.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#UNLOCK_TYPE
     */
    public AndroidMobileOptions setUnlockType(UnlockType type) {
        return amend(AndroidMobileCapabilityType.UNLOCK_TYPE, type.name().toLowerCase());
    }

    /**
     * Get the device to unlock with particular lock pattern instead of just waking up the device with a helper app.
     *
     * @return String of the device to unlock with particular lock pattern.
     * @see AndroidMobileCapabilityType#UNLOCK_TYPE
     */
    public String getUnlockType() {
        return (String) getCapability(AndroidMobileCapabilityType.UNLOCK_TYPE);
    }

    /**
     * Set to use a custom keystore to sign apks.
     *
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#USE_KEYSTORE
     */
    public AndroidMobileOptions setUseKeystore() {
        return setUseKeystore(true);
    }

    /**
     * Set whether to use a custom keystore to sign apks.
     *
     * @param bool when true allows using a custom keystore to sign apks.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#USE_KEYSTORE
     */
    public AndroidMobileOptions setUseKeystore(boolean bool) {
        return amend(AndroidMobileCapabilityType.USE_KEYSTORE, bool);
    }

    /**
     * Get whether to use a custom keystore to sign apks.
     *
     * @return true if using a custom keystore to sign apks is allowed.
     * @see AndroidMobileCapabilityType#USE_KEYSTORE
     */
    public boolean doesUseKeystore() {
        return (boolean) getCapability(AndroidMobileCapabilityType.USE_KEYSTORE);
    }

    /**
     * Set the TCP port for communication with the webview.
     *
     * @param port is the port for communicating with the webview.
     * @return this MobileOptions, for chaining.
     * @see AndroidMobileCapabilityType#WEBVIEW_DEVTOOLS_PORT
     */
    public AndroidMobileOptions setWebviewDevtoolsPort(String port) {
        return amend(AndroidMobileCapabilityType.WEBVIEW_DEVTOOLS_PORT, port);
    }

    /**
     * Get the TCP port for communication with the webview.
     *
     * @return String of the TCP port for communication with the webview.
     * @see AndroidMobileCapabilityType#WEBVIEW_DEVTOOLS_PORT
     */
    public String getWebviewDevtoolsPort() {
        return (String) getCapability(AndroidMobileCapabilityType.WEBVIEW_DEVTOOLS_PORT);
    }
}
