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

/**
 * Support for the {@code prerun} capability: the script executed before the session is started.
 */
public interface SupportsPrerunOption<T extends BaseOptions<T>, S extends SystemScript<?>>
        extends Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code prerun} capability.
     */
    String PRERUN_OPTION = "prerun";

    /**
     * Sets the script to execute before the session.
     *
     * @param script The script data.
     * @return self instance for chaining.
     */
    T setPrerun(S script);

    /**
     * Get the script to execute before the session.
     *
     * @return The script data.
     */
    Optional<S> getPrerun();
}
