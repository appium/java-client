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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static java.util.Optional.ofNullable;
import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;

import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import io.appium.java_client.AppiumClientConfig;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandCodec;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.Dialect;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.ProtocolHandshake;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.ResponseCodec;
import org.openqa.selenium.remote.codec.w3c.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.service.DriverService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AppiumCommandExecutor extends HttpCommandExecutor {
    // https://github.com/appium/appium-base-driver/pull/400
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    private final Optional<DriverService> serviceOptional;

    private final HttpClient.Factory httpClientFactory;

    private final AppiumClientConfig appiumClientConfig;

    private static final String DIRECT_CONNECT_PROTOCOL = "directConnectProtocol";
    private static final String DIRECT_CONNECT_PATH = "directConnectPath";
    private static final String DIRECT_CONNECT_HOST = "directConnectHost";
    private static final String DIRECT_CONNECT_PORT = "directConnectPort";

    /**
     * Create an AppiumCommandExecutor instance.
     *
     * @param additionalCommands is the map of Appium commands
     * @param service take a look at {@link DriverService}
     * @param addressOfRemoteServer is the address of remotely/locally started Appium server
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AppiumCommandExecutor(
            @Nonnull Map<String, CommandInfo> additionalCommands,
            @Nullable DriverService service,
            @Nonnull URL addressOfRemoteServer,
            @Nullable HttpClient.Factory httpClientFactory,
            @Nullable AppiumClientConfig appiumClientConfig) {
        super(additionalCommands,
                ofNullable(appiumClientConfig).orElse(
                        AppiumClientConfig.defaultConfig()
                                .baseUrl(Require.nonNull("Server URL", ofNullable(service)
                                        .map(DriverService::getUrl)
                                        .orElse(addressOfRemoteServer)))
                ),
                ofNullable(httpClientFactory).orElseGet(HttpCommandExecutor::getDefaultClientFactory)
        );
        serviceOptional = ofNullable(service);

        this.httpClientFactory = httpClientFactory;
        this.appiumClientConfig = appiumClientConfig;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory) {
        this(additionalCommands, checkNotNull(service),  checkNotNull(service).getUrl(), httpClientFactory, null);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, checkNotNull(service), checkNotNull(service).getUrl(), httpClientFactory,
                appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, HttpClient.Factory httpClientFactory) {
        this(additionalCommands, null, checkNotNull(addressOfRemoteServer), httpClientFactory, null);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer,
                                 HttpClient.Factory httpClientFactory,
                                 AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, checkNotNull(addressOfRemoteServer), httpClientFactory,
                appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, checkNotNull(appiumClientConfig.baseUrl()),
                null, appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer) {
        this(additionalCommands, checkNotNull(addressOfRemoteServer), HttpClient.Factory.createDefault(), null);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, checkNotNull(addressOfRemoteServer),
                HttpClient.Factory.createDefault(), appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service) {
        this(additionalCommands, service, HttpClient.Factory.createDefault(), null);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, service, HttpClient.Factory.createDefault(), appiumClientConfig);
    }

    @SuppressWarnings("SameParameterValue")
    protected <B> B getPrivateFieldValue(
            Class<? extends CommandExecutor> cls, String fieldName, Class<B> fieldType) {
        try {
            final Field f = cls.getDeclaredField(fieldName);
            f.setAccessible(true);
            return fieldType.cast(f.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void setPrivateFieldValue(
            Class<? extends CommandExecutor> cls, String fieldName, Object newValue) {
        try {
            final Field f = cls.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(this, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    protected Map<String, CommandInfo> getAdditionalCommands() {
        //noinspection unchecked
        return getPrivateFieldValue(HttpCommandExecutor.class, "additionalCommands", Map.class);
    }

    protected CommandCodec<HttpRequest> getCommandCodec() {
        //noinspection unchecked
        return getPrivateFieldValue(HttpCommandExecutor.class, "commandCodec", CommandCodec.class);
    }

    protected void setCommandCodec(CommandCodec<HttpRequest> newCodec) {
        setPrivateFieldValue(HttpCommandExecutor.class, "commandCodec", newCodec);
    }

    protected void setResponseCodec(ResponseCodec<HttpResponse> codec) {
        setPrivateFieldValue(HttpCommandExecutor.class, "responseCodec", codec);
    }

    protected HttpClient getClient() {
        return getPrivateFieldValue(HttpCommandExecutor.class, "client", HttpClient.class);
    }

    /**
     * Override the http client in the HttpCommandExecutor class with a new http client instance with the given URL.
     * Use the same http client factory and client config if the constructor got them.
     *
     * @param serverUrl to set the URL as the new client's base url.
     */
    protected void overrideServerUrl(URL serverUrl) {
        setPrivateFieldValue(HttpCommandExecutor.class, "client",
                ofNullable(this.httpClientFactory).orElseGet(HttpCommandExecutor::getDefaultClientFactory)
                                .createClient(this.appiumClientConfig.baseUrl(serverUrl)));
    }

    private Response createSession(Command command) throws IOException {
        if (getCommandCodec() != null) {
            throw new SessionNotCreatedException("Session already exists");
        }

        ProtocolHandshake.Result result = new AppiumProtocolHandshake().createSession(
                getClient().with((httpHandler) -> (req) -> {
                    req.setHeader(IDEMPOTENCY_KEY_HEADER, UUID.randomUUID().toString().toLowerCase());
                    return httpHandler.execute(req);
                }), command
        );
        Dialect dialect = result.getDialect();
        if (!(dialect.getCommandCodec() instanceof W3CHttpCommandCodec)) {
            throw new SessionNotCreatedException("Only W3C sessions are supported. "
                    + "Please make sure your server is up to date.");
        }
        setCommandCodec(new AppiumW3CHttpCommandCodec());
        refreshAdditionalCommands();
        setResponseCodec(dialect.getResponseCodec());
        Response response = result.createResponse();
        if (this.appiumClientConfig.isDirectConnectEnabled()) {
            setDirectConnect(response);
        }
        return response;
    }

    public void refreshAdditionalCommands() {
        getAdditionalCommands().forEach(this::defineCommand);
    }

    @SuppressWarnings("unchecked")
    private void setDirectConnect(Response response) throws SessionNotCreatedException {
        Map<String, ?> responseValue = (Map<String, ?>) response.getValue();

        String directConnectProtocol = getDirectConnectValue(responseValue, DIRECT_CONNECT_PROTOCOL);
        String directConnectPath = getDirectConnectValue(responseValue, DIRECT_CONNECT_PATH);
        String directConnectHost = getDirectConnectValue(responseValue, DIRECT_CONNECT_HOST);
        String directConnectPort = getDirectConnectValue(responseValue, DIRECT_CONNECT_PORT);

        if (directConnectProtocol == null || directConnectHost == null
                || directConnectPath == null || directConnectPort == null) {
            return;
        }

        if (!directConnectProtocol.equals("https")) {
            throw new SessionNotCreatedException(
                    String.format("The protocol must be https. %s was given.", directConnectProtocol));
        }

        URL newUrl;
        String newUrlCandidate = String.format("%s://%s:%s%s",
                directConnectProtocol, directConnectHost, directConnectPort, directConnectPath);
        try {
            newUrl = new URL(newUrlCandidate);
        } catch (MalformedURLException e) {
            // TODO: tweak the description
            throw new SessionNotCreatedException(String.format("directConnect generated invalid URL as %s",
                    newUrlCandidate));
        }

        overrideServerUrl(newUrl);
    }

    @Nullable
    private String getDirectConnectValue(Map<String, ?> responseValue, String key) {
        String directConnectPath = String.valueOf(responseValue.get(APPIUM_PREFIX + key));
        return directConnectPath == null ? String.valueOf(responseValue.get(key)) : directConnectPath;
    }

    @Override
    public Response execute(Command command) throws WebDriverException {
        if (DriverCommand.NEW_SESSION.equals(command.getName())) {
            serviceOptional.ifPresent(driverService -> {
                try {
                    driverService.start();
                } catch (IOException e) {
                    throw new WebDriverException(e.getMessage(), e);
                }
            });
        }

        try {
            return NEW_SESSION.equals(command.getName()) ? createSession(command) : super.execute(command);
        } catch (Throwable t) {
            Throwable rootCause = Throwables.getRootCause(t);
            if (rootCause instanceof ConnectException
                    && rootCause.getMessage().contains("Connection refused")) {
                throw serviceOptional.map(service -> {
                    if (service.isRunning()) {
                        return new WebDriverException("The session is closed!", rootCause);
                    }

                    return new WebDriverException("The appium server has accidentally died!", rootCause);
                }).orElseGet((Supplier<WebDriverException>) () ->
                        new WebDriverException(rootCause.getMessage(), rootCause));
            }
            throwIfUnchecked(t);
            throw new WebDriverException(t);
        } finally {
            if (DriverCommand.QUIT.equals(command.getName())) {
                serviceOptional.ifPresent(DriverService::stop);
            }
        }
    }
}
