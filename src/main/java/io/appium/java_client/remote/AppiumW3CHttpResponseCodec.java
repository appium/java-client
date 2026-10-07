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

import com.google.gson.reflect.TypeToken;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.internal.json.WireJson;
import io.appium.java_client.internal.webdriver.ErrorCodec;
import org.openqa.selenium.UnhandledAlertException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_BAD_GATEWAY;
import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

/**
 * Decodes responses of W3C WebDriver servers. Adapted from Selenium's
 * {@code W3CHttpResponseCodec} (Apache License 2.0).
 */
public class AppiumW3CHttpResponseCodec implements ResponseCodec {
    /**
     * Creates a new instance.
     */
    public AppiumW3CHttpResponseCodec() {
    }

    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() { }.getType();

    private final ErrorCodes errorCodes = new ErrorCodes();

    @Override
    public Response decode(HttpResponse encodedResponse) {
        Response response = new Response();
        final String contentType = encodedResponse.getContentType() == null ? ""
                : encodedResponse.getContentType();

        if (!encodedResponse.isSuccessful()) {
            decodeError(encodedResponse, response);
            return response;
        }

        response.setState(ErrorCodes.SUCCESS_STRING);
        response.setStatus(ErrorCodes.SUCCESS);
        if (contentType.startsWith("application/octet-stream")) {
            response.setValue(encodedResponse.getContent());
            return response;
        }

        String content = encodedResponse.contentAsString().trim();
        if (!content.isEmpty() && contentType.startsWith("application/json")) {
            Map<String, Object> parsed = WireJson.fromJson(content, MAP_TYPE);
            // Without the "value" key the whole body is the value
            response.setValue(parsed.containsKey("value") ? parsed.get("value") : WireJson.fromJson(content));
        }

        Object value = response.getValue();
        if (value instanceof String) {
            // Normalize to \n, because Java translates it to \r\n where suitable and \r\n would become \r\r\n
            response.setValue(((String) value).replace("\r\n", "\n"));
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    private void decodeError(HttpResponse encodedResponse, Response response) {
        String content = encodedResponse.contentAsString().trim();
        if (HTTP_BAD_METHOD == encodedResponse.getStatus()) {
            response.setState("unknown command");
            response.setStatus(ErrorCodes.UNKNOWN_COMMAND);
            response.setValue(content);
            return;
        }
        if (HTTP_GATEWAY_TIMEOUT == encodedResponse.getStatus() || HTTP_BAD_GATEWAY == encodedResponse.getStatus()) {
            response.setState("unknown error");
            response.setStatus(ErrorCodes.UNHANDLED_ERROR);
            response.setValue(content);
            return;
        }

        Map<String, Object> body = WireJson.fromJson(content, MAP_TYPE);
        Map<String, Object> error = body;
        Object wrapped = body.get("value");
        if (wrapped instanceof Map && ((Map<?, ?>) wrapped).containsKey("error")) {
            error = (Map<String, Object>) wrapped;
        }
        String message = error.get("message") instanceof String ? (String) error.get("message")
                : "An unknown error has occurred";
        String errorCode = error.get("error") instanceof String ? (String) error.get("error") : "unknown error";

        response.setState(errorCode);
        response.setStatus(errorCodes.toStatus(errorCode, Optional.of(encodedResponse.getStatus())));
        if ("unexpected alert open".equals(errorCode) && HTTP_INTERNAL_ERROR == encodedResponse.getStatus()) {
            String text = "";
            Object data = error.get("data");
            if (data instanceof Map && ((Map<?, ?>) data).get("text") instanceof String) {
                text = (String) ((Map<?, ?>) data).get("text");
            }
            response.setValue(new UnhandledAlertException(message, text));
        } else {
            response.setValue(ErrorCodec.decode(body));
        }
    }
}
