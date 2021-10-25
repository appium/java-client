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

public interface SupportsLogcatFormatOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOGCAT_FORMAT_OPTION = "logcatFormat";

    /**
     * The log print format, where format is one of: brief process tag thread raw time
     * threadtime long. threadtime is the default value.
     *
     * @param format The format specifier.
     * @return self instance for chaining.
     */
    default T setLogcatFormat(String format) {
        return amend(LOGCAT_FORMAT_OPTION, format);
    }

    /**
     * Get the log print format.
     *
     * @return Format specifier.
     */
    default Optional<String> getLogcatFormat() {
        return Optional.ofNullable((String) getCapability(LOGCAT_FORMAT_OPTION));
    }
}
