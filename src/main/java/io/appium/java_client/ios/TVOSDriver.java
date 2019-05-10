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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.*;
import io.appium.java_client.battery.HasBattery;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;

import javax.annotation.Nullable;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.prepareArguments;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;

/**
 * tvOS driver implementation.
 *
 * @param <T> the required type of class which implement
 *           {@link WebElement}.
 *           Instances of the defined type will be returned via findElement* and findElements*.
 *           Warning (!!!). Allowed types:
 *           {@link WebElement}
 *           {@link org.openqa.selenium.remote.RemoteWebElement}
 *           {@link io.appium.java_client.MobileElement}
 *           {@link IOSElement}
 */
public class TVOSDriver<T extends WebElement>
    extends AppiumDriver<T>
    implements HidesKeyboardWithKeyName, ShakesDevice, HasIOSSettings, HasOnScreenKeyboard,
        LocksDevice, PerformsTouchID, FindsByIosNSPredicate<T>, FindsByIosClassChain<T>,
        PushesFiles, CanRecordScreen, HasIOSClipboard, ListensToSyslogMessages,
        HasBattery<IOSBatteryInfo> {

    private static final String TVOS_PLATFORM = MobilePlatform.TVOS;

    private StringWebSocketClient syslogClient;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, substituteMobilePlatform(capabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                      Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                      Capabilities desiredCapabilities) {
        super(service, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                      Capabilities desiredCapabilities) {
        super(builder, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public TVOSDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities, TVOS_PLATFORM));
    }

    /**
     * Runs the current app as a background app for the number of seconds
     * or minimizes the app.
     *
     * @param duration The time to run App in background.
     */
    @Override public void runAppInBackground(Duration duration) {
        // timeout parameter is expected to be in milliseconds
        // float values are allowed
        execute(RUN_APP_IN_BACKGROUND,
                prepareArguments("seconds", prepareArguments("timeout", duration.toMillis())));
    }

    @Override public TargetLocator switchTo() {
        return new InnerTargetLocator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public IOSBatteryInfo getBatteryInfo() {
        return new IOSBatteryInfo((Map<String, Object>) execute(EXECUTE_SCRIPT, ImmutableMap.of(
                "script", "mobile: batteryInfo", "args", Collections.emptyList())).getValue());
    }

    private class InnerTargetLocator extends RemoteTargetLocator {
        @Override public Alert alert() {
            return new IOSAlert(super.alert());
        }
    }

    /**
     * Returns capabilities that were provided on instantiation.
     *
     * @return given {@link Capabilities}
     */
    @Nullable
    public Capabilities getCapabilities() {
        MutableCapabilities capabilities = (MutableCapabilities) super.getCapabilities();
        if (capabilities != null) {
            capabilities.setCapability(PLATFORM_NAME, TVOS_PLATFORM);
        }
        return capabilities;
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
    public synchronized StringWebSocketClient getSyslogClient() {
        if (syslogClient == null) {
            syslogClient = new StringWebSocketClient();
        }
        return syslogClient;
    }
}
