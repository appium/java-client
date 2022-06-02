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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.CommandPayload;

import java.util.Map;

import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;

public class AppiumNewSessionCommandPayload extends CommandPayload {
    /**
     * Appends "appium:" prefix to all non-prefixed non-standard capabilities.
     *
     * @param possiblyInvalidCapabilities user-provided capabilities mapping.
     * @return Fixed capabilities mapping.
     */
    private static Map<String, Object> makeW3CSafe(Capabilities possiblyInvalidCapabilities) {
        return Require.nonNull("Capabilities", possiblyInvalidCapabilities)
                .asMap().entrySet().stream()
                .collect(ImmutableMap.toImmutableMap(
                    entry -> BaseOptions.toW3cName(entry.getKey()),
                    Map.Entry::getValue
                ));
    }

    /**
     * Overrides the default new session behavior to
     * only handle W3C capabilities.
     *
     * @param capabilities User-provided capabilities.
     */
    public AppiumNewSessionCommandPayload(Capabilities capabilities) {
        super(NEW_SESSION, ImmutableMap.of(
                "capabilities", ImmutableSet.of(makeW3CSafe(capabilities)),
                "desiredCapabilities", capabilities
        ));
    }
}
