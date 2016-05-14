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

import static com.google.common.base.Preconditions.checkArgument;
import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.END_TEST_COVERAGE;
import static io.appium.java_client.MobileCommand.GET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.IS_LOCKED;
import static io.appium.java_client.MobileCommand.LOCK;
import static io.appium.java_client.MobileCommand.LONG_PRESS_KEY_CODE;
import static io.appium.java_client.MobileCommand.OPEN_NOTIFICATIONS;
import static io.appium.java_client.MobileCommand.PRESS_KEY_CODE;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static io.appium.java_client.MobileCommand.SET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.START_ACTIVITY;
import static io.appium.java_client.MobileCommand.TOGGLE_AIRPLANE_MODE;
import static io.appium.java_client.MobileCommand.TOGGLE_DATA;
import static io.appium.java_client.MobileCommand.TOGGLE_LOCATION_SERVICES;
import static io.appium.java_client.MobileCommand.TOGGLE_WIFI;
import static io.appium.java_client.MobileCommand.UNLOCK;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumSetting;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.internal.JsonToAndroidElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;

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

    @Deprecated
    static String uiScrollable(String uiSelector) {
        return "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("
            + uiSelector + ".instance(0));";
    }

    /**
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    @Deprecated
    @Override public T scrollTo(String text) {
        String uiScrollables =
            uiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") + uiScrollable(
                "new UiSelector().textContains(\"" + text + "\")");
        return findElementByAndroidUIAutomator(uiScrollables);
    }

    /**
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    @Deprecated
    @Override public T scrollToExact(String text) {
        String uiScrollables =
            uiScrollable("new UiSelector().description(\"" + text + "\")") + uiScrollable(
                "new UiSelector().text(\"" + text + "\")");
        return findElementByAndroidUIAutomator(uiScrollables);
    }

    /**
     * Send a key event to the device.
     *
     * @param key code for the key pressed on the device.
     */
    @Override public void pressKeyCode(int key) {
        execute(PRESS_KEY_CODE, getCommandImmutableMap("keycode", key));
    }

    /**
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer).
     */
    @Override public void pressKeyCode(int key, Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        execute(PRESS_KEY_CODE, getCommandImmutableMap(parameters, values));
    }

    /**
     * Send a long key event to the device.
     *
     * @param key code for the long key pressed on the device.
     */
    @Override public void longPressKeyCode(int key) {
        execute(LONG_PRESS_KEY_CODE, getCommandImmutableMap("keycode", key));
    }

    /**
     * @param key       code for the long key pressed on the Android device.
     * @param metastate metastate for the long key press.
     * @see AndroidKeyCode
     * @see AndroidKeyMetastate
     * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer)
     */
    @Override public void longPressKeyCode(int key, Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        execute(LONG_PRESS_KEY_CODE, getCommandImmutableMap(parameters, values));
    }

    /**
     * @see HasNetworkConnection#getNetworkConnection().
     */
    @Override public NetworkConnectionSetting getNetworkConnection() {
        Response response = execute(GET_NETWORK_CONNECTION);
        return new NetworkConnectionSetting(Integer.parseInt(response.getValue().toString()));
    }

    /**
     * @param connection The NetworkConnectionSetting configuration to use for the
     *                   device.
     * @see HasNetworkConnection#setNetworkConnection(NetworkConnectionSetting)
     */
    @Override public void setNetworkConnection(NetworkConnectionSetting connection) {
        // the new version of the webdriver protocol is going forward with
        // sending JSON message which look like
        // {name: "name of endpoint", parameters: "JSON parameters"}
        // this is for webdrivers which run on protocols besides HTTP (like TCP)
        // we're implementing that pattern here, for this new method, but
        // haven't translated it to all other commands yet
        String[] parameters = new String[] {"name", "parameters"};
        Object[] values =
            new Object[] {"network_connection", ImmutableMap.of("type", connection.value)};
        execute(SET_NETWORK_CONNECTION, getCommandImmutableMap(parameters, values));
    }

    /**
     * @param remotePath Path to file to write data to on remote device.
     * @param base64Data Base64 encoded byte array of data to write to remote device.
     * @see PushesFiles#pushFile(String, byte[])
     */
    @Override public void pushFile(String remotePath, byte[] base64Data) {
        String[] parameters = new String[] {"path", "data"};
        Object[] values = new Object[] {remotePath, base64Data};
        execute(PUSH_FILE, getCommandImmutableMap(parameters, values));
    }

    @Override public void startActivity(String appPackage, String appActivity,
                                        String appWaitPackage,
                                        String appWaitActivity, String intentAction,
                                        String intentCategory, String intentFlags,
                                        String optionalIntentArguments,boolean stopApp )
            throws IllegalArgumentException {

        checkArgument((!StringUtils.isBlank(appPackage)
                && !StringUtils.isBlank(appActivity)),
            String.format("'%s' and '%s' are required.", "appPackage", "appActivity"));

        appWaitPackage = !StringUtils.isBlank(appWaitPackage) ? appWaitPackage : "";
        appWaitActivity = !StringUtils.isBlank(appWaitActivity) ? appWaitActivity : "";
        intentAction = !StringUtils.isBlank(intentAction) ? intentAction : "";
        intentCategory = !StringUtils.isBlank(intentCategory) ? intentCategory : "";
        intentFlags = !StringUtils.isBlank(intentFlags) ? intentFlags : "";
        optionalIntentArguments = !StringUtils.isBlank(optionalIntentArguments)
                ? optionalIntentArguments : "";

        ImmutableMap<String, ?> parameters = ImmutableMap
                .<String, Object>builder().put("appPackage", appPackage)
                .put("appActivity", appActivity)
                .put("appWaitPackage", appWaitPackage)
                .put("appWaitActivity", appWaitActivity)
                .put("dontStopAppOnReset", !stopApp)
                .put("intentAction", intentAction)
                .put("intentCategory", intentCategory)
                .put("intentFlags", intentFlags)
                .put("optionalIntentArguments", optionalIntentArguments)
                .build();
        execute(START_ACTIVITY, parameters);
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
        String[] parameters = new String[] {"intent", "path"};
        Object[] values = new Object[] {intent, path};
        execute(END_TEST_COVERAGE, getCommandImmutableMap(parameters, values));
    }

    /**
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    public String currentActivity() {
        Response response = execute(CURRENT_ACTIVITY);
        return response.getValue().toString();
    }

    /**
     * Open the notification shade, on Android devices.
     */
    public void openNotifications() {
        execute(OPEN_NOTIFICATIONS);
    }

    /**
     * Check if the device is locked.
     *
     * @return true if device is locked. False otherwise
     */
    public boolean isLocked() {
        Response response = execute(IS_LOCKED);
        return Boolean.parseBoolean(response.getValue().toString());
    }

    public void toggleLocationServices() {
        execute(TOGGLE_LOCATION_SERVICES);
    }

    public void toggleFlightMode() {
        execute(TOGGLE_AIRPLANE_MODE);
    }

    public void toggleData() {
        execute(TOGGLE_DATA);
    }

    public void toggleWiFi() {
        execute(TOGGLE_WIFI);
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
     * @throws org.openqa.selenium.WebDriverException This method is not
     *     applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findElementByAndroidUIAutomator(String using)
        throws WebDriverException {
        return (T) findElement("-android uiautomator", using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findElementsByAndroidUIAutomator(String using)
        throws WebDriverException {
        return (List<T>) findElements("-android uiautomator", using);
    }

    /**
     * This method locks a device.
     */
    public void lockDevice() {
        execute(LOCK, ImmutableMap.of("seconds", 0));
    }

    /**
     * This method unlocks a device.
     */
    public void unlockDevice() {
        execute(UNLOCK);
    }
}
