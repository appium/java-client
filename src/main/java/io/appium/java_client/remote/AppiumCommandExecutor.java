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

package io.appium.java_client.remote;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumCommandInfo;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpClient.Factory;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.internal.DirectConnectUrlSafety;
import io.appium.java_client.internal.webdriver.ProtocolHandshake;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;

import java.io.Closeable;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * Executes the commands of a driver by sending them to an Appium server over HTTP.
 * Adapted from Selenium's {@code HttpCommandExecutor} (Apache License 2.0).
 */
@NullMarked
public class AppiumCommandExecutor implements CommandExecutor, Closeable {
    private static final String JSON_UTF_8 = "application/json; charset=utf-8";

    private final Optional<AppiumDriverLocalService> serviceOptional;
    @Getter
    private final AppiumClientConfig appiumClientConfig;
    private final @Nullable URL remoteServer;
    private final Map<String, AppiumCommandInfo> additionalCommands;
    @Getter
    private final Factory httpClientFactory;
    private HttpClient client;
    private @Nullable CommandCodec commandCodec;
    private @Nullable ResponseCodec responseCodec;

    /**
     * Create an AppiumCommandExecutor instance.
     *
     * @param additionalCommands is the map of Appium commands
     * @param service take a look at {@link AppiumDriverLocalService}
     * @param httpClientFactory take a look at {@link Factory}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AppiumCommandExecutor(
            Map<String, AppiumCommandInfo> additionalCommands,
            @Nullable AppiumDriverLocalService service,
            @Nullable Factory httpClientFactory,
            AppiumClientConfig appiumClientConfig) {
        this.additionalCommands = new HashMap<>(requireNonNull(additionalCommands, "Additional commands"));
        this.appiumClientConfig = requireNonNull(appiumClientConfig, "HTTP client configuration");
        this.httpClientFactory = ofNullable(httpClientFactory).orElseGet(Factory::createDefault);
        this.client = this.httpClientFactory.createClient(appiumClientConfig);
        this.remoteServer = appiumClientConfig.baseUrl();
        this.serviceOptional = ofNullable(service);
    }

    /**
     * Creates an executor that talks to a local Appium service.
     *
     * @param additionalCommands the map of Appium commands
     * @param service the local Appium service to send the commands to
     * @param httpClientFactory the HTTP client factory, or {@code null} for the default one
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
                                 AppiumDriverLocalService service,
                                 @Nullable Factory httpClientFactory) {
        this(additionalCommands, requireNonNull(service), httpClientFactory,
                AppiumClientConfig.defaultConfig().baseUrl(requireNonNull(service).getUrl()));
    }

    /**
     * Creates an executor that talks to the given server address.
     *
     * @param additionalCommands the map of Appium commands
     * @param addressOfRemoteServer the address of the Appium server
     * @param httpClientFactory the HTTP client factory, or {@code null} for the default one
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands, URL addressOfRemoteServer,
                                 @Nullable Factory httpClientFactory) {
        this(additionalCommands, null, httpClientFactory,
                AppiumClientConfig.defaultConfig().baseUrl(requireNonNull(addressOfRemoteServer)));
    }

    /**
     * Creates an executor using the given client configuration.
     *
     * @param additionalCommands the map of Appium commands
     * @param appiumClientConfig the HTTP client configuration
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
                                 AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, null, appiumClientConfig);
    }

    /**
     * Creates an executor that talks to the given server address.
     *
     * @param additionalCommands the map of Appium commands
     * @param addressOfRemoteServer the address of the Appium server
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands, URL addressOfRemoteServer) {
        this(additionalCommands, null, Factory.createDefault(),
                AppiumClientConfig.defaultConfig().baseUrl(requireNonNull(addressOfRemoteServer)));
    }

    /**
     * Creates an executor that talks to the given server address using the given client configuration.
     *
     * @param additionalCommands the map of Appium commands
     * @param addressOfRemoteServer the address of the Appium server
     * @param appiumClientConfig the HTTP client configuration
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands, URL addressOfRemoteServer,
                                 AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, Factory.createDefault(),
                appiumClientConfig.baseUrl(requireNonNull(addressOfRemoteServer)));
    }

    /**
     * Creates an executor that talks to a local Appium service.
     *
     * @param additionalCommands the map of Appium commands
     * @param service the local Appium service to send the commands to
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
                                 AppiumDriverLocalService service) {
        this(additionalCommands, service, Factory.createDefault(),
                AppiumClientConfig.defaultConfig().baseUrl(service.getUrl()));
    }

    /**
     * Creates an executor that talks to a local Appium service using the given client configuration.
     *
     * @param additionalCommands the map of Appium commands
     * @param service the local Appium service to send the commands to
     * @param appiumClientConfig the HTTP client configuration
     */
    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
                                 AppiumDriverLocalService service, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, service, Factory.createDefault(), appiumClientConfig);
    }

    /**
     * Returns the additional (non-standard) commands known to this executor.
     *
     * @return the unmodifiable map of the additional commands
     */
    public Map<String, AppiumCommandInfo> getAdditionalCommands() {
        return Collections.unmodifiableMap(additionalCommands);
    }

    /**
     * The address of the server the commands are sent to.
     *
     * @return the base URL of the client configuration
     */
    @Nullable
    public URL getAddressOfRemoteServer() {
        return remoteServer;
    }

    /**
     * Returns the codec used to encode commands.
     *
     * @return the command codec, or {@code null} if the session has not been created yet
     */
    @Nullable
    protected CommandCodec getCommandCodec() {
        return this.commandCodec;
    }

    /**
     * Sets the codec used to encode commands.
     *
     * @param newCodec the command codec
     */
    public void setCommandCodec(CommandCodec newCodec) {
        this.commandCodec = newCodec;
    }

    /**
     * Sets the codec used to decode responses.
     *
     * @param codec the response codec
     */
    public void setResponseCodec(ResponseCodec codec) {
        this.responseCodec = codec;
    }

    /**
     * Returns the HTTP client used to send the commands.
     *
     * @return the HTTP client
     */
    public HttpClient getClient() {
        return this.client;
    }

    /**
     * Override the http client with a new http client instance with the given URL.
     * It uses the same http client factory and client config for the new http client instance
     * if the constructor got them.
     *
     * @param serverUrl URL to use for subsequent HTTP requests. Before switching clients, the host is
     *                  resolved and the override is refused if any resolved address is loopback,
     *                  link-local (including IPv4 link-local such as metadata-service ranges),
     *                  unspecified ({@code 0.0.0.0} / {@code ::}), or multicast. Private (RFC 1918),
     *                  carrier-grade NAT ({@code 100.64.0.0/10}), IPv6 unique-local ({@code fc00::/7}),
     *                  and other non-special addresses are not rejected by this check.
     */
    protected void overrideServerUrl(URL serverUrl) {
        DirectConnectUrlSafety.requireSafeOverrideTarget(serverUrl);
        HttpClient newClient = getHttpClientFactory().createClient(appiumClientConfig.baseUrl(serverUrl));
        HttpClient oldClient = this.client;
        this.client = newClient;
        oldClient.close();
    }

    private Response createSession(Command command) {
        if (getCommandCodec() != null) {
            throw new SessionNotCreatedException("Session already exists");
        }

        final var result = new ProtocolHandshake().createSession(getClient(), command);
        setCommandCodec(new AppiumW3CHttpCommandCodec());
        refreshAdditionalCommands();
        setResponseCodec(new AppiumW3CHttpResponseCodec());
        Response response = result.createResponse();
        if (appiumClientConfig.isDirectConnectEnabled()) {
            setDirectConnect(response);
        }

        return response;
    }

    /** Re-registers the additional commands in the current command codec. */
    public void refreshAdditionalCommands() {
        getAdditionalCommands().forEach(this::defineCommand);
    }

    /**
     * Defines a command that is not part of the standard ones.
     *
     * @param commandName the command name
     * @param info the HTTP method and the URL template of the command
     */
    public void defineCommand(String commandName, AppiumCommandInfo info) {
        requireNonNull(commandName, "Command name");
        requireNonNull(info, "Command info");
        requireNonNull(commandCodec, "The session has not been started yet")
                .defineCommand(commandName, info.getMethod(), info.getUrl());
    }

    @SuppressWarnings("unchecked")
    private void setDirectConnect(Response response) throws SessionNotCreatedException {
        Map<String, ?> responseValue = (Map<String, ?>) response.getValue();

        DirectConnect directConnect = new DirectConnect(responseValue);

        if (!directConnect.isValid()) {
            return;
        }

        if (!directConnect.getProtocol().equals("https")) {
            throw new SessionNotCreatedException(
                    String.format("The given protocol '%s' as the direct connection url returned by "
                            + "the remote server is not accurate. Only 'https' is supported.",
                            directConnect.getProtocol()));
        }

        URL newUrl;
        try {
            newUrl = directConnect.getUrl();
        } catch (MalformedURLException e) {
            throw new SessionNotCreatedException(e.getMessage());
        }

        overrideServerUrl(newUrl);
    }

    @Override
    public Response execute(Command command) throws WebDriverException {
        if (DriverCommand.NEW_SESSION.equals(command.getName())) {
            serviceOptional.ifPresent(AppiumDriverLocalService::start);
        }

        try {
            return DriverCommand.NEW_SESSION.equals(command.getName())
                    ? createSession(command) : executeInSession(command);
        } catch (Throwable t) {
            Throwable rootCause = getRootCause(t);
            if (rootCause instanceof ConnectException
                    && String.valueOf(rootCause.getMessage()).contains("Connection refused")) {
                throw serviceOptional.map(service -> {
                    if (service.isRunning()) {
                        return new WebDriverException("The session is closed!", rootCause);
                    }

                    return new WebDriverException("The appium server has accidentally died!", rootCause);
                }).orElseGet(() -> new WebDriverException(rootCause.getMessage(), rootCause));
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw new WebDriverException(t);
        } finally {
            if (DriverCommand.QUIT.equals(command.getName())) {
                serviceOptional.ifPresent(AppiumDriverLocalService::stop);
            }
        }
    }

    private Response executeInSession(Command command) {
        if (command.getSessionId() == null) {
            if (DriverCommand.QUIT.equals(command.getName())) {
                return new Response();
            }
            throw new NoSuchSessionException("Session ID is null. Using WebDriver after calling quit()?");
        }

        if (commandCodec == null || responseCodec == null) {
            throw new WebDriverException("No command or response codec has been defined. Unable to proceed");
        }

        HttpRequest httpRequest = commandCodec.encode(command);

        // Ensure that the required headers are set
        if (httpRequest.getHeader("Content-Type") == null) {
            httpRequest.addHeader("Content-Type", JSON_UTF_8);
        }

        try {
            HttpResponse httpResponse = client.execute(httpRequest);

            Response response = responseCodec.decode(httpResponse);
            if (response.getSessionId() == null) {
                // Spam in the session id from the request
                response.setSessionId(command.getSessionId().toString());
            }
            if (DriverCommand.QUIT.equals(command.getName())) {
                client.close();
            }
            return response;
        } catch (UnsupportedCommandException e) {
            if (e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new UnsupportedOperationException(
                        "No information from server. Command name was: " + command.getName(), e.getCause());
            }
            throw e;
        }
    }

    private static Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }

    @Override
    public void close() {
        client.close();
    }
}
