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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.Map;
import java.util.Optional;

public class RunScript extends BaseMapOptionData<RunScript> {
    public RunScript() {
    }

    public RunScript(Map<String, Object> options) {
        super(options);
    }

    /**
     * Allows to provide a multiline AppleScript.
     *
     * @param script A valid AppleScript.
     * @return self instance for chaining.
     */
    public RunScript withScript(String script) {
        return assignOptionValue("script", script);
    }

    /**
     * Get a multiline AppleScript.
     *
     * @return AppleScript snippet.
     */
    public Optional<String> getScript() {
        return getOptionValue("script");
    }

    /**
     * Allows to provide a single-line AppleScript.
     *
     * @param command A valid AppleScript.
     * @return self instance for chaining.
     */
    public RunScript withCommand(String command) {
        return assignOptionValue("command", command);
    }

    /**
     * Get a single-line AppleScript.
     *
     * @return AppleScript snippet.
     */
    public Optional<String> getCommand() {
        return getOptionValue("command");
    }
}
