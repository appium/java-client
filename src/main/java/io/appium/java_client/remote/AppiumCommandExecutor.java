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

import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.net.HttpHeaders;
import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.AppiumUserAgentFilter;
import io.appium.java_client.internal.ReflectionHelpers;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
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
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.service.DriverService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static java.util.Optional.ofNullable;
import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;

public class AppiumCommandExecutor extends HttpCommandExecutor {
    // https://github.com/appium/appium-base-driver/pull/400
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    private final Optional<DriverService> serviceOptional;

    private final HttpClient.Factory httpClientFactory;

    private final AppiumClientConfig appiumClientConfig;

    /**
     * Create an AppiumCommandExecutor instance.
     *
     * @param additionalCommands is the map of Appium commands
     * @param service take a look at {@link DriverService}
     * @param httpClientFactory take a look at {@link HttpClient.Factory}
     * @param appiumClientConfig take a look at {@link AppiumClientConfig}
     */
    public AppiumCommandExecutor(
            @Nonnull Map<String, CommandInfo> additionalCommands,
            @Nullable DriverService service,
            @Nullable HttpClient.Factory httpClientFactory,
            @Nonnull AppiumClientConfig appiumClientConfig) {
        super(additionalCommands,
                appiumClientConfig,
                ofNullable(httpClientFactory).orElseGet(AppiumCommandExecutor::getDefaultClientFactory)
        );
        serviceOptional = ofNullable(service);

        this.httpClientFactory = httpClientFactory;
        this.appiumClientConfig = appiumClientConfig;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory) {
        this(additionalCommands, checkNotNull(service), httpClientFactory,
                AppiumClientConfig.defaultConfig().baseUrl(checkNotNull(service).getUrl()));
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, URL addressOfRemoteServer,
                                 HttpClient.Factory httpClientFactory) {
        this(additionalCommands, null, httpClientFactory,
                AppiumClientConfig.defaultConfig().baseUrl(checkNotNull(addressOfRemoteServer)));
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, null, appiumClientConfig);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, URL addressOfRemoteServer) {
        this(additionalCommands, null, HttpClient.Factory.createDefault(),
                AppiumClientConfig.defaultConfig().baseUrl(checkNotNull(addressOfRemoteServer)));
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, URL addressOfRemoteServer,
                                 AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, null, HttpClient.Factory.createDefault(),
                appiumClientConfig.baseUrl(checkNotNull(addressOfRemoteServer)));
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service) {
        this(additionalCommands, service, HttpClient.Factory.createDefault(),
                AppiumClientConfig.defaultConfig().baseUrl(service.getUrl()));
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service, AppiumClientConfig appiumClientConfig) {
        this(additionalCommands, service, HttpClient.Factory.createDefault(), appiumClientConfig);
    }

    @SuppressWarnings("SameParameterValue")
    protected <B> B getPrivateFieldValue(
            Class<? extends CommandExecutor> cls, String fieldName, Class<B> fieldType) {
        return ReflectionHelpers.getPrivateFieldValue(cls, this, fieldName, fieldType);
    }

    @SuppressWarnings("SameParameterValue")
    protected void setPrivateFieldValue(
            Class<? extends CommandExecutor> cls, String fieldName, Object newValue) {
        ReflectionHelpers.setPrivateFieldValue(cls, this, fieldName, newValue);
    }

    protected Map<String, CommandInfo> getAdditionalCommands() {
        //noinspection unchecked
        return getPrivateFieldValue(HttpCommandExecutor.class, "additionalCommands", Map.class);
    }

    protected CommandCodec<HttpRequest> getCommandCodec() {
        //noinspection unchecked
        return getPrivateFieldValue(HttpCommandExecutor.class, "commandCodec", CommandCodec.class);
    }

    public void setCommandCodec(CommandCodec<HttpRequest> newCodec) {
        setPrivateFieldValue(HttpCommandExecutor.class, "commandCodec", newCodec);
    }

    public void setResponseCodec(ResponseCodec<HttpResponse> codec) {
        setPrivateFieldValue(HttpCommandExecutor.class, "responseCodec", codec);
    }

    protected HttpClient getClient() {
        return getPrivateFieldValue(HttpCommandExecutor.class, "client", HttpClient.class);
    }

    /**
     * Override the http client in the HttpCommandExecutor class with a new http client instance with the given URL.
     * It uses the same http client factory and client config for the new http client instance
     * if the constructor got them.
     * @param serverUrl A url to override.
     */
    protected void overrideServerUrl(URL serverUrl) {
        if (this.appiumClientConfig == null) {
            return;
        }
        setPrivateFieldValue(HttpCommandExecutor.class, "client",
                ofNullable(this.httpClientFactory).orElseGet(AppiumCommandExecutor::getDefaultClientFactory)
                                .createClient(this.appiumClientConfig.baseUrl(serverUrl)));
    }

    private Response createSession(Command command) throws IOException {
        if (getCommandCodec() != null) {
            throw new SessionNotCreatedException("Session already exists");
        }

        ProtocolHandshake.Result result = new AppiumProtocolHandshake().createSession(
                getClient().with((httpHandler) -> (req) -> {
                    req.setHeader(HttpHeaders.USER_AGENT,
                            AppiumUserAgentFilter.buildUserAgent(req.getHeader(HttpHeaders.USER_AGENT)));
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
        if (this.appiumClientConfig != null && this.appiumClientConfig.isDirectConnectEnabled()) {
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
