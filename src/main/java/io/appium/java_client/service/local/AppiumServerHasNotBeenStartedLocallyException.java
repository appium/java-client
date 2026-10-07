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

package io.appium.java_client.service.local;

/**
 * Thrown if a local Appium server cannot be started.
 */
public class AppiumServerHasNotBeenStartedLocallyException extends RuntimeException {
    /**
     * Creates an exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public AppiumServerHasNotBeenStartedLocallyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with the given message.
     *
     * @param message the detail message
     */
    public AppiumServerHasNotBeenStartedLocallyException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the given cause.
     *
     * @param cause the cause
     */
    public AppiumServerHasNotBeenStartedLocallyException(Throwable cause) {
        super(cause);
    }
}
