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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsWebSocketUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEB_SOCKET_URL = "webSocketUrl";

    /**
     * Enable BiDi session support.
     *
     * @return self instance for chaining.
     */
    default T enableBiDi() {
        return amend(WEB_SOCKET_URL, true);
    }

    /**
     * Whether to enable BiDi session support.
     *
     * @return self instance for chaining.
     */
    default T setWebSocketUrl(boolean value) {
        return amend(WEB_SOCKET_URL, value);
    }

    /**
     * For input capabilities: whether enable BiDi session support is enabled.
     * For session creation response capabilities: BiDi web socket URL.
     *
     * @return If called on request capabilities if BiDi support is enabled for the driver session
     */
    default Optional<Object> getWebSocketUrl() {
        return Optional.ofNullable(getCapability(WEB_SOCKET_URL));
    }
}
