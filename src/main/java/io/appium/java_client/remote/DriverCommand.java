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
    /** Gets the capabilities of the session. */
    public static final String GET_CAPABILITIES = "getCapabilities";
    /** Creates a new session. */
    public static final String NEW_SESSION = "newSession";
    /** Gets the status of the server. */
    public static final String STATUS = "status";
    /** Closes the current window. */
    public static final String CLOSE = "close";
    /** Ends the session. */
    public static final String QUIT = "quit";
    /** Navigates to the given URL. */
    public static final String GET = "get";
    /** Navigates back in the browser history. */
    public static final String GO_BACK = "goBack";
    /** Navigates forward in the browser history. */
    public static final String GO_FORWARD = "goForward";
    /** Reloads the current page. */
    public static final String REFRESH = "refresh";
    /** Adds a cookie. */
    public static final String ADD_COOKIE = "addCookie";
    /** Gets all cookies. */
    public static final String GET_ALL_COOKIES = "getCookies";
    /** Gets a cookie by name. */
    public static final String GET_COOKIE = "getCookie";
    /** Deletes a cookie by name. */
    public static final String DELETE_COOKIE = "deleteCookie";
    /** Deletes all cookies. */
    public static final String DELETE_ALL_COOKIES = "deleteAllCookies";
    /** Finds an element. */
    public static final String FIND_ELEMENT = "findElement";
    /** Finds multiple elements. */
    public static final String FIND_ELEMENTS = "findElements";
    /** Finds an element inside another element. */
    public static final String FIND_CHILD_ELEMENT = "findChildElement";
    /** Finds multiple elements inside another element. */
    public static final String FIND_CHILD_ELEMENTS = "findChildElements";
    /** Clears the content of an element. */
    public static final String CLEAR_ELEMENT = "clearElement";
    /** Clicks an element. */
    public static final String CLICK_ELEMENT = "clickElement";
    /** Sends keys to an element. */
    public static final String SEND_KEYS_TO_ELEMENT = "sendKeysToElement";
    /** Submits the form an element belongs to. */
    public static final String SUBMIT_ELEMENT = "submitElement";
    /** Gets the handle of the current window. */
    public static final String GET_CURRENT_WINDOW_HANDLE = "getCurrentWindowHandle";
    /** Gets the handles of all windows. */
    public static final String GET_WINDOW_HANDLES = "getWindowHandles";
    /** Switches to the given window. */
    public static final String SWITCH_TO_WINDOW = "switchToWindow";
    /** Creates a new window or tab and switches to it. */
    public static final String SWITCH_TO_NEW_WINDOW = "newWindow";
    /** Switches to the given frame. */
    public static final String SWITCH_TO_FRAME = "switchToFrame";
    /** Switches to the parent of the current frame. */
    public static final String SWITCH_TO_PARENT_FRAME = "switchToParentFrame";
    /** Gets the element that currently has focus. */
    public static final String GET_ACTIVE_ELEMENT = "getActiveElement";
    /** Gets the URL of the current page. */
    public static final String GET_CURRENT_URL = "getCurrentUrl";
    /** Gets the source of the current page. */
    public static final String GET_PAGE_SOURCE = "getPageSource";
    /** Gets the title of the current page. */
    public static final String GET_TITLE = "getTitle";
    /** Executes a script synchronously. */
    public static final String EXECUTE_SCRIPT = "executeScript";
    /** Executes a script asynchronously. */
    public static final String EXECUTE_ASYNC_SCRIPT = "executeAsyncScript";
    /** Gets the visible text of an element. */
    public static final String GET_ELEMENT_TEXT = "getElementText";
    /** Gets the tag name of an element. */
    public static final String GET_ELEMENT_TAG_NAME = "getElementTagName";
    /** Checks whether an element is selected. */
    public static final String IS_ELEMENT_SELECTED = "isElementSelected";
    /** Checks whether an element is enabled. */
    public static final String IS_ELEMENT_ENABLED = "isElementEnabled";
    /** Checks whether an element is displayed. */
    public static final String IS_ELEMENT_DISPLAYED = "isElementDisplayed";
    /** Gets the position and the size of an element. */
    public static final String GET_ELEMENT_RECT = "getElementRect";
    /** Gets the position of an element. */
    public static final String GET_ELEMENT_LOCATION = "getElementLocation";
    /** Gets the position of an element after it has been scrolled into view. */
    public static final String GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW = "getElementLocationOnceScrolledIntoView";
    /** Gets the size of an element. */
    public static final String GET_ELEMENT_SIZE = "getElementSize";
    /** Gets a DOM property of an element. */
    public static final String GET_ELEMENT_DOM_PROPERTY = "getElementDomProperty";
    /** Gets a DOM attribute of an element. */
    public static final String GET_ELEMENT_DOM_ATTRIBUTE = "getElementDomAttribute";
    /** Gets an attribute or a property of an element. */
    public static final String GET_ELEMENT_ATTRIBUTE = "getElementAttribute";
    /** Gets the value of a CSS property of an element. */
    public static final String GET_ELEMENT_VALUE_OF_CSS_PROPERTY = "getElementValueOfCssProperty";
    /** Gets the ARIA role of an element. */
    public static final String GET_ELEMENT_ARIA_ROLE = "getElementAriaRole";
    /** Gets the accessible name of an element. */
    public static final String GET_ELEMENT_ACCESSIBLE_NAME = "getElementAccessibleName";
    /** Gets the shadow root of an element. */
    public static final String GET_ELEMENT_SHADOW_ROOT = "getElementShadowRoot";
    /** Finds an element inside a shadow root. */
    public static final String FIND_ELEMENT_FROM_SHADOW_ROOT = "findElementFromShadowRoot";
    /** Finds multiple elements inside a shadow root. */
    public static final String FIND_ELEMENTS_FROM_SHADOW_ROOT = "findElementsFromShadowRoot";
    /** Adds a virtual authenticator. */
    public static final String ADD_VIRTUAL_AUTHENTICATOR = "addVirtualAuthenticator";
    /** Removes a virtual authenticator. */
    public static final String REMOVE_VIRTUAL_AUTHENTICATOR = "removeVirtualAuthenticator";
    /** Adds a credential to a virtual authenticator. */
    public static final String ADD_CREDENTIAL = "addCredential";
    /** Gets the credentials of a virtual authenticator. */
    public static final String GET_CREDENTIALS = "getCredentials";
    /** Removes a credential from a virtual authenticator. */
    public static final String REMOVE_CREDENTIAL = "removeCredential";
    /** Removes all credentials from a virtual authenticator. */
    public static final String REMOVE_ALL_CREDENTIALS = "removeAllCredentials";
    /** Sets whether the virtual authenticator reports the user as verified. */
    public static final String SET_USER_VERIFIED = "setUserVerified";
    /** Cancels the federated credential management dialog. */
    public static final String CANCEL_DIALOG = "cancelDialog";
    /** Selects an account in the federated credential management dialog. */
    public static final String SELECT_ACCOUNT = "selectAccount";
    /** Clicks the button of the federated credential management dialog. */
    public static final String CLICK_DIALOG = "clickDialog";
    /** Gets the accounts of the federated credential management dialog. */
    public static final String GET_ACCOUNTS = "getAccounts";
    /** Gets the title of the federated credential management dialog. */
    public static final String GET_FEDCM_TITLE = "getFedCmTitle";
    /** Gets the type of the federated credential management dialog. */
    public static final String GET_FEDCM_DIALOG_TYPE = "getFedCmDialogType";
    /** Enables or disables the delay of the federated credential management dialog. */
    public static final String SET_DELAY_ENABLED = "setDelayEnabled";
    /** Resets the cooldown of the federated credential management dialog. */
    public static final String RESET_COOLDOWN = "resetCooldown";
    /** Takes a screenshot of the page. */
    public static final String SCREENSHOT = "screenshot";
    /** Takes a screenshot of an element. */
    public static final String ELEMENT_SCREENSHOT = "elementScreenshot";
    /** Accepts the alert. */
    public static final String ACCEPT_ALERT = "acceptAlert";
    /** Dismisses the alert. */
    public static final String DISMISS_ALERT = "dismissAlert";
    /** Gets the text of the alert. */
    public static final String GET_ALERT_TEXT = "getAlertText";
    /** Types text into the alert prompt. */
    public static final String SET_ALERT_VALUE = "setAlertValue";
    /** Gets the session timeouts. */
    public static final String GET_TIMEOUTS = "getTimeouts";
    /** Sets a session timeout. */
    public static final String SET_TIMEOUT = "setTimeout";
    /** Prints the page to PDF. */
    public static final String PRINT_PAGE = "printPage";
    /** Sets the implicit wait timeout. */
    public static final String IMPLICITLY_WAIT = "implicitlyWait";
    /** Sets the script timeout. */
    public static final String SET_SCRIPT_TIMEOUT = "setScriptTimeout";
    /** Performs a sequence of input actions. */
    public static final String ACTIONS = "actions";
    /** Releases all input actions that are currently pressed. */
    public static final String CLEAR_ACTIONS_STATE = "clearActionState";
    /** Sets the position of the current window. */
    public static final String SET_CURRENT_WINDOW_POSITION = "setWindowPosition";
    /** Gets the position of the current window. */
    public static final String GET_CURRENT_WINDOW_POSITION = "getWindowPosition";
    /** Sets the size of the current window. */
    public static final String SET_CURRENT_WINDOW_SIZE = "setCurrentWindowSize";
    /** Gets the size of the current window. */
    public static final String GET_CURRENT_WINDOW_SIZE = "getCurrentWindowSize";
    /** Maximizes the current window. */
    public static final String MAXIMIZE_CURRENT_WINDOW = "maximizeCurrentWindow";
    /** Minimizes the current window. */
    public static final String MINIMIZE_CURRENT_WINDOW = "minimizeCurrentWindow";
    /** Makes the current window fullscreen. */
    public static final String FULLSCREEN_CURRENT_WINDOW = "fullscreenCurrentWindow";
    /** Gets the available log types. */
    public static final String GET_AVAILABLE_LOG_TYPES = "getAvailableLogTypes";
    /** Gets the log of the given type. */
    public static final String GET_LOG = "getLog";

    private DriverCommand() {
    }

    /**
     * Creates the new session payload for a single set of capabilities.
     *
     * @param capabilities the capabilities
     * @return the command payload
     */
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

    /**
     * Creates the payload of the command that navigates to the given URL.
     *
     * @param url the URL to navigate to
     * @return the command payload
     */
    public static CommandPayload GET(String url) {
        return new CommandPayload(GET, Map.of("url", url));
    }

    /**
     * Creates the payload of the command that adds a cookie.
     *
     * @param cookie the cookie to add
     * @return the command payload
     */
    public static CommandPayload ADD_COOKIE(Cookie cookie) {
        return new CommandPayload(ADD_COOKIE, Map.of("cookie", cookie));
    }

    /**
     * Creates the payload of the command that deletes a cookie by name.
     *
     * @param name the cookie name
     * @return the command payload
     */
    public static CommandPayload DELETE_COOKIE(String name) {
        return new CommandPayload(DELETE_COOKIE, Map.of("name", name));
    }

    /**
     * Creates the payload of the command that finds an element.
     *
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_ELEMENT(String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENT, Map.of("using", strategy, "value", value));
    }

    /**
     * Creates the payload of the command that finds multiple elements.
     *
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_ELEMENTS(String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENTS, Map.of("using", strategy, "value", value));
    }

    /**
     * Creates the payload of the command that finds an element inside another element.
     *
     * @param id the element id
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_CHILD_ELEMENT(String id, String strategy, Object value) {
        return new CommandPayload(FIND_CHILD_ELEMENT, Map.of("id", id, "using", strategy, "value", value));
    }

    /**
     * Creates the payload of the command that finds multiple elements inside another element.
     *
     * @param id the element id
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_CHILD_ELEMENTS(String id, String strategy, Object value) {
        return new CommandPayload(FIND_CHILD_ELEMENTS, Map.of("id", id, "using", strategy, "value", value));
    }

    /**
     * Creates the payload of the command that clears the content of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload CLEAR_ELEMENT(String id) {
        return new CommandPayload(CLEAR_ELEMENT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that clicks an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload CLICK_ELEMENT(String id) {
        return new CommandPayload(CLICK_ELEMENT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that sends keys to an element.
     *
     * @param id the element id
     * @param keysToSend the keys to send
     * @return the command payload
     */
    public static CommandPayload SEND_KEYS_TO_ELEMENT(String id, CharSequence[] keysToSend) {
        return new CommandPayload(SEND_KEYS_TO_ELEMENT, Map.of("id", id, "value", keysToSend));
    }

    /**
     * Creates the payload of the command that submits the form an element belongs to.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload SUBMIT_ELEMENT(String id) {
        return new CommandPayload(SUBMIT_ELEMENT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that switches to the given window.
     *
     * @param windowHandleOrName the window handle or name
     * @return the command payload
     */
    public static CommandPayload SWITCH_TO_WINDOW(String windowHandleOrName) {
        return new CommandPayload(SWITCH_TO_WINDOW, Map.of("handle", windowHandleOrName));
    }

    /**
     * Creates the payload of the command that creates a new window or tab and switches to it.
     *
     * @param typeHint the type of the new window
     * @return the command payload
     */
    public static CommandPayload SWITCH_TO_NEW_WINDOW(WindowType typeHint) {
        return new CommandPayload(SWITCH_TO_NEW_WINDOW, Map.of("type", typeHint.toString()));
    }

    /**
     * Creates the payload of the command that switches to the given frame.
     *
     * @param frame the frame index, name, or element, or {@code null} for the top-level context
     * @return the command payload
     */
    public static CommandPayload SWITCH_TO_FRAME(@Nullable Object frame) {
        return new CommandPayload(SWITCH_TO_FRAME, singletonMap("id", frame));
    }

    /**
     * Creates the payload of the command that executes a script synchronously.
     *
     * @param script the script to execute
     * @param args the script arguments
     * @return the command payload
     */
    public static CommandPayload EXECUTE_SCRIPT(String script, List<Object> args) {
        return new CommandPayload(EXECUTE_SCRIPT, Map.of("script", script, "args", args));
    }

    /**
     * Creates the payload of the command that executes a script asynchronously.
     *
     * @param script the script to execute
     * @param args the script arguments
     * @return the command payload
     */
    public static CommandPayload EXECUTE_ASYNC_SCRIPT(String script, List<Object> args) {
        return new CommandPayload(EXECUTE_ASYNC_SCRIPT, Map.of("script", script, "args", args));
    }

    /**
     * Creates the payload of the command that gets the visible text of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_TEXT(String id) {
        return new CommandPayload(GET_ELEMENT_TEXT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the tag name of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_TAG_NAME(String id) {
        return new CommandPayload(GET_ELEMENT_TAG_NAME, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that checks whether an element is selected.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload IS_ELEMENT_SELECTED(String id) {
        return new CommandPayload(IS_ELEMENT_SELECTED, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that checks whether an element is enabled.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload IS_ELEMENT_ENABLED(String id) {
        return new CommandPayload(IS_ELEMENT_ENABLED, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that checks whether an element is displayed.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload IS_ELEMENT_DISPLAYED(String id) {
        return new CommandPayload(IS_ELEMENT_DISPLAYED, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the position and the size of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_RECT(String id) {
        return new CommandPayload(GET_ELEMENT_RECT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the position of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_LOCATION(String id) {
        return new CommandPayload(GET_ELEMENT_LOCATION, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the position of an element after it has been scrolled into view.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW(String id) {
        return new CommandPayload(GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the size of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_SIZE(String id) {
        return new CommandPayload(GET_ELEMENT_SIZE, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets a DOM property of an element.
     *
     * @param id the element id
     * @param name the property name
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_DOM_PROPERTY(String id, String name) {
        return new CommandPayload(GET_ELEMENT_DOM_PROPERTY, Map.of("id", id, "name", name));
    }

    /**
     * Creates the payload of the command that gets a DOM attribute of an element.
     *
     * @param id the element id
     * @param name the attribute name
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_DOM_ATTRIBUTE(String id, String name) {
        return new CommandPayload(GET_ELEMENT_DOM_ATTRIBUTE, Map.of("id", id, "name", name));
    }

    /**
     * Creates the payload of the command that gets an attribute or a property of an element.
     *
     * @param id the element id
     * @param name the attribute name
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_ATTRIBUTE(String id, String name) {
        return new CommandPayload(GET_ELEMENT_ATTRIBUTE, Map.of("id", id, "name", name));
    }

    /**
     * Creates the payload of the command that gets the value of a CSS property of an element.
     *
     * @param id the element id
     * @param name the CSS property name
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_VALUE_OF_CSS_PROPERTY(String id, String name) {
        return new CommandPayload(GET_ELEMENT_VALUE_OF_CSS_PROPERTY, Map.of("id", id, "propertyName", name));
    }

    /**
     * Creates the payload of the command that gets the ARIA role of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_ARIA_ROLE(String id) {
        return new CommandPayload(GET_ELEMENT_ARIA_ROLE, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the accessible name of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_ACCESSIBLE_NAME(String id) {
        return new CommandPayload(GET_ELEMENT_ACCESSIBLE_NAME, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that gets the shadow root of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload GET_ELEMENT_SHADOW_ROOT(String id) {
        return new CommandPayload(GET_ELEMENT_SHADOW_ROOT, Map.of("id", requireNonNull(id, "Element ID")));
    }

    /**
     * Creates the payload of the command that finds an element inside a shadow root.
     *
     * @param shadowId the shadow root id
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_ELEMENT_FROM_SHADOW_ROOT(String shadowId, String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENT_FROM_SHADOW_ROOT, shadowFinder(shadowId, strategy, value));
    }

    /**
     * Creates the payload of the command that finds multiple elements inside a shadow root.
     *
     * @param shadowId the shadow root id
     * @param strategy the locator strategy
     * @param value the locator value
     * @return the command payload
     */
    public static CommandPayload FIND_ELEMENTS_FROM_SHADOW_ROOT(String shadowId, String strategy, Object value) {
        return new CommandPayload(FIND_ELEMENTS_FROM_SHADOW_ROOT, shadowFinder(shadowId, strategy, value));
    }

    private static Map<String, Object> shadowFinder(String shadowId, String strategy, Object value) {
        return Map.of("shadowId", requireNonNull(shadowId, "Shadow root ID"),
                "using", requireNonNull(strategy, "Element finding strategy"),
                "value", requireNonNull(value, "Value for finding strategy"));
    }

    /**
     * Creates the payload of the command that selects an account in the federated credential management dialog.
     *
     * @param index the index of the account
     * @return the command payload
     */
    public static CommandPayload SELECT_ACCOUNT(int index) {
        return new CommandPayload(SELECT_ACCOUNT, Map.of("accountIndex", index));
    }

    /**
     * Creates the payload of the command that enables or disables the delay of the FedCM dialog.
     *
     * @param enabled whether the delay is enabled
     * @return the command payload
     */
    public static CommandPayload SET_DELAY_ENABLED(boolean enabled) {
        return new CommandPayload(SET_DELAY_ENABLED, Map.of("enabled", enabled));
    }

    /**
     * Creates the payload of the command that takes a screenshot of an element.
     *
     * @param id the element id
     * @return the command payload
     */
    public static CommandPayload ELEMENT_SCREENSHOT(String id) {
        return new CommandPayload(ELEMENT_SCREENSHOT, Map.of("id", id));
    }

    /**
     * Creates the payload of the command that types text into the alert prompt.
     *
     * @param keysToSend the text to type
     * @return the command payload
     */
    public static CommandPayload SET_ALERT_VALUE(String keysToSend) {
        return new CommandPayload(SET_ALERT_VALUE, Map.of("text", keysToSend));
    }

    /**
     * Creates the payload of the command that prints the page to PDF.
     *
     * @param options the print options
     * @return the command payload
     */
    public static CommandPayload PRINT_PAGE(PrintOptions options) {
        return new CommandPayload(PRINT_PAGE, options.toMap());
    }

    /**
     * Creates the payload of the command that sets the implicit wait timeout.
     *
     * @param duration the timeout value
     * @return the command payload
     */
    public static CommandPayload SET_IMPLICIT_WAIT_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("implicit", duration.toMillis()));
    }

    /**
     * Creates the payload of the command that sets the script timeout.
     *
     * @param duration the timeout value
     * @return the command payload
     */
    public static CommandPayload SET_SCRIPT_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("script", duration.toMillis()));
    }

    /**
     * Creates the payload of the command that sets the page load timeout.
     *
     * @param duration the timeout value
     * @return the command payload
     */
    public static CommandPayload SET_PAGE_LOAD_TIMEOUT(Duration duration) {
        return new CommandPayload(SET_TIMEOUT, Map.of("pageLoad", duration.toMillis()));
    }

    /**
     * Creates the payload of the command that performs a sequence of input actions.
     *
     * @param actions the action sequences
     * @return the command payload
     */
    public static CommandPayload ACTIONS(Collection<Sequence> actions) {
        return new CommandPayload(ACTIONS, Map.of("actions", actions));
    }

    /**
     * Creates the payload of the command that sets the position of the current window.
     *
     * @param targetPosition the target position
     * @return the command payload
     */
    public static CommandPayload SET_CURRENT_WINDOW_POSITION(Point targetPosition) {
        return new CommandPayload(SET_CURRENT_WINDOW_POSITION, Map.of("x", targetPosition.x, "y", targetPosition.y));
    }

    /**
     * Creates the payload of the command that gets the position of the current window.
     *
     * @return the command payload
     */
    public static CommandPayload GET_CURRENT_WINDOW_POSITION() {
        return new CommandPayload(GET_CURRENT_WINDOW_POSITION, Map.of("windowHandle", "current"));
    }

    /**
     * Creates the payload of the command that sets the size of the current window.
     *
     * @param targetSize the target size
     * @return the command payload
     */
    public static CommandPayload SET_CURRENT_WINDOW_SIZE(Dimension targetSize) {
        return new CommandPayload(SET_CURRENT_WINDOW_SIZE,
                Map.of("width", targetSize.width, "height", targetSize.height));
    }
}
