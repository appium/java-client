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

import com.google.gson.Gson;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;

import java.util.Map;
import java.util.Optional;

public interface SupportsProxyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PROXY_OPTION = "proxy";

    /**
     * Defines the current session’s proxy configuration.
     *
     * @param proxy Session proxy config.
     * @return self instance for chaining.
     */
    default T setProxy(Proxy proxy) {
        return amend(PROXY_OPTION, proxy.toJson());
    }

    /**
     * Get the current session’s proxy configuration.
     *
     * @return Proxy config.
     */
    default Optional<Proxy> getProxy() {
        return Optional.ofNullable(getCapability(PROXY_OPTION))
                .map(String::valueOf)
                .map(v -> new Gson().fromJson(v, Map.class))
                .map(Proxy::new);
    }
}
