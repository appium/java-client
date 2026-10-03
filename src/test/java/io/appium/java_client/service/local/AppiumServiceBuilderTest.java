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

package io.appium.java_client.service.local;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppiumServiceBuilderTest {
    private static final File NODE = new File(Path.of(System.getProperty("java.home"), "bin", "java").toString());

    @TempDir
    Path tempDir;

    private AppiumServiceBuilder newBuilder() throws IOException {
        File mainJs = Files.createFile(tempDir.resolve("main.js")).toFile();
        return new AppiumServiceBuilder().usingDriverExecutable(NODE).withAppiumJS(mainJs);
    }

    @Test
    void buildsServiceWithGivenPortAndAddress() throws IOException {
        var service = newBuilder().usingPort(4777).withIPAddress("127.0.0.1").build();

        assertEquals("http://127.0.0.1:4777/", service.getUrl().toString());
        assertFalse(service.isRunning());
    }

    @Test
    void appliesBasePath() throws IOException {
        var service = newBuilder().usingPort(4777).withIPAddress("127.0.0.1")
                .withArgument(io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH, "/wd/hub")
                .build();

        assertEquals("http://127.0.0.1:4777/wd/hub/", service.getUrl().toString());
    }

    @Test
    void picksFreePortWhenRequested() throws IOException {
        var service = newBuilder().usingAnyFreePort().withIPAddress("127.0.0.1").build();

        assertNotEquals("http://127.0.0.1:0/", service.getUrl().toString());
        assertTrue(service.getUrl().getPort() > 0);
    }

    @Test
    void picksFreePortOnReusedBuilder() throws IOException {
        var builder = newBuilder().usingPort(4777);

        assertEquals(4777, builder.build().getUrl().getPort());
        assertNotEquals(4777, builder.build().getUrl().getPort());
    }

    @Test
    void passesPortAddressAndLogFileToTheServer() throws IOException {
        var logFile = tempDir.resolve("appium.log").toFile();
        var args = newBuilder().usingPort(4888).withIPAddress("127.0.0.1").withLogFile(logFile).createArgs();

        assertEquals(List.of(
                tempDir.resolve("main.js").toString(),
                "--port", "4888",
                "--address", "127.0.0.1",
                "--log", logFile.getAbsolutePath()
        ), args);
    }

    @Test
    void rejectsNegativePort() {
        assertThrows(IllegalArgumentException.class, () -> new AppiumServiceBuilder().usingPort(-1));
    }

    @Test
    void acceptsEnvironmentAndTimeout() throws IOException {
        var service = newBuilder().usingPort(4777)
                .withEnvironment(Map.of("FOO", "bar"))
                .withTimeout(Duration.ofSeconds(5))
                .build();

        assertFalse(service.isRunning());
    }
}
