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
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

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

    public GeckoDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(URL remoteAddress, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(remoteAddress, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(remoteAddress, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME),
                appiumClientConfig);
    }

    public GeckoDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(AppiumDriverLocalService service, Capabilities capabilities,
                       AppiumClientConfig appiumClientConfig) {
        super(service, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities) {
        super(service, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(service, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(AppiumServiceBuilder builder, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(builder, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities) {
        super(builder, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                       Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(builder, httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities,
                       AppiumClientConfig appiumClientConfig) {
        super(httpClientFactory, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
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
     * GeckoOptions options = new GeckoOptions();
     * GeckoDriver driver = new GeckoDriver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public GeckoDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(clientConfig, ensureAutomationName(capabilities, AUTOMATION_NAME));
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
     * AppiumClientConfig appiumClientConfig = AppiumClientConfig.defaultConfig().directConnect(false);
     * GeckoOptions options = new GeckoOptions();
     * GeckoDriver driver = new GeckoDriver(clientConfig, options, appiumClientConfig);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     *
     */
    public GeckoDriver(ClientConfig clientConfig, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(clientConfig, ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }

    public GeckoDriver(Capabilities capabilities) {
        super(ensureAutomationName(capabilities, AUTOMATION_NAME));
    }

    public GeckoDriver(Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(ensureAutomationName(capabilities, AUTOMATION_NAME), appiumClientConfig);
    }
}
