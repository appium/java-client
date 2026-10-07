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

package io.appium.java_client;

import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.internal.SessionHelpers;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumRemoteWebDriver;
import io.appium.java_client.remote.AppiumW3CHttpCommandCodec;
import io.appium.java_client.remote.AppiumW3CHttpResponseCodec;
import io.appium.java_client.remote.DriverCommand;
import io.appium.java_client.remote.ErrorHandler;
import io.appium.java_client.remote.ExecuteMethod;
import io.appium.java_client.remote.Response;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static io.appium.java_client.internal.Strings.isNullOrEmpty;
import static io.appium.java_client.remote.CapabilityType.BROWSER_NAME;
import static io.appium.java_client.remote.CapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static java.util.Collections.singleton;

/**
 * Default Appium driver implementation.
 */
public class AppiumDriver extends AppiumRemoteWebDriver implements
        ExecutesMethod,
        ComparesImages,
        ExecutesDriverScript,
        LogsEvents,
        HasBrowserCheck,
        HasSettings {

    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler(new ErrorCodesMobile());
    // frequently used command parameters
    @Getter
    private final URL remoteAddress;
    private final ExecuteMethod executeMethod;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor     is an instance of {@link AppiumCommandExecutor}
     *                     or class that extends it. Default commands or another vendor-specific
     *                     commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AppiumDriver(AppiumCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
        this.executeMethod = new AppiumExecutionMethod(this);
        super.setErrorHandler(ERROR_HANDLER);
        this.remoteAddress = executor.getAddressOfRemoteServer();
    }

    /**
     * Creates a new instance based on the HTTP client configuration and {@code capabilities}.
     *
     * @param clientConfig the HTTP client configuration
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(AppiumClientConfig clientConfig, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, clientConfig), capabilities);
    }

    /**
     * Creates a new instance based on the address of the server and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(URL remoteAddress, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress),
                capabilities);
    }

    /**
     * Creates a new instance based on the address of the server, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress,
                httpClientFactory), capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service),
                capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service, httpClientFactory),
                capabilities);
    }

    /**
     * Creates a new instance based on the builder of a local Appium service and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        this(builder.build(), capabilities);
    }

    /**
     * Creates a new instance based on the builder of a local Appium service, a custom HTTP client
     * factory and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(builder.build(), httpClientFactory, capabilities);
    }

    /**
     * Creates a new instance based on a custom HTTP client factory and {@code capabilities}.
     * The default local Appium service is used.
     *
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(AppiumDriverLocalService.buildDefaultService(), httpClientFactory,
                capabilities);
    }

    /**
     * Creates a new instance based on {@code capabilities} using the default local Appium service.
     *
     * @param capabilities the capabilities of the session to create
     */
    public AppiumDriver(Capabilities capabilities) {
        this(AppiumDriverLocalService.buildDefaultService(), capabilities);
    }

    /**
     * This is a special constructor used to connect to a running driver instance.
     * It does not do any necessary verifications, but rather assumes the given
     * driver session is already running at `remoteSessionAddress`.
     * The maintenance of driver state(s) is the caller's responsibility.
     * !!! This API is supposed to be used for **debugging purposes only**.
     *
     * @param remoteSessionAddress The address of the **running** session including the session identifier.
     * @param platformName         The name of the target platform.
     * @param automationName       The name of the target automation.
     */
    public AppiumDriver(URL remoteSessionAddress, String platformName, String automationName) {
        super();
        this.capabilities = new ImmutableCapabilities(
                Map.of(
                        PLATFORM_NAME, platformName,
                        APPIUM_PREFIX + AUTOMATION_NAME_OPTION, automationName
                )
        );
        SessionHelpers.SessionAddress sessionAddress = SessionHelpers.parseSessionAddress(remoteSessionAddress);
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, sessionAddress.getServerUrl()
        );
        executor.setCommandCodec(new AppiumW3CHttpCommandCodec());
        executor.setResponseCodec(new AppiumW3CHttpResponseCodec());
        executor.refreshAdditionalCommands();
        setCommandExecutor(executor);
        this.executeMethod = new AppiumExecutionMethod(this);
        super.setErrorHandler(ERROR_HANDLER);
        this.remoteAddress = executor.getAddressOfRemoteServer();

        setSessionId(sessionAddress.getId());
    }

    @Override
    public ExecuteMethod getExecuteMethod() {
        return executeMethod;
    }

    /**
     * This method is used to get build version status of running Appium server.
     *
     * @return map containing version details
     */
    public Map<String, Object> getStatus() {
        //noinspection unchecked
        return (Map<String, Object>) execute(DriverCommand.STATUS).getValue();
    }

    /**
     * This method is used to add custom appium commands in Appium 2.0.
     *
     * @param httpMethod the available {@link HttpMethod}.
     * @param url        The url to URL template as https://www.w3.org/TR/webdriver/#endpoints.
     * @param methodName The name of custom appium command.
     */
    public void addCommand(HttpMethod httpMethod, String url, String methodName) {
        AppiumCommandInfo commandInfo;
        switch (httpMethod) {
            case GET:
                commandInfo = MobileCommand.getC(url);
                break;
            case POST:
                commandInfo = MobileCommand.postC(url);
                break;
            case DELETE:
                commandInfo = MobileCommand.deleteC(url);
                break;
            default:
                throw new WebDriverException(String.format("Unsupported HTTP Method: %s. Only %s methods are supported",
                        httpMethod,
                        Arrays.toString(HttpMethod.values())));
        }
        ((AppiumCommandExecutor) getCommandExecutor()).defineCommand(methodName, commandInfo);
    }

    @Override
    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

    @Override
    public Response execute(String command) {
        return super.execute(command, Collections.emptyMap());
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) {
        // TODO: Eventually we should not override this method.
        // TODO: Although, we have a legacy burden,
        // TODO: so it's impossible to do it the other way as of Oct 29 2022.
        // TODO: See https://github.com/SeleniumHQ/selenium/issues/11168
        return super.getScreenshotAs(new OutputType<X>() {
            @Override
            public X convertFromBase64Png(String base64Png) {
                String rfc4648Base64 = base64Png.replaceAll("\\r?\\n", "");
                return outputType.convertFromBase64Png(rfc4648Base64);
            }

            @Override
            public X convertFromPngBytes(byte[] png) {
                return outputType.convertFromPngBytes(png);
            }
        });
    }

    /**
     * Gets the HTTP client used by the command executor.
     *
     * @return the HTTP client
     */
    protected HttpClient getHttpClient() {
        return ((AppiumCommandExecutor) getCommandExecutor()).getClient();
    }

    @Override
    protected void startSession(Capabilities requestCapabilities) {
        var response = Optional.ofNullable(
                execute(DriverCommand.NEW_SESSION(singleton(requestCapabilities)))
        ).orElseThrow(() -> new SessionNotCreatedException(
                "The underlying command executor returned a null response."
        ));

        var rawResponseCapabilities = Optional.ofNullable(response.getValue())
                .map(value -> {
                    if (!(value instanceof Map)) {
                        throw new SessionNotCreatedException(String.format(
                                "The underlying command executor returned a response "
                                        + "with a non well formed payload: %s", response)
                        );
                    }
                    //noinspection unchecked
                    return (Map<String, Object>) value;
                })
                .orElseThrow(() -> new SessionNotCreatedException(
                        "The underlying command executor returned a response without payload: " + response)
                );

        // TODO: remove this workaround for Selenium API enforcing some legacy capability values in major version
        rawResponseCapabilities.remove("platform");
        if (rawResponseCapabilities.containsKey(BROWSER_NAME)
                && isNullOrEmpty((String) rawResponseCapabilities.get(BROWSER_NAME))) {
            rawResponseCapabilities.remove(BROWSER_NAME);
        }
        this.capabilities = new BaseOptions<>(rawResponseCapabilities);
        setSessionId(response.getSessionId());
    }

    /**
     * Changes platform name if it is not set and returns merged capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param defaultName          a platformName value which has to be set up
     * @return {@link Capabilities} with changed platform name value or the original capabilities
     */
    protected static Capabilities ensurePlatformName(
            Capabilities originalCapabilities, String defaultName) {
        return originalCapabilities.getPlatformName() == null
                ? originalCapabilities.merge(new ImmutableCapabilities(PLATFORM_NAME, defaultName))
                : originalCapabilities;
    }

    /**
     * Changes automation name if it is not set and returns merged capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param defaultName          a platformName value which has to be set up
     * @return {@link Capabilities} with changed mobile automation name value or the original capabilities
     */
    protected static Capabilities ensureAutomationName(
            Capabilities originalCapabilities, String defaultName) {
        String currentAutomationName = CapabilityHelpers.getCapability(
                originalCapabilities, AUTOMATION_NAME_OPTION, String.class);
        if (isNullOrEmpty(currentAutomationName)) {
            String capabilityName = originalCapabilities.getCapabilityNames()
                    .contains(AUTOMATION_NAME_OPTION) ? AUTOMATION_NAME_OPTION : APPIUM_PREFIX + AUTOMATION_NAME_OPTION;
            return originalCapabilities.merge(new ImmutableCapabilities(capabilityName, defaultName));
        }
        return originalCapabilities;
    }

    /**
     * Changes platform and automation names if they are not set
     * and returns merged capabilities.
     *
     * @param originalCapabilities  the given {@link Capabilities}.
     * @param defaultPlatformName   a platformName value which has to be set up
     * @param defaultAutomationName The default automation name to set up for this class
     * @return {@link Capabilities} with changed platform/automation name value or the original capabilities
     */
    protected static Capabilities ensurePlatformAndAutomationNames(
            Capabilities originalCapabilities, String defaultPlatformName, String defaultAutomationName) {
        Capabilities capsWithPlatformFixed = ensurePlatformName(originalCapabilities, defaultPlatformName);
        return ensureAutomationName(capsWithPlatformFixed, defaultAutomationName);
    }
}
