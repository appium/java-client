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

package io.appium.java_client.windows;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.PullsFiles;
import io.appium.java_client.PushesFiles;
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

public class WindowsDriver extends AppiumDriver implements
        PerformsTouchActions,
        PullsFiles,
        PushesFiles,
        CanRecordScreen {
    private static final String PLATFORM_NAME = Platform.WINDOWS.name();
    private static final String AUTOMATION_NAME = AutomationName.WINDOWS;
    
    public WindowsDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, null, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(URL remoteAddress, Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(remoteAddress, null, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, null, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(remoteAddress, null, null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(null, service, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumDriverLocalService service, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        super(null, service, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(null, service, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(null, service, null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(null, builder.build(), null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumServiceBuilder builder, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        super(null, builder.build(), null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(null, builder.build(), null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(null, builder.build(), null, httpClientFactory, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(null, null, null, httpClientFactory, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities,
                         AppiumClientConfig appiumClientConfig) {
        super(null, null, null, httpClientFactory, appiumClientConfig,
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
     * WindowsOptions options = new WindowsOptions();
     * WindowsDriver driver = new WindowsDriver(clientConfig, options);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public WindowsDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(null, null, clientConfig, null, null,
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
     * WindowsOptions options = new WindowsOptions();
     * WindowsDriver driver = new WindowsDriver(clientConfig, options, appiumClientConfig);
     *
     * </pre>
     *
     * @param clientConfig take a look at {@link ClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     *
     */
    public WindowsDriver(ClientConfig clientConfig, Capabilities capabilities,
                                AppiumClientConfig appiumClientConfig) {
        super(null, null, clientConfig, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(Capabilities capabilities) {
        super(null, null, null, null, null,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    public WindowsDriver(Capabilities capabilities, AppiumClientConfig appiumClientConfig) {
        super(null, null, null, null, appiumClientConfig,
                ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Launch the application app under test after it was closed.
     */
    public void launchApp() {
        execute(MobileCommand.LAUNCH_APP);
    }

    /**
     * Close the app under test.
     */
    public void closeApp() {
        execute(MobileCommand.CLOSE_APP);
    }
}
