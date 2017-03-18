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

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static org.openqa.selenium.remote.ErrorCodes.SESSION_NOT_CREATED;
import static org.openqa.selenium.remote.ErrorCodes.SUCCESS;

import com.google.common.base.Preconditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.BeanToJsonConverter;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Dialect;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.JsonException;
import org.openqa.selenium.remote.JsonToBeanConverter;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class AppiumProtocolHandShake {

    public Result createSession(HttpClient client, Command command)
            throws IOException {

        Capabilities desired = (Capabilities) command.getParameters().get("desiredCapabilities");
        desired = desired == null ? new DesiredCapabilities() : desired;
        Capabilities required = (Capabilities) command.getParameters().get("requiredCapabilities");
        required = required == null ? new DesiredCapabilities() : required;

        JsonParser parser = new JsonParser();
        JsonElement des = parser.parse(new BeanToJsonConverter().convert(desired));
        JsonElement req = parser.parse(new BeanToJsonConverter().convert(required));

        JsonObject jsonObject = new JsonObject();

        amendW3CParameters(jsonObject, des, req);
        amendOssParamters(jsonObject, des, req);
        Optional<Result> result = createSession(client, jsonObject);

        // Assume a fragile OSS webdriver implementation
        if (!result.isPresent()) {
            jsonObject = new JsonObject();
            amendOssParamters(jsonObject, des, req);
            result = createSession(client, jsonObject);
        }

        // Assume a fragile w3c implementation
        if (!result.isPresent()) {
            jsonObject = new JsonObject();
            amendW3CParameters(jsonObject, des, req);
            result = createSession(client, jsonObject);
        }

        if (result.isPresent()) {
            Result toReturn = result.get();
            return toReturn;
        }

        throw new SessionNotCreatedException(
                String.format(
                        "Unable to create new remote session. "
                                + "desired capabilities = %s, required capabilities = %s",
                        desired,
                        required));
    }

    private Optional<Result> createSession(HttpClient client, JsonObject params)
            throws IOException {
        // Create the http request and send it
        HttpRequest request = new HttpRequest(HttpMethod.POST, "/session");
        String content = params.toString();
        byte[] data = content.getBytes(UTF_8);

        request.setHeader(CONTENT_LENGTH, String.valueOf(data.length));
        request.setHeader(CONTENT_TYPE, JSON_UTF_8.toString());
        request.setContent(data);
        HttpResponse response = client.execute(request, true);

        Map<?, ?> jsonBlob = null;
        String resultString = response.getContentString();
        try {
            jsonBlob = new JsonToBeanConverter().convert(Map.class, resultString);
        } catch (ClassCastException e) {
            return Optional.empty();
        } catch (JsonException e) {
            // Fine. Handle that below
        }

        if (jsonBlob == null) {
            jsonBlob = new HashMap<>();
        }

        // If the result looks positive, return the result.
        Object sessionId = jsonBlob.get("sessionId");
        Object value = jsonBlob.get("value");
        Object w3cError = jsonBlob.get("error");
        Object ossStatus = jsonBlob.get("status");
        Map<String, ?> capabilities = null;
        if (value != null && value instanceof Map) {
            capabilities = (Map<String, ?>) value;
        } else if (value != null && value instanceof Capabilities) {
            capabilities = ((Capabilities) capabilities).asMap();
        }

        if (response.getStatus() == HttpURLConnection.HTTP_OK
                && sessionId != null && capabilities != null) {
            Dialect dialect = ossStatus == null ? Dialect.W3C : Dialect.OSS;
            return Optional.of(
                        new Result(dialect, String.valueOf(sessionId), capabilities));
        }

        // If the result was an error that we believe has to do with the remote end failing to start the
        // session, create an exception and throw it.
        Response tempResponse = null;
        if ("session not created".equals(w3cError)) {
            tempResponse = new Response(null);
            tempResponse.setStatus(SESSION_NOT_CREATED);
            tempResponse.setValue(jsonBlob);
        } else if (
                ossStatus instanceof Number
                        && ((Number) ossStatus).intValue() == SESSION_NOT_CREATED) {
            tempResponse = new Response(null);
            tempResponse.setStatus(SESSION_NOT_CREATED);
            tempResponse.setValue(jsonBlob);
        }

        if (tempResponse != null) {
            new ErrorHandler().throwIfResponseFailed(tempResponse, 0);
        }

        // Otherwise, just return empty.
        return Optional.empty();
    }

    private void amendW3CParameters(JsonObject jsonObject, JsonElement desired,
                                    JsonElement required) {
        JsonArray result = new JsonArray();
        JsonObject desiredJson = new JsonObject();
        JsonObject requiredJson = new JsonObject();

        desiredJson.add("desiredCapabilities", desired);
        requiredJson.add("requiredCapabilities", required);

        result.add(desiredJson);
        result.add(requiredJson);

        jsonObject.add("capabilities", result);
    }

    private void amendOssParamters(
            JsonObject jsonObject, JsonElement desired,
            JsonElement required) {
        jsonObject.add("desiredCapabilities", desired);
        jsonObject.add("requiredCapabilities", required);
    }

    public class Result {
        private final Dialect dialect;
        private final Map<String, ?> capabilities;
        private final SessionId sessionId;

        private Result(Dialect dialect, String sessionId, Map<String, ?> capabilities) {
            this.dialect = dialect;
            this.sessionId = new SessionId(Preconditions.checkNotNull(sessionId));
            this.capabilities = capabilities;
        }

        public Dialect getDialect() {
            return dialect;
        }

        public Response createResponse() {
            Response response = new Response(sessionId);
            response.setValue(capabilities);
            response.setStatus(SUCCESS);
            return response;
        }

        @Override
        public String toString() {
            return String.format("%s: %s", dialect, capabilities);
        }
    }
}
