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

package io.appium.java_client.driverscripts;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;


public class ScriptOptions {
    private ScriptType scriptType = ScriptType.WEBDRIVERIO;
    private Long timeoutMs;

    /**
     * Sets the script type.
     *
     * @param type the actual script type
     * @return self instance for chaining
     */
    public ScriptOptions withScriptType(ScriptType type) {
        this.scriptType = checkNotNull(type);
        return this;
    }

    /**
     * Sets the script execution timeout.
     * If this is not set then the maximum duration of the script
     * is not limited (e. g. may block forever).
     *
     * @param timeoutMs the timeout in milliseconds
     * @return self instance for chaining
     */
    public ScriptOptions withTimeout(long timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    /**
     * Builds a values map for further usage in HTTP requests to Appium.
     *
     * @return The map containing the provided options
     */
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(scriptType).map(x -> builder.put("type", x.name().toLowerCase()));
        ofNullable(timeoutMs).map(x -> builder.put("timeout", x));
        return builder.build();
    }
}
