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

package io.appium.java_client.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for GitHubReleaseFetcher functionality.
 */
public class GitHubReleaseFetcherTest {

    private static final String VODQA_RELEASE_URL =
            "https://github.com/appium/VodQAReactNative/releases/download/v1.0.1/VodQAReactNative-simulator-release.zip";

    private GitHubReleaseFetcher fetcher;

    @BeforeEach
    public void setUp() {
        fetcher = new GitHubReleaseFetcher(VODQA_RELEASE_URL);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up cache after each test
        fetcher.clearCache();
    }

    @Test
    public void testParseGitHubReleaseUrl() {
        GitHubReleaseFetcher.GitHubReleaseInfo info = fetcher.releaseInfo;

        assertEquals("appium", info.owner);
        assertEquals("VodQAReactNative", info.repo);
        assertEquals("v1.0.1", info.tag);
        assertEquals("VodQAReactNative-simulator-release.zip", info.filename);
    }

    @Test
    public void testParseInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GitHubReleaseFetcher("https://invalid-url.com/file.zip");
        });
    }

    @Test
    public void testFetchFile() throws IOException {
        Path appPath = fetcher.fetch();

        assertNotNull(appPath);
        assertTrue(Files.exists(appPath));
        assertTrue(Files.isRegularFile(appPath));
        assertTrue(Files.size(appPath) > 0);
    }

    @Test
    public void testCacheValidation() throws IOException {
        // First fetch
        Path appPath1 = fetcher.fetch();
        assertTrue(Files.exists(appPath1));

        // Second fetch should use cache
        Path appPath2 = fetcher.fetch();
        assertEquals(appPath1, appPath2);
        assertTrue(Files.exists(appPath2));
    }

    @Test
    public void testClearCache() throws IOException {
        // Fetch and cache the app
        Path appPath = fetcher.fetch();

        // Clear cache
        fetcher.clearCache();

        // Cache files should be deleted
        Path cacheDir = appPath.getParent();
        String[] files = cacheDir.toFile().list((dir, name) ->
            name.contains("appium_VodQAReactNative_v1.0.1_VodQAReactNative-simulator-release.zip"));
        assertTrue(files == null || files.length == 0);
    }

    @Test
    public void testETagIsFetchedAndStored() throws IOException {
        // Clear any existing cache
        fetcher.clearCache();

        // Fetch the app (this should fetch ETag from remote)
        Path appPath = fetcher.fetch();

        // Verify the app file exists
        assertTrue(Files.exists(appPath));

        // Verify ETag file was created
        Path cacheDir = appPath.getParent();
        String[] etagFiles = cacheDir.toFile().list(
                (dir, name) ->
                    name.contains("appium_VodQAReactNative_v1.0.1_VodQAReactNative-simulator-release.zip")
                    && name.endsWith(".etag")
        );
        assertNotNull(etagFiles);
        assertTrue(etagFiles.length > 0);

        // Verify ETag file has content
        Path etagFile = cacheDir.resolve(etagFiles[0]);
        assertTrue(Files.exists(etagFile));
        String etag = Files.readString(etagFile).trim();
        assertNotNull(etag);
        assertFalse(etag.isEmpty());
    }

    @Test
    public void testSecondFetchUsesCache() throws IOException {
        // First fetch - should download from remote
        final long startTime1 = System.currentTimeMillis();
        Path appPath1 = fetcher.fetch();
        final long fetchTime1 = System.currentTimeMillis() - startTime1;

        assertTrue(Files.exists(appPath1));
        assertTrue(Files.size(appPath1) > 0);

        // Second fetch - should use cache (much faster)
        final long startTime2 = System.currentTimeMillis();
        Path appPath2 = fetcher.fetch();
        final long fetchTime2 = System.currentTimeMillis() - startTime2;

        // Verify same file path is returned
        assertEquals(appPath1, appPath2);
        assertTrue(Files.exists(appPath2));

        // Verify second fetch was much faster (indicating cache usage)
        // Cache fetch should be at least 2x faster than network fetch
        assertTrue(fetchTime2 < fetchTime1 / 2,
            String.format("Second fetch (%dms) should be much faster than first fetch (%dms)",
                fetchTime2, fetchTime1));
    }

    @Test
    public void testConcurrentAccessWithSameUrl() throws Exception {
        // Clear any existing cache first
        fetcher.clearCache();

        int numberOfThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        try {
            CountDownLatch latch = new CountDownLatch(numberOfThreads);
            List<Future<Path>> futures = new ArrayList<>();

            // Create multiple fetchers with the same URL and fetch concurrently
            for (int i = 0; i < numberOfThreads; i++) {
                Future<Path> future = executor.submit(() -> {
                    try {
                        GitHubReleaseFetcher concurrentFetcher = new GitHubReleaseFetcher(VODQA_RELEASE_URL);
                        return concurrentFetcher.fetch();
                    } finally {
                        latch.countDown();
                    }
                });
                futures.add(future);
            }

            // Wait for all threads to complete
            latch.await();

            // Verify all fetchers returned the same file path
            List<Path> results = new ArrayList<>();
            for (Future<Path> future : futures) {
                Path result = future.get();
                assertNotNull(result);
                assertTrue(Files.exists(result));
                results.add(result);
            }

            // All results should be the same file path
            Path firstResult = results.get(0);
            for (Path result : results) {
                assertEquals(firstResult, result, "All concurrent fetchers should return the same cached file path");
            }
        } finally {
            executor.shutdown();
        }
    }
}
