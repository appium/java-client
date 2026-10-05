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

/**
 * Executes a command and returns the value of the response.
 */
public interface ExecuteMethod {
    /**
     * Executes the command.
     *
     * @param commandName the command name
     * @param parameters the command parameters, may be null
     * @return the value of the response
     */
    @Nullable
    Object execute(String commandName, @Nullable Map<String, ?> parameters);
}
