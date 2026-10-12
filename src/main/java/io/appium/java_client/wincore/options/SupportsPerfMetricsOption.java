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

package io.appium.java_client.wincore.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code perfMetrics} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsPerfMetricsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code perfMetrics} capability.
     */
    String PERF_METRICS_OPTION = "perfMetrics";

    /**
     * Whether the per-session performance counters are collected. They are read and reset via the
     * {@code windows: getPerfMetrics} and {@code windows: resetPerfMetrics} extensions. Disabled by
     * default.
     *
     * @param value True to enable, false to disable.
     * @return self instance for chaining.
     */
    default T setPerfMetrics(boolean value) {
        return amend(PERF_METRICS_OPTION, value);
    }

    /**
     * Get whether the per-session performance counters are collected.
     *
     * @return The capability value.
     */
    default Optional<Boolean> isPerfMetrics() {
        return Optional.ofNullable(toSafeBoolean(getCapability(PERF_METRICS_OPTION)));
    }
}
