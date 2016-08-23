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

import static com.google.common.base.Preconditions.checkNotNull;

import static io.appium.java_client.android.AndroidMobileCommandHelper.currentActivityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.endTestCoverageCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getNetworkConnectionCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.isLockedCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.lockDeviceCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.longPressKeyCodeCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.pressKeyCodeCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.pushFileCommandCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.setConnectionCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.startActivityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleLocationServicesCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.unlockCommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumSetting;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileSelector;
import io.appium.java_client.android.internal.JsonToAndroidElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @param <T> the required type of class which implement {@link org.openqa.selenium.WebElement}.
 *     Instances of the defined type will be returned via findElement* and findElements*.
 *     Warning (!!!). Allowed types:
 * {@link org.openqa.selenium.WebElement}
 * {@link io.appium.java_client.TouchableElement}
 * {@link org.openqa.selenium.remote.RemoteWebElement}
 * {@link io.appium.java_client.MobileElement}
 * {@link io.appium.java_client.android.AndroidElement}
 */
public class AndroidDriver<T extends WebElement>
    extends AppiumDriver<T>
    implements AndroidDeviceActionShortcuts, HasNetworkConnection, PushesFiles, StartsActivity,
    FindsByAndroidUIAutomator<T> {

    private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

    /**
     * @param executor is an instance of {@link org.openqa.selenium.remote.HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look
     *                     at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities, JsonToAndroidElementConverter.class);
    }

    /**
     * @param remoteAddress is the address of remotely/locally
     *                      started Appium server
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param remoteAddress is the address of remotely/locally
     *                      started Appium server
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(service, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param builder take a look
     *                at {@link io.appium.java_client.service.local.AppiumServiceBuilder}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param builder take a look
     *                at {@link io.appium.java_client.service.local.AppiumServiceBuilder}
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(builder, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM),
            JsonToAndroidElementConverter.class);
    }

    /**
     * @see io.appium.java_client.TouchShortcuts#swipe(int, int, int, int, int)
     */
    @Override public void swipe(int startx, int starty, int endx, int endy, int duration) {
        doSwipe(startx, starty, endx, endy, duration);
    }

    /**
     * Send a key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    @Override public void pressKeyCode(int key) {
        CommandExecutionHelper.execute(this, pressKeyCodeCommand(key));
    }

    /**
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer).
     */
    @Override public void pressKeyCode(int key, Integer metastate) {
        CommandExecutionHelper.execute(this, pressKeyCodeCommand(key, metastate));
    }

    /**
     * Send a long key event to the device.
     *
     * @param key code for the long key pressed on the device.
     */
    @Override public void longPressKeyCode(int key) {
        CommandExecutionHelper.execute(this, longPressKeyCodeCommand(key));
    }

    /**
     * @param key       code for the long key pressed on the Android device.
     * @param metastate metastate for the long key press.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer)
     */
    @Override public void longPressKeyCode(int key, Integer metastate) {
        CommandExecutionHelper.execute(this, longPressKeyCodeCommand(key, metastate));
    }

    @Override public void setConnection(Connection connection) {
        CommandExecutionHelper.execute(this, setConnectionCommand(connection));
    }

    @Override public Connection getConnection() {
        long bitMask = CommandExecutionHelper.execute(this, getNetworkConnectionCommand());
        Connection[] types = Connection.values();

        for (Connection connection: types) {
            if (connection.bitMask == bitMask) {
                return connection;
            }
        }
        throw new WebDriverException("The unknown network connection "
            + "type has been returned. The bitmask is " + bitMask);
    }

    @Override public void pushFile(String remotePath, byte[] base64Data) {
        CommandExecutionHelper.execute(this, pushFileCommandCommand(remotePath, base64Data));
    }

    @Override public void pushFile(String remotePath, File file) throws IOException {
        checkNotNull(file, "A reference to file should not be NULL");
        if (!file.exists()) {
            throw new IOException("The given file "
                + file.getAbsolutePath() + " doesn't exist");
        }
        pushFile(remotePath,
            Base64.encodeBase64(FileUtils.readFileToByteArray(file)));
    }

    @Override public void startActivity(String appPackage, String appActivity,
        String appWaitPackage,
        String appWaitActivity, String intentAction,
        String intentCategory, String intentFlags,
        String optionalIntentArguments,boolean stopApp )
        throws IllegalArgumentException {
        CommandExecutionHelper.execute(this, startActivityCommand(appPackage, appActivity,
            appWaitPackage, appWaitActivity, intentAction, intentCategory, intentFlags,
            optionalIntentArguments, stopApp));
    }


    @Override
    public void startActivity(String appPackage, String appActivity,
        String appWaitPackage, String appWaitActivity, boolean stopApp)
        throws IllegalArgumentException {
        this.startActivity(appPackage,appActivity,appWaitPackage,
            appWaitActivity,null,null,null,null,stopApp);

    }

    @Override public void startActivity(String appPackage, String appActivity,
        String appWaitPackage,
        String appWaitActivity) throws IllegalArgumentException {

        this.startActivity(appPackage, appActivity,
            appWaitPackage, appWaitActivity,null,null,null,null,true);
    }

    @Override public void startActivity(String appPackage, String appActivity)
        throws IllegalArgumentException {
        this.startActivity(appPackage, appActivity, null, null,
            null,null,null,null,true);
    }

    @Override public void startActivity(String appPackage, String appActivity,
        String appWaitPackage, String appWaitActivity,
        String intentAction,String intentCategory,
        String intentFlags,String intentOptionalArgs)
        throws IllegalArgumentException {
        this.startActivity(appPackage,appActivity,
            appWaitPackage,appWaitActivity,
            intentAction,intentCategory,intentFlags,intentOptionalArgs,true);
    }

    /**
     * Get test-coverage data.
     *
     * @param intent intent to broadcast.
     * @param path   path to .ec file.
     */
    public void endTestCoverage(String intent, String path) {
        CommandExecutionHelper.execute(this, endTestCoverageCommand(intent, path));
    }

    /**
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    public String currentActivity() {
        return CommandExecutionHelper.execute(this, currentActivityCommand());
    }

    /**
     * Open the notification shade, on Android devices.
     */
    public void openNotifications() {
        CommandExecutionHelper.execute(this, openNotificationsCommand());
    }

    /**
     * Check if the device is locked.
     *
     * @return true if device is locked. False otherwise
     */
    public boolean isLocked() {
        return CommandExecutionHelper.execute(this, isLockedCommand());
    }

    public void toggleLocationServices() {
        CommandExecutionHelper.execute(this, toggleLocationServicesCommand());
    }

    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*.
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     */
    // Should be moved to the subclass
    public void ignoreUnimportantViews(Boolean compress) {
        setSetting(AppiumSetting.IGNORE_UNIMPORTANT_VIEWS, compress);
    }

    /**
     * @throws WebDriverException This method is not
     *     applicable with browser/webview UI.
     */
    @Override
    public T findElementByAndroidUIAutomator(String using)
        throws WebDriverException {
        return findElement(MobileSelector.ANDROID_UI_AUTOMATOR.toString(), using);
    }

    /**
     * @throws WebDriverException This method is not
     *      applicable with browser/webview UI.
     */
    @Override
    public List<T> findElementsByAndroidUIAutomator(String using)
        throws WebDriverException {
        return findElements(MobileSelector.ANDROID_UI_AUTOMATOR.toString(), using);
    }

    /**
     * This method locks a device.
     */
    public void lockDevice() {
        CommandExecutionHelper.execute(this, lockDeviceCommand());
    }

    /**
     * This method unlocks a device.
     */
    public void unlockDevice() {
        CommandExecutionHelper.execute(this, unlockCommand());
    }
}
