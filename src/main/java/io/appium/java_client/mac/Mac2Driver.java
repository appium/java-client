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

package io.appium.java_client.mac;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import javax.annotation.Nullable;
import java.net.URL;

/**
 * Mac2Driver is an officially supported Appium driver
 * created to automate Mac OS apps. The driver uses W3C
 * WebDriver protocol and is built on top of Apple's XCTest
 * automation framework. Read https://github.com/appium/appium-mac2-driver
 * for more details on how to configure and use it.
 *
 * @since Appium 1.20.0
 */
public class Mac2Driver extends AppiumDriver implements
        PerformsTouchActions,
        CanRecordScreen {
    private static final String PLATFORM_NAME = Platform.MAC.name();
    private static final String AUTOMATION_NAME = AutomationName.MAC2;

    private Mac2Driver(
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

    public Mac2Driver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(URL remoteAddress, Capabilities capabilities) {
        this(remoteAddress, null, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(URL remoteAddress, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(remoteAddress, null, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(remoteAddress, null, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                      Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(remoteAddress, null, null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumDriverLocalService service, Capabilities capabilities) {
        this(null, service, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumDriverLocalService service, Capabilities capabilities,
                      AppiumClientConfig appiumClientConfig) {
        this(null, service, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                      Capabilities capabilities) {
        this(null, service, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                      Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, service, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumServiceBuilder builder, Capabilities capabilities) {
        this(null, builder.build(), null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumServiceBuilder builder, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, builder.build(), null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                      Capabilities capabilities) {
        this(null, builder.build(), null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                      Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, builder.build(), null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(null, null, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(HttpClient.Factory httpClientFactory, Capabilities capabilities,
                      AppiumClientConfig appiumClientConfig) {
        this(null, null, null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
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
     * Mac2Options options = new Mac2Options();
     * Mac2Driver driver = new Mac2Driver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public Mac2Driver(ClientConfig clientConfig, Capabilities capabilities) {
        this(null, null, clientConfig, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
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
     * Mac2Options options = new Mac2Options();
     * Mac2Driver driver = new Mac2Driver(clientConfig, options, appiumClientConfig);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     *
     */
    public Mac2Driver(ClientConfig clientConfig, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, null, clientConfig, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(Capabilities capabilities) {
        this(null, null, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public Mac2Driver(Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        this(null, null, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }
}
