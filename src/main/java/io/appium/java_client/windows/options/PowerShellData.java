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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.options.RunScript;

import java.util.Map;
import java.util.Optional;

public class PowerShellData extends RunScript<PowerShellData> {
    public PowerShellData() {
    }

    public PowerShellData(Map<String, Object> options) {
        super(options);
    }

    /**
     * Allows to provide a multiline PowerShell script.
     *
     * @param script A valid PowerShell script.
     * @return self instance for chaining.
     */
    @Override
    public PowerShellData withScript(String script) {
        return super.withScript(script);
    }

    /**
     * Get a multiline PowerShell script.
     *
     * @return PowerShell script.
     */
    @Override
    public Optional<String> getScript() {
        return super.getScript();
    }

    /**
     * Allows to provide a single-line PowerShell script.
     *
     * @param command A valid PowerShell script.
     * @return self instance for chaining.
     */
    @Override
    public PowerShellData withCommand(String command) {
        return super.withCommand(command);
    }

    /**
     * Get a single-line PowerShell script.
     *
     * @return PowerShell script.
     */
    @Override
    public Optional<String> getCommand() {
        return super.getCommand();
    }
}
