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

public interface SupportsSafariGarbageCollectOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_GARBAGE_COLLECT_OPTION = "safariGarbageCollect";

    /**
     * Enforces to turn on garbage collection when executing scripts on Safari.
     *
     * @return self instance for chaining.
     */
    default T safariGarbageCollect() {
        return amend(SAFARI_GARBAGE_COLLECT_OPTION, true);
    }

    /**
     * Turns on/off Web Inspector garbage collection when executing scripts on Safari.
     * Turning on may improve performance. Defaults to false.
     *
     * @param value Whether to turn on garbage collection when executing scripts on Safari.
     * @return self instance for chaining.
     */
    default T setSafariGarbageCollect(boolean value) {
        return amend(SAFARI_GARBAGE_COLLECT_OPTION, value);
    }

    /**
     * Get whether to turn on garbage collection when executing scripts on Safari.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSafariGarbageCollect() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_GARBAGE_COLLECT_OPTION)));
    }
}
