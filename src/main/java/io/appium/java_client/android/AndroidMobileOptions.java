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
    public AndroidMobileOptions() {
        setPlatformName(MobilePlatform.ANDROID);
    }

    public AndroidMobileOptions(Capabilities source) {
        this();
        merge(source);
    }

    public AndroidMobileOptions setAdbExecTimeout(Duration ms) {
        return amend(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT, ms);
    }

    public Duration getAdbExecTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT);
    }

    public AndroidMobileOptions setAdbPort(String port) {
        return amend(AndroidMobileCapabilityType.ADB_PORT, port);
    }

    public String getAdbPort() {
        return (String) getCapability(AndroidMobileCapabilityType.ADB_PORT);
    }

    public AndroidMobileOptions setAllowTestPackages() {
        return setAllowTestPackages(true);
    }

    public AndroidMobileOptions setAllowTestPackages(boolean bool) {
        return amend(AndroidMobileCapabilityType.ALLOW_TEST_PACKAGES, bool);
    }

    public boolean doesAllowTestPackages() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ALLOW_TEST_PACKAGES);
    }

    public AndroidMobileOptions setAndroidCoverage(String coverage) {
        return amend(AndroidMobileCapabilityType.ANDROID_COVERAGE, coverage);
    }

    public String getAndroidCoverage() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_COVERAGE);
    }

    public AndroidMobileOptions setAndroidCoverageEndIntent(String coverage) {
        return amend(AndroidMobileCapabilityType.ANDROID_COVERAGE_END_INTENT, coverage);
    }

    public String getAndroidCoverageEndIntent() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_COVERAGE_END_INTENT);
    }

    public AndroidMobileOptions setAndroidDeviceReadyTimeout(Duration seconds) {
        return amend(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, seconds);
    }

    public Duration getAndroidDeviceReadyTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT);
    }

    public AndroidMobileOptions setAndroidDeviceSocket(String activity) {
        return amend(AndroidMobileCapabilityType.ANDROID_DEVICE_SOCKET, activity);
    }

    public String getAndroidDeviceSocket() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_SOCKET);
    }

    public AndroidMobileOptions setAndroidInstallPath(String path) {
        return amend(AndroidMobileCapabilityType.ANDROID_INSTALL_PATH, path);
    }

    public String getAndroidInstallPath() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_PATH);
    }

    public AndroidMobileOptions setAndroidInstallTimeout(Duration ms) {
        return amend(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT, ms);
    }

    public Duration getAndroidInstallTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT);
    }

    public AndroidMobileOptions setAndroidNaturalOrientation() {
        return setAndroidNaturalOrientation(true);
    }

    public AndroidMobileOptions setAndroidNaturalOrientation(boolean bool) {
        return amend(AndroidMobileCapabilityType.ANDROID_NATURAL_ORIENTATION, bool);
    }

    public boolean isAndroidNaturalOrientation() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ANDROID_NATURAL_ORIENTATION);
    }

    public AndroidMobileOptions setAndroidScreenshotPath(String path) {
        return amend(AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH, path);
    }

    public String getAndroidScreenshotPath() {
        return (String) getCapability(AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH);
    }

    public AndroidMobileOptions setAppActivity(String activity) {
        return amend(AndroidMobileCapabilityType.APP_ACTIVITY, activity);
    }

    public String getAppActivity() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_ACTIVITY);
    }

    public AndroidMobileOptions setAppPackage(String pkg) {
        return amend(AndroidMobileCapabilityType.APP_PACKAGE, pkg);
    }

    public String getAppPackage() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_PACKAGE);
    }

    public AndroidMobileOptions setAppWaitActivity(String activity) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, activity);
    }

    public String getAppWaitActivity() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY);
    }

    public AndroidMobileOptions setAppWaitDuration(Duration ms) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_DURATION, ms);
    }

    public Duration getAppWaitDuration() {
        return (Duration) getCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION);
    }

    public AndroidMobileOptions setAppWaitPackage(String pkg) {
        return amend(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, pkg);
    }

    public String getAppWaitPackage() {
        return (String) getCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE);
    }

    public AndroidMobileOptions setAutoGrantPermissions() {
        return setAutoGrantPermissions(true);
    }

    public AndroidMobileOptions setAutoGrantPermissions(boolean bool) {
        return amend(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, bool);
    }

    public boolean doesAutoGrantPermissions() {
        return (boolean) getCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS);
    }

    public AndroidMobileOptions setAutoLaunch() {
        return setAutoLaunch(true);
    }

    public AndroidMobileOptions setAutoLaunch(boolean bool) {
        return amend(AndroidMobileCapabilityType.AUTO_LAUNCH, bool);
    }

    public boolean doesAutoLaunch() {
        return (boolean) getCapability(AndroidMobileCapabilityType.AUTO_LAUNCH);
    }

    public AndroidMobileOptions setAutoWebviewTimeout(Duration ms) {
        return amend(AndroidMobileCapabilityType.AUTO_WEBVIEW_TIMEOUT, ms);
    }

    public Duration getAutoWebviewTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.AUTO_WEBVIEW_TIMEOUT);
    }

    public AndroidMobileOptions setAvd(String name) {
        return amend(AndroidMobileCapabilityType.AVD, name);
    }

    public String getAvd() {
        return (String) getCapability(AndroidMobileCapabilityType.AVD);
    }

    public AndroidMobileOptions setAvdArgs(String args) {
        return amend(AndroidMobileCapabilityType.AVD_ARGS, args);
    }

    public String getAvdArgs() {
        return (String) getCapability(AndroidMobileCapabilityType.AVD_ARGS);
    }

    public AndroidMobileOptions setAvdLaunchTimeout(Duration ms) {
        return amend(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, ms);
    }

    public Duration getAvdLaunchTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT);
    }

    public AndroidMobileOptions setAvdReadyTimeout(Duration ms) {
        return amend(AndroidMobileCapabilityType.AVD_READY_TIMEOUT, ms);
    }

    public Duration getAvdReadyTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.AVD_READY_TIMEOUT);
    }

    public AndroidMobileOptions setBuildToolsVersion(String pkg) {
        return amend(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION, pkg);
    }

    public String getBuildToolsVersion() {
        return (String) getCapability(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION);
    }

    public AndroidMobileOptions setChromedriverArgs(List<String> args) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_ARGS, args);
    }

    public Object getChromedriverArgs() {
        return getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_ARGS);
    }

    public AndroidMobileOptions setChromedriverChromeMappingFile(String path) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_CHROME_MAPPING_FILE, path);
    }

    public String getChromedriverChromeMappingFile() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_CHROME_MAPPING_FILE);
    }

    public AndroidMobileOptions setChromedriverDisableBuildCheck() {
        return setChromedriverDisableBuildCheck(true);
    }

    public AndroidMobileOptions setChromedriverDisableBuildCheck(boolean bool) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK, bool);
    }

    public boolean doesChromedriverDisableBuildCheck() {
        return (boolean) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK);
    }

    public AndroidMobileOptions setChromedriverExecutable(String path) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, path);
    }

    public String getChromedriverExecutable() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE);
    }

    public AndroidMobileOptions setChromedriverExecutableDir(String dir) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, dir);
    }

    public String getChromedriverExecutableDir() {
        return (String) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR);
    }

    public AndroidMobileOptions setChromedriverPort(int port) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_PORT, port);
    }

    public int getChromedriverPort() {
        return (int) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_PORT);
    }

    public AndroidMobileOptions setChromedriverPorts(List<Integer> ports) {
        return amend(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION, ports);
    }

    public Object getChromedriverPorts() {
        return getCapability(AndroidMobileCapabilityType.BUILD_TOOLS_VERSION);
    }

    public AndroidMobileOptions setChromedriverUseSystemExecutable() {
        return setChromedriverUseSystemExecutable(true);
    }

    public AndroidMobileOptions setChromedriverUseSystemExecutable(boolean bool) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK, bool);
    }

    public boolean doesChromedriverUseSystemExecutable() {
        return (boolean) getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_DISABLE_BUILD_CHECK);
    }

    public AndroidMobileOptions setChromeOptions(ChromeOptions opts) {
        return amend(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, opts);
    }

    public Object getChromeOptions() {
        return getCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR);
    }

    public AndroidMobileOptions setDeviceReadyTimeout(Duration seconds) {
        return amend(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, seconds);
    }

    public Duration getDeviceReadyTimeout() {
        return (Duration) getCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT);
    }

    public AndroidMobileOptions setDisableAndroidWatchers() {
        return setDisableAndroidWatchers(true);
    }

    public AndroidMobileOptions setDisableAndroidWatchers(boolean bool) {
        return amend(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS, bool);
    }

    public boolean doesDisableAndroidWatchers() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS);
    }

    public AndroidMobileOptions setDisableWindowAnimation() {
        return setDisableWindowAnimation(true);
    }

    public AndroidMobileOptions setDisableWindowAnimation(boolean bool) {
        return amend(AndroidMobileCapabilityType.DISABLE_WINDOW_ANIMATION, bool);
    }

    public boolean doesDisableWindowAnimation() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DISABLE_WINDOW_ANIMATION);
    }

    public AndroidMobileOptions setDontStopAppOnReset() {
        return setDontStopAppOnReset(true);
    }

    public AndroidMobileOptions setDontStopAppOnReset(boolean bool) {
        return amend(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, bool);
    }

    public boolean isDontStopAppOnReset() {
        return (boolean) getCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET);
    }

    public AndroidMobileOptions setEnforceAppInstall() {
        return setEnforceAppInstall(true);
    }

    public AndroidMobileOptions setEnforceAppInstall(boolean bool) {
        return amend(AndroidMobileCapabilityType.ENFORCE_APP_INSTALL, bool);
    }

    public boolean doesEnforceAppInstall() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ENFORCE_APP_INSTALL);
    }

    public AndroidMobileOptions setEnsureWebviewsHavePages() {
        return setEnsureWebviewsHavePages(true);
    }

    public AndroidMobileOptions setEnsureWebviewsHavePages(boolean bool) {
        return amend(AndroidMobileCapabilityType.ENSURE_WEBVIEWS_HAVE_PAGES, bool);
    }

    public boolean doesEnsureWebviewsHavePages() {
        return (boolean) getCapability(AndroidMobileCapabilityType.ENSURE_WEBVIEWS_HAVE_PAGES);
    }

    public AndroidMobileOptions setGpsEnabled() {
        return setGpsEnabled(true);
    }

    public AndroidMobileOptions setGpsEnabled(boolean bool) {
        return amend(AndroidMobileCapabilityType.GPS_ENABLED, bool);
    }

    public boolean isGpsEnabled() {
        return (boolean) getCapability(AndroidMobileCapabilityType.GPS_ENABLED);
    }

    public AndroidMobileOptions setIgnoreUnimportantViews() {
        return setIgnoreUnimportantViews(true);
    }

    public AndroidMobileOptions setIgnoreUnimportantViews(boolean bool) {
        return amend(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, bool);
    }

    public boolean doesIgnoreUnimportantViews() {
        return (boolean) getCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS);
    }

    public AndroidMobileOptions setIntentAction(String action) {
        return amend(AndroidMobileCapabilityType.INTENT_ACTION, action);
    }

    public String getIntentAction() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_ACTION);
    }

    public AndroidMobileOptions setIntentCategory(String category) {
        return amend(AndroidMobileCapabilityType.INTENT_CATEGORY, category);
    }

    public String getIntentCategory() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_CATEGORY);
    }

    public AndroidMobileOptions setIntentFlags(String category) {
        return amend(AndroidMobileCapabilityType.INTENT_FLAGS, category);
    }

    public String getIntentFlags() {
        return (String) getCapability(AndroidMobileCapabilityType.INTENT_FLAGS);
    }

    public AndroidMobileOptions setIsHeadless(boolean bool) {
        return amend(AndroidMobileCapabilityType.IS_HEADLESS, bool);
    }

    public boolean isHeadless() {
        return (boolean) getCapability(AndroidMobileCapabilityType.IS_HEADLESS);
    }

    public AndroidMobileOptions setKeyAlias(String alias) {
        return amend(AndroidMobileCapabilityType.KEY_ALIAS, alias);
    }

    public String getKeyAlias() {
        return (String) getCapability(AndroidMobileCapabilityType.KEY_ALIAS);
    }

    public AndroidMobileOptions setKeyPassword(String password) {
        return amend(AndroidMobileCapabilityType.KEY_PASSWORD, password);
    }

    public String getKeyPassword() {
        return (String) getCapability(AndroidMobileCapabilityType.KEY_PASSWORD);
    }

    public AndroidMobileOptions setKeystorePassword(String password) {
        return amend(AndroidMobileCapabilityType.KEYSTORE_PASSWORD, password);
    }

    public String getKeystorePassword() {
        return (String) getCapability(AndroidMobileCapabilityType.KEYSTORE_PASSWORD);
    }

    public AndroidMobileOptions setKeystorePath(String path) {
        return amend(AndroidMobileCapabilityType.KEYSTORE_PATH, path);
    }

    public String getKeystorePath() {
        return (String) getCapability(AndroidMobileCapabilityType.KEYSTORE_PATH);
    }

    public AndroidMobileOptions setLocaleScript(String path) {
        return amend(AndroidMobileCapabilityType.LOCALE_SCRIPT, path);
    }

    public String getLocaleScript() {
        return (String) getCapability(AndroidMobileCapabilityType.LOCALE_SCRIPT);
    }

    public AndroidMobileOptions setNativeWebScreenshot() {
        return setNativeWebScreenshot(true);
    }

    public AndroidMobileOptions setNativeWebScreenshot(boolean bool) {
        return amend(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, bool);
    }

    public boolean doesNativeWebScreenshot() {
        return (boolean) getCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT);
    }

    public AndroidMobileOptions setNetworkSpeed(String speed) {
        return amend(AndroidMobileCapabilityType.NETWORK_SPEED, speed);
    }

    public String getNetworkSpeed() {
        return (String) getCapability(AndroidMobileCapabilityType.NETWORK_SPEED);
    }

    public AndroidMobileOptions setNoSign() {
        return setNoSign(true);
    }

    public AndroidMobileOptions setNoSign(boolean bool) {
        return amend(AndroidMobileCapabilityType.NO_SIGN, bool);
    }

    public boolean isNoSign() {
        return (boolean) getCapability(AndroidMobileCapabilityType.NO_SIGN);
    }

    public AndroidMobileOptions setOptionalIntentArguments(String speed) {
        return amend(AndroidMobileCapabilityType.OPTIONAL_INTENT_ARGUMENTS, speed);
    }

    public String getOptionalIntentArguments() {
        return (String) getCapability(AndroidMobileCapabilityType.OPTIONAL_INTENT_ARGUMENTS);
    }

    public AndroidMobileOptions setRecreateChromeDriverSessions() {
        return setRecreateChromeDriverSessions(true);
    }

    public AndroidMobileOptions setRecreateChromeDriverSessions(boolean bool) {
        return amend(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, bool);
    }

    public boolean doesRecreateChromeDriverSessions() {
        return (boolean) getCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS);
    }

    public AndroidMobileOptions setRemoteAdbHost(String host) {
        return amend(AndroidMobileCapabilityType.REMOTE_ADB_HOST, host);
    }

    public String getRemoteAdbHost() {
        return (String) getCapability(AndroidMobileCapabilityType.REMOTE_ADB_HOST);
    }

    public AndroidMobileOptions setRemoteAppsCacheLimit(int num) {
        return amend(AndroidMobileCapabilityType.REMOTE_APPS_CACHE_LIMIT, num);
    }

    public int getRemoteAppsCacheLimit() {
        return (int) getCapability(AndroidMobileCapabilityType.REMOTE_APPS_CACHE_LIMIT);
    }

    public AndroidMobileOptions setResetKeyboard() {
        return setResetKeyboard(true);
    }

    public AndroidMobileOptions setResetKeyboard(boolean bool) {
        return amend(AndroidMobileCapabilityType.RESET_KEYBOARD, bool);
    }

    public boolean doesResetKeyboard() {
        return (boolean) getCapability(AndroidMobileCapabilityType.RESET_KEYBOARD);
    }

    public AndroidMobileOptions setSkipDeviceInitialization() {
        return setSkipDeviceInitialization(true);
    }

    public AndroidMobileOptions setSkipDeviceInitialization(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_DEVICE_INITIALIZATION, bool);
    }

    public boolean doesSkipDeviceInitialization() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_DEVICE_INITIALIZATION);
    }

    public AndroidMobileOptions setSkipLogcatCapture() {
        return setSkipLogcatCapture(true);
    }

    public AndroidMobileOptions setSkipLogcatCapture(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_LOGCAT_CAPTURE, bool);
    }

    public boolean doesSkipLogcatCapture() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_LOGCAT_CAPTURE);
    }

    public AndroidMobileOptions setSkipUnlock() {
        return setSkipUnlock(true);
    }

    public AndroidMobileOptions setSkipUnlock(boolean bool) {
        return amend(AndroidMobileCapabilityType.SKIP_UNLOCK, bool);
    }

    public boolean doesSkipUnlock() {
        return (boolean) getCapability(AndroidMobileCapabilityType.SKIP_UNLOCK);
    }

    public AndroidMobileOptions setSystemPort(String port) {
        return amend(AndroidMobileCapabilityType.SYSTEM_PORT, port);
    }

    public String getSystemPort() {
        return (String) getCapability(AndroidMobileCapabilityType.SYSTEM_PORT);
    }

    public AndroidMobileOptions setUnicodeKeyboard() {
        return setUnicodeKeyboard(true);
    }

    public AndroidMobileOptions setUnicodeKeyboard(boolean bool) {
        return amend(AndroidMobileCapabilityType.UNICODE_KEYBOARD, bool);
    }

    public boolean isUnicodeKeyboard() {
        return (boolean) getCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD);
    }

    public AndroidMobileOptions setUninstallOtherPackages(List<String> packages) {
        return amend(AndroidMobileCapabilityType.UNINSTALL_OTHER_PACKAGES, packages);
    }

    public Object getUninstallOtherPackages() {
        return getCapability(AndroidMobileCapabilityType.UNINSTALL_OTHER_PACKAGES);
    }

    public AndroidMobileOptions setUnlockKey(String key) {
        return amend(AndroidMobileCapabilityType.UNLOCK_KEY, key);
    }

    public String getUnlockKey() {
        return (String) getCapability(AndroidMobileCapabilityType.UNLOCK_KEY);
    }

    public AndroidMobileOptions setUnlockType(String type) {
        return amend(AndroidMobileCapabilityType.UNLOCK_TYPE, type);
    }

    public String getUnlockType() {
        return (String) getCapability(AndroidMobileCapabilityType.UNLOCK_TYPE);
    }

    public AndroidMobileOptions setUseKeystore() {
        return setUseKeystore(true);
    }

    public AndroidMobileOptions setUseKeystore(boolean bool) {
        return amend(AndroidMobileCapabilityType.USE_KEYSTORE, bool);
    }

    public boolean doesUseKeystore() {
        return (boolean) getCapability(AndroidMobileCapabilityType.USE_KEYSTORE);
    }

    public AndroidMobileOptions setWebviewDevtoolsPort(String port) {
        return amend(AndroidMobileCapabilityType.WEBVIEW_DEVTOOLS_PORT, port);
    }

    public String getWebviewDevtoolsPort() {
        return (String) getCapability(AndroidMobileCapabilityType.WEBVIEW_DEVTOOLS_PORT);
    }
}
