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

import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Process arguments and environment sent to the WebDriverAgent server.
 */
@ToString()
public class ProcessArguments {
    private final List<String> args;
    private final Map<String, Object> env;

    /**
     * Creates process arguments with an environment.
     *
     * @param args Process arguments.
     * @param env Process environment variables.
     */
    public ProcessArguments(List<String> args, Map<String, Object> env) {
        this.args = args;
        this.env = env;
    }

    /**
     * Creates process arguments without an environment.
     *
     * @param args Process arguments.
     */
    public ProcessArguments(List<String> args) {
        this(args, null);
    }

    /**
     * Creates an environment without process arguments.
     *
     * @param env Process environment variables.
     */
    public ProcessArguments(Map<String, Object> env) {
        this(null, env);
    }

    /**
     * Returns the data object content as a map.
     *
     * @return Properties as a map.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        Optional.ofNullable(args).ifPresent(v -> result.put("args", v));
        Optional.ofNullable(env).ifPresent(v -> result.put("env", v));
        return Collections.unmodifiableMap(result);
    }
}
