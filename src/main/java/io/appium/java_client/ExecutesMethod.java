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

package io.appium.java_client;

import io.appium.java_client.remote.Response;

import java.util.Map;

/**
 * The interface for the objects that can execute Appium commands.
 */
public interface ExecutesMethod {
    /**
     * Executes the given command and returns a response.
     *
     * @param driverCommand a command to execute
     * @param parameters map of command parameters
     * @return a result response
     */
    Response execute(String driverCommand, Map<String, ?> parameters);

    /**
     * Executes the given command and returns a response.
     *
     * @param driverCommand a command to execute
     * @return a result response
     */
    Response execute(String driverCommand);
}
