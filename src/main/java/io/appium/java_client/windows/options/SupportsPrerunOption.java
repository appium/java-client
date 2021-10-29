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

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

public interface SupportsPrerunOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PRERUN_OPTION = "prerun";

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid PowerShell script or command to be
     * executed prior to the WinAppDriver session startup.
     * See
     * https://github.com/appium/appium-windows-driver#power-shell-commands-execution
     * for more details.
     *
     * @param script E.g. {script: 'Get-Process outlook -ErrorAction SilentlyContinue'}.
     * @return self instance for chaining.
     */
    default T setPrerun(RunScript script) {
        return amend(PRERUN_OPTION, script.toMap());
    }

    /**
     * Get the prerun script.
     *
     * @return Prerun script.
     */
    default Optional<RunScript> getPrerun() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(PRERUN_OPTION))
                .map((v) -> new RunScript((Map<String, Object>) v));
    }
}
