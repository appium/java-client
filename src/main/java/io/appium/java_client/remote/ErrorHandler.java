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
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Throws the exception described by a failed response. Adapted from Selenium's
 * {@code ErrorHandler} (Apache License 2.0).
 */
public class ErrorHandler {
    private static final String MESSAGE = "message";
    private static final String SCREEN_SHOT = "screen";

    private final ErrorCodes errorCodes;

    /** Creates a handler with the default error codes. */
    public ErrorHandler() {
        this(new ErrorCodes());
    }

    /**
     * Creates a handler with the given error codes.
     *
     * @param codes the error codes mapping
     */
    public ErrorHandler(ErrorCodes codes) {
        this.errorCodes = codes;
    }

    /**
     * Returns the response if it is successful and throws the matching exception otherwise.
     *
     * @param response the response to check
     * @param duration how long the command took in milliseconds, which is added to the message
     * @return the given response
     * @throws RuntimeException the exception described by the failed response
     */
    @SuppressWarnings("unchecked")
    public Response throwIfResponseFailed(Response response, long duration) throws RuntimeException {
        if (ErrorCodes.SUCCESS_STRING.equals(response.getState())) {
            return response;
        }
        Object value = response.getValue();
        if (value instanceof Throwable) {
            Throwable throwable = (Throwable) value;
            if (throwable instanceof Error) {
                throw (Error) throwable;
            }
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new RuntimeException(throwable);
        }

        Class<? extends WebDriverException> outerErrorType = errorCodes.getExceptionType(response.getState());
        if (outerErrorType == null) {
            outerErrorType = WebDriverException.class;
        }

        String message = null;
        Throwable cause = null;
        if (value instanceof Map) {
            Map<String, Object> rawErrorData = (Map<String, Object>) value;
            if (!rawErrorData.containsKey(MESSAGE) && rawErrorData.get("value") instanceof Map) {
                rawErrorData = (Map<String, Object>) rawErrorData.get("value");
            }
            Object rawMessage = rawErrorData.get(MESSAGE);
            message = rawMessage == null ? null : String.valueOf(rawMessage);
            if (rawErrorData.get(SCREEN_SHOT) != null) {
                cause = new ScreenshotException(String.valueOf(rawErrorData.get(SCREEN_SHOT)), null);
            }
        } else if (value != null) {
            message = String.valueOf(value);
        }

        String durationSuffix = durationSuffix(duration);
        if (message != null && !message.contains(durationSuffix)) {
            message = message + durationSuffix;
        }

        WebDriverException toThrow = null;
        if (outerErrorType.equals(UnhandledAlertException.class) && value instanceof Map) {
            toThrow = createUnhandledAlertException((Map<String, Object>) value);
        }
        if (toThrow == null) {
            toThrow = createThrowable(outerErrorType, new Class<?>[]{String.class, Throwable.class},
                    new Object[]{message, cause});
        }
        if (toThrow == null) {
            toThrow = createThrowable(outerErrorType, new Class<?>[]{String.class}, new Object[]{message});
        }
        if (toThrow == null) {
            toThrow = new WebDriverException(message, cause);
        }
        throw toThrow;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private UnhandledAlertException createUnhandledAlertException(Map<String, Object> rawErrorData) {
        if (rawErrorData.containsKey("alert") || rawErrorData.containsKey("alertText")) {
            Object alertText = rawErrorData.get("alertText");
            if (alertText == null) {
                Map<String, Object> alert = (Map<String, Object>) rawErrorData.get("alert");
                if (alert != null) {
                    alertText = alert.get("text");
                }
            }
            return createThrowable(UnhandledAlertException.class, new Class<?>[]{String.class, String.class},
                    new Object[]{rawErrorData.get(MESSAGE), alertText});
        }
        return null;
    }

    private static String durationSuffix(long duration) {
        String prefix = "\nCommand duration or timeout: ";
        if (duration < 1000) {
            return prefix + duration + " milliseconds";
        }
        return prefix + new BigDecimal(duration).divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP)
                + " seconds";
    }

    @Nullable
    private static <T extends Throwable> T createThrowable(Class<T> clazz, Class<?>[] parameterTypes,
                                                           Object[] parameters) {
        try {
            Constructor<T> constructor = clazz.getConstructor(parameterTypes);
            return constructor.newInstance(parameters);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
