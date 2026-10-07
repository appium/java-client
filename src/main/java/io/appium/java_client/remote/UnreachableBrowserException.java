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

import org.openqa.selenium.WebDriverException;

/**
 * Thrown if the server cannot be reached, because its address is invalid or it has died.
 */
public class UnreachableBrowserException extends WebDriverException {
    /**
     * Creates an exception with a message.
     *
     * @param message the detail message
     */
    public UnreachableBrowserException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and a cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UnreachableBrowserException(String message, Throwable cause) {
        super(message, cause);
    }
}
