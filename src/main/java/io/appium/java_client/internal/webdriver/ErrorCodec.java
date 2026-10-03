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

import io.appium.java_client.remote.ScreenshotException;
import org.openqa.selenium.DetachedShadowRootException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InsecureCertificateException;
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

import java.util.Map;
import java.util.function.Function;

/**
 * Creates exceptions from the W3C error responses. Adapted from Selenium's
 * {@code ErrorCodec} (Apache License 2.0).
 */
public final class ErrorCodec {
    private static final Map<String, Function<String, ? extends WebDriverException>> ERRORS = Map.ofEntries(
            Map.entry("script timeout", ScriptTimeoutException::new),
            Map.entry("detached shadow root", DetachedShadowRootException::new),
            Map.entry("element click intercepted", ElementClickInterceptedException::new),
            Map.entry("element not interactable", ElementNotInteractableException::new),
            Map.entry("invalid argument", InvalidArgumentException::new),
            Map.entry("invalid cookie domain", InvalidCookieDomainException::new),
            Map.entry("invalid element state", InvalidElementStateException::new),
            Map.entry("invalid selector", InvalidSelectorException::new),
            Map.entry("invalid session id", NoSuchSessionException::new),
            Map.entry("insecure certificate", InsecureCertificateException::new),
            Map.entry("javascript error", JavascriptException::new),
            Map.entry("move target out of bounds", MoveTargetOutOfBoundsException::new),
            Map.entry("no such alert", NoAlertPresentException::new),
            Map.entry("no such cookie", NoSuchCookieException::new),
            Map.entry("no such element", NoSuchElementException::new),
            Map.entry("no such frame", NoSuchFrameException::new),
            Map.entry("no such shadow root", NoSuchShadowRootException::new),
            Map.entry("no such window", NoSuchWindowException::new),
            Map.entry("session not created", SessionNotCreatedException::new),
            Map.entry("stale element reference", StaleElementReferenceException::new),
            Map.entry("timeout", TimeoutException::new),
            Map.entry("unable to capture screen", ScreenshotException::new),
            Map.entry("unable to set cookie", UnableToSetCookieException::new),
            Map.entry("unexpected alert open", UnhandledAlertException::new),
            Map.entry("unsupported operation", UnsupportedCommandException::new),
            Map.entry("unknown command", UnsupportedCommandException::new),
            Map.entry("unknown method", UnsupportedCommandException::new),
            Map.entry("unknown error", WebDriverException::new)
    );

    private ErrorCodec() {
    }

    /**
     * Creates the exception described by a W3C error response.
     *
     * @param response the response body, containing the "value" object with "error" and "message"
     * @return the exception, {@link WebDriverException} for unknown errors
     * @throws InvalidResponseException if the response is not a valid W3C error response
     */
    public static WebDriverException decode(Map<String, Object> response) {
        if (!(response.get("value") instanceof Map)) {
            throw new InvalidResponseException("missing \"value\" field", response);
        }
        Map<?, ?> value = (Map<?, ?>) response.get("value");
        Object error = value.get("error") == null ? "" : value.get("error");
        Object message = value.get("message") == null ? response.toString() : value.get("message");
        if (!(error instanceof String)) {
            throw new InvalidResponseException("\"error\" field must be a string", response);
        }
        if (!(message instanceof String)) {
            throw new InvalidResponseException("\"message\" field must be a string", response);
        }
        var constructor = ERRORS.get(error);
        if (constructor != null) {
            return constructor.apply((String) message);
        }
        return new WebDriverException(String.format("%s (error code: \"%s\")", message, error));
    }
}
