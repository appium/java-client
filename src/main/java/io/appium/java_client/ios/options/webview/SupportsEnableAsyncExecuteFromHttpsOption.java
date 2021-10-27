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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsEnableAsyncExecuteFromHttpsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ENABLE_ASYNC_EXECUTE_FROM_HTTPS_OPTION = "enableAsyncExecuteFromHttps";

    /**
     * Enforces to allow simulators to execute async JavaScript on pages using HTTPS.
     *
     * @return self instance for chaining.
     */
    default T enableAsyncExecuteFromHttps() {
        return amend(ENABLE_ASYNC_EXECUTE_FROM_HTTPS_OPTION, true);
    }

    /**
     * Capability to allow simulators to execute asynchronous JavaScript
     * on pages using HTTPS. Defaults to false.
     *
     * @param value Whether to allow simulators to execute async JavaScript on pages using HTTPS.
     * @return self instance for chaining.
     */
    default T setEnableAsyncExecuteFromHttps(boolean value) {
        return amend(ENABLE_ASYNC_EXECUTE_FROM_HTTPS_OPTION, value);
    }

    /**
     * Get whether to allow simulators to execute async JavaScript on pages using HTTPS.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesEnableAsyncExecuteFromHttps() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ENABLE_ASYNC_EXECUTE_FROM_HTTPS_OPTION)));
    }
}
