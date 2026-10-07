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
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchCookieException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchShadowRootException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnableToSetCookieException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps between W3C error codes, the legacy JSON wire protocol status codes and exception types.
 * Adapted from Selenium's {@code ErrorCodes} (Apache License 2.0).
 */
public class ErrorCodes {
    /** The W3C-style success status string. */
    public static final String SUCCESS_STRING = "success";
    /** The status code of a successful command. */
    public static final int SUCCESS = 0;
    /** The session does not exist. */
    public static final int NO_SUCH_SESSION = 6;
    /** The element could not be found. */
    public static final int NO_SUCH_ELEMENT = 7;
    /** The frame could not be found. */
    public static final int NO_SUCH_FRAME = 8;
    /** The command is not known to the server. */
    public static final int UNKNOWN_COMMAND = 9;
    /** The element is no longer attached to the document. */
    public static final int STALE_ELEMENT_REFERENCE = 10;
    /** The element is in a state that does not allow the operation. */
    public static final int INVALID_ELEMENT_STATE = 12;
    /** An unknown server-side error occurred. */
    public static final int UNHANDLED_ERROR = 13;
    /** The executed script has thrown an error. */
    public static final int JAVASCRIPT_ERROR = 17;
    /** An XPath lookup failed. */
    public static final int XPATH_LOOKUP_ERROR = 19;
    /** The operation has timed out. */
    public static final int TIMEOUT = 21;
    /** The window could not be found. */
    public static final int NO_SUCH_WINDOW = 23;
    /** The cookie domain is not valid for the current page. */
    public static final int INVALID_COOKIE_DOMAIN = 24;
    /** The cookie could not be set. */
    public static final int UNABLE_TO_SET_COOKIE = 25;
    /** An unexpected alert is open. */
    public static final int UNEXPECTED_ALERT_PRESENT = 26;
    /** There is no alert to operate on. */
    public static final int NO_ALERT_PRESENT = 27;
    /** The asynchronous script has timed out. */
    public static final int ASYNC_SCRIPT_TIMEOUT = 28;
    /** The element locator is not valid. */
    public static final int INVALID_SELECTOR_ERROR = 32;
    /** The session could not be created. */
    public static final int SESSION_NOT_CREATED = 33;
    /** The move target is outside of the viewport. */
    public static final int MOVE_TARGET_OUT_OF_BOUNDS = 34;
    /** The XPath expression is not valid. */
    public static final int INVALID_XPATH_SELECTOR = 51;
    /** The XPath expression does not return the expected type. */
    public static final int INVALID_XPATH_SELECTOR_RETURN_TYPER = 52;
    // The JSON wire protocol has no status codes for the W3C errors below, so they are made up
    /** The element cannot be interacted with. */
    public static final int ELEMENT_NOT_INTERACTABLE = 60;
    /** An argument of the command is not valid. */
    public static final int INVALID_ARGUMENT = 61;
    /** The cookie could not be found. */
    public static final int NO_SUCH_COOKIE = 62;
    /** The screenshot could not be taken. */
    public static final int UNABLE_TO_CAPTURE_SCREEN = 63;
    /** The click has been intercepted by another element. */
    public static final int ELEMENT_CLICK_INTERCEPTED = 64;
    /** The shadow root could not be found. */
    public static final int NO_SUCH_SHADOW_ROOT = 65;
    /** The HTTP method is not allowed for the endpoint. */
    public static final int METHOD_NOT_ALLOWED = 405;

    private static final List<KnownError> KNOWN_ERRORS = List.of(
            new KnownError(ASYNC_SCRIPT_TIMEOUT, "script timeout", 500, ScriptTimeoutException.class, true, true),
            new KnownError(ELEMENT_CLICK_INTERCEPTED, "element click intercepted", 400,
                    ElementClickInterceptedException.class, true, true),
            new KnownError(ELEMENT_NOT_INTERACTABLE, "element not interactable", 400,
                    ElementNotInteractableException.class, true, true),
            new KnownError(INVALID_ARGUMENT, "invalid argument", 400, InvalidArgumentException.class, true, true),
            new KnownError(INVALID_COOKIE_DOMAIN, "invalid cookie domain", 400,
                    InvalidCookieDomainException.class, true, true),
            new KnownError(INVALID_ELEMENT_STATE, "invalid element state", 400,
                    InvalidElementStateException.class, true, true),
            new KnownError(INVALID_SELECTOR_ERROR, "invalid selector", 400, InvalidSelectorException.class,
                    true, true),
            new KnownError(INVALID_XPATH_SELECTOR, "invalid selector", 400, InvalidSelectorException.class,
                    false, false),
            new KnownError(INVALID_XPATH_SELECTOR_RETURN_TYPER, "invalid selector", 400,
                    InvalidSelectorException.class, false, true),
            new KnownError(JAVASCRIPT_ERROR, "javascript error", 500, JavascriptException.class, true, true),
            new KnownError(METHOD_NOT_ALLOWED, "unknown method", 405, UnsupportedCommandException.class,
                    false, false),
            new KnownError(METHOD_NOT_ALLOWED, "unsupported operation", 500, UnsupportedCommandException.class,
                    false, true),
            new KnownError(MOVE_TARGET_OUT_OF_BOUNDS, "move target out of bounds", 500,
                    MoveTargetOutOfBoundsException.class, true, true),
            new KnownError(NO_ALERT_PRESENT, "no such alert", 404, NoAlertPresentException.class, true, true),
            new KnownError(NO_SUCH_COOKIE, "no such cookie", 404, NoSuchCookieException.class, true, true),
            new KnownError(NO_SUCH_ELEMENT, "no such element", 404, NoSuchElementException.class, true, true),
            new KnownError(NO_SUCH_FRAME, "no such frame", 404, NoSuchFrameException.class, true, true),
            new KnownError(NO_SUCH_SESSION, "invalid session id", 404, NoSuchSessionException.class, true, true),
            new KnownError(NO_SUCH_SHADOW_ROOT, "no such shadow root", 404, NoSuchShadowRootException.class,
                    true, true),
            new KnownError(NO_SUCH_WINDOW, "no such window", 404, NoSuchWindowException.class, true, true),
            new KnownError(SESSION_NOT_CREATED, "session not created", 500, SessionNotCreatedException.class,
                    true, true),
            new KnownError(STALE_ELEMENT_REFERENCE, "stale element reference", 404,
                    StaleElementReferenceException.class, true, true),
            new KnownError(TIMEOUT, "timeout", 500, TimeoutException.class, true, true),
            new KnownError(XPATH_LOOKUP_ERROR, "invalid selector", 400, InvalidSelectorException.class,
                    false, false),
            new KnownError(UNABLE_TO_CAPTURE_SCREEN, "unable to capture screen", 500, ScreenshotException.class,
                    true, true),
            new KnownError(UNABLE_TO_SET_COOKIE, "unable to set cookie", 500, UnableToSetCookieException.class,
                    true, true),
            new KnownError(UNEXPECTED_ALERT_PRESENT, "unexpected alert open", 500, UnhandledAlertException.class,
                    true, true),
            new KnownError(UNHANDLED_ERROR, "unknown error", 500, WebDriverException.class, true, true),
            new KnownError(UNKNOWN_COMMAND, "unknown command", 404, UnsupportedCommandException.class, true, true)
    );

    /**
     * Converts a legacy status code to the W3C error code.
     *
     * @param status the status code, null means an unhandled error
     * @return the W3C error code, or "success"
     */
    public String toState(@Nullable Integer status) {
        if (status == null) {
            return toState(UNHANDLED_ERROR);
        }
        if (SUCCESS == status) {
            return SUCCESS_STRING;
        }
        return onlyElement(KNOWN_ERRORS.stream()
                .filter(e -> e.jsonCode == status && e.isCanonicalForW3C)
                .map(e -> e.w3cCode)
                .collect(Collectors.toSet()), "unhandled error");
    }

    /**
     * Converts a W3C error code to the legacy status code.
     *
     * @param webdriverState the W3C error code
     * @param httpStatus the HTTP status of the response, if known
     * @return the legacy status code
     */
    public int toStatus(String webdriverState, Optional<Integer> httpStatus) {
        if (SUCCESS_STRING.equals(webdriverState)) {
            return SUCCESS;
        }
        return KNOWN_ERRORS.stream()
                .filter(e -> e.w3cCode.equals(webdriverState) && e.isCanonicalForW3C)
                .min(Comparator.comparingInt(e -> e.jsonCode))
                .map(e -> e.jsonCode)
                .orElse(UNHANDLED_ERROR);
    }

    /**
     * Resolves the exception type of a legacy status code.
     *
     * @param statusCode the status code
     * @return the exception type, null for success
     */
    @Nullable
    public Class<? extends WebDriverException> getExceptionType(int statusCode) {
        if (SUCCESS == statusCode) {
            return null;
        }
        return onlyElement(KNOWN_ERRORS.stream()
                .filter(e -> e.jsonCode == statusCode)
                .map(e -> e.exception)
                .collect(Collectors.toSet()), WebDriverException.class);
    }

    /**
     * Resolves the exception type of a W3C error code.
     *
     * @param webdriverState the W3C error code
     * @return the exception type, {@link WebDriverException} if the code is unknown
     */
    public Class<? extends WebDriverException> getExceptionType(@Nullable String webdriverState) {
        return onlyElement(KNOWN_ERRORS.stream()
                .filter(e -> e.w3cCode.equals(webdriverState) && e.isCanonicalForW3C)
                .map(e -> e.exception)
                .collect(Collectors.toSet()), WebDriverException.class);
    }

    /**
     * Converts an exception to the legacy status code.
     *
     * @param e the exception, null means success
     * @return the status code
     */
    public int toStatusCode(@Nullable Throwable e) {
        if (e == null) {
            return SUCCESS;
        }
        return onlyElement(KNOWN_ERRORS.stream()
                .filter(known -> known.exception.equals(e.getClass()) && known.isCanonicalJsonCodeForException)
                .map(known -> known.jsonCode)
                .collect(Collectors.toSet()), UNHANDLED_ERROR);
    }

    private static <V> V onlyElement(Collection<V> values, V fallback) {
        switch (values.size()) {
            case 0:
                return fallback;
            case 1:
                return values.iterator().next();
            default:
                throw new IllegalArgumentException("expected one value, found: " + values);
        }
    }

    private static final class KnownError {
        private final int jsonCode;
        private final String w3cCode;
        private final Class<? extends WebDriverException> exception;
        private final boolean isCanonicalJsonCodeForException;
        private final boolean isCanonicalForW3C;

        KnownError(int jsonCode, String w3cCode, int w3cHttpStatus, Class<? extends WebDriverException> exception,
                   boolean isCanonicalJsonCodeForException, boolean isCanonicalForW3C) {
            this.jsonCode = jsonCode;
            this.w3cCode = w3cCode;
            this.exception = exception;
            this.isCanonicalJsonCodeForException = isCanonicalJsonCodeForException;
            this.isCanonicalForW3C = isCanonicalForW3C;
        }
    }
}
