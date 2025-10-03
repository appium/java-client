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

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Locale.ROOT;

public enum UnhandledPromptBehavior {
    DISMISS, ACCEPT,
    DISMISS_AND_NOTIFY, ACCEPT_AND_NOTIFY,
    IGNORE;

    @Override
    public String toString() {
        return name().toLowerCase(ROOT).replace("_", " ");
    }

    /**
     * Converts the given value to an enum member.
     *
     * @param value The value to convert.
     * @return Enum member.
     * @throws IllegalArgumentException If the provided value cannot be matched.
     */
    public static UnhandledPromptBehavior fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.toString().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unhandled prompt behavior '%s' is not supported. "
                                        + "The only supported values are: %s", value,
                                Arrays.stream(values()).map(UnhandledPromptBehavior::toString)
                                        .collect(Collectors.joining(",")))
                ));
    }
}
