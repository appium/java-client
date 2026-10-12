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

package io.appium.java_client.wincore;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.PullsFiles;
import io.appium.java_client.PushesFiles;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static io.appium.java_client.MobileCommand.PULL_FILE;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static java.util.Objects.requireNonNull;

/**
 * WincoreDriver is an officially supported Appium driver created to automate Windows apps.
 * It is the successor of the Windows driver, which is deprecated and not supported since Appium 4,
 * see {@link io.appium.java_client.windows.WindowsDriver}.
 * Read https://github.com/appium/appium-wincore-driver for more details.
 */
public class WincoreDriver extends AppiumDriver implements
        PullsFiles,
        PushesFiles,
        SupportsContextSwitching,
        CanRecordScreen {
    private static final String PLATFORM_NAME = Platform.WINDOWS.name();
    private static final String AUTOMATION_NAME = AutomationName.WINCORE;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor is an instance of {@link AppiumCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(AppiumCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium server URL and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium server URL, HTTP client factory and {@code capabilities}.
     *
     * @param remoteAddress is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium driver local service and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium driver local service, HTTP client factory and {@code capabilities}.
     *
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(service, httpClientFactory, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium service builder and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on Appium service builder, HTTP client factory and {@code capabilities}.
     *
     * @param builder take a look at {@link AppiumServiceBuilder}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(builder, httpClientFactory, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on HTTP client factory and {@code capabilities}.
     *
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance connected to an already running driver session.
     * Intended for debugging purposes only; the caller maintains the session state.
     *
     * @param remoteSessionAddress The address of the **running** session including the session identifier.
     */
    public WincoreDriver(URL remoteSessionAddress) {
        super(remoteSessionAddress, PLATFORM_NAME, AUTOMATION_NAME);
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
     * WincoreOptions options = new WincoreOptions();
     * WincoreDriver driver = new WincoreDriver(appiumClientConfig, options);
     *
     * </pre>
     *
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     * @param capabilities take a look at {@link Capabilities}
     *
     */
    public WincoreDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, ensurePlatformAndAutomationNames(
                capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Creates a new instance based on {@code capabilities}.
     *
     * @param capabilities take a look at {@link Capabilities}
     */
    public WincoreDriver(Capabilities capabilities) {
        super(ensurePlatformAndAutomationNames(capabilities, PLATFORM_NAME, AUTOMATION_NAME));
    }

    /**
     * Pulls a file from the remote system. The Wincore driver has no `mobile:` extension for it.
     *
     * @param remotePath Path to the file to read data from the remote device.
     * @return A byte array of Base64 encoded data.
     */
    @Override
    public byte[] pullFile(String remotePath) {
        return decodeBase64(CommandExecutionHelper.execute(this, Map.entry(PULL_FILE, Map.of("path", remotePath))));
    }

    /**
     * Not supported by the Wincore driver.
     *
     * @param remotePath Path to the folder to read data from the remote device.
     * @return Never returns normally.
     * @throws UnsupportedOperationException always.
     */
    @Override
    public byte[] pullFolder(String remotePath) {
        throw new UnsupportedOperationException("The Wincore driver cannot pull folders");
    }

    /**
     * Saves base64-encoded data as a file on the remote system. The Wincore driver has no `mobile:` extension for it.
     *
     * @param remotePath Path to the file to write data to on the remote device.
     * @param base64Data Base64 encoded byte array of the data to write to the remote device.
     */
    @Override
    public void pushFile(String remotePath, byte[] base64Data) {
        CommandExecutionHelper.execute(this, Map.entry(PUSH_FILE, Map.of(
                "path", remotePath,
                "data", new String(base64Data, StandardCharsets.UTF_8)
        )));
    }

    /**
     * Starts the screen recording with the default options. The driver returns no value,
     * so the options overload is used to stay null-safe.
     *
     * @return `not used`.
     */
    @Override
    public String startRecordingScreen() {
        return startRecordingScreen(new WincoreStartScreenRecordingOptions());
    }

    private static byte[] decodeBase64(@Nullable String base64String) {
        return Base64.getDecoder().decode(requireNonNull(base64String).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Launch the application app under test after it was closed.
     */
    public void launchApp() {
        CommandExecutionHelper.executeScript(this, "windows: launchApp");
    }

    /**
     * Close the app under test.
     */
    public void closeApp() {
        CommandExecutionHelper.executeScript(this, "windows: closeApp");
    }
}
