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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CapabilityHelpers {
    public static final String APPIUM_PREFIX = "appium:";

    /**
     * Helper that is used for capability values retrieval.
     * Supports both prefixed W3C and "classic" capability names.
     *
     * @param <T>          The corresponding capability type.
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
            if (caps.getCapability(capName) == null) {
                continue;
            }

            if (expectedType == String.class) {
                return expectedType.cast(String.valueOf(caps.getCapability(capName)));
            }
            if (expectedType.isAssignableFrom(caps.getCapability(capName).getClass())) {
                return expectedType.cast(caps.getCapability(capName));
            }
        }
        return null;
    }

    /**
     * Converts generic capability value to boolean without
     * throwing exceptions.
     *
     * @param value The capability value.
     * @return null is the passed value is null otherwise the converted value.
     */
    @Nullable
    public static Boolean toSafeBoolean(Object value) {
        return value == null ? null : Boolean.parseBoolean(String.valueOf(value));
    }

    /**
     * Converts generic capability value to integer.
     *
     * @param value The capability value.
     * @throws NumberFormatException If the given value cannot be parsed to a valid integer.
     * @return null is the passed value is null otherwise the converted value.
     */
    @Nullable
    public static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            return Integer.parseInt(String.valueOf(value));
        }
    }

    /**
     * Converts generic capability value to long without
     * throwing exceptions.
     *
     * @param value The capability value.
     * @throws NumberFormatException If the given value cannot be parsed to a valid long.
     * @return null is the passed value is null otherwise the converted value.
     */
    @Nullable
    public static Long toLong(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else {
            return Long.parseLong(String.valueOf(value));
        }
    }

    /**
     * Converts generic capability value to duration without
     * throwing exceptions. The value is assumed to be
     * measured in milliseconds.
     *
     * @param value The capability value.
     * @throws NumberFormatException If the given value cannot be parsed to a valid number.
     * @return null is the passed value is null otherwise the converted value.
     */
    @Nullable
    public static Duration toDuration(Object value) {
        return toDuration(value, Duration::ofMillis);
    }

    /**
     * Converts generic capability value to duration without
     * throwing exceptions.
     *
     * @param value     The capability value.
     * @param converter Converts the numeric value to a Duration instance.
     * @throws NumberFormatException If the given value cannot be parsed to a valid number.
     * @return null is the passed value is null otherwise the converted value.
     */
    @Nullable
    public static Duration toDuration(Object value,
                                      Function<Long, Duration> converter) {
        Long v = toLong(value);
        return v == null ? null : converter.apply(v);
    }
}
