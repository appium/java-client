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

package io.appium.java_client.internal.process;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExecutableFinderTest {

    @Test
    void returnsNullWhenNothingMatches() {
        String named = "definitely-not-a-real-executable-" + UUID.randomUUID();

        assertNull(new ExecutableFinder().find(named));
    }

    @Test
    void findsAnExecutableOnPath() {
        // The JVM running this test was itself launched via a PATH-resolvable "java",
        // so this exercises the real PATH-scanning branch without mocking the environment.
        assertNotNull(new ExecutableFinder().find("java"));
    }
}
