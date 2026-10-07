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
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

import static io.appium.java_client.remote.DriverCommand.EXECUTE_SCRIPT;

/**
 * The helper that simplifies the execution of the Appium commands and extension scripts.
 */
public final class CommandExecutionHelper {

    private CommandExecutionHelper() {
    }

    /**
     * Executes the given command and converts its result.
     *
     * @param <T> the type of the returned value
     * @param executesMethod the command executor
     * @param keyValuePair the command name and its parameters
     * @return the command result or null
     */
    @Nullable
    public static <T> T execute(
            ExecutesMethod executesMethod, Map.Entry<String, Map<String, ?>> keyValuePair
    ) {
        return handleResponse(executesMethod.execute(keyValuePair.getKey(), keyValuePair.getValue()));
    }

    /**
     * Executes the given command without parameters and converts its result.
     *
     * @param <T> the type of the returned value
     * @param executesMethod the command executor
     * @param command the command name
     * @return the command result or null
     */
    @Nullable
    public static <T> T execute(ExecutesMethod executesMethod, String command) {
        return handleResponse(executesMethod.execute(command));
    }

    @Nullable
    private static <T> T handleResponse(Response response) {
        //noinspection unchecked
        return response == null ? null : (T) response.getValue();
    }

    /**
     * Executes the given extension script without arguments.
     *
     * @param <T> the type of the returned value
     * @param executesMethod the command executor
     * @param scriptName the extension script name
     * @return the script execution result
     */
    @Nullable
    public static <T> T executeScript(ExecutesMethod executesMethod, String scriptName) {
        return executeScript(executesMethod, scriptName, null);
    }

    /**
     * Simplifies arguments preparation for the script execution command.
     *
     * @param <T> the type of the returned value
     * @param executesMethod Method executor instance.
     * @param scriptName     Extension script name.
     * @param args           Extension script arguments (if present).
     * @return Script execution result.
     */
    @Nullable
    public static <T> T executeScript(
            ExecutesMethod executesMethod, String scriptName, @Nullable Map<String, ?> args
    ) {
        return execute(executesMethod, Map.entry(EXECUTE_SCRIPT, Map.of(
                "script", scriptName,
                "args", (args == null || args.isEmpty()) ? Collections.emptyList() : Collections.singletonList(args)
        )));
    }
}
