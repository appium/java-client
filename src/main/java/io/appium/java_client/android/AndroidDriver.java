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
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmCallCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmSignalStrengthCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmVoiceCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.networkSpeedCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerACCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerCapacityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.sendSMSCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleLocationServicesCommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

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
    implements PressesKeyCode, HasNetworkConnection, PushesFiles, StartsActivity,
        FindsByAndroidUIAutomator<T>, LocksDevice, HasAndroidSettings, HasDeviceDetails,
        HasSupportedPerformanceDataType, AuthenticatesByFinger, CanRecordScreen {

    private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, substituteMobilePlatform(capabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param desiredCapabilities take a look at {@link Capabilities}
     */
    public AndroidDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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

    /**
     * Emulate send SMS event on the connected emulator.
     *
     * @param phoneNumber The phone number of message sender.
     * @param message   The message content.
     */
    public void sendSMS(String phoneNumber, String message) {
        CommandExecutionHelper.execute(this, sendSMSCommand(phoneNumber, message));
    }

    /**
     * Emulate GSM call event on the connected emulator.
     *
     * @param phoneNumber The phone number of the caller.
     * @param gsmCallActions   One of available GSM call actions.
     */
    public void gsmCall(String phoneNumber, GsmCallActions gsmCallActions) {
        CommandExecutionHelper.execute(this, gsmCallCommand(phoneNumber, gsmCallActions));
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available GSM signal strength.
     */
    public void gsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        CommandExecutionHelper.execute(this, gsmSignalStrengthCommand(gsmSignalStrength));
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available GSM voice state.
     */
    public void gsmVoice(GsmVoiceState gsmVoiceState) {
        CommandExecutionHelper.execute(this, gsmVoiceCommand(gsmVoiceState));
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available Network Speed values.
     */
    public void networkSpeed(NetworkSpeed networkSpeed) {
        CommandExecutionHelper.execute(this, networkSpeedCommand(networkSpeed));
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    public void powerCapacity(int percent) {
        CommandExecutionHelper.execute(this, powerCapacityCommand(percent));
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available Power AC state.
     */
    public void powerAC(PowerACState powerACState) {
        CommandExecutionHelper.execute(this, powerACCommand(powerACState));
    }
}
