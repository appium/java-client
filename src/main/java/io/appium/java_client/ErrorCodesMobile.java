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


package io.appium.java_client;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ErrorCodes;

import java.util.Map;

/**
 * Defines common error codes for the mobile JSON wire protocol.
 *
 * @author jonahss@gmail.com (Jonah Stiennon)
 */
public class ErrorCodesMobile extends ErrorCodes {

    public static final int NO_SUCH_CONTEXT = 35;

    private static Map<Integer, String> statusToState =
        ImmutableMap.<Integer, String>builder().put(NO_SUCH_CONTEXT, "No such context found")
            .build();

    /**
    * Returns the exception type that corresponds to the given {@code statusCode}. All unrecognized
    * status codes will be mapped to {@link WebDriverException WebDriverException.class}.
    *
    * @param statusCode The status code to convert.
    * @return The exception type that corresponds to the provided status
    */
    public Class<? extends WebDriverException> getExceptionType(int statusCode) {
        switch (statusCode) {
            case NO_SUCH_CONTEXT:
                return NoSuchContextException.class;
            default:
                return super.getExceptionType(statusCode);
        }
    }

    /**
     * @param message message An error message returned by Appium server
     * @return The exception type that corresponds to the provided error message or {@code null} if
     *     there are no matching mobile exceptions.
     */
    public Class<? extends WebDriverException> getExceptionType(String message) {
        for (Map.Entry<Integer, String> entry : statusToState.entrySet()) {
            if (message.contains(entry.getValue())) {
                return getExceptionType(entry.getKey());
            }
        }
        return null;
    }

    /**
    * Converts a thrown error into the corresponding status code.
    *
    * @param thrown The thrown error.
    * @return The corresponding status code for the given thrown error.
    */
    public int toStatusCode(Throwable thrown) {
        if (thrown instanceof NoSuchContextException) {
            return NO_SUCH_CONTEXT;
        } else {
            return super.toStatusCode(thrown);
        }
    }

}
