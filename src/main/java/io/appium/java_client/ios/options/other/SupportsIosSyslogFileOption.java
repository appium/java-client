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

package io.appium.java_client.ios.options.other;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code iosSyslogFile} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsIosSyslogFileOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code iosSyslogFile} capability.
     */
    String IOS_SYSLOG_FILE_OPTION = "iosSyslogFile";

    /**
     * File path to write iOS syslogs to when {@code showIOSLog} is set to {@code true},
     * instead of writing them to the Appium server log.
     *
     * @param path File path for iOS syslogs.
     * @return self instance for chaining.
     */
    default T setIosSyslogFile(String path) {
        return amend(IOS_SYSLOG_FILE_OPTION, path);
    }

    /**
     * Get the file path where iOS syslogs are written.
     *
     * @return File path for iOS syslogs.
     */
    default Optional<String> getIosSyslogFile() {
        return Optional.ofNullable((String) getCapability(IOS_SYSLOG_FILE_OPTION));
    }
}
