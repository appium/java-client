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

import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByIosClassChain;
import io.appium.java_client.FindsByIosNSPredicate;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboardWithKeyName;
import io.appium.java_client.LocksDevice;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import javax.annotation.Nullable;

/**
 * Apple tvOS driver implementation.
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
    implements HidesKeyboardWithKeyName, HasIOSSettings, HasOnScreenKeyboard,
        LocksDevice, FindsByIosNSPredicate<T>, FindsByIosClassChain<T>,
        PushesFiles, CanRecordScreen, HasIOSClipboard {

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
}
