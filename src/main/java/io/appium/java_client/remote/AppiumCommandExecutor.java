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

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AppiumCommandExecutor extends HttpCommandExecutor {
    // https://github.com/appium/appium-base-driver/pull/400
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";
    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofMinutes(10);

    private final Optional<DriverService> serviceOptional;

    private final AppiumClientConfig appiumClientConfig;

    private AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                  URL addressOfRemoteServer,
                                  HttpClient.Factory httpClientFactory,
                                  ClientConfig clientConfig,
                                  AppiumClientConfig appiumClientConfig) {
        super(additionalCommands,
                ofNullable(clientConfig).orElse(
                        ClientConfig.defaultConfig()
                                .baseUrl(Require.nonNull("Server URL", ofNullable(service)
                                        .map(DriverService::getUrl)
                                        .orElse(addressOfRemoteServer)))
                                .readTimeout(DEFAULT_READ_TIMEOUT)
                ),
                ofNullable(httpClientFactory).orElseGet(HttpCommandExecutor::getDefaultClientFactory)
        );
        serviceOptional = ofNullable(service);
        this.appiumClientConfig = appiumClientConfig;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory) {
        this(additionalCommands, checkNotNull(service), null, httpClientFactory, null, AppiumClientConfig.defaultConfig());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, checkNotNull(service), null, httpClientFactory, null, appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, HttpClient.Factory httpClientFactory) {
        this(additionalCommands, null, checkNotNull(addressOfRemoteServer), httpClientFactory, null, AppiumClientConfig.defaultConfig());
    }
    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, HttpClient.Factory httpClientFactory, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, checkNotNull(addressOfRemoteServer), httpClientFactory, null, appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, ClientConfig clientConfig) {
        this(additionalCommands, null, checkNotNull(clientConfig.baseUrl()), null, clientConfig, AppiumClientConfig.defaultConfig());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, ClientConfig clientConfig, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, checkNotNull(clientConfig.baseUrl()), null, clientConfig, appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer) {
        this(additionalCommands, addressOfRemoteServer, HttpClient.Factory.createDefault(), AppiumClientConfig.defaultConfig());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, addressOfRemoteServer, HttpClient.Factory.createDefault(), appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service) {
        this(additionalCommands, service, HttpClient.Factory.createDefault(), AppiumClientConfig.defaultConfig());
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

    protected void setClientWithURL(URL serverUrl) {
        HttpClient.Factory httpClientFactory = getPrivateFieldValue(HttpCommandExecutor.class, "httpClientFactory", HttpClient.Factory.class);
        setPrivateFieldValue(HttpCommandExecutor.class, "client", httpClientFactory.createClient(serverUrl));
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

        setDirectConnect(response);

        return response;
    }

    public void refreshAdditionalCommands() {
        getAdditionalCommands().forEach(this::defineCommand);
    }

    @SuppressWarnings("unchecked")
    private void setDirectConnect(Response response) throws MalformedURLException {
        if (!this.appiumClientConfig.isDirectConnectEnabled()) {
            return;
        }

        Map<String, ?> responseValue = (Map<String, ?>) response.getValue();

        String directConnectProtocol = getDirectConnectValue(responseValue, "directConnectProtocol");
        String directConnectPath = getDirectConnectValue(responseValue, "directConnectPath");
        String directConnectHost = getDirectConnectValue(responseValue, "directConnectHost");
        String directConnectPort = getDirectConnectValue(responseValue, "directConnectPort");

        if (directConnectProtocol == null || directConnectHost == null || directConnectPath == null || directConnectPort == null) {
            return;
        }
        setClientWithURL(new URL(directConnectProtocol + "://" + directConnectHost + ":" + directConnectPort + directConnectPath));
    }

    private String getDirectConnectValue(Map<String, ?> responseValue, String key) {
        String directConnectPath = String.valueOf(responseValue.get("appium:" + key));
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
