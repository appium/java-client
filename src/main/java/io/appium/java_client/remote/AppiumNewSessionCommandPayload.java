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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.AcceptedW3CCapabilityKeys;
import org.openqa.selenium.remote.CommandPayload;

import java.util.AbstractMap;
import java.util.Map;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;
import static org.openqa.selenium.remote.DriverCommand.NEW_SESSION;

public class AppiumNewSessionCommandPayload extends CommandPayload {
    private static final AcceptedW3CCapabilityKeys ACCEPTED_W3C_PATTERNS = new AcceptedW3CCapabilityKeys();

    /**
     * Appends "appium:" prefix to all non-prefixed non-standard capabilities.
     *
     * @param possiblyInvalidCapabilities user-provided capabilities mapping.
     * @return Fixed capabilities mapping.
     */
    private static Map<String, Object> makeW3CSafe(Capabilities possiblyInvalidCapabilities) {
        Require.nonNull("Capabilities", possiblyInvalidCapabilities);

        return possiblyInvalidCapabilities.asMap().entrySet().stream()
                .map((entry) -> ACCEPTED_W3C_PATTERNS.test(entry.getKey())
                        ? entry
                        : new AbstractMap.SimpleEntry<>(
                        String.format("%s%s", APPIUM_PREFIX, entry.getKey()), entry.getValue()))
                .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
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
