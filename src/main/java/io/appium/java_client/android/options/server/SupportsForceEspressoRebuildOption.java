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

public interface SupportsForceEspressoRebuildOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FORCE_ESPRESSO_REBUILD_OPTION = "forceEspressoRebuild";

    /**
     * Enforces Espresso server rebuild on a new session startup.
     *
     * @return self instance for chaining.
     */
    default T forceEspressoRebuild() {
        return amend(FORCE_ESPRESSO_REBUILD_OPTION, true);
    }

    /**
     * Whether to always enforce Espresso server rebuild (true).
     * By default, Espresso caches the already built server apk and only rebuilds
     * it when it is necessary, because rebuilding process needs extra time.
     * false by default.
     *
     * @param value True to force Espresso server rebuild on a new session startup.
     * @return self instance for chaining.
     */
    default T setForceEspressoRebuild(boolean value) {
        return amend(FORCE_ESPRESSO_REBUILD_OPTION, value);
    }

    /**
     * Get to force Espresso server rebuild on a new session startup.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesForceEspressoRebuild() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(FORCE_ESPRESSO_REBUILD_OPTION))
        );
    }
}
