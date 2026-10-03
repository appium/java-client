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

package io.appium.java_client.internal.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Strictness;
import com.google.gson.ToNumberStrategy;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * JSON (de)serialization for the WebDriver wire protocol. Values are written and read the way
 * Selenium's {@code Json} does it for the types Appium exchanges: integers are read as {@link Long},
 * whole numbers as {@link Long}, other numbers as {@link Double}, and objects exposing
 * {@code toJson()}, {@code asMap()} or {@code toMap()} are serialized through that method.
 */
public final class WireJson {
    private static final int MAX_DEPTH = 64;
    private static final String[] CONVERSION_METHODS = {"toJson", "asMap", "toMap"};

    private static final ToNumberStrategy NUMBER_STRATEGY = reader -> {
        String text = reader.nextString();
        try {
            return toNumber(text);
        } catch (NumberFormatException e) {
            throw new MalformedJsonException("Unable to parse a number: " + text, e);
        }
    };

    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .setObjectToNumberStrategy(NUMBER_STRATEGY)
            .setNumberToNumberStrategy(NUMBER_STRATEGY)
            .create();

    private WireJson() {
    }

    private static Number toNumber(String text) {
        boolean isDecimal = text.indexOf('.') >= 0 || text.indexOf('e') >= 0 || text.indexOf('E') >= 0;
        if (!isDecimal) {
            try {
                return Long.parseLong(text);
            } catch (NumberFormatException e) {
                // Does not fit into a long
                return new BigDecimal(text).doubleValue();
            }
        }
        double value = new BigDecimal(text).doubleValue();
        if (value % 1 != 0 || value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
            return value;
        }
        return (long) value;
    }

    /**
     * Serializes the given value to compact JSON.
     *
     * @param value the value to serialize
     * @return JSON string
     */
    public static String toJson(@Nullable Object value) {
        return GSON.toJson(toTree(value, 0));
    }

    /**
     * Parses a JSON document into the given type. Parsing is strict: trailing content is rejected.
     *
     * @param json the JSON document
     * @param type the target type, e.g. a {@code Map<String, Object>} type token
     * @param <T> the target type
     * @return the parsed value, or null if the document is a JSON null
     * @throws WireJsonException if the document is blank or malformed
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type type) {
        if (json.trim().isEmpty()) {
            throw new WireJsonException("Unable to parse an empty JSON document");
        }
        try (JsonReader reader = new JsonReader(new StringReader(json))) {
            reader.setStrictness(Strictness.STRICT);
            T result = (T) GSON.getAdapter(TypeToken.get(type)).read(reader);
            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw new WireJsonException("Unexpected content after the JSON document: " + json);
            }
            return result;
        } catch (IOException | RuntimeException e) {
            if (e instanceof WireJsonException) {
                throw (WireJsonException) e;
            }
            throw new WireJsonException("Unable to parse the JSON document: " + e.getMessage(), e);
        }
    }

    /**
     * Parses a JSON document into untyped values: maps, lists, strings, booleans, longs and doubles.
     *
     * @param json the JSON document
     * @return the parsed value, or null if the document is a JSON null
     * @throws WireJsonException if the document is blank or malformed
     */
    @Nullable
    public static Object fromJson(String json) {
        return fromJson(json, Object.class);
    }

    private static JsonElement toTree(@Nullable Object value, int depth) {
        if (value == null) {
            return JsonNull.INSTANCE;
        }
        if (depth > MAX_DEPTH) {
            throw new WireJsonException("Reached the maximum depth of " + MAX_DEPTH + " while writing JSON");
        }
        JsonElement simple = simpleToTree(value);
        if (simple != null) {
            return simple;
        }
        if (value instanceof JsonElement) {
            return (JsonElement) value;
        }
        if (value instanceof Optional) {
            return toTree(((Optional<?>) value).orElse(null), depth);
        }
        Object converted = convertUsingMethod(value);
        if (converted != NO_CONVERSION) {
            return toTree(converted, depth + 1);
        }
        if (value instanceof Collection) {
            var array = new JsonArray();
            for (Object item : (Collection<?>) value) {
                if (!isEmptyOptional(item)) {
                    array.add(toTree(item, depth + 1));
                }
            }
            return array;
        }
        if (value instanceof Map) {
            var object = new JsonObject();
            ((Map<?, ?>) value).forEach((k, v) -> {
                if (!isEmptyOptional(v)) {
                    object.add(String.valueOf(k), toTree(v, depth + 1));
                }
            });
            return object;
        }
        if (value.getClass().isArray()) {
            var array = new JsonArray();
            for (int i = 0; i < Array.getLength(value); i++) {
                Object item = Array.get(value, i);
                if (!isEmptyOptional(item)) {
                    array.add(toTree(item, depth + 1));
                }
            }
            return array;
        }
        // Unknown types are serialized by their fields
        return GSON.toJsonTree(value);
    }

    @Nullable
    private static JsonElement simpleToTree(Object value) {
        if (value instanceof CharSequence) {
            return new JsonPrimitive(value.toString());
        }
        if (value instanceof Number) {
            return new JsonPrimitive((Number) value);
        }
        if (value instanceof Boolean) {
            return new JsonPrimitive((Boolean) value);
        }
        if (value instanceof Date) {
            return new JsonPrimitive(MILLISECONDS.toSeconds(((Date) value).getTime()));
        }
        if (value instanceof Instant) {
            return new JsonPrimitive(DateTimeFormatter.ISO_INSTANT.format((Instant) value));
        }
        if (value instanceof Enum || value instanceof URI || value instanceof UUID) {
            return new JsonPrimitive(String.valueOf(value));
        }
        if (value instanceof File) {
            return new JsonPrimitive(((File) value).getAbsolutePath());
        }
        if (value instanceof URL) {
            return new JsonPrimitive(((URL) value).toExternalForm());
        }
        return null;
    }

    private static boolean isEmptyOptional(@Nullable Object value) {
        return value instanceof Optional && ((Optional<?>) value).isEmpty();
    }

    private static final Object NO_CONVERSION = new Object();

    private static Object convertUsingMethod(Object value) {
        if (value instanceof Collection || value instanceof Map || value.getClass().isArray()) {
            return NO_CONVERSION;
        }
        for (String name : CONVERSION_METHODS) {
            Method method = findMethod(value.getClass(), name);
            if (method != null) {
                try {
                    return method.invoke(value);
                } catch (ReflectiveOperationException e) {
                    throw new WireJsonException("Unable to read " + value + " using method " + name, e);
                }
            }
        }
        return NO_CONVERSION;
    }

    @Nullable
    private static Method findMethod(@Nullable Class<?> type, String name) {
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            try {
                Method method = current.getDeclaredMethod(name);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                // Look at the super class
            }
        }
        return null;
    }

    /**
     * Thrown if a JSON document cannot be read or written.
     */
    public static class WireJsonException extends WebDriverException {
        public WireJsonException(String message) {
            super(message);
        }

        public WireJsonException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
