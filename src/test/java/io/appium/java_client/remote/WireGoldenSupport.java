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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpRequest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Helpers for golden-file tests that pin the wire behavior. Set GOLDEN_UPDATE=1 to regenerate the files.
 */
final class WireGoldenSupport {
    private static final Path GOLDEN_DIR = Path.of("src", "test", "resources", "wire-golden");
    private static final Gson PRETTY = new GsonBuilder()
            .setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();

    private WireGoldenSupport() {
    }

    static void verifyGolden(String name, Object actual) {
        String actualJson = PRETTY.toJson(actual) + "\n";
        Path file = GOLDEN_DIR.resolve(name);
        try {
            if (System.getenv("GOLDEN_UPDATE") != null) {
                Files.createDirectories(file.getParent());
                Files.writeString(file, actualJson, StandardCharsets.UTF_8);
                return;
            }
            if (!Files.exists(file)) {
                fail("Missing golden file " + file + ". Run with GOLDEN_UPDATE=1 to create it.");
            }
            assertEquals(Files.readString(file, StandardCharsets.UTF_8), actualJson, "Golden mismatch: " + name);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /** Describes a value with its concrete number/collection types, so Long vs Double drift is visible. */
    static Object typed(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Map) {
            Map<String, Object> result = new TreeMap<>();
            ((Map<?, ?>) value).forEach((k, v) -> result.put(String.valueOf(k), typed(v)));
            return result;
        }
        if (value instanceof Collection) {
            List<Object> result = new ArrayList<>();
            ((Collection<?>) value).forEach(v -> result.add(typed(v)));
            return result;
        }
        if (value instanceof WebDriverException) {
            return value.getClass().getSimpleName() + ":" + stableMessage((WebDriverException) value);
        }
        return value.getClass().getSimpleName() + ":" + value;
    }

    /** The exception message without the host-specific "Host info"/"Build info" suffix. */
    static String stableMessage(WebDriverException e) {
        return e.getRawMessage().split("\\n(Host|Build) info:")[0];
    }

    /** Parses JSON keeping number text as is and sorts object keys, so only semantic drift is visible. */
    static JsonElement canonical(String json) {
        return canonical(JsonParser.parseString(json));
    }

    static JsonElement canonical(JsonElement element) {
        if (element.isJsonObject()) {
            var sorted = new JsonObject();
            new TreeMap<>(element.getAsJsonObject().asMap()).forEach((k, v) -> sorted.add(k, canonical(v)));
            return sorted;
        }
        if (element.isJsonArray()) {
            var array = new JsonArray();
            element.getAsJsonArray().forEach(v -> array.add(canonical(v)));
            return array;
        }
        return element;
    }

    static Map<String, Object> describeRequest(HttpRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("method", request.getMethod().name());
        result.put("uri", request.getUri());
        Map<String, Object> headers = new TreeMap<>();
        for (String header : request.getHeaderNames()) {
            if (!header.equalsIgnoreCase("content-length")) {
                headers.put(header.toLowerCase(), request.getHeader(header));
            }
        }
        result.put("headers", headers);
        String body = Contents.string(request);
        String contentLength = request.getHeader("Content-Length");
        if (contentLength != null) {
            assertEquals(body.getBytes(StandardCharsets.UTF_8).length, Integer.parseInt(contentLength));
        }
        result.put("body", body.isEmpty() ? JsonNull.INSTANCE : canonical(body));
        return result;
    }

    static Map<String, Object> mapOf(Object... kv) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (int i = 0; i < kv.length; i += 2) {
            result.put((String) kv[i], kv[i + 1]);
        }
        return result;
    }

    static Sequence pointerSequence() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 0);
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), 10, 20));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        return sequence;
    }

    /** Values of the types sent as command parameters. */
    static Map<String, Object> jsonSamples() {
        var element = new AppiumWebElement();
        element.setId("e1");
        Map<String, Object> samples = new LinkedHashMap<>();
        samples.put("null", null);
        samples.put("integer", 1);
        samples.put("long", 12345678901234L);
        samples.put("double", 1.5);
        samples.put("wholeDouble", 2.0);
        samples.put("float", 1.25f);
        samples.put("boolean", true);
        samples.put("specialString", "a<b>&'\"\\\n\t" + new String(Character.toChars(0x01))
                + new String(Character.toChars(0xe9)) + new String(Character.toChars(0x2028))
                + new String(Character.toChars(0x1f600)));
        samples.put("optionalPresent", Optional.of("x"));
        samples.put("optionalEmpty", Optional.empty());
        samples.put("enum", Platform.ANDROID);
        samples.put("date", new Date(1700000000000L));
        samples.put("instant", Instant.ofEpochSecond(1700000000L));
        samples.put("uri", URI.create("https://example.com/a?b=c"));
        samples.put("uuid", new UUID(1L, 2L));
        samples.put("objectArray", new Object[]{"a", 1, null, Map.of("k", "v")});
        samples.put("charSequenceArray", new CharSequence[]{"ab", new StringBuilder("c")});
        samples.put("nestedMap", mapOf("a", mapOf("b", List.of(1, 2.5, "3")), "n", null));
        samples.put("cookie", new Cookie.Builder("n", "v").path("/").domain("d").isSecure(true)
                .expiresOn(new Date(1700000000000L)).sameSite("Lax").build());
        samples.put("immutableCapabilities", new ImmutableCapabilities(
                "platformName", "Android", "appium:deviceName", "x", "appium:width", 1080));
        samples.put("mutableCapabilitiesWithPlatform", new MutableCapabilities(
                Map.of("platformName", Platform.ANDROID)));
        samples.put("element", element);
        samples.put("elementList", List.of(element));
        samples.put("sequence", pointerSequence());
        return samples;
    }
}
