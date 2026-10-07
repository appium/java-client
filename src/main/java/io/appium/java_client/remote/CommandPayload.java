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

package io.appium.java_client.remote;

import org.jspecify.annotations.Nullable;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The name and the parameters of a command, before it is bound to a session.
 */
public class CommandPayload {
    private final String name;
    private final Map<String, ? extends @Nullable Object> parameters;

    /**
     * Creates a payload.
     *
     * @param name the name of the command
     * @param parameters the parameters of the command
     */
    public CommandPayload(String name, Map<String, ? extends @Nullable Object> parameters) {
        this.name = requireNonNull(name, "name");
        this.parameters = requireNonNull(parameters, "parameters");
    }

    /**
     * Returns the name of the command.
     *
     * @return the command name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the parameters of the command.
     *
     * @return the command parameters
     */
    public Map<String, ? extends @Nullable Object> getParameters() {
        return parameters;
    }
}
