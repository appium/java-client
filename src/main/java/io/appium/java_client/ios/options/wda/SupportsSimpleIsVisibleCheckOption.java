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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSimpleIsVisibleCheckOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMPLE_IS_VISIBLE_CHECK_OPTION = "simpleIsVisibleCheck";

    /**
     * Enforce usage of native methods for determining visibility of elements.
     *
     * @return self instance for chaining.
     */
    default T simpleIsVisibleCheck() {
        return amend(SIMPLE_IS_VISIBLE_CHECK_OPTION, true);
    }

    /**
     * Use native methods for determining visibility of elements.
     * In some cases this takes a long time. Setting this capability to false will
     * cause the system to use the position and size of elements to make sure they
     * are visible on the screen. This can, however, lead to false results in some
     * situations. Defaults to false, except iOS 9.3, where it defaults to true.
     *
     * @param value Whether to use native methods for determining visibility of elements
     * @return self instance for chaining.
     */
    default T setSimpleIsVisibleCheck(boolean value) {
        return amend(SIMPLE_IS_VISIBLE_CHECK_OPTION, value);
    }

    /**
     * Get whether to use native methods for determining visibility of elements.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSimpleIsVisibleCheck() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SIMPLE_IS_VISIBLE_CHECK_OPTION)));
    }
}
