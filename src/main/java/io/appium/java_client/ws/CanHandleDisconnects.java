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

public interface CanHandleDisconnects {

    /**
     * @return The list of web socket disconnection handlers.
     */
    List<Runnable> getDisconnectionHandlers();

    /**
     * Register a new web socket disconnect handler.
     *
     * @param handler a callback function, which is going to be executed when web socket disconnect event arrives
     */
    default void addDisconnectionHandler(Runnable handler) {
        getDisconnectionHandlers().add(handler);
    }

    /**
     * Removes existing disconnection handlers.
     */
    default void removeDisconnectionHandlers() {
        getDisconnectionHandlers().clear();
    }
}
