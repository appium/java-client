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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WireJsonTest {
    private static final Path GOLDEN = Path.of("src", "test", "resources", "wire-golden", "json.json");

    @Test
    void serializesLikeTheRecordedGolden() throws IOException {
        var golden = JsonParser.parseString(Files.readString(GOLDEN, StandardCharsets.UTF_8)).getAsJsonObject();
        var samples = io.appium.java_client.remote.WireGoldenSupportAccess.jsonSamples();

        assertEquals(golden.keySet(), samples.keySet());
        samples.forEach((name, value) -> {
            JsonElement actual = JsonParser.parseString(WireJson.toJson(value));
            assertEquals(golden.get(name), io.appium.java_client.remote.WireGoldenSupportAccess.canonical(actual),
                    "Sample " + name);
        });
    }

    @Test
    void readsIntegersAsLongAndOtherNumbersAsDouble() {
        Map<String, Object> parsed = WireJson.fromJson(
                "{\"i\":1,\"big\":12345678901234,\"d\":1.5,\"whole\":2.0,\"exp\":1e2,\"neg\":-3,"
                        + "\"nested\":{\"l\":[1,2.5,\"3\",null,true]}}",
                new TypeToken<Map<String, Object>>() { }.getType());

        assertEquals(1L, parsed.get("i"));
        assertEquals(12345678901234L, parsed.get("big"));
        assertEquals(1.5, parsed.get("d"));
        assertEquals(2L, parsed.get("whole"));
        assertEquals(100L, parsed.get("exp"));
        assertEquals(-3L, parsed.get("neg"));
        var list = (List<?>) ((Map<?, ?>) parsed.get("nested")).get("l");
        assertEquals(List.of(1L, 2.5, "3"), list.subList(0, 3));
        assertNull(list.get(3));
        assertEquals(true, list.get(4));
    }

    @Test
    void readsNumbersOutOfTheLongRangeAsDouble() {
        assertInstanceOf(Double.class, WireJson.fromJson("123456789012345678901234567890"));
    }

    @Test
    void readsAJsonNullAsNull() {
        assertNull(WireJson.fromJson("null"));
    }

    @Test
    void rejectsMalformedDocuments() {
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson(""));
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson("   "));
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson("OK"));
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson("{\"a\":1} trailing"));
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson("{\"a\":"));
        assertThrows(WireJson.WireJsonException.class, () -> WireJson.fromJson("<html></html>"));
    }

    @Test
    void skipsEmptyOptionalsInContainersAndWritesNullForTopLevelOnes() {
        assertEquals("{\"a\":1}", WireJson.toJson(new java.util.LinkedHashMap<String, Object>() {
            {
                put("a", 1);
                put("b", java.util.Optional.empty());
            }
        }));
        assertEquals("[1]", WireJson.toJson(List.of(1, java.util.Optional.empty())));
        assertEquals("null", WireJson.toJson(java.util.Optional.empty()));
    }

    @Test
    void writesPrimitiveArrays() {
        assertEquals("[1,2]", WireJson.toJson(new int[]{1, 2}));
    }

    @Test
    void failsOnCyclicStructures() {
        var cycle = new java.util.ArrayList<Object>();
        cycle.add(cycle);

        assertThrows(WireJson.WireJsonException.class, () -> WireJson.toJson(cycle));
    }
}
