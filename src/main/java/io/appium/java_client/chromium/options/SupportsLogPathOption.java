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

package io.appium.java_client.chromium.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsLogPathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOG_PATH = "logPath";

    /**
     * If set, the path to use with the --log-path parameter directing
     * WebDriver to write its log to that path.
     *
     * @param logPath where to write the logs.
     * @return self instance for chaining.
     */
    default T setLogPath(String logPath) {
        return amend(LOG_PATH, logPath);
    }

    /**
     * Get the log path where the WebDrive writes the logs.
     *
     * @return the log path.
     */
    default Optional<String> getLogPath() {
        return Optional.ofNullable((String) getCapability(LOG_PATH));
    }
}
