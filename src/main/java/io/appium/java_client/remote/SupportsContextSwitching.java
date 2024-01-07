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

package io.appium.java_client.remote;

import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.NoSuchContextException;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Response;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public interface SupportsContextSwitching extends WebDriver, ContextAware, ExecutesMethod {
    /**
     * Switches to the given context.
     *
     * @param name The name of the context to switch to.
     * @return self instance for chaining.
     */
    default WebDriver context(String name) {
        requireNonNull(name, "Must supply a context name");
        try {
            execute(MobileCommand.SWITCH_TO_CONTEXT, Map.of("name", name));
            return this;
        } catch (WebDriverException e) {
            throw new NoSuchContextException(e.getMessage(), e);
        }
    }

    /**
     * Get the names of available contexts.
     *
     * @return List list of context names.
     */
    default Set<String> getContextHandles() {
        Response response = execute(MobileCommand.GET_CONTEXT_HANDLES, Map.of());
        Object value = response.getValue();
        try {
            //noinspection unchecked
            List<String> returnedValues = (List<String>) value;
            return new LinkedHashSet<>(returnedValues);
        } catch (ClassCastException ex) {
            throw new WebDriverException(
                    "Returned value cannot be converted to List<String>: " + value, ex);
        }
    }

    /**
     * Get the name of the current context.
     *
     * @return Context name or null if it cannot be determined.
     */
    @Nullable
    default String getContext() {
        String contextName =
                String.valueOf(execute(MobileCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
        return "null".equalsIgnoreCase(contextName) ? null : contextName;
    }
}
