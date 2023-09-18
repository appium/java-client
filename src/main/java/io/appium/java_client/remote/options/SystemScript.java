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

package io.appium.java_client.remote.options;

import java.util.Map;
import java.util.Optional;

public abstract class SystemScript<T extends SystemScript<T>> extends BaseMapOptionData<T> {
    public SystemScript() {
    }

    public SystemScript(Map<String, Object> options) {
        super(options);
    }

    public T withScript(String script) {
        return assignOptionValue("script", script);
    }

    public Optional<String> getScript() {
        return getOptionValue("script");
    }

    public T withCommand(String command) {
        return assignOptionValue("command", command);
    }

    public Optional<String> getCommand() {
        return getOptionValue("command");
    }
}
