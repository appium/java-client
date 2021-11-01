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

package io.appium.java_client.android.options.server;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsShowGradleLogOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SHOW_GRADLE_LOG_OPTION = "showGradleLog";

    /**
     * Enforces inclusion of the Gradle log to the regular server logs.
     *
     * @return self instance for chaining.
     */
    default T showGradleLog() {
        return amend(SHOW_GRADLE_LOG_OPTION, true);
    }

    /**
     * Whether to include Gradle log to the regular server logs while
     * building Espresso server. false by default.
     *
     * @param value Whether to include Gradle log to the regular server logs.
     * @return self instance for chaining.
     */
    default T setShowGradleLog(boolean value) {
        return amend(SHOW_GRADLE_LOG_OPTION, value);
    }

    /**
     * Get whether to include Gradle log to the regular server log.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesShowGradleLog() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(SHOW_GRADLE_LOG_OPTION))
        );
    }
}
