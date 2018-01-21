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

import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import io.appium.java_client.AppiumW3CHttpCommandCodec;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandCodec;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.internal.ApacheHttpClient;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class AppiumCommandExecutor extends HttpCommandExecutor {

    private final Optional<DriverService> serviceOptional;

    private AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                  URL addressOfRemoteServer,
                                  HttpClient.Factory httpClientFactory) {
        super(additionalCommands,
                ofNullable(service)
                        .map(DriverService::getUrl)
                        .orElse(addressOfRemoteServer), httpClientFactory);
        serviceOptional = ofNullable(service);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
                                 HttpClient.Factory httpClientFactory) {
        this(additionalCommands, checkNotNull(service), null, httpClientFactory);
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer, HttpClient.Factory httpClientFactory) {
        this(additionalCommands, null, checkNotNull(addressOfRemoteServer), httpClientFactory);
    }


    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 URL addressOfRemoteServer) {
        this(additionalCommands, addressOfRemoteServer, new ApacheHttpClient.Factory());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service) {
        this(additionalCommands, service, new ApacheHttpClient.Factory());
    }

    private <B> B getPrivateFieldValue(String fieldName, Class<B> fieldType) {
        try {
            final Field f = getClass().getSuperclass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return fieldType.cast(f.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    private void setPrivateFieldValue(String fieldName, Object newValue) {
        try {
            final Field f = getClass().getSuperclass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(this, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    private Map<String, CommandInfo> getAdditionalCommands() {
        //noinspection unchecked
        return getPrivateFieldValue("additionalCommands", Map.class);
    }

    private CommandCodec<HttpRequest> getCommandCodec() {
        //noinspection unchecked
        return getPrivateFieldValue("commandCodec", CommandCodec.class);
    }

    private void setCommandCodec(CommandCodec<HttpRequest> newCodec) {
        setPrivateFieldValue("commandCodec", newCodec);
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

        Response response;
        try {
            response = super.execute(command);
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

        if (DriverCommand.NEW_SESSION.equals(command.getName()) &&
                getCommandCodec() instanceof W3CHttpCommandCodec) {
            setCommandCodec(new AppiumW3CHttpCommandCodec());
            for (Map.Entry<String, CommandInfo> entry : getAdditionalCommands().entrySet()) {
                defineCommand(entry.getKey(), entry.getValue());
            }
        }

        return response;
    }
}