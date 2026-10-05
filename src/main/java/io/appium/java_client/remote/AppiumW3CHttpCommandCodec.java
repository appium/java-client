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

import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpHeader;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.internal.json.WireJson;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.UnsupportedCommandException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.appium.java_client.remote.DriverCommand.ACCEPT_ALERT;
import static io.appium.java_client.remote.DriverCommand.ACTIONS;
import static io.appium.java_client.remote.DriverCommand.ADD_COOKIE;
import static io.appium.java_client.remote.DriverCommand.ADD_CREDENTIAL;
import static io.appium.java_client.remote.DriverCommand.ADD_VIRTUAL_AUTHENTICATOR;
import static io.appium.java_client.remote.DriverCommand.CANCEL_DIALOG;
import static io.appium.java_client.remote.DriverCommand.CLEAR_ACTIONS_STATE;
import static io.appium.java_client.remote.DriverCommand.CLEAR_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.CLICK_DIALOG;
import static io.appium.java_client.remote.DriverCommand.CLICK_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.CLOSE;
import static io.appium.java_client.remote.DriverCommand.DELETE_ALL_COOKIES;
import static io.appium.java_client.remote.DriverCommand.DELETE_COOKIE;
import static io.appium.java_client.remote.DriverCommand.DISMISS_ALERT;
import static io.appium.java_client.remote.DriverCommand.ELEMENT_SCREENSHOT;
import static io.appium.java_client.remote.DriverCommand.EXECUTE_ASYNC_SCRIPT;
import static io.appium.java_client.remote.DriverCommand.EXECUTE_SCRIPT;
import static io.appium.java_client.remote.DriverCommand.FIND_CHILD_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.FIND_CHILD_ELEMENTS;
import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENTS;
import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENTS_FROM_SHADOW_ROOT;
import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENT_FROM_SHADOW_ROOT;
import static io.appium.java_client.remote.DriverCommand.FULLSCREEN_CURRENT_WINDOW;
import static io.appium.java_client.remote.DriverCommand.GET;
import static io.appium.java_client.remote.DriverCommand.GET_ACCOUNTS;
import static io.appium.java_client.remote.DriverCommand.GET_ACTIVE_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.GET_ALERT_TEXT;
import static io.appium.java_client.remote.DriverCommand.GET_ALL_COOKIES;
import static io.appium.java_client.remote.DriverCommand.GET_AVAILABLE_LOG_TYPES;
import static io.appium.java_client.remote.DriverCommand.GET_CAPABILITIES;
import static io.appium.java_client.remote.DriverCommand.GET_COOKIE;
import static io.appium.java_client.remote.DriverCommand.GET_CREDENTIALS;
import static io.appium.java_client.remote.DriverCommand.GET_CURRENT_URL;
import static io.appium.java_client.remote.DriverCommand.GET_CURRENT_WINDOW_HANDLE;
import static io.appium.java_client.remote.DriverCommand.GET_CURRENT_WINDOW_POSITION;
import static io.appium.java_client.remote.DriverCommand.GET_CURRENT_WINDOW_SIZE;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_ACCESSIBLE_NAME;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_ARIA_ROLE;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_ATTRIBUTE;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_DOM_ATTRIBUTE;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_DOM_PROPERTY;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_RECT;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_SHADOW_ROOT;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_TAG_NAME;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_TEXT;
import static io.appium.java_client.remote.DriverCommand.GET_ELEMENT_VALUE_OF_CSS_PROPERTY;
import static io.appium.java_client.remote.DriverCommand.GET_FEDCM_DIALOG_TYPE;
import static io.appium.java_client.remote.DriverCommand.GET_FEDCM_TITLE;
import static io.appium.java_client.remote.DriverCommand.GET_LOG;
import static io.appium.java_client.remote.DriverCommand.GET_PAGE_SOURCE;
import static io.appium.java_client.remote.DriverCommand.GET_TIMEOUTS;
import static io.appium.java_client.remote.DriverCommand.GET_TITLE;
import static io.appium.java_client.remote.DriverCommand.GET_WINDOW_HANDLES;
import static io.appium.java_client.remote.DriverCommand.GO_BACK;
import static io.appium.java_client.remote.DriverCommand.GO_FORWARD;
import static io.appium.java_client.remote.DriverCommand.IMPLICITLY_WAIT;
import static io.appium.java_client.remote.DriverCommand.IS_ELEMENT_DISPLAYED;
import static io.appium.java_client.remote.DriverCommand.IS_ELEMENT_ENABLED;
import static io.appium.java_client.remote.DriverCommand.IS_ELEMENT_SELECTED;
import static io.appium.java_client.remote.DriverCommand.MAXIMIZE_CURRENT_WINDOW;
import static io.appium.java_client.remote.DriverCommand.MINIMIZE_CURRENT_WINDOW;
import static io.appium.java_client.remote.DriverCommand.NEW_SESSION;
import static io.appium.java_client.remote.DriverCommand.PRINT_PAGE;
import static io.appium.java_client.remote.DriverCommand.QUIT;
import static io.appium.java_client.remote.DriverCommand.REFRESH;
import static io.appium.java_client.remote.DriverCommand.REMOVE_ALL_CREDENTIALS;
import static io.appium.java_client.remote.DriverCommand.REMOVE_CREDENTIAL;
import static io.appium.java_client.remote.DriverCommand.REMOVE_VIRTUAL_AUTHENTICATOR;
import static io.appium.java_client.remote.DriverCommand.RESET_COOLDOWN;
import static io.appium.java_client.remote.DriverCommand.SCREENSHOT;
import static io.appium.java_client.remote.DriverCommand.SELECT_ACCOUNT;
import static io.appium.java_client.remote.DriverCommand.SEND_KEYS_TO_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.SET_ALERT_VALUE;
import static io.appium.java_client.remote.DriverCommand.SET_CURRENT_WINDOW_POSITION;
import static io.appium.java_client.remote.DriverCommand.SET_CURRENT_WINDOW_SIZE;
import static io.appium.java_client.remote.DriverCommand.SET_DELAY_ENABLED;
import static io.appium.java_client.remote.DriverCommand.SET_SCRIPT_TIMEOUT;
import static io.appium.java_client.remote.DriverCommand.SET_TIMEOUT;
import static io.appium.java_client.remote.DriverCommand.SET_USER_VERIFIED;
import static io.appium.java_client.remote.DriverCommand.STATUS;
import static io.appium.java_client.remote.DriverCommand.SWITCH_TO_FRAME;
import static io.appium.java_client.remote.DriverCommand.SWITCH_TO_NEW_WINDOW;
import static io.appium.java_client.remote.DriverCommand.SWITCH_TO_PARENT_FRAME;
import static io.appium.java_client.remote.DriverCommand.SWITCH_TO_WINDOW;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Encodes commands according to the W3C WebDriver specification and the Appium extensions of it.
 * The command table is adapted from Selenium's {@code W3CHttpCommandCodec} (Apache License 2.0),
 * without the commands implemented there as browser scripts, which do not work with Appium.
 */
public class AppiumW3CHttpCommandCodec implements CommandCodec {
    private static final String JSON_UTF_8 = "application/json; charset=utf-8";
    private static final String SESSION_ID_PARAM = "sessionId";

    private final Map<String, CommandSpec> nameToSpec = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();

    /**
     * Creates the codec with the standard commands defined.
     */
    public AppiumW3CHttpCommandCodec() {
        final String sessionId = "/session/:sessionId";
        final String window = sessionId + "/window";
        final String elementId = sessionId + "/element/:id";
        final String cookie = sessionId + "/cookie";
        final String timeouts = sessionId + "/timeouts";
        final String alert = sessionId + "/alert";

        defineCommand(STATUS, get("/status"));
        defineCommand(NEW_SESSION, post("/session"));
        defineCommand(GET_CAPABILITIES, get(sessionId));
        defineCommand(QUIT, delete(sessionId));

        defineCommand(SWITCH_TO_FRAME, post(sessionId + "/frame"));
        defineCommand(SWITCH_TO_PARENT_FRAME, post(sessionId + "/frame/parent"));

        defineCommand(CLOSE, delete(window));
        defineCommand(SWITCH_TO_WINDOW, post(window));
        defineCommand(SWITCH_TO_NEW_WINDOW, post(window + "/new"));
        defineCommand(FULLSCREEN_CURRENT_WINDOW, post(window + "/fullscreen"));
        defineCommand(MAXIMIZE_CURRENT_WINDOW, post(window + "/maximize"));
        defineCommand(MINIMIZE_CURRENT_WINDOW, post(window + "/minimize"));
        defineCommand(GET_CURRENT_WINDOW_SIZE, get(window + "/rect"));
        defineCommand(SET_CURRENT_WINDOW_SIZE, post(window + "/rect"));
        alias(GET_CURRENT_WINDOW_POSITION, GET_CURRENT_WINDOW_SIZE);
        alias(SET_CURRENT_WINDOW_POSITION, SET_CURRENT_WINDOW_SIZE);
        defineCommand(GET_CURRENT_WINDOW_HANDLE, get(window));
        defineCommand(GET_WINDOW_HANDLES, get(window + "/handles"));

        defineCommand(GET_CURRENT_URL, get(sessionId + "/url"));
        defineCommand(GET, post(sessionId + "/url"));
        defineCommand(GO_BACK, post(sessionId + "/back"));
        defineCommand(GO_FORWARD, post(sessionId + "/forward"));
        defineCommand(REFRESH, post(sessionId + "/refresh"));
        defineCommand(GET_TITLE, get(sessionId + "/title"));
        // Appium returns the source of the application, not of a web page
        defineCommand(GET_PAGE_SOURCE, get(sessionId + "/source"));
        defineCommand(SCREENSHOT, get(sessionId + "/screenshot"));
        defineCommand(ELEMENT_SCREENSHOT, get(elementId + "/screenshot"));
        defineCommand(PRINT_PAGE, post(sessionId + "/print"));

        defineCommand(EXECUTE_SCRIPT, post(sessionId + "/execute/sync"));
        defineCommand(EXECUTE_ASYNC_SCRIPT, post(sessionId + "/execute/async"));

        defineCommand(FIND_ELEMENT, post(sessionId + "/element"));
        defineCommand(FIND_ELEMENTS, post(sessionId + "/elements"));
        defineCommand(GET_ACTIVE_ELEMENT, get(sessionId + "/element/active"));
        defineCommand(FIND_CHILD_ELEMENT, post(elementId + "/element"));
        defineCommand(FIND_CHILD_ELEMENTS, post(elementId + "/elements"));
        defineCommand(CLICK_ELEMENT, post(elementId + "/click"));
        defineCommand(CLEAR_ELEMENT, post(elementId + "/clear"));
        defineCommand(SEND_KEYS_TO_ELEMENT, post(elementId + "/value"));
        defineCommand(GET_ELEMENT_VALUE_OF_CSS_PROPERTY, get(elementId + "/css/:propertyName"));
        defineCommand(IS_ELEMENT_ENABLED, get(elementId + "/enabled"));
        defineCommand(IS_ELEMENT_SELECTED, get(elementId + "/selected"));
        // Appium evaluates the visibility natively instead of by a browser script
        defineCommand(IS_ELEMENT_DISPLAYED, get(elementId + "/displayed"));
        defineCommand(GET_ELEMENT_RECT, get(elementId + "/rect"));
        defineCommand(GET_ELEMENT_TAG_NAME, get(elementId + "/name"));
        defineCommand(GET_ELEMENT_TEXT, get(elementId + "/text"));
        defineCommand(GET_ELEMENT_DOM_PROPERTY, get(elementId + "/property/:name"));
        defineCommand(GET_ELEMENT_DOM_ATTRIBUTE, get(elementId + "/attribute/:name"));
        defineCommand(GET_ELEMENT_ATTRIBUTE, get(elementId + "/attribute/:name"));
        defineCommand(GET_ELEMENT_ARIA_ROLE, get(elementId + "/computedrole"));
        defineCommand(GET_ELEMENT_ACCESSIBLE_NAME, get(elementId + "/computedlabel"));
        defineCommand(GET_ELEMENT_SHADOW_ROOT, get(elementId + "/shadow"));
        defineCommand(FIND_ELEMENT_FROM_SHADOW_ROOT, post(sessionId + "/shadow/:shadowId/element"));
        defineCommand(FIND_ELEMENTS_FROM_SHADOW_ROOT, post(sessionId + "/shadow/:shadowId/elements"));

        defineCommand(GET_ALL_COOKIES, get(cookie));
        defineCommand(GET_COOKIE, get(cookie + "/:name"));
        defineCommand(ADD_COOKIE, post(cookie));
        defineCommand(DELETE_ALL_COOKIES, delete(cookie));
        defineCommand(DELETE_COOKIE, delete(cookie + "/:name"));

        defineCommand(GET_TIMEOUTS, get(timeouts));
        defineCommand(SET_TIMEOUT, post(timeouts));
        defineCommand(SET_SCRIPT_TIMEOUT, post(timeouts + "/async_script"));
        defineCommand(IMPLICITLY_WAIT, post(timeouts + "/implicit_wait"));

        defineCommand(ACCEPT_ALERT, post(alert + "/accept"));
        defineCommand(DISMISS_ALERT, post(alert + "/dismiss"));
        defineCommand(GET_ALERT_TEXT, get(alert + "/text"));
        defineCommand(SET_ALERT_VALUE, post(alert + "/text"));

        defineCommand(ACTIONS, post(sessionId + "/actions"));
        defineCommand(CLEAR_ACTIONS_STATE, delete(sessionId + "/actions"));

        final String webauthn = sessionId + "/webauthn/authenticator";
        final String webauthnId = webauthn + "/:authenticatorId";
        defineCommand(ADD_VIRTUAL_AUTHENTICATOR, post(webauthn));
        defineCommand(REMOVE_VIRTUAL_AUTHENTICATOR, delete(webauthnId));
        defineCommand(ADD_CREDENTIAL, post(webauthnId + "/credential"));
        defineCommand(GET_CREDENTIALS, get(webauthnId + "/credentials"));
        defineCommand(REMOVE_CREDENTIAL, delete(webauthnId + "/credentials/:credentialId"));
        defineCommand(REMOVE_ALL_CREDENTIALS, delete(webauthnId + "/credentials"));
        defineCommand(SET_USER_VERIFIED, post(webauthnId + "/uv"));

        final String fedcm = sessionId + "/fedcm";
        defineCommand(CANCEL_DIALOG, post(fedcm + "/canceldialog"));
        defineCommand(SELECT_ACCOUNT, post(fedcm + "/selectaccount"));
        defineCommand(CLICK_DIALOG, post(fedcm + "/clickdialogbutton"));
        defineCommand(GET_ACCOUNTS, get(fedcm + "/accountlist"));
        defineCommand(GET_FEDCM_TITLE, get(fedcm + "/gettitle"));
        defineCommand(GET_FEDCM_DIALOG_TYPE, get(fedcm + "/getdialogtype"));
        defineCommand(SET_DELAY_ENABLED, post(fedcm + "/setdelayenabled"));
        defineCommand(RESET_COOLDOWN, post(fedcm + "/resetcooldown"));

        defineCommand(GET_LOG, post(sessionId + "/se/log"));
        defineCommand(GET_AVAILABLE_LOG_TYPES, get(sessionId + "/se/log/types"));
    }

    private static CommandSpec get(String path) {
        return new CommandSpec(HttpMethod.GET, path);
    }

    private static CommandSpec post(String path) {
        return new CommandSpec(HttpMethod.POST, path);
    }

    private static CommandSpec delete(String path) {
        return new CommandSpec(HttpMethod.DELETE, path);
    }

    private void defineCommand(String name, CommandSpec spec) {
        nameToSpec.put(name, spec);
    }

    @Override
    public void defineCommand(String name, HttpMethod method, String pathPattern) {
        defineCommand(name, new CommandSpec(method, pathPattern));
    }

    /**
     * Makes a command use the definition of another command.
     *
     * @param commandName the command name
     * @param isAnAliasFor the name of the command that defines it
     */
    public void alias(String commandName, String isAnAliasFor) {
        aliases.put(commandName, isAnAliasFor);
    }

    @Override
    public boolean isSupported(String commandName) {
        return nameToSpec.containsKey(commandName) || aliases.containsKey(commandName);
    }

    @Override
    public HttpRequest encode(Command command) {
        String name = aliases.getOrDefault(command.getName(), command.getName());
        CommandSpec spec = nameToSpec.get(name);
        if (spec == null) {
            throw new UnsupportedCommandException(command.getName());
        }
        Map<String, ?> parameters = amendParameters(command.getName(), command.getParameters());
        String uri = spec.buildUri(name, command.getSessionId() == null ? null : command.getSessionId().toString(),
                parameters);

        HttpRequest request = new HttpRequest(spec.method, uri);
        if (HttpMethod.POST == spec.method) {
            byte[] data = WireJson.toJson(parameters).getBytes(UTF_8);
            request.setHeader(HttpHeader.ContentLength.getName(), String.valueOf(data.length));
            request.setHeader(HttpHeader.ContentType.getName(), JSON_UTF_8);
            request.setContent(Contents.bytes(data));
        }
        if (HttpMethod.GET == spec.method) {
            request.setHeader(HttpHeader.CacheControl.getName(), "no-cache");
        }
        return request;
    }

    /**
     * Adjusts the parameters of a command before they are sent.
     *
     * @param name the command name
     * @param parameters the original parameters
     * @return the parameters to send
     */
    @SuppressWarnings("unchecked")
    protected Map<String, ?> amendParameters(String name, Map<String, ?> parameters) {
        switch (name) {
            case SEND_KEYS_TO_ELEMENT:
                // When converted from JSON this is a collection, otherwise it is an array
                Object rawValue = parameters.get("value");
                Stream<CharSequence> source = rawValue instanceof Collection
                        ? ((Collection<CharSequence>) rawValue).stream()
                        : Stream.of((CharSequence[]) rawValue);
                String text = source.collect(Collectors.joining());
                Map<String, Object> merged = new LinkedHashMap<>();
                parameters.forEach((key, val) -> {
                    if (!"text".equals(key) && !"value".equals(key)) {
                        merged.put(key, val);
                    }
                });
                merged.put("text", text);
                merged.put("value", stringToCodePoints(text));
                return Map.copyOf(merged);
            case SET_TIMEOUT:
                String timeoutType = (String) parameters.get("type");
                if (timeoutType == null) {
                    // The caller follows the specification already
                    return parameters;
                }
                Map<String, Object> amended = new LinkedHashMap<>();
                parameters.forEach((key, val) -> {
                    if (!timeoutType.equals(key)) {
                        amended.put(key, val);
                    }
                });
                amended.put(timeoutType, parameters.get("ms"));
                return Map.copyOf(amended);
            default:
                return parameters;
        }
    }

    private static List<String> stringToCodePoints(String toConvert) {
        List<String> result = new ArrayList<>();
        int offset = 0;
        while (offset < toConvert.length()) {
            int next = toConvert.codePointAt(offset);
            result.add(new StringBuilder().appendCodePoint(next).toString());
            offset += Character.charCount(next);
        }
        return result;
    }

    private static final class CommandSpec {
        private final HttpMethod method;
        private final List<String> pathSegments;

        CommandSpec(HttpMethod method, String path) {
            this.method = method;
            this.pathSegments = Arrays.stream(path.split("/")).filter(e -> !e.isEmpty())
                    .collect(Collectors.toUnmodifiableList());
        }

        String buildUri(String commandName, @Nullable String sessionId, Map<String, ?> parameters) {
            StringBuilder builder = new StringBuilder();
            for (String part : pathSegments) {
                builder.append('/');
                builder.append(part.startsWith(":")
                        ? parameter(part.substring(1), commandName, sessionId, parameters) : part);
            }
            return builder.toString();
        }

        private static String parameter(String name, String commandName, @Nullable String sessionId,
                                        Map<String, ?> parameters) {
            if (SESSION_ID_PARAM.equals(name)) {
                if (sessionId == null) {
                    throw new IllegalArgumentException(
                            String.format("Session id may not be null for command %s", commandName));
                }
                return sessionId;
            }
            Object value = parameters.get(name);
            if (value == null) {
                throw new IllegalArgumentException(
                        String.format("Missing required parameter \"%s\" for command %s", name, commandName));
            }
            return URLEncoder.encode(String.valueOf(value), UTF_8);
        }
    }
}
