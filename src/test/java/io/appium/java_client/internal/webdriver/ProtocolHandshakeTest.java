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

import com.google.gson.JsonParser;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpHandler;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.remote.Command;
import io.appium.java_client.remote.DriverCommand;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.SessionNotCreatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProtocolHandshakeTest {
    private final List<HttpRequest> requests = new ArrayList<>();

    private HttpHandler server(int status, String body) {
        return req -> {
            requests.add(req);
            return new HttpResponse().setStatus(status).setHeader("Content-Type", "application/json")
                    .setContent(Contents.utf8String(body));
        };
    }

    private static Command newSession(Capabilities... capabilities) {
        return new Command(null, DriverCommand.NEW_SESSION(List.of(capabilities)));
    }

    @Test
    void sendsOnlyFirstMatchCapabilitiesAndReturnsTheSession() {
        var handler = server(200, "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":{\"platformName\":\"Android\","
                + "\"appium:width\":1080}}}");

        final var result = new ProtocolHandshake().createSession(handler,
                newSession(new ImmutableCapabilities("platformName", "Android", "appium:deviceName", "emu")));

        HttpRequest request = requests.get(0);
        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/session", request.getUri());
        assertEquals("application/json; charset=utf-8", request.getHeader("content-type"));
        assertEquals(JsonParser.parseString("{\"capabilities\":{\"firstMatch\":[{\"platformName\":\"ANDROID\","
                        + "\"appium:deviceName\":\"emu\"}]}}"),
                JsonParser.parseString(Contents.string(request)));
        var response = result.createResponse();
        assertEquals("s1", response.getSessionId());
        assertEquals("success", response.getState());
        assertEquals(1080L, ((Map<?, ?>) response.getValue()).get("appium:width"));
    }

    @Test
    void sendsEachDistinctAlternativeOnce() {
        var handler = server(200, "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":{}}}");
        var first = new ImmutableCapabilities("platformName", "Android");

        new ProtocolHandshake().createSession(handler, newSession(first, first,
                new ImmutableCapabilities("platformName", "iOS")));

        var body = JsonParser.parseString(Contents.string(requests.get(0))).getAsJsonObject();
        assertEquals(2, body.getAsJsonObject("capabilities").getAsJsonArray("firstMatch").size());
    }

    @Test
    void rejectsInvalidCapabilitiesBeforeSendingAnything() {
        var handler = server(200, "{}");

        var illegal = assertThrows(IllegalArgumentException.class, () -> new ProtocolHandshake()
                .createSession(handler, newSession(new ImmutableCapabilities("not-allowed", "x"))));

        assertTrue(illegal.getMessage().contains("not-allowed"));
        assertTrue(requests.isEmpty());
    }

    @Test
    void reportsTheServerErrorMessage() {
        var handler = server(500, "{\"value\":{\"error\":\"session not created\",\"message\":\"no device\"}}");

        var error = assertThrows(SessionNotCreatedException.class, () -> new ProtocolHandshake()
                .createSession(handler, newSession(new ImmutableCapabilities("platformName", "Android"))));

        assertTrue(error.getMessage().contains("Response code 500. Message: no device"), error.getMessage());
    }

    @Test
    void rejectsResponsesThatAreNotJson() {
        var handler = server(200, "<html></html>");

        var error = assertThrows(SessionNotCreatedException.class, () -> new ProtocolHandshake()
                .createSession(handler, newSession(new ImmutableCapabilities("platformName", "Android"))));

        assertTrue(error.getMessage().contains("Unable to parse remote response: <html></html>"));
    }

    @Test
    void rejectsLegacyJsonWireResponses() {
        var handler = server(200, "{\"status\":0,\"sessionId\":\"s1\",\"value\":{\"platformName\":\"Android\"}}");

        var error = assertThrows(SessionNotCreatedException.class, () -> new ProtocolHandshake()
                .createSession(handler, newSession(new ImmutableCapabilities("platformName", "Android"))));

        assertTrue(error.getMessage().contains("does not match any supported protocol"));
    }

    @Test
    void throwsTheMappedExceptionForErrorsInSuccessfulResponses() {
        var handler = server(200, "{\"value\":{\"error\":\"session not created\",\"message\":\"boom\"}}");

        assertThrows(SessionNotCreatedException.class, () -> new ProtocolHandshake()
                .createSession(handler, newSession(new ImmutableCapabilities("platformName", "Android"))));
    }
}
