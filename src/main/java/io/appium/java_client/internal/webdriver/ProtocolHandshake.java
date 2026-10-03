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

package io.appium.java_client.internal.webdriver;

import com.google.gson.reflect.TypeToken;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpHandler;
import io.appium.java_client.http.HttpHeader;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.internal.json.WireJson;
import io.appium.java_client.remote.Command;
import io.appium.java_client.remote.ErrorCodes;
import io.appium.java_client.remote.ErrorHandler;
import io.appium.java_client.remote.Response;
import io.appium.java_client.remote.SessionId;
import io.appium.java_client.remote.options.W3CCapabilityKeys;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;

/**
 * Creates a W3C session. Adapted from Selenium's {@code ProtocolHandshake} (Apache License 2.0),
 * which also negotiated the legacy JSON wire protocol.
 */
public class ProtocolHandshake {
    private static final Logger LOG = LoggerFactory.getLogger(ProtocolHandshake.class);
    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() { }.getType();
    private static final Predicate<String> ACCEPTED_W3C_PATTERNS = W3CCapabilityKeys.INSTANCE;

    /**
     * Sends the new session request.
     *
     * @param client the handler that executes HTTP requests
     * @param command the new session command, containing the requested capabilities
     * @return the created session
     * @throws SessionNotCreatedException if the server refuses to create the session
     */
    @SuppressWarnings("unchecked")
    public Result createSession(HttpHandler client, Command command) {
        Collection<Capabilities> desired = (Collection<Capabilities>) command.getParameters().get("capabilities");
        desired = desired == null || desired.isEmpty() ? singleton(new ImmutableCapabilities()) : desired;
        List<Map<String, Object>> firstMatch = desired.stream().map(Capabilities::asMap).distinct()
                .collect(Collectors.toList());
        validate(firstMatch);

        // Only "firstMatch" is populated, the specification allows to omit "alwaysMatch"
        String payload = WireJson.toJson(Map.of("capabilities", Map.of("firstMatch", firstMatch)));
        HttpRequest request = new HttpRequest(HttpMethod.POST, "/session");
        request.setHeader(HttpHeader.ContentType.getName(), "application/json; charset=utf-8");
        request.setContent(Contents.bytes(payload.getBytes(StandardCharsets.UTF_8)));

        long start = System.currentTimeMillis();
        HttpResponse response = client.execute(request);
        final long time = System.currentTimeMillis() - start;

        // The content type is ignored on purpose, it may not have been set
        String body = Contents.string(response);
        Map<String, Object> blob;
        try {
            blob = WireJson.fromJson(body, MAP_TYPE);
        } catch (WebDriverException e) {
            throw new SessionNotCreatedException("Unable to parse remote response: " + body, e);
        }
        if (blob == null) {
            throw new SessionNotCreatedException("Unable to parse remote response: " + body);
        }

        if (response.getStatus() != 200) {
            Object rawValue = blob.get("value");
            String responseMessage = rawValue instanceof Map
                    ? String.valueOf(((Map<?, ?>) rawValue).get("message")) : WireJson.toJson(rawValue);
            throw new SessionNotCreatedException(
                    String.format("Response code %s. Message: %s", response.getStatus(), responseMessage));
        }

        throwIfErrorResponse(blob, response.getStatus(), time);
        Result result = toResult(blob);
        if (result == null) {
            throw new SessionNotCreatedException(String.format(
                    "Handshake response does not match any supported protocol. Response payload: %s", body));
        }
        LOG.debug("Created a W3C session: {}", result);
        return result;
    }

    private static void validate(List<Map<String, Object>> alternatives) {
        for (Map<String, Object> map : alternatives) {
            List<String> nullKeys = map.entrySet().stream().filter(e -> e.getValue() == null)
                    .map(Map.Entry::getKey).sorted().collect(Collectors.toList());
            if (!nullKeys.isEmpty()) {
                throw new IllegalArgumentException("Null values found in w3c capabilities. Keys are: " + nullKeys);
            }
            List<String> illegalKeys = map.keySet().stream().filter(ACCEPTED_W3C_PATTERNS.negate()).sorted()
                    .collect(Collectors.toList());
            if (!illegalKeys.isEmpty()) {
                throw new IllegalArgumentException("Illegal key values seen in w3c capabilities: " + illegalKeys);
            }
        }
    }

    private static void throwIfErrorResponse(Map<String, Object> blob, int statusCode, long duration) {
        if (!(blob.get("value") instanceof Map)) {
            return;
        }
        Map<?, ?> value = (Map<?, ?>) blob.get("value");
        Object rawMessage = value.get("message");
        Object rawError = value.get("error");
        if (!(rawError instanceof String) || !(rawMessage instanceof String)) {
            return;
        }
        var errorCodes = new ErrorCodes();
        Response response = new Response();
        response.setState((String) rawError);
        response.setStatus(errorCodes.toStatus((String) rawError, Optional.of(statusCode)));
        Class<? extends WebDriverException> type = errorCodes.getExceptionType((String) rawError);
        try {
            WebDriverException exception = type.getConstructor(String.class).newInstance((String) rawMessage);
            Object rawStackTrace = value.get("stacktrace");
            exception.addInfo("remote stacktrace", rawStackTrace == null ? "" : String.valueOf(rawStackTrace));
            response.setValue(exception);
        } catch (ReflectiveOperationException e) {
            response.setValue(rawMessage);
        }
        new ErrorHandler().throwIfResponseFailed(response, duration);
    }

    @SuppressWarnings("unchecked")
    private static Result toResult(Map<String, Object> blob) {
        if (!(blob.get("value") instanceof Map)) {
            return null;
        }
        Map<?, ?> value = (Map<?, ?>) blob.get("value");
        Object rawSessionId = value.get("sessionId");
        Object rawCapabilities = value.get("capabilities");
        if (!(rawSessionId instanceof String) || !(rawCapabilities instanceof Map)) {
            return null;
        }
        for (Object key : ((Map<?, ?>) rawCapabilities).keySet()) {
            if (!(key instanceof String)) {
                return null;
            }
        }
        return new Result((String) rawSessionId, (Map<String, Object>) rawCapabilities);
    }

    /**
     * The created session.
     */
    public static class Result {
        private final SessionId sessionId;
        private final Map<String, Object> capabilities;

        Result(String sessionId, Map<String, Object> capabilities) {
            this.sessionId = new SessionId(sessionId);
            this.capabilities = capabilities;
        }

        /**
         * Creates the response of the new session command.
         *
         * @return a successful response with the session capabilities as value
         */
        public Response createResponse() {
            Response response = new Response(sessionId);
            response.setValue(capabilities);
            response.setStatus(ErrorCodes.SUCCESS);
            response.setState(ErrorCodes.SUCCESS_STRING);
            return response;
        }

        @Override
        public String toString() {
            return String.format("W3C: %s", capabilities);
        }
    }
}
