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

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.print.PrintOptions;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static java.util.Objects.requireNonNull;

/**
 * The names of the standard WebDriver commands, adapted from Selenium's
 * {@code org.openqa.selenium.remote.DriverCommand} (Apache License 2.0).
 * Appium-specific commands are defined in {@link io.appium.java_client.MobileCommand}.
 */
@SuppressWarnings({"checkstyle:MethodName", "checkstyle:AbbreviationAsWordInName"})
public final class DriverCommand {
    public static final String GET_CAPABILITIES = "getCapabilities";
    public static final String NEW_SESSION = "newSession";
    public static final String STATUS = "status";
    public static final String CLOSE = "close";
    public static final String QUIT = "quit";
    public static final String GET = "get";
    public static final String GO_BACK = "goBack";
    public static final String GO_FORWARD = "goForward";
    public static final String REFRESH = "refresh";
    public static final String ADD_COOKIE = "addCookie";
    public static final String GET_ALL_COOKIES = "getCookies";
    public static final String GET_COOKIE = "getCookie";
    public static final String DELETE_COOKIE = "deleteCookie";
    public static final String DELETE_ALL_COOKIES = "deleteAllCookies";
    public static final String FIND_ELEMENT = "findElement";
    public static final String FIND_ELEMENTS = "findElements";
    public static final String FIND_CHILD_ELEMENT = "findChildElement";
    public static final String FIND_CHILD_ELEMENTS = "findChildElements";
    public static final String CLEAR_ELEMENT = "clearElement";
    public static final String CLICK_ELEMENT = "clickElement";
    public static final String SEND_KEYS_TO_ELEMENT = "sendKeysToElement";
    public static final String SUBMIT_ELEMENT = "submitElement";
    public static final String GET_CURRENT_WINDOW_HANDLE = "getCurrentWindowHandle";
    public static final String GET_WINDOW_HANDLES = "getWindowHandles";
    public static final String SWITCH_TO_WINDOW = "switchToWindow";
    public static final String SWITCH_TO_NEW_WINDOW = "newWindow";
    public static final String SWITCH_TO_FRAME = "switchToFrame";
    public static final String SWITCH_TO_PARENT_FRAME = "switchToParentFrame";
    public static final String GET_ACTIVE_ELEMENT = "getActiveElement";
    public static final String GET_CURRENT_URL = "getCurrentUrl";
    public static final String GET_PAGE_SOURCE = "getPageSource";
    public static final String GET_TITLE = "getTitle";
    public static final String EXECUTE_SCRIPT = "executeScript";
    public static final String EXECUTE_ASYNC_SCRIPT = "executeAsyncScript";
    public static final String GET_ELEMENT_TEXT = "getElementText";
    public static final String GET_ELEMENT_TAG_NAME = "getElementTagName";
    public static final String IS_ELEMENT_SELECTED = "isElementSelected";
    public static final String IS_ELEMENT_ENABLED = "isElementEnabled";
    public static final String IS_ELEMENT_DISPLAYED = "isElementDisplayed";
    public static final String GET_ELEMENT_RECT = "getElementRect";
    public static final String GET_ELEMENT_LOCATION = "getElementLocation";
    public static final String GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW = "getElementLocationOnceScrolledIntoView";
    public static final String GET_ELEMENT_SIZE = "getElementSize";
    public static final String GET_ELEMENT_DOM_PROPERTY = "getElementDomProperty";
    public static final String GET_ELEMENT_DOM_ATTRIBUTE = "getElementDomAttribute";
    public static final String GET_ELEMENT_ATTRIBUTE = "getElementAttribute";
    public static final String GET_ELEMENT_VALUE_OF_CSS_PROPERTY = "getElementValueOfCssProperty";
    public static final String GET_ELEMENT_ARIA_ROLE = "getElementAriaRole";
    public static final String GET_ELEMENT_ACCESSIBLE_NAME = "getElementAccessibleName";
    public static final String SCREENSHOT = "screenshot";
    public static final String ELEMENT_SCREENSHOT = "elementScreenshot";
    public static final String ACCEPT_ALERT = "acceptAlert";
    public static final String DISMISS_ALERT = "dismissAlert";
    public static final String GET_ALERT_TEXT = "getAlertText";
    public static final String SET_ALERT_VALUE = "setAlertValue";
    public static final String GET_TIMEOUTS = "getTimeouts";
    public static final String SET_TIMEOUT = "setTimeout";
    public static final String PRINT_PAGE = "printPage";
    public static final String IMPLICITLY_WAIT = "implicitlyWait";
    public static final String SET_SCRIPT_TIMEOUT = "setScriptTimeout";
    public static final String ACTIONS = "actions";
    public static final String CLEAR_ACTIONS_STATE = "clearActionState";
    public static final String SET_CURRENT_WINDOW_POSITION = "setWindowPosition";
    public static final String GET_CURRENT_WINDOW_POSITION = "getWindowPosition";
    public static final String SET_CURRENT_WINDOW_SIZE = "setCurrentWindowSize";
    public static final String GET_CURRENT_WINDOW_SIZE = "getCurrentWindowSize";
    public static final String MAXIMIZE_CURRENT_WINDOW = "maximizeCurrentWindow";
    public static final String MINIMIZE_CURRENT_WINDOW = "minimizeCurrentWindow";
    public static final String FULLSCREEN_CURRENT_WINDOW = "fullscreenCurrentWindow";
    public static final String GET_AVAILABLE_LOG_TYPES = "getAvailableLogTypes";
    public static final String GET_LOG = "getLog";

    private DriverCommand() {
    }

    public static CommandPayload NEW_SESSION(Capabilities capabilities) {
        return NEW_SESSION(singleton(requireNonNull(capabilities, "Capabilities")));
    }

    /**
     * Creates the new session payload.
     *
     * @param capabilities the alternative capabilities to match, must not be empty
     * @return the payload
     */
    public static CommandPayload NEW_SESSION(Collection<Capabilities> capabilities) {
        requireNonNull(capabilities, "Capabilities");
        if (capabilities.isEmpty()) {
            throw new IllegalArgumentException("Capabilities for new session must not be empty");
        }
        return new CommandPayload(NEW_SESSION, Map.of("capabilities", capabilities));
    }

    public static CommandPayload GET(String url) {
        return new CommandPayload(GET, Map.of("url", url));
    }

    public static CommandPayload ADD_COOKIE(Cookie cookie) {
        return new CommandPayload(ADD_COOKIE, Map.of("cookie", cookie));
    }

    public static CommandPayload DELETE_COOKIE(String name) {
        return new CommandPayload(DELETE_COOKIE, Map.of("name", name));
    }

    public static CommandPayload FIND_ELEMENT(String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENT, Map.of("using", strategy, "value", value));
    }

    public static CommandPayload FIND_ELEMENTS(String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENTS, Map.of("using", strategy, "value", value));
    }

    public static CommandPayload FIND_CHILD_ELEMENT(String id, String strategy, Object value) {
        return new CommandPayload(FIND_CHILD_ELEMENT, Map.of("id", id, "using", strategy, "value", value));
    }

    public static CommandPayload FIND_CHILD_ELEMENTS(String id, String strategy, Object value) {
        return new CommandPayload(FIND_CHILD_ELEMENTS, Map.of("id", id, "using", strategy, "value", value));
    }

    public static CommandPayload CLEAR_ELEMENT(String id) {
        return new CommandPayload(CLEAR_ELEMENT, Map.of("id", id));
    }

    public static CommandPayload CLICK_ELEMENT(String id) {
        return new CommandPayload(CLICK_ELEMENT, Map.of("id", id));
    }

    public static CommandPayload SEND_KEYS_TO_ELEMENT(String id, CharSequence[] keysToSend) {
        return new CommandPayload(SEND_KEYS_TO_ELEMENT, Map.of("id", id, "value", keysToSend));
    }

    public static CommandPayload SUBMIT_ELEMENT(String id) {
        return new CommandPayload(SUBMIT_ELEMENT, Map.of("id", id));
    }

    public static CommandPayload SWITCH_TO_WINDOW(String windowHandleOrName) {
        return new CommandPayload(SWITCH_TO_WINDOW, Map.of("handle", windowHandleOrName));
    }

    public static CommandPayload SWITCH_TO_NEW_WINDOW(WindowType typeHint) {
        return new CommandPayload(SWITCH_TO_NEW_WINDOW, Map.of("type", typeHint.toString()));
    }

    public static CommandPayload SWITCH_TO_FRAME(@Nullable Object frame) {
        return new CommandPayload(SWITCH_TO_FRAME, singletonMap("id", frame));
    }

    public static CommandPayload EXECUTE_SCRIPT(String script, List<Object> args) {
        return new CommandPayload(EXECUTE_SCRIPT, Map.of("script", script, "args", args));
    }

    public static CommandPayload EXECUTE_ASYNC_SCRIPT(String script, List<Object> args) {
        return new CommandPayload(EXECUTE_ASYNC_SCRIPT, Map.of("script", script, "args", args));
    }

    public static CommandPayload GET_ELEMENT_TEXT(String id) {
        return new CommandPayload(GET_ELEMENT_TEXT, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_TAG_NAME(String id) {
        return new CommandPayload(GET_ELEMENT_TAG_NAME, Map.of("id", id));
    }

    public static CommandPayload IS_ELEMENT_SELECTED(String id) {
        return new CommandPayload(IS_ELEMENT_SELECTED, Map.of("id", id));
    }

    public static CommandPayload IS_ELEMENT_ENABLED(String id) {
        return new CommandPayload(IS_ELEMENT_ENABLED, Map.of("id", id));
    }

    public static CommandPayload IS_ELEMENT_DISPLAYED(String id) {
        return new CommandPayload(IS_ELEMENT_DISPLAYED, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_RECT(String id) {
        return new CommandPayload(GET_ELEMENT_RECT, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_LOCATION(String id) {
        return new CommandPayload(GET_ELEMENT_LOCATION, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW(String id) {
        return new CommandPayload(GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_SIZE(String id) {
        return new CommandPayload(GET_ELEMENT_SIZE, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_DOM_PROPERTY(String id, String name) {
        return new CommandPayload(GET_ELEMENT_DOM_PROPERTY, Map.of("id", id, "name", name));
    }

    public static CommandPayload GET_ELEMENT_DOM_ATTRIBUTE(String id, String name) {
        return new CommandPayload(GET_ELEMENT_DOM_ATTRIBUTE, Map.of("id", id, "name", name));
    }

    public static CommandPayload GET_ELEMENT_ATTRIBUTE(String id, String name) {
        return new CommandPayload(GET_ELEMENT_ATTRIBUTE, Map.of("id", id, "name", name));
    }

    public static CommandPayload GET_ELEMENT_VALUE_OF_CSS_PROPERTY(String id, String name) {
        return new CommandPayload(GET_ELEMENT_VALUE_OF_CSS_PROPERTY, Map.of("id", id, "propertyName", name));
    }

    public static CommandPayload GET_ELEMENT_ARIA_ROLE(String id) {
        return new CommandPayload(GET_ELEMENT_ARIA_ROLE, Map.of("id", id));
    }

    public static CommandPayload GET_ELEMENT_ACCESSIBLE_NAME(String id) {
        return new CommandPayload(GET_ELEMENT_ACCESSIBLE_NAME, Map.of("id", id));
    }

    public static CommandPayload ELEMENT_SCREENSHOT(String id) {
        return new CommandPayload(ELEMENT_SCREENSHOT, Map.of("id", id));
    }

    public static CommandPayload SET_ALERT_VALUE(String keysToSend) {
        return new CommandPayload(SET_ALERT_VALUE, Map.of("text", keysToSend));
    }

    public static CommandPayload PRINT_PAGE(PrintOptions options) {
        return new CommandPayload(PRINT_PAGE, options.toMap());
    }

    public static CommandPayload SET_IMPLICIT_WAIT_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("implicit", duration.toMillis()));
    }

    public static CommandPayload SET_SCRIPT_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("script", duration.toMillis()));
    }

    public static CommandPayload SET_PAGE_LOAD_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("pageLoad", duration.toMillis()));
    }

    public static CommandPayload ACTIONS(Collection<Sequence> actions) {
        return new CommandPayload(ACTIONS, Map.of("actions", actions));
    }

    public static CommandPayload SET_CURRENT_WINDOW_POSITION(Point targetPosition) {
        return new CommandPayload(SET_CURRENT_WINDOW_POSITION, Map.of("x", targetPosition.x, "y", targetPosition.y));
    }

    public static CommandPayload GET_CURRENT_WINDOW_POSITION() {
        return new CommandPayload(GET_CURRENT_WINDOW_POSITION, Map.of("windowHandle", "current"));
    }

    public static CommandPayload SET_CURRENT_WINDOW_SIZE(Dimension targetSize) {
        return new CommandPayload(SET_CURRENT_WINDOW_SIZE,
                Map.of("width", targetSize.width, "height", targetSize.height));
    }
}
