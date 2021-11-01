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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSafariAutomaticProfilingOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_AUTOMATIC_PROFILING_OPTION = "safari:automaticProfiling";

    /**
     * Enforces safaridriver to use Automatic Inspection.
     *
     * @return self instance for chaining.
     */
    default T safariAutomaticProfiling() {
        return amend(SAFARI_AUTOMATIC_PROFILING_OPTION, true);
    }

    /**
     * This capability instructs Safari to preload the Web Inspector and start
     * a Timeline recording in the background prior to returning a newly-created
     * window. To view the recording, open the Web Inspector through Safari's
     * Develop menu.
     *
     * @param bool Whether to use automatic profiling.
     * @return self instance for chaining.
     */
    default T setSafariAutomaticProfiling(boolean bool) {
        return amend(SAFARI_AUTOMATIC_PROFILING_OPTION, bool);
    }

    /**
     * Get whether to use automatic profiling.
     *
     * @return true or false.
     */
    default Optional<Boolean> doesAafariAutomaticProfiling() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_AUTOMATIC_PROFILING_OPTION)));
    }
}
