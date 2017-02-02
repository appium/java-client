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
import static io.appium.java_client.android.AndroidMobileCommandHelper.getPerformanceDataCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSupportedPerformanceDataTypesCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleLocationServicesCommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import java.util.List;

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
        FindsByAndroidUIAutomator<T>, LocksAndroidDevice, HasSettings, HasDeviceDetails,
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
     * returns the information type of the system state which is supported to read
     * as like cpu, memory, network traffic, and battery.
     * @return output - array like below
     *                  [cpuinfo, batteryinfo, networkinfo, memoryinfo]
     *
     */
    public List<String> getSupportedPerformanceDataTypes() {
        return CommandExecutionHelper.execute(this, getSupportedPerformanceDataTypesCommand());
    }

    /**
     * returns the resource usage information of the application. the resource is one of the system state
     * which means cpu, memory, network traffic, and battery.
     *
     * @param packageName the package name of the application
     * @param dataType the type of system state which wants to read.
     *                 It should be one of the supported performance data types,
     *                 the return value of the function "getSupportedPerformanceDataTypes"
     * @param dataReadTimeout the number of attempts to read
     * @return table of the performance data, The first line of the table represents the type of data.
     *          The remaining lines represent the values of the data.
     *          in case of battery info : [[power], [23]]
     *          in case of memory info :
     *              [[totalPrivateDirty, nativePrivateDirty, dalvikPrivateDirty, eglPrivateDirty, glPrivateDirty,
     *                        totalPss, nativePss, dalvikPss, eglPss, glPss, nativeHeapAllocatedSize, nativeHeapSize],
     *                        [18360, 8296, 6132, null, null, 42588, 8406, 7024, null, null, 26519, 10344]]
     *          in case of network info :
     *              [[bucketStart, activeTime, rxBytes, rxPackets, txBytes, txPackets, operations, bucketDuration,],
     *                        [1478091600000, null, 1099075, 610947, 928, 114362, 769, 0, 3600000],
     *                        [1478095200000, null, 1306300, 405997, 509, 46359, 370, 0, 3600000]]
     *          in case of network info :
     *              [[st, activeTime, rb, rp, tb, tp, op, bucketDuration],
     *                        [1478088000, null, null, 32115296, 34291, 2956805, 25705, 0, 3600],
     *                        [1478091600, null, null, 2714683, 11821, 1420564, 12650, 0, 3600],
     *                        [1478095200, null, null, 10079213, 19962, 2487705, 20015, 0, 3600],
     *                        [1478098800, null, null, 4444433, 10227, 1430356, 10493, 0, 3600]]
     *          in case of cpu info : [[user, kernel], [0.9, 1.3]]
     * @throws Exception if the performance data type is not supported, thows Error
     */
    public List<List<Object>> getPerformanceData(String packageName, String dataType, int dataReadTimeout)
            throws Exception {
        return CommandExecutionHelper.execute(this,
                                                getPerformanceDataCommand(packageName, dataType, dataReadTimeout));
    }

    /**
     * This method is deprecated. It is going to be removed
     */
    @Override public void swipe(int startx, int starty, int endx, int endy, int duration) {
        new TouchAction(this).press(startx, starty).waitAction(duration).moveTo(endx, endy).release().perform();
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
