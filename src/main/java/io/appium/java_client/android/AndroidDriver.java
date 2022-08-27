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

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecuteCDPCommand;
import io.appium.java_client.HasAppStrings;
import io.appium.java_client.HasDeviceTime;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.PullsFiles;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.PushesFiles;
import io.appium.java_client.SupportsLegacyAppManagement;
import io.appium.java_client.android.connection.HasNetworkConnection;
import io.appium.java_client.android.geolocation.SupportsExtendedGeolocationCommands;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.battery.HasBattery;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.remote.SupportsLocation;
import io.appium.java_client.remote.SupportsRotation;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * Android driver implementation.
 */
public class AndroidDriver extends AppiumDriver implements
        PressesKey,
        SupportsRotation,
        SupportsContextSwitching,
        SupportsLocation,
        PerformsTouchActions,
        HidesKeyboard,
        HasDeviceTime,
        PullsFiles,
        InteractsWithApps,
        SupportsLegacyAppManagement,
        HasAppStrings,
        HasNetworkConnection,
        PushesFiles,
        StartsActivity,
        LocksDevice,
        HasAndroidSettings,
        HasAndroidDeviceDetails,
        HasSupportedPerformanceDataType,
        AuthenticatesByFinger,
        HasOnScreenKeyboard,
        CanRecordScreen,
        SupportsSpecialEmulatorCommands,
        SupportsNetworkStateManagement,
        ListensToLogcatMessages,
        HasAndroidClipboard,
        HasBattery<AndroidBatteryInfo>,
        ExecuteCDPCommand,
        CanReplaceElementValue,
        SupportsExtendedGeolocationCommands {
    private static final String ANDROID_PLATFORM = Platform.ANDROID.name();

    private StringWebSocketClient logcatClient;

    private AndroidDriver(
            @Nullable URL remoteAddress,
            @Nullable AppiumDriverLocalService service,
            @Nullable ClientConfig clientConfig,
            @Nullable HttpClient.Factory httpClientFactory,
            @Nullable AppiumClientConfig appiumClientConfig,
            @Nullable Capabilities capabilities) {
        super(
                remoteAddress,
                service,
                clientConfig,
                httpClientFactory,
                appiumClientConfig,
                capabilities
        );
    }

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(URL remoteAddress, Capabilities capabilities) {
        this(remoteAddress, null, null, null, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(URL remoteAddress, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(remoteAddress, null, null, null, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(
            URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(remoteAddress, null, null, httpClientFactory, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(
            URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities,
            AppiumClientConfig appiumClientConfig) {
        this(remoteAddress, null, null, httpClientFactory, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        this(null, service, null, null, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        this(null, service, null, null, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(null, service, null, httpClientFactory, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities,
            AppiumClientConfig appiumClientConfig) {
        this(null, service, null, httpClientFactory, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        this(null, builder.build(), null, null, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));

    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        this(null, builder.build(), null, null, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities capabilities) {
        this(null, builder.build(), null, httpClientFactory, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, builder.build(), null, httpClientFactory, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(null, null, null, httpClientFactory, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        this(null, null, null, httpClientFactory, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on the given ClientConfig and {@code capabilities}.
     * The HTTP client is default client generated by {@link HttpCommandExecutor#getDefaultClientFactory}.
     * For example:
     *
     * <pre>
     *
     * ClientConfig clientConfig = ClientConfig.defaultConfig()
     *     .baseUri(URI.create("WebDriver URL"))
     *     .readTimeout(Duration.ofMinutes(5));
     * UiAutomator2Options options = new UiAutomator2Options();
     * AndroidDriver driver = new AndroidDriver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public AndroidDriver(ClientConfig clientConfig, Capabilities capabilities) {
        this(null, null, clientConfig, null, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on the given ClientConfig and {@code capabilities}.
     * The HTTP client is default client generated by {@link HttpCommandExecutor#getDefaultClientFactory}.
     * For example:
     *
     * <pre>
     *
     * ClientConfig clientConfig = ClientConfig.defaultConfig()
     *     .baseUri(URI.create("WebDriver URL"))
     *     .readTimeout(Duration.ofMinutes(5));
     * AppiumClientConfig appiumClientConfig = AppiumClientConfig.defaultConfig().directConnect(true);
     * UiAutomator2Options options = new UiAutomator2Options();
     * AndroidDriver driver = new AndroidDriver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     *
     */
    public AndroidDriver(ClientConfig clientConfig, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        this(null, null, clientConfig, null, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(Capabilities capabilities) {
        this(null, null, null, null, null,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AndroidDriver(Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, null, null, null, appiumClientConfig,
                ensurePlatformName(capabilities, ANDROID_PLATFORM));
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
    public RemoteLocationContext getLocationContext() {
        return locationContext;
    }

    @Override
    public synchronized StringWebSocketClient getLogcatClient() {
        if (logcatClient == null) {
            logcatClient = new StringWebSocketClient();
        }
        return logcatClient;
    }
}
