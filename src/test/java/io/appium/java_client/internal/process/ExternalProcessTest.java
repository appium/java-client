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
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExternalProcessTest {
    private static final String JAVA = Path.of(System.getProperty("java.home"), "bin", "java").toString();

    @TempDir
    Path tempDir;

    private ExternalProcess startSource(String body, ByteArrayOutputStream copy) throws IOException {
        Path source = tempDir.resolve("Main.java");
        Files.writeString(source, "public class Main { public static void main(String[] a) throws Exception { "
                + body + " } }");
        return ExternalProcess.builder()
                .command(JAVA, List.of(source.toString()))
                .copyOutputTo(copy)
                .start();
    }

    @Test
    void capturesAndForwardsCombinedOutput() throws IOException {
        var copy = new ByteArrayOutputStream();
        var process = startSource("System.out.println(\"out-line\"); System.err.println(\"err-line\");", copy);
        assertTrue(process.waitFor(Duration.ofSeconds(60)));

        assertTrue(process.getOutput().contains("out-line"));
        assertTrue(process.getOutput().contains("err-line"));
        assertTrue(copy.toString().contains("out-line"));
    }

    @Test
    void keepsOnlyTheTailOfTheOutput() throws IOException {
        var process = startSource("System.out.print(\"x\".repeat(100000) + \"END\");", new ByteArrayOutputStream());
        assertTrue(process.waitFor(Duration.ofSeconds(60)));

        assertTrue(process.getOutput().endsWith("END"));
        assertTrue(process.getOutput().length() <= 32768);
    }

    @Test
    void passesEnvironmentToTheProcess() throws IOException {
        var source = tempDir.resolve("Env.java");
        Files.writeString(source, "public class Env { public static void main(String[] a) { "
                + "System.out.print(System.getenv(\"APPIUM_TEST_VAR\")); } }");
        var process = ExternalProcess.builder()
                .command(JAVA, List.of(source.toString()))
                .environment("APPIUM_TEST_VAR", "expected-value")
                .start();
        assertTrue(process.waitFor(Duration.ofSeconds(60)));

        assertTrue(process.getOutput().endsWith("expected-value"));
    }

    @Test
    void shutdownStopsALongRunningProcess() throws IOException {
        var process = startSource("Thread.sleep(600000);", new ByteArrayOutputStream());
        assertTrue(process.isAlive());

        process.shutdown(Duration.ofSeconds(30));

        assertFalse(process.isAlive());
    }

    @Test
    void shutdownCompletesAndKeepsTheInterruptStatusWhenTheCallerIsInterrupted() throws IOException {
        var process = startSource("Thread.sleep(600000);", new ByteArrayOutputStream());
        assertTrue(process.isAlive());

        Thread.currentThread().interrupt();
        try {
            process.shutdown(Duration.ofSeconds(30));

            assertFalse(process.isAlive());
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            // Clear the status so it does not leak into other tests
            Thread.interrupted();
        }
    }

    @Test
    void failsToStartANonExistingExecutable() {
        var missing = new File(tempDir.toFile(), "no-such-executable").getAbsolutePath();

        assertThrows(IOException.class, () -> ExternalProcess.builder().command(missing, List.of()).start());
    }
}
