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

package io.appium.java_client.plugin;

import io.appium.java_client.plugins.storage.StorageClient;
import io.appium.java_client.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Runs the storage client against a fake of the storage plugin of the Appium server.
 */
public class StorageTest {
    private static final String NAME = "hello appium - saved page.htm";

    private FakeStorageServer server;
    private StorageClient storageClient;

    @BeforeEach
    void before() throws IOException {
        server = new FakeStorageServer();
        storageClient = new StorageClient(server.url());
        storageClient.reset();
    }

    @AfterEach
    void after() throws IOException {
        server.close();
    }

    @Test
    void shouldBeAbleToPerformBasicStorageActions() throws IOException {
        assertTrue(storageClient.list().isEmpty());
        var testFile = TestUtils.resourcePathToAbsolutePath("html/" + NAME).toFile();
        storageClient.add(testFile);
        assertItemsCount(1);
        var item = storageClient.list().get(0);
        assertEquals(NAME, item.getName());
        assertEquals(testFile.length(), item.getSize());
        assertArrayEquals(Files.readAllBytes(testFile.toPath()), server.content(NAME));
        assertTrue(storageClient.delete(NAME));
        assertFalse(storageClient.delete(NAME));
        assertItemsCount(0);
        storageClient.add(testFile);
        assertItemsCount(1);
        storageClient.reset();
        assertItemsCount(0);
    }

    @Test
    void shouldAddAnItemUnderTheGivenName() {
        var testFile = TestUtils.resourcePathToAbsolutePath("html/" + NAME).toFile();
        storageClient.add(testFile, "renamed.htm");
        assertEquals("renamed.htm", storageClient.list().get(0).getName());
    }

    @Test
    void shouldFailIfTheServerReportsAFailedUpload() {
        var testFile = TestUtils.resourcePathToAbsolutePath("html/" + NAME).toFile();
        server.rejectUploads();
        var error = assertThrows(WebDriverException.class, () -> storageClient.add(testFile));
        assertTrue(error.getRawMessage().startsWith("The upload of '" + NAME + "' has failed"), error.getMessage());
        assertNull(error.getCause());
        assertItemsCount(0);
    }

    private void assertItemsCount(int expected) {
        assertEquals(expected, storageClient.list().size());
    }
}
