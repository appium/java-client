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

package io.appium.java_client.android.options.adb;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.List;
import java.util.Optional;

public interface SupportsLogcatFilterSpecsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOGCAT_FILTER_SPECS_OPTION = "logcatFilterSpecs";

    /**
     * Allows to customize logcat output filtering.
     *
     * @param format The filter specifier. Consists from series of `tag[:priority]` items,
     *               where `tag` is a log component tag (or `*` to match any value)
     *               and `priority`: V (Verbose), D (Debug), I (Info), W (Warn), E (Error), F (Fatal),
     *               S (Silent - suppresses all output). `tag` without `priority` defaults to `tag:v`.
     *               If not specified, filterspec is set from ANDROID_LOG_TAGS environment variable.
     *               If no filterspec is found, filter defaults to `*:I`, which means
     *               to only show log lines with any tag and the log level INFO or higher.
     * @return self instance for chaining.
     */
    default T setLogcatFilterSpecs(List<String> format) {
        return amend(LOGCAT_FILTER_SPECS_OPTION, format);
    }

    /**
     * Get the logcat filter format.
     *
     * @return Format specifier. See the documentation on {@link #setLogcatFilterSpecs(List)} for more details.
     */
    default Optional<List<String>> getLogcatFilterSpecs() {
        //noinspection unchecked
        return Optional.ofNullable((List<String>) getCapability(LOGCAT_FILTER_SPECS_OPTION));
    }
}
