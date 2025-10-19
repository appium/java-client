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

import io.appium.java_client.utils.TestUtils;
import io.appium.java_client.plugins.storage.StorageClient;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private StorageClient storageClient;

    @BeforeEach
    void before() throws MalformedURLException {
        // These tests assume Appium server with storage plugin is already running
        // at the given baseUrl
        Assumptions.assumeFalse(TestUtils.isCiEnv());
        storageClient = new StorageClient(new URL("http://127.0.0.1:4723"));
        storageClient.reset();
    }

    @Test
    void shouldBeAbleToPerformBasicStorageActions() {
        assertTrue(storageClient.list().isEmpty());
        var name = "hello appium - saved page.htm";
        var testFile = TestUtils.resourcePathToAbsolutePath("html/" + name).toFile();
        storageClient.add(testFile);
        assertItemsCount(1);
        assertTrue(storageClient.delete(name));
        assertItemsCount(0);
        storageClient.add(testFile);
        assertItemsCount(1);
        storageClient.reset();
        assertItemsCount(0);
    }

    private void assertItemsCount(int expected) {
        var items = storageClient.list();
        assertEquals(expected, items.size());
    }
}
