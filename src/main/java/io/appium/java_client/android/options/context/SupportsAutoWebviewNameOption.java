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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code autoWebviewName} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAutoWebviewNameOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code autoWebviewName} capability.
     */
    String AUTO_WEBVIEW_NAME_OPTION = "autoWebviewName";

    /**
     * Sets a custom name to replace the application package part in the
     * {@code WEBVIEW_<name>} context name (for example {@code com.example.app})
     * when {@code autoWebview} is enabled. The driver prepends {@code WEBVIEW_}
     * automatically.
     *
     * @param name Custom name replacing the application package part of the context name.
     * @return self instance for chaining.
     */
    default T setAutoWebviewName(String name) {
        return amend(AUTO_WEBVIEW_NAME_OPTION, name);
    }

    /**
     * Get the custom name replacing the application package part of the
     * {@code WEBVIEW_<name>} context name for {@code autoWebview}.
     *
     * @return Custom package part of the webview context name.
     */
    default Optional<String> getAutoWebviewName() {
        return Optional.ofNullable((String) getCapability(AUTO_WEBVIEW_NAME_OPTION));
    }
}
