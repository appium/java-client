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

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Converts script arguments to their JSON representation, in particular elements to element references.
 * Adapted from Selenium's {@code WebElementToJsonConverter} (Apache License 2.0).
 */
class WebElementToJsonConverter implements Function<@Nullable Object, @Nullable Object> {
    @Nullable
    @Override
    public Object apply(@Nullable Object arg) {
        if (arg == null || arg instanceof String || arg instanceof Boolean || arg instanceof Number) {
            return arg;
        }

        while (arg instanceof WrapsElement) {
            arg = ((WrapsElement) arg).getWrappedElement();
        }

        if (arg instanceof AppiumWebElement) {
            return Map.of(AppiumWebElement.ELEMENT_KEY, ((AppiumWebElement) arg).getId());
        }

        if (arg instanceof ShadowRoot) {
            return ((ShadowRoot) arg).toJson();
        }

        if (arg.getClass().isArray()) {
            arg = arrayToList(arg);
        }

        if (arg instanceof Collection<?>) {
            return ((Collection<?>) arg).stream().map(this).collect(toList());
        }

        if (arg instanceof Map<?, ?>) {
            Map<?, ?> args = (Map<?, ?>) arg;
            Map<String, @Nullable Object> converted = new HashMap<>(args.size());
            for (Map.Entry<?, ?> entry : args.entrySet()) {
                Object key = entry.getKey();
                if (!(key instanceof String)) {
                    throw new IllegalArgumentException(
                            "All keys in Map script arguments must be strings: " + key.getClass().getName());
                }
                converted.put((String) key, apply(entry.getValue()));
            }
            return converted;
        }

        throw new IllegalArgumentException("Argument is of an illegal type: " + arg.getClass().getName());
    }

    private static List<Object> arrayToList(Object array) {
        List<Object> list = new ArrayList<>();
        int arrayLength = Array.getLength(array);
        for (int i = 0; i < arrayLength; i++) {
            list.add(Array.get(array, i));
        }
        return list;
    }
}
