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

public interface SupportsPrintPageSourceOnFindFailureOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PRINT_PAGE_SOURCE_ON_FIND_FAILURE_OPTION = "printPageSourceOnFindFailure";

    /**
     * Set the app to print page source when a find operation fails.
     *
     * @return self instance for chaining.
     */
    default T printPageSourceOnFindFailure() {
        return setPrintPageSourceOnFindFailure(true);
    }

    /**
     * Set whether the app to print page source when a find operation fails.
     *
     * @param bool is whether to print page source.
     * @return self instance for chaining.
     */
    default T setPrintPageSourceOnFindFailure(boolean bool) {
        return amend(PRINT_PAGE_SOURCE_ON_FIND_FAILURE_OPTION, bool);
    }

    /**
     * Get whether the app to print page source when a find operation fails.
     *
     * @return true if app prints page source.
     */
    default Optional<Boolean> doesPrintPageSourceOnFindFailure() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(PRINT_PAGE_SOURCE_ON_FIND_FAILURE_OPTION))
        );
    }
}
