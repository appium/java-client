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

package io.appium.java_client.gecko;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;

import java.net.URL;

/**
 * GeckoDriver is an officially supported Appium driver
 * created to automate Mobile browsers and web views based on
 * the Gecko engine. The driver uses W3C
 * WebDriver protocol and is built on top of Mozilla's geckodriver
 * server. Read https://github.com/appium/appium-geckodriver
 * for more details on how to configure and use it.
 *
 * @since Appium 1.20.0
 */
public class GeckoDriver extends AppiumDriver {
    private static final String AUTOMATION_NAME = AutomationName.GECKO;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor the command executor that sends the commands to the server
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(AppiumCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the server address and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the server address, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the local Appium service and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the local Appium service, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities) {
        super(service, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the local Appium service builder and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on the local Appium service builder, a custom HTTP client
     * factory and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities) {
        super(builder, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on a custom HTTP client factory and {@code capabilities}.
     * The default local Appium service is used.
     *
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * This is a special constructor used to connect to a running driver instance.
     * It does not do any necessary verifications, but rather assumes the given
     * driver session is already running at `remoteSessionAddress`.
     * The maintenance of driver state(s) is the caller's responsibility.
     * !!! This API is supposed to be used for **debugging purposes only**.
     *
     * @param remoteSessionAddress The address of the **running** session including the session identifier.
     * @param platformName The name of the target platform.
     */
    public GeckoDriver(URL remoteSessionAddress, String platformName) {
        super(remoteSessionAddress, platformName, AUTOMATION_NAME);
    }

    /**
     * Creates a new instance based on the given AppiumClientConfig and {@code capabilities}.
     * The HTTP client is default client generated by {@link HttpClient.Factory#createDefault()}.
     * For example:
     *
     * <pre>
     *
     * AppiumClientConfig appiumClientConfig = AppiumClientConfig.defaultConfig()
     *     .directConnect(true)
     *     .baseUri(URI.create("WebDriver URL"))
     *     .readTimeout(Duration.ofMinutes(5));
     * GeckoOptions options = new GeckoOptions();
     * GeckoDriver driver = new GeckoDriver(options, appiumClientConfig);
     *
     * </pre>
     *
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public GeckoDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on {@code capabilities} using the default local Appium service.
     *
     * @param capabilities the capabilities of the session to create
     */
    public GeckoDriver(Capabilities capabilities) {
        super(ensureAutomationName(capabilities, AUTOMATION_NAME));
    }
}
