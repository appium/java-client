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

package io.appium.java_client.ios;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.HasAppStrings;
import io.appium.java_client.HasDeviceTime;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.HidesKeyboardWithKeyName;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.PullsFiles;
import io.appium.java_client.PushesFiles;
import io.appium.java_client.SupportsLegacyAppManagement;
import io.appium.java_client.battery.HasBattery;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.remote.SupportsLocation;
import io.appium.java_client.remote.SupportsRotation;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

import static io.appium.java_client.MobileCommand.prepareArguments;

/**
 * iOS driver implementation.
 */
public class IOSDriver extends AppiumDriver implements
        SupportsContextSwitching,
        SupportsRotation,
        SupportsLocation,
        HidesKeyboard,
        HasDeviceTime,
        PullsFiles,
        InteractsWithApps,
        SupportsLegacyAppManagement,
        HasAppStrings,
        PerformsTouchActions,
        HidesKeyboardWithKeyName,
        ShakesDevice,
        HasIOSSettings,
        HasOnScreenKeyboard,
        LocksDevice,
        PerformsTouchID,
        PushesFiles,
        CanRecordScreen,
        HasIOSClipboard,
        ListensToSyslogMessages,
        HasBattery<IOSBatteryInfo> {
    private static final String PLATFORM_NAME = Platform.IOS.name();

    private StringWebSocketClient syslogClient;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities capabilities) {
        super(service, httpClientFactory, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities capabilities) {
        super(builder, httpClientFactory, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, ensurePlatformName(capabilities, PLATFORM_NAME));
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
     * XCUITestOptions options = new XCUITestOptions();
     * IOSDriver driver = new IOSDriver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public IOSDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(AppiumClientConfig.fromClientConfig(clientConfig),
                    ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * Creates a new instance based on the given ClientConfig and {@code capabilities}.
     * The HTTP client is default client generated by {@link HttpCommandExecutor#getDefaultClientFactory}.
     * For example:
     *
     * <pre>
     *
     * AppiumClientConfig appiumClientConfig = AppiumClientConfig.defaultConfig()
     *     .directConnect(true)
     *     .baseUri(URI.create("WebDriver URL"))
     *     .readTimeout(Duration.ofMinutes(5));
     * XCUITestOptions options = new XCUITestOptions();
     * IOSDriver driver = new IOSDriver(options, appiumClientConfig);
     *
     * </pre>
     *
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public IOSDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    /**
     * This is a special constructor used to connect to a running driver instance.
     * It does not do any necessary verifications, but rather assumes the given
     * driver session is already running at `remoteSessionAddress`.
     * The maintenance of driver state(s) is the caller's responsibility.
     * !!! This API is supposed to be used for **debugging purposes only**.
     *
     * @param remoteSessionAddress The address of the **running** session including the session identifier.
     */
    public IOSDriver(URL remoteSessionAddress) {
        super(remoteSessionAddress, PLATFORM_NAME, AutomationName.IOS_XCUI_TEST);
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param capabilities take a look at {@link Capabilities}
     */
    public IOSDriver(Capabilities capabilities) {
        super(ensurePlatformName(capabilities, PLATFORM_NAME));
    }

    @Override public TargetLocator switchTo() {
        return new InnerTargetLocator();
    }

    @Override
    public IOSBatteryInfo getBatteryInfo() {
        return new IOSBatteryInfo(CommandExecutionHelper.executeScript(this, "mobile: batteryInfo"));
    }

    private class InnerTargetLocator extends RemoteTargetLocator {
        @Override public Alert alert() {
            return new IOSAlert(super.alert());
        }
    }

    class IOSAlert implements Alert {

        private final Alert alert;

        IOSAlert(Alert alert) {
            this.alert = alert;
        }

        @Override public void dismiss() {
            execute(DriverCommand.DISMISS_ALERT);
        }

        @Override public void accept() {
            execute(DriverCommand.ACCEPT_ALERT);
        }

        @Override public String getText() {
            Response response = execute(DriverCommand.GET_ALERT_TEXT);
            return response.getValue().toString();
        }

        @Override public void sendKeys(String keysToSend) {
            execute(DriverCommand.SET_ALERT_VALUE, prepareArguments("value", keysToSend));
        }

    }

    @Override
    public RemoteLocationContext getLocationContext() {
        return locationContext;
    }

    @Override
    public synchronized StringWebSocketClient getSyslogClient() {
        if (syslogClient == null) {
            syslogClient = new StringWebSocketClient();
        }
        return syslogClient;
    }
}
