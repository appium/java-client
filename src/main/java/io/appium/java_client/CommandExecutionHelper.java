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

import org.openqa.selenium.remote.Response;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;

public final class CommandExecutionHelper {

    @Nullable
    public static <T> T execute(ExecutesMethod executesMethod,
        Map.Entry<String, Map<String, ?>> keyValuePair) {
        return handleResponse(executesMethod.execute(keyValuePair.getKey(), keyValuePair.getValue()));
    }

    @Nullable
    public static <T> T execute(ExecutesMethod executesMethod, String command) {
        return handleResponse(executesMethod.execute(command));
    }

    @Nullable
    private static <T> T handleResponse(Response response) {
        //noinspection unchecked
        return response == null ? null : (T) response.getValue();
    }

    @Nullable
    public static <T> T executeScript(ExecutesMethod executesMethod, String scriptName) {
        return executeScript(executesMethod, scriptName, null);
    }

    /**
     * Simplifies arguments preparation for the script execution command.
     *
     * @param executesMethod Method executor instance.
     * @param scriptName Extension script name.
     * @param args Extension script arguments (if present).
     * @return Script execution result.
     */
    @Nullable
    public static <T> T executeScript(
            ExecutesMethod executesMethod, String scriptName, @Nullable Map<String, Object> args
    ) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("script", scriptName);
        if (args != null) {
            payload.put("args", args.isEmpty() ? Collections.emptyList() : Collections.singletonList(args));
        }
        return execute(executesMethod, new AbstractMap.SimpleEntry<>(EXECUTE_SCRIPT, payload));
    }
}
