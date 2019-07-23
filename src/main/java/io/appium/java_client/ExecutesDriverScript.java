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

import io.appium.java_client.driverscripts.ScriptOptions;
import io.appium.java_client.driverscripts.ScriptValue;
import org.openqa.seleniumone.remote.Response;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.EXECUTE_DRIVER_SCRIPT;

public interface ExecutesDriverScript extends ExecutesMethod {

    /**
     * Run a set of scripts in scope of the current session.
     * This allows multiple web driver commands to be executed within one request
     * and may significantly speed up the automation script performance in
     * distributed client-server environments with high latency.
     * Read http://appium.io/docs/en/commands/session/execute-driver for more details.
     *
     * @since Appium 1.14
     * @param script the web driver script to execute (it should
     *               be a valid webdriverio code snippet by default
     *               unless another option is provided)
     * @param options additional scripting options
     * @return The script result
     * @throws org.openqa.seleniumone.WebDriverException if there was a failure while executing the script
     */
    default ScriptValue executeDriverScript(String script, @Nullable ScriptOptions options) {
        Map<String, Object> data = new HashMap<>();
        data.put("script", checkNotNull(script));
        if (options != null) {
            data.putAll(options.build());
        }
        Response response = execute(EXECUTE_DRIVER_SCRIPT, data);
        //noinspection unchecked
        Map<String, Object> value = (Map<String, Object>) response.getValue();
        //noinspection unchecked
        return new ScriptValue(value.get("result"), (Map<String, Object>) value.get("logs"));
    }

    /**
     * Run a set of scripts in scope of the current session with default options.
     *
     * @since Appium 1.14
     * @param script the web driver script to execute (it should
     *               be a valid webdriverio code snippet)
     * @return The script result
     */
    default ScriptValue executeDriverScript(String script) {
        return executeDriverScript(script, null);
    }
}
