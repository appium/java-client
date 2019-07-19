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

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;
public class ScriptValue {
    /**
     * The result of ExecuteDriverScript call.
     *
     * @return The actual returned value depends on the script content
     */
    @Getter private final Object result;
    /**
     * Retrieves logs mapping from ExecuteDriverScript call.
     *
     * @return Mapping keys are log levels, for example `warn` or
     *          `error` and the values are lists of strings that were printed
     *          by the script into the corresponding logging level
     */
    @Getter private final Map<String, Object> logs;

    public ScriptValue(Object result, Map<String, Object> logs) {
        this.result = result;
        this.logs = logs;
    }
}
