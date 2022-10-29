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

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_NAME;
import static org.apache.commons.lang3.StringUtils.isBlank;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumNewSessionCommandPayload;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Default Appium driver implementation.
 */
public class AppiumDriver extends RemoteWebDriver implements
        ExecutesMethod,
        ComparesImages,
        ExecutesDriverScript,
        LogsEvents,
        HasBrowserCheck,
        HasSettings {

    private static final ErrorHandler errorHandler = new ErrorHandler(new ErrorCodesMobile(), true);
    // frequently used command parameters
    private final URL remoteAddress;
    protected final RemoteLocationContext locationContext;
    private final ExecuteMethod executeMethod;

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
        super.setErrorHandler(errorHandler);
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
     * Changes platform name if it is not set and returns merged capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param defaultName          a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                             to be set up
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
     * @param defaultName          a {@link MobileCapabilityType#AUTOMATION_NAME} value which has
     *                             to be set up
     * @return {@link Capabilities} with changed mobile automation name value or the original capabilities
     */
    protected static Capabilities ensureAutomationName(
            Capabilities originalCapabilities, String defaultName) {
        String currentAutomationName = CapabilityHelpers.getCapability(
                originalCapabilities, AUTOMATION_NAME, String.class);
        if (isBlank(currentAutomationName)) {
            String capabilityName = originalCapabilities.getCapabilityNames()
                    .contains(AUTOMATION_NAME) ? AUTOMATION_NAME : APPIUM_PREFIX + AUTOMATION_NAME;
            return originalCapabilities.merge(new ImmutableCapabilities(capabilityName, defaultName));
        }
        return originalCapabilities;
    }

    /**
     * Changes platform and automation names if they are not set
     * and returns merged capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param defaultPlatformName  a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                             to be set up
     * @param defaultAutomationName The default automation name to set up for this class
     * @return {@link Capabilities} with changed platform/automation name value or the original capabilities
     */
    protected static Capabilities ensurePlatformAndAutomationNames(
            Capabilities originalCapabilities, String defaultPlatformName, String defaultAutomationName) {
        Capabilities capsWithPlatformFixed = ensurePlatformName(originalCapabilities, defaultPlatformName);
        return ensureAutomationName(capsWithPlatformFixed, defaultAutomationName);
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
        switch (httpMethod) {
            case GET:
                MobileCommand.commandRepository.put(methodName, MobileCommand.getC(url));
                break;
            case POST:
                MobileCommand.commandRepository.put(methodName, MobileCommand.postC(url));
                break;
            case DELETE:
                MobileCommand.commandRepository.put(methodName, MobileCommand.deleteC(url));
                break;
            default:
                throw new WebDriverException(String.format("Unsupported HTTP Method: %s. Only %s methods are supported",
                        httpMethod,
                        Arrays.toString(HttpMethod.values())));
        }
        ((AppiumCommandExecutor) getCommandExecutor()).refreshAdditionalCommands();
    }

    public URL getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    protected void startSession(Capabilities capabilities) {
        Response response = execute(new AppiumNewSessionCommandPayload(capabilities));
        if (response == null) {
            throw new SessionNotCreatedException(
                    "The underlying command executor returned a null response.");
        }

        Object responseValue = response.getValue();
        if (responseValue == null) {
            throw new SessionNotCreatedException(
                    "The underlying command executor returned a response without payload: "
                            + response);
        }
        if (!(responseValue instanceof Map)) {
            throw new SessionNotCreatedException(
                    "The underlying command executor returned a response with a non well formed payload: "
                            + response);
        }

        @SuppressWarnings("unchecked") Map<String, Object> rawCapabilities = (Map<String, Object>) responseValue;
        // A workaround for Selenium API enforcing some legacy capability values
        rawCapabilities.remove(CapabilityType.PLATFORM);
        if (rawCapabilities.containsKey(CapabilityType.BROWSER_NAME)
                && isBlank((String) rawCapabilities.get(CapabilityType.BROWSER_NAME))) {
            rawCapabilities.remove(CapabilityType.BROWSER_NAME);
        }
        MutableCapabilities returnedCapabilities = new BaseOptions<>(rawCapabilities);
        try {
            Field capsField = RemoteWebDriver.class.getDeclaredField("capabilities");
            capsField.setAccessible(true);
            capsField.set(this, returnedCapabilities);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
        setSessionId(response.getSessionId());
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
                String rfc4648Base64 = base64Png.replaceAll("\r?\n", "");
                return outputType.convertFromBase64Png(rfc4648Base64);
            }

            @Override
            public X convertFromPngBytes(byte[] png) {
                return outputType.convertFromPngBytes(png);
            }
        });
    }
}
