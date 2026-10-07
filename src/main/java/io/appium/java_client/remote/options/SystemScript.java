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

import java.util.Map;
import java.util.Optional;

/**
 * Base class for data objects that describe a system script, provided either as a script or as a command.
 *
 * @param <T> The concrete data type, used for chaining.
 */
public abstract class SystemScript<T extends SystemScript<T>> extends BaseMapOptionData<T> {
    /** Creates an empty data object. */
    public SystemScript() {
    }

    /**
     * Creates a data object backed by the given map.
     *
     * @param options The initial option values.
     */
    public SystemScript(Map<String, Object> options) {
        super(options);
    }

    /**
     * Sets a multiline script.
     *
     * @param script The script content.
     * @return self instance for chaining.
     */
    public T withScript(String script) {
        return assignOptionValue("script", script);
    }

    /**
     * Get the multiline script.
     *
     * @return The script content.
     */
    public Optional<String> getScript() {
        return getOptionValue("script");
    }

    /**
     * Sets a single-line command.
     *
     * @param command The command to execute.
     * @return self instance for chaining.
     */
    public T withCommand(String command) {
        return assignOptionValue("command", command);
    }

    /**
     * Get the single-line command.
     *
     * @return The command to execute.
     */
    public Optional<String> getCommand() {
        return getOptionValue("command");
    }
}
