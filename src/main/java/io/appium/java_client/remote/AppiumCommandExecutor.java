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
import static com.google.common.base.Throwables.getRootCause;
import static com.google.common.base.Throwables.throwIfUnchecked;
import static org.openqa.selenium.remote.DriverCommand.GET_ALL_SESSIONS;
import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;
import static org.openqa.selenium.remote.DriverCommand.QUIT;

import io.appium.java_client.AppiumCommandInfo;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandCodec;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Dialect;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpSessionId;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.ResponseCodec;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.internal.ApacheHttpClient;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

public class AppiumCommandExecutor implements CommandExecutor {

    private final URL remoteServer;
    private final HttpClient client;
    private final Map<String, AppiumCommandInfo> additionalCommands;
    private CommandCodec<HttpRequest> commandCodec;
    private ResponseCodec<HttpResponse> responseCodec;
    private DriverService service;

    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
        URL addressOfRemoteServer, HttpClient.Factory httpClientFactory) {
        checkNotNull(addressOfRemoteServer);
        remoteServer = addressOfRemoteServer;
        this.additionalCommands = additionalCommands;
        this.client = httpClientFactory.createClient(remoteServer);
    }

    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands, DriverService service,
        HttpClient.Factory httpClientFactory) {
        this(additionalCommands, service.getUrl(), httpClientFactory);
        this.service = service;
    }

    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
        URL addressOfRemoteServer) {
        this(additionalCommands, addressOfRemoteServer, new ApacheHttpClient.Factory());
    }

    public AppiumCommandExecutor(Map<String, AppiumCommandInfo> additionalCommands,
        DriverService service) {
        this(additionalCommands, service, new ApacheHttpClient.Factory());
    }

    public URL getAddressOfRemoteServer() {
        return remoteServer;
    }

    private Response doExecute(Command command) throws IOException, WebDriverException {
        if (command.getSessionId() == null) {
            if (QUIT.equals(command.getName())) {
                return new Response();
            }
            if (!GET_ALL_SESSIONS.equals(command.getName())
                    && !NEW_SESSION.equals(command.getName())) {
                throw new NoSuchSessionException(
                        "Session ID is null. Using WebDriver after calling quit()?");
            }
        }

        if (NEW_SESSION.equals(command.getName())) {
            if (commandCodec != null) {
                throw new SessionNotCreatedException("Session already exists");
            }
            AppiumProtocolHandShake handshake = new AppiumProtocolHandShake();
            AppiumProtocolHandShake.Result result = handshake.createSession(client, command);
            Dialect dialect = result.getDialect();
            commandCodec = dialect.getCommandCodec();
            for (Map.Entry<String, AppiumCommandInfo> entry : additionalCommands.entrySet()) {
                String key = entry.getKey();
                AppiumCommandInfo value = entry.getValue();
                checkNotNull(key);
                checkNotNull(entry.getValue());
                commandCodec.defineCommand(key, value.getMethod(), value.getUrl());
            }
            responseCodec = dialect.getResponseCodec();
            return result.createResponse();
        }

        if (commandCodec == null || responseCodec == null) {
            throw new WebDriverException(
                    "No command or response codec has been defined. Unable to proceed");
        }

        HttpRequest httpRequest = commandCodec.encode(command);
        try {
            HttpResponse httpResponse = client.execute(httpRequest, true);

            Response response = responseCodec.decode(httpResponse);
            if (response.getSessionId() == null) {
                if (httpResponse.getTargetHost() != null) {
                    response.setSessionId(HttpSessionId.getSessionId(httpResponse.getTargetHost()));
                } else {
                    response.setSessionId(command.getSessionId().toString());
                }
            }
            if (QUIT.equals(command.getName())) {
                client.close();
            }
            return response;
        } catch (UnsupportedCommandException e) {
            if (e.getMessage() == null || "".equals(e.getMessage())) {
                throw new UnsupportedOperationException(
                        "No information from server. Command name was: " + command.getName(),
                        e.getCause());
            }
            throw e;
        }
    }

    @Override public Response execute(Command command) throws IOException, WebDriverException {
        if (DriverCommand.NEW_SESSION.equals(command.getName()) && service != null) {
            service.start();
        }

        try {
            return doExecute(command);
        } catch (Throwable t) {
            Throwable rootCause = getRootCause(t);
            if (rootCause instanceof ConnectException
                && rootCause.getMessage().contains("Connection refused")
                && service != null) {
                if (service.isRunning()) {
                    throw new WebDriverException("The session is closed!", t);
                }

                if (!service.isRunning()) {
                    throw new WebDriverException("The appium server has accidentally died!", t);
                }
            }
            throwIfUnchecked(t);
            throw new WebDriverException(t);
        } finally {
            if (DriverCommand.QUIT.equals(command.getName()) && service != null) {
                service.stop();
            }
        }
    }

}
