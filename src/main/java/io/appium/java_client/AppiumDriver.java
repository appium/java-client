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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.internal.SessionHelpers;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumW3CHttpCommandCodec;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsWebSocketUrlOption;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.BiDiException;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static java.util.Collections.singleton;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

/**
 * Default Appium driver implementation.
 */
public class AppiumDriver extends RemoteWebDriver implements
        ExecutesMethod,
        ComparesImages,
        ExecutesDriverScript,
        LogsEvents,
        HasBrowserCheck,
        CanRememberExtensionPresence,
        HasSettings,
        HasBiDi {

    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler(new ErrorCodesMobile(), true);
    // frequently used command parameters
    @Getter
    private final URL remoteAddress;
    @Deprecated(forRemoval = true)
    protected final RemoteLocationContext locationContext;
    private final ExecuteMethod executeMethod;
    private final Set<String> absentExtensionNames = new HashSet<>();
    private URI biDiUri;
    private BiDi biDi;
    private boolean wasBiDiRequested = false;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor     is an instance of {@link HttpCommandExecutor}
     *                     or class that extends it. Default commands or another vendor-specific
     *                     commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AppiumDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
        this.executeMethod = new AppiumExecutionMethod(this);
        locationContext = new RemoteLocationContext(executeMethod);
        super.setErrorHandler(ERROR_HANDLER);
        this.remoteAddress = executor.getAddressOfRemoteServer();
    }

    public AppiumDriver(AppiumClientConfig clientConfig, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, clientConfig), capabilities);
    }

    public AppiumDriver(URL remoteAddress, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress),
                capabilities);
    }

    public AppiumDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress,
                httpClientFactory), capabilities);
    }

    public AppiumDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service),
                capabilities);
    }

    public AppiumDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service, httpClientFactory),
                capabilities);
    }

    public AppiumDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        this(builder.build(), capabilities);
    }

    public AppiumDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                        Capabilities capabilities) {
        this(builder.build(), httpClientFactory, capabilities);
    }

    public AppiumDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        this(AppiumDriverLocalService.buildDefaultService(), httpClientFactory,
                capabilities);
    }

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
        executor.setResponseCodec(new W3CHttpResponseCodec());
        setCommandExecutor(executor);
        this.executeMethod = new AppiumExecutionMethod(this);
        this.locationContext = new RemoteLocationContext(executeMethod);
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
        CommandInfo commandInfo;
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

    @Override
    public AppiumDriver assertExtensionExists(String extName) {
        if (absentExtensionNames.contains(extName)) {
            throw new UnsupportedCommandException();
        }
        return this;
    }

    @Override
    public AppiumDriver markExtensionAbsence(String extName) {
        absentExtensionNames.add(extName);
        return this;
    }

    @Override
    public Optional<BiDi> maybeGetBiDi() {
        return Optional.ofNullable(this.biDi);
    }

    @Override
    @Nonnull
    public BiDi getBiDi() {
        var webSocketUrl = ((BaseOptions<?>) this.capabilities).getWebSocketUrl().orElseThrow(
                () -> {
                    var suffix = wasBiDiRequested
                            ? "Do both the server and the driver declare BiDi support?"
                            : String.format("Did you set %s to true?", SupportsWebSocketUrlOption.WEB_SOCKET_URL);
                    return new BiDiException(String.format(
                            "BiDi is not enabled for this driver session. %s", suffix
                    ));
                }
        );
        if (this.biDiUri == null) {
            throw new BiDiException(
                    String.format(
                            "BiDi is not enabled for this driver session. "
                                    + "Is the %s '%s' received from the create session response valid?",
                            SupportsWebSocketUrlOption.WEB_SOCKET_URL, webSocketUrl
                    )
            );
        }
        if (this.biDi == null) {
            // This should not happen
            throw new IllegalStateException();
        }
        return this.biDi;
    }

    protected HttpClient getHttpClient() {
        return ((HttpCommandExecutor) getCommandExecutor()).client;
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
        if (rawResponseCapabilities.containsKey(CapabilityType.BROWSER_NAME)
                && isNullOrEmpty((String) rawResponseCapabilities.get(CapabilityType.BROWSER_NAME))) {
            rawResponseCapabilities.remove(CapabilityType.BROWSER_NAME);
        }
        this.capabilities = new BaseOptions<>(rawResponseCapabilities);
        this.wasBiDiRequested = Boolean.TRUE.equals(
                requestCapabilities.getCapability(SupportsWebSocketUrlOption.WEB_SOCKET_URL)
        );
        if (wasBiDiRequested) {
            this.initBiDi((BaseOptions<?>) capabilities);
        }
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

    private void initBiDi(BaseOptions<?> responseCaps) {
        var webSocketUrl = responseCaps.getWebSocketUrl();
        if (webSocketUrl.isEmpty()) {
            return;
        }
        URISyntaxException uriSyntaxError = null;
        try {
            this.biDiUri = new URI(String.valueOf(webSocketUrl.get()));
        } catch (URISyntaxException e) {
            uriSyntaxError = e;
        }
        if (uriSyntaxError != null || this.biDiUri.getScheme() == null) {
            var message = String.format(
                    "BiDi cannot be enabled for this driver session. "
                            + "Is the %s '%s' received from the create session response valid?",
                    SupportsWebSocketUrlOption.WEB_SOCKET_URL, webSocketUrl.get()
            );
            if (uriSyntaxError == null) {
                throw new BiDiException(message);
            }
            throw new BiDiException(message, uriSyntaxError);
        }
        var executor = getCommandExecutor();
        final HttpClient wsClient;
        if (executor instanceof AppiumCommandExecutor) {
            var wsConfig = ((AppiumCommandExecutor) executor).getAppiumClientConfig().baseUri(biDiUri);
            wsClient = ((AppiumCommandExecutor) executor).getHttpClientFactory().createClient(wsConfig);
        } else {
            var wsConfig = AppiumClientConfig.defaultConfig().baseUri(biDiUri);
            wsClient = HttpClient.Factory.createDefault().createClient(wsConfig);
        }
        var biDiConnection = new org.openqa.selenium.bidi.Connection(wsClient, biDiUri.toString());
        this.biDi = new BiDi(biDiConnection);
    }
}
