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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SupportsProcessArgumentsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PROCESS_ARGUMENTS_OPTION = "processArguments";

    /**
     * Provides process arguments and environment which will be sent
     * to the WebDriverAgent server.
     *
     * @param pa Process arguments.
     * @return self instance for chaining.
     */
    default T setProcessArguments(ProcessArguments pa) {
        return amend(PROCESS_ARGUMENTS_OPTION, pa.toMap());
    }

    /**
     * Get process arguments of the app under test.
     *
     * @return Process arguments.
     */
    @SuppressWarnings("unchecked")
    default Optional<ProcessArguments> getProcessArguments() {
        Map<String, Object> pa = (Map<String, Object>) getCapability(PROCESS_ARGUMENTS_OPTION);
        return pa == null || !(pa.containsKey("args") || pa.containsKey("env"))
            ? Optional.empty()
            : Optional.of(new ProcessArguments(
                (List<String>) pa.getOrDefault("args", null),
                (Map<String, Object>) pa.getOrDefault("env", null)));
    }
}
