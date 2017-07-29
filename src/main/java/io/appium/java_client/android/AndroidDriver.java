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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

/**
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
        FindsByAndroidUIAutomator<T>, LocksAndroidDevice, HasAndroidSettings, HasDeviceDetails,
            HasSupportedPerformanceDataType {

    private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

    /**
     * @param executor is an instance of {@link org.openqa.selenium.remote.HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look
     *                     at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, substituteMobilePlatform(capabilities, ANDROID_PLATFORM));
    }

    /**
     * @param remoteAddress is the address of remotely/locally
     *                      started Appium server
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * @param builder take a look
     *                at {@link io.appium.java_client.service.local.AppiumServiceBuilder}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
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
            substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, ANDROID_PLATFORM));
    }

    /**
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
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

}
