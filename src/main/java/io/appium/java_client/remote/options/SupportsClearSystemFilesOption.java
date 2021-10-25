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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsClearSystemFilesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CLEAR_SYSTEM_FILES_OPTION = "clearSystemFiles";

    /**
     * Set the app to delete any generated files at the end of a session.
     *
     * @return this MobileOptions, for chaining.
     */
    default T clearSystemFiles() {
        return setClearSystemFiles(true);
    }

    /**
     * Set whether the app deletes generated files at the end of a session.
     *
     * @param bool is whether the app deletes generated files at the end of a session.
     * @return this MobileOptions, for chaining.
     */
    default T setClearSystemFiles(boolean bool) {
        return amend(CLEAR_SYSTEM_FILES_OPTION, bool);
    }

    /**
     * Get whether the app deletes generated files at the end of a session.
     *
     * @return true if the app deletes generated files at the end of a session.
     */
    default Optional<Boolean> doesClearSystemFiles() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CLEAR_SYSTEM_FILES_OPTION)));
    }
}
