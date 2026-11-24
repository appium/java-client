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

package io.appium.java_client.ws;

import java.util.List;
import java.util.function.Consumer;

/**
 * Supports handlers of the web socket errors.
 *
 * @deprecated The logs broadcasting web socket of the Appium drivers is deprecated and will be removed
 *     together with this API. Subscribe to the BiDi {@code log.entryAdded} event instead, see the
 *     <a href="https://github.com/appium/java-client/blob/master/docs/selenium-bridge.md#bidi">Selenium
 *     bridge documentation</a>.
 */
@Deprecated(since = "11.1.0", forRemoval = true)
@SuppressWarnings("removal")
public interface CanHandleErrors {

    /**
     * Returns a list of all registered web socket error handlers.
     *
     * @return The list of web socket error handlers.
     */
    List<Consumer<Throwable>> getErrorHandlers();

    /**
     * Register a new error handler.
     *
     * @param handler a callback function, which accepts the received exception instance as a parameter
     */
    default void addErrorHandler(Consumer<Throwable> handler) {
        getErrorHandlers().add(handler);
    }

    /**
     * Removes existing error handlers.
     */
    default void removeErrorHandlers() {
        getErrorHandlers().clear();
    }
}
