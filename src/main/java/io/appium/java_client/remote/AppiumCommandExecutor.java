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
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;
import static java.util.logging.Logger.getLogger;
import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;

import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import com.google.common.io.CountingOutputStream;
import com.google.common.io.FileBackedOutputStream;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandCodec;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.Dialect;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.ProtocolHandshake;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.ResponseCodec;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.service.DriverService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        this(additionalCommands, addressOfRemoteServer, new AppiumHttpClientFactory());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
                                 DriverService service) {
        this(additionalCommands, service, new AppiumHttpClientFactory());
    }

    protected <B> B getPrivateFieldValue(String fieldName, Class<B> fieldType) {
        Class<?> superclass = getClass().getSuperclass();
        Throwable recentException = null;
        while (superclass != Object.class) {
            try {
                final Field f = superclass.getDeclaredField(fieldName);
                f.setAccessible(true);
                return fieldType.cast(f.get(this));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                recentException = e;
            }
            superclass = superclass.getSuperclass();
        }
        throw new WebDriverException(recentException);
    }

    protected void setPrivateFieldValue(String fieldName, Object newValue) {
        Class<?> superclass = getClass().getSuperclass();
        Throwable recentException = null;
        while (superclass != Object.class) {
            try {
                final Field f = superclass.getDeclaredField(fieldName);
                f.setAccessible(true);
                f.set(this, newValue);
                return;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                recentException = e;
            }
            superclass = superclass.getSuperclass();
        }
        throw new WebDriverException(recentException);
    }

    protected Map<String, CommandInfo> getAdditionalCommands() {
        //noinspection unchecked
        return getPrivateFieldValue("additionalCommands", Map.class);
    }

    protected CommandCodec<HttpRequest> getCommandCodec() {
        //noinspection unchecked
        return getPrivateFieldValue("commandCodec", CommandCodec.class);
    }

    protected void setCommandCodec(CommandCodec<HttpRequest> newCodec) {
        setPrivateFieldValue("commandCodec", newCodec);
    }

    protected void setResponseCodec(ResponseCodec<HttpResponse> codec) {
        setPrivateFieldValue("responseCodec", codec);
    }

    protected HttpClient getClient() {
        //noinspection unchecked
        return getPrivateFieldValue("client", HttpClient.class);
    }

    private Response createSession(Command command) throws IOException {
        if (getCommandCodec() != null) {
            throw new SessionNotCreatedException("Session already exists");
        }
        ProtocolHandshake handshake = new ProtocolHandshake() {
            @SuppressWarnings("unchecked")
            public Result createSession(HttpClient client, Command command)
                    throws IOException {
                Capabilities desiredCapabilities = (Capabilities) command.getParameters().get("desiredCapabilities");
                Capabilities desired = desiredCapabilities == null ? new ImmutableCapabilities() : desiredCapabilities;

                //the number of bytes before the stream should switch to buffering to a file
                int threshold = (int) Math.min(Runtime.getRuntime().freeMemory() / 10, Integer.MAX_VALUE);
                FileBackedOutputStream os = new FileBackedOutputStream(threshold);
                try {

                    CountingOutputStream counter = new CountingOutputStream(os);
                    Writer writer = new OutputStreamWriter(counter, UTF_8);
                    NewAppiumSessionPayload payload = NewAppiumSessionPayload.create(desired);
                    payload.writeTo(writer);

                    try (InputStream rawIn = os.asByteSource().openBufferedStream();
                         BufferedInputStream contentStream = new BufferedInputStream(rawIn)) {

                        Method createSessionMethod = this.getClass().getSuperclass()
                                .getDeclaredMethod("createSession", HttpClient.class, InputStream.class, long.class);
                        createSessionMethod.setAccessible(true);

                        Optional<Result> result = (Optional<Result>) createSessionMethod
                                .invoke(this, client, contentStream, counter.getCount());

                        return result.map(result1 -> {
                            Result toReturn = result.get();
                            getLogger(ProtocolHandshake.class.getName())
                                    .info(format("Detected dialect: %s", toReturn.getDialect()));
                            return toReturn;
                        }).orElseThrow(() -> new SessionNotCreatedException(
                                format("Unable to create new remote session. desired capabilities = %s", desired)));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new WebDriverException(format("It is impossible to create a new session "
                                        + "because 'createSession' which takes %s, %s and %s was not found "
                                        + "or it is not accessible",
                                HttpClient.class.getSimpleName(),
                                InputStream.class.getSimpleName(),
                                long.class.getSimpleName()), e);
                    }
                } finally {
                    os.reset();
                }
            }
        };

        ProtocolHandshake.Result result = handshake
                .createSession(getClient(), command);
        Dialect dialect = result.getDialect();
        setCommandCodec(dialect.getCommandCodec());
        getAdditionalCommands().forEach(this::defineCommand);
        setResponseCodec(dialect.getResponseCodec());
        return result.createResponse();
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
            response = NEW_SESSION.equals(command.getName()) ? createSession(command) : super.execute(command);
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

        if (DriverCommand.NEW_SESSION.equals(command.getName())
                && getCommandCodec() instanceof W3CHttpCommandCodec) {
            setCommandCodec(new AppiumW3CHttpCommandCodec());
            getAdditionalCommands().forEach(this::defineCommand);
        }

        return response;
    }
}
