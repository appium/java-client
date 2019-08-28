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

package io.appium.java_client.internal;

import org.openqa.selenium.Capabilities;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CapabilityHelpers {
    public static final String APPIUM_PREFIX = "appium:";

    /**
     * Helper that is used for capability values retrieval.
     * Supports both prefixed W3C and "classic" capability names.
     *
     * @param caps         driver caps object
     * @param name         capability name
     * @param expectedType the expected capability type
     * @return The retrieved capability value or null if the cap either not present has an unexpected type
     */
    @Nullable
    public static <T> T getCapability(Capabilities caps, String name, Class<T> expectedType) {
        List<String> possibleNames = new ArrayList<>();
        possibleNames.add(name);
        if (!name.startsWith(APPIUM_PREFIX)) {
            possibleNames.add(APPIUM_PREFIX + name);
        }
        for (String capName : possibleNames) {
            if (caps.getCapability(capName) != null
                    && expectedType.isAssignableFrom(caps.getCapability(capName).getClass())) {
                return expectedType.cast(caps.getCapability(capName));
            }
        }
        return null;
    }
}
