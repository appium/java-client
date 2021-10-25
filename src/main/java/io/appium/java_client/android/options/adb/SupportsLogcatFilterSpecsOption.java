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

import java.util.Optional;

public interface SupportsLogcatFilterSpecsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOGCAT_FILTER_SPECS_OPTION = "logcatFilterSpecs";

    /**
     * Series of tag[:priority] where tag is a log component tag (or * for all)
     * and priority is: V Verbose, D Debug, I Info, W Warn, E Error, F Fatal,
     * S Silent (supress all output). '' means ':d' and tag by itself means tag:v.
     * If not specified on the commandline, filterspec is set from ANDROID_LOG_TAGS.
     * If no filterspec is found, filter defaults to '*:I'.
     *
     * @param format The filter specifier.
     * @return self instance for chaining.
     */
    default T setLogcatFilterSpecs(String format) {
        return amend(LOGCAT_FILTER_SPECS_OPTION, format);
    }

    /**
     * Get the logcat filter format.
     *
     * @return Format specifier.
     */
    default Optional<String> getLogcatFilterSpecs() {
        return Optional.ofNullable((String) getCapability(LOGCAT_FILTER_SPECS_OPTION));
    }
}
