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

import static io.appium.java_client.android.AndroidMobileCommandHelper.endTestCoverageCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleLocationServicesCommand;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidDataMatcher;
import io.appium.java_client.FindsByAndroidViewMatcher;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByAndroidViewTag;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.android.connection.HasNetworkConnection;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.battery.HasBattery;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * Android driver implementation.
 *
 * @param <T> the required type of class which implement {@link org.openqa.selenium.WebElement}.
 *           Instances of the defined type will be returned via findElement* and findElements*.
 *           Warning (!!!). Allowed types:
 *           {@link org.openqa.selenium.WebElement}
 *           {@link org.openqa.selenium.remote.RemoteWebElement}
 *           {@link io.appium.java_client.MobileElement}
 *           {@link io.appium.java_client.android.AndroidElement}
 */
public class AndroidDriver<T extends WebElement>
    extends AppiumDriver<T>
    implements PressesKey, HasNetworkConnection, PushesFiles, StartsActivity,
        FindsByAndroidUIAutomator<T>, FindsByAndroidViewTag<T>, FindsByAndroidDataMatcher<T>,
        FindsByAndroidViewMatcher<T>, LocksDevice, HasAndroidSettings, HasAndroidDeviceDetails,
        HasSupportedPerformanceDataType, AuthenticatesByFinger, HasOnScreenKeyboard,
        CanRecordScreen, SupportsSpecialEmulatorCommands,
        SupportsNetworkStateManagement, ListensToLogcatMessages, HasAndroidClipboard,
        HasBattery<AndroidBatteryInfo> {

    private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

    private StringWebSocketClient logcatClient;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, updateDefaultPlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory,
            updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(service, httpClientFactory,
            updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(builder, httpClientFactory,
            updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(Capabilities desiredCapabilities) {
        super(updateDefaultPlatformName(desiredCapabilities, ANDROID_PLATFORM));
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
     * Open the notification shade, on Android devices.
     */
    public void openNotifications() {
        CommandExecutionHelper.execute(this, openNotificationsCommand());
    }

    public void toggleLocationServices() {
        CommandExecutionHelper.execute(this, toggleLocationServicesCommand());
    }

    @SuppressWarnings("unchecked")
    @Override
    public AndroidBatteryInfo getBatteryInfo() {
        return new AndroidBatteryInfo((Map<String, Object>) execute(EXECUTE_SCRIPT, ImmutableMap.of(
                "script", "mobile: batteryInfo", "args", Collections.emptyList())).getValue());
    }

    @Override
    public synchronized StringWebSocketClient getLogcatClient() {
        if (logcatClient == null) {
            logcatClient = new StringWebSocketClient();
        }
        return logcatClient;
    }
}
