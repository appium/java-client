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

import io.appium.java_client.AppiumCommandInfo;
import io.appium.java_client.ErrorCodesMobile;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Golden-file tests that pin the wire behavior (command encoding, JSON serialization,
 * response decoding and error mapping). Set GOLDEN_UPDATE=1 to regenerate the files.
 */
class AppiumWireCodecsTest {
    private static final Pattern MISSING_PARAM = Pattern.compile("Missing required parameter \"([^\"]+)\"");
    private static final SessionId SESSION = new SessionId("test-session");

    private static Collection<String> allCommandNames() {
        var names = new TreeSet<String>();
        for (Field field : DriverCommand.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == String.class) {
                try {
                    names.add((String) field.get(null));
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                }
            }
        }
        names.addAll(MobileCommand.commandRepository.keySet());
        return names;
    }

    private static AppiumW3CHttpCommandCodec newCodec() {
        var codec = new AppiumW3CHttpCommandCodec();
        MobileCommand.commandRepository.forEach((name, info) -> {
            var appiumInfo = (AppiumCommandInfo) info;
            codec.defineCommand(name, appiumInfo.getMethod(),
                    appiumInfo.getUrl());
        });
        return codec;
    }

    @Test
    void commandEncoding() {
        var codec = newCodec();
        Map<String, Object> result = new TreeMap<>();
        for (String name : allCommandNames()) {
            if (!codec.isSupported(name)) {
                result.put(name, "unsupported");
                continue;
            }
            Map<String, Object> params = new LinkedHashMap<>();
            HttpRequest request = null;
            String failure = null;
            for (int i = 0; i < 20 && request == null && failure == null; i++) {
                try {
                    request = codec.encode(new Command(SESSION, name, params));
                } catch (IllegalArgumentException e) {
                    var matcher = MISSING_PARAM.matcher(String.valueOf(e.getMessage()));
                    if (matcher.find()) {
                        params.put(matcher.group(1), "sample-" + matcher.group(1));
                    } else {
                        failure = "error:" + e.getClass().getSimpleName();
                    }
                } catch (RuntimeException e) {
                    // Commands that need structured parameters are covered by the realistic cases
                    failure = "error:" + e.getClass().getSimpleName();
                }
            }
            result.put(name, request == null ? (failure == null ? "error:unresolved" : failure)
                    : WireGoldenSupport.describeRequest(request));
        }
        WireGoldenSupport.verifyGolden("commands.json", result);
    }

    @Test
    void commandEncodingWithRealisticParameters() {
        Map<String, Map<String, Object>> cases = new LinkedHashMap<>();
        cases.put(DriverCommand.SEND_KEYS_TO_ELEMENT, Map.of("id", "e1", "value", new CharSequence[]{"ab", "c"}));
        cases.put(DriverCommand.SET_TIMEOUT, Map.of("implicit", 5000L, "pageLoad", 10000L, "script", 3000L));
        cases.put(DriverCommand.FIND_ELEMENT, Map.of("using", "css selector", "value", "[id=\"x\"]"));
        cases.put(DriverCommand.FIND_CHILD_ELEMENTS, Map.of("id", "e1", "using", "xpath", "value", "//a"));
        cases.put(DriverCommand.EXECUTE_SCRIPT, Map.of("script", "mobile: shell", "args", List.of(Map.of("a", 1))));
        cases.put(DriverCommand.ADD_COOKIE, Map.of("cookie", new Cookie("n", "v", "/")));
        cases.put(DriverCommand.SET_CURRENT_WINDOW_SIZE, Map.of("x", 1, "y", 2, "width", 300, "height", 400));
        cases.put(DriverCommand.SWITCH_TO_WINDOW, Map.of("handle", "w1"));
        cases.put(DriverCommand.SWITCH_TO_FRAME, Map.of("id", 1));
        cases.put(DriverCommand.GET, Map.of("url", "https://example.com/a?b=c&d=<e>"));
        cases.put(DriverCommand.GET_ELEMENT_ATTRIBUTE, Map.of("id", "e 1", "name", "a/b c"));
        cases.put(DriverCommand.ACTIONS, Map.of("actions", List.of(WireGoldenSupport.pointerSequence())));
        cases.put(DriverCommand.SET_ALERT_VALUE, Map.of("text", "hi", "value", new String[]{"h", "i"}));
        cases.put(DriverCommand.PRINT_PAGE, Map.of("orientation", "portrait", "scale", 1.0));
        var codec = newCodec();
        Map<String, Object> result = new LinkedHashMap<>();
        for (var entry : cases.entrySet()) {
            try {
                result.put(entry.getKey(), WireGoldenSupport.describeRequest(
                        codec.encode(new Command(SESSION, entry.getKey(), entry.getValue()))));
            } catch (RuntimeException e) {
                result.put(entry.getKey(), "error:" + e.getClass().getSimpleName());
            }
        }
        WireGoldenSupport.verifyGolden("commands-realistic.json", result);
    }

    private static HttpResponse httpResponse(int status, String contentType, String body) {
        var response = new HttpResponse().setStatus(status);
        if (contentType != null) {
            response.setHeader("Content-Type", contentType);
        }
        return response.setContent(Contents.utf8String(body));
    }

    private static Map<String, Object> responseCases() {
        String json = "application/json; charset=utf-8";
        Map<String, Object> cases = new LinkedHashMap<>();
        cases.put("newSession", httpResponse(200, json, "{\"value\":{\"sessionId\":\"abc\",\"capabilities\":"
                + "{\"platformName\":\"Android\",\"appium:width\":1080,\"appium:scale\":2.5,\"flag\":true,"
                + "\"nested\":{\"n\":null,\"arr\":[1,2.0,\"3\"]}}}}"));
        cases.put("null", httpResponse(200, json, "{\"value\":null}"));
        cases.put("string", httpResponse(200, json, "{\"value\":\"text\"}"));
        cases.put("list", httpResponse(200, json, "{\"value\":[1,2,3]}"));
        cases.put("long", httpResponse(200, json, "{\"value\":123456789012}"));
        cases.put("double", httpResponse(200, json, "{\"value\":1.5}"));
        cases.put("wholeDouble", httpResponse(200, json, "{\"value\":2.0}"));
        cases.put("bool", httpResponse(200, json, "{\"value\":true}"));
        cases.put("element", httpResponse(200, json,
                "{\"value\":{\"ELEMENT\":\"e1\",\"element-6066-11e4-a52e-4f735466cecf\":\"e1\"}}"));
        cases.put("elements", httpResponse(200, json,
                "{\"value\":[{\"element-6066-11e4-a52e-4f735466cecf\":\"e1\"},"
                        + "{\"element-6066-11e4-a52e-4f735466cecf\":\"e2\"}]}"));
        cases.put("noContentType", httpResponse(200, null, "{\"value\":\"x\"}"));
        cases.put("emptyBody200", httpResponse(200, json, ""));
        cases.put("textBody200", httpResponse(200, "text/plain", "OK"));
        cases.put("emptyBody500", httpResponse(500, json, ""));
        cases.put("textBody502", httpResponse(502, "text/html", "<html>Bad gateway</html>"));
        cases.put("noValueKey", httpResponse(200, json, "{\"status\":0}"));
        cases.put("noSuchContext", httpResponse(404, json,
                "{\"value\":{\"error\":\"unknown error\",\"message\":\"No such context found\"}}"));
        for (String error : W3C_ERRORS) {
            cases.put("error:" + error, httpResponse(error.equals("unknown method") ? 405 : 400, json,
                    "{\"value\":{\"error\":\"" + error + "\",\"message\":\"msg for " + error
                            + "\",\"stacktrace\":\"at foo (bar.js:1:2)\\nat baz (qux.js:3:4)\"}}"));
        }
        cases.put("error:unrecognized", httpResponse(500, json,
                "{\"value\":{\"error\":\"totally new error\",\"message\":\"boom\"}}"));
        cases.put("error:noStacktrace", httpResponse(404, json,
                "{\"value\":{\"error\":\"no such element\",\"message\":\"nope\"}}"));
        return cases;
    }

    private static final List<String> W3C_ERRORS = List.of(
            "detached shadow root", "element click intercepted", "element not interactable",
            "insecure certificate", "invalid argument", "invalid cookie domain", "invalid element state",
            "invalid selector", "invalid session id", "javascript error", "move target out of bounds",
            "no such alert", "no such context", "no such cookie", "no such element", "no such frame",
            "no such shadow root",
            "no such window", "script timeout", "session not created", "stale element reference", "timeout",
            "unable to set cookie", "unable to capture screen", "unexpected alert open", "unknown command",
            "unknown error", "unknown method", "unsupported operation");

    @Test
    void responseDecoding() {
        var codec = new AppiumW3CHttpResponseCodec();
        Map<String, Object> result = new LinkedHashMap<>();
        responseCases().forEach((name, response) -> {
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                Response decoded = codec.decode((HttpResponse) response);
                described.put("state", decoded.getState());
                described.put("status", decoded.getStatus());
                described.put("sessionId", decoded.getSessionId());
                described.put("value", WireGoldenSupport.typed(decoded.getValue()));
            } catch (RuntimeException e) {
                described.put("error", e.getClass().getSimpleName());
            }
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("responses.json", result);
    }

    @Test
    void errorMapping() {
        var codec = new AppiumW3CHttpResponseCodec();
        var handler = new ErrorHandler(new ErrorCodesMobile());
        Map<String, Object> result = new LinkedHashMap<>();
        responseCases().forEach((name, response) -> {
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                handler.throwIfResponseFailed(codec.decode((HttpResponse) response), 100);
                described.put("thrown", "none");
            } catch (WebDriverException e) {
                described.put("thrown", e.getClass().getName());
                described.put("rawMessage", WireGoldenSupport.stableMessage(e));
                described.put("cause", e.getCause() == null ? null : e.getCause().getClass().getName());
                described.put("serverStackFrames", Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .filter(f -> f.contains("bar.js") || f.contains("qux.js"))
                        .collect(Collectors.toList()));
            } catch (RuntimeException e) {
                described.put("thrown", "runtime:" + e.getClass().getName());
            }
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("errors.json", result);
    }
}
