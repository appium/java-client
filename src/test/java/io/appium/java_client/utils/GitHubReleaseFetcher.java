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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generic utility class for fetching files from GitHub releases with ETag-based caching.
 */
public class GitHubReleaseFetcher {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private static final Path CACHE_DIR = Paths.get(System.getProperty("java.io.tmpdir"), "appium-java-client-cache");

    // Pattern to extract owner, repo, and tag from GitHub release URL
    private static final Pattern GITHUB_RELEASE_PATTERN = Pattern.compile(
        "https://github\\.com/([^/]+)/([^/]+)/releases/download/([^/]+)/(.+)"
    );

    // Static lock map to prevent race conditions for same URLs
    // Using WeakHashMap to prevent memory leaks - entries are garbage collected when URLs are no longer referenced
    private static final Map<String, Object> URL_LOCKS = Collections.synchronizedMap(new WeakHashMap<>());

    private final String releaseUrl;
    final GitHubReleaseInfo releaseInfo; // Package-private for testing
    private final Path appFile;
    private final Path etagFile;

    public GitHubReleaseFetcher(String releaseUrl) {
        this.releaseUrl = releaseUrl;
        this.releaseInfo = parseGitHubReleaseUrl(releaseUrl);
        Path cacheKey = createCacheKey(this.releaseInfo);
        this.appFile = CACHE_DIR.resolve(cacheKey);
        this.etagFile = CACHE_DIR.resolve(cacheKey + ".etag");
    }

    /**
     * Fetches a file from the GitHub release URL with ETag-based caching.
     *
     * @return Path to the local cached file
     * @throws IOException if there's an error fetching or caching the file
     */
    public Path fetch() throws IOException {
        // Get or create a lock for this specific URL to prevent race conditions
        Object lock = URL_LOCKS.computeIfAbsent(releaseUrl, k -> new Object());

        synchronized (lock) {
            try {
                // Ensure cache directory exists
                Files.createDirectories(CACHE_DIR);

                // Always send conditional request to verify if file has changed
                return fetchRemoteFile();

            } catch (Exception e) {
                throw new IOException("Failed to fetch GitHub release: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Parses a GitHub release URL to extract repository information.
     *
     * @param releaseUrl The GitHub release URL
     * @return GitHubReleaseInfo containing parsed components
     * @throws IllegalArgumentException if the URL is not a valid GitHub release URL
     */
    private static GitHubReleaseInfo parseGitHubReleaseUrl(String releaseUrl) {
        Matcher matcher = GITHUB_RELEASE_PATTERN.matcher(releaseUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid GitHub release URL: " + releaseUrl);
        }

        return new GitHubReleaseInfo(
            matcher.group(1), // owner
            matcher.group(2), // repo
            matcher.group(3), // tag
            matcher.group(4)  // filename
        );
    }

    /**
     * Clears the cache for this GitHub release.
     *
     * @throws IOException if there's an error clearing the cache
     */
    public void clearCache() throws IOException {
        // Get or create a lock for this specific URL to prevent race conditions
        Object lock = URL_LOCKS.computeIfAbsent(releaseUrl, k -> new Object());

        synchronized (lock) {
            Files.deleteIfExists(appFile);
            Files.deleteIfExists(etagFile);
        }
    }

    private static Path createCacheKey(GitHubReleaseInfo releaseInfo) {
        // Create a unique cache key based on owner, repo, tag, and filename
        String key = String.format("%s_%s_%s_%s",
            releaseInfo.owner,
            releaseInfo.repo,
            releaseInfo.tag,
            releaseInfo.filename);
        // Replace invalid filename characters
        return Paths.get(key.replaceAll("[^a-zA-Z0-9._-]", "_"));
    }



    private Path fetchRemoteFile() throws IOException, InterruptedException {
        // Create request with If-None-Match header for conditional GET
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(releaseUrl))
                .timeout(Duration.ofMinutes(5))
                .GET();

        // Add If-None-Match header if we have a cached ETag
        if (Files.exists(etagFile)) {
            try {
                String storedEtag = Files.readString(etagFile).trim();
                if (!storedEtag.isEmpty()) {
                    requestBuilder.header("If-None-Match", storedEtag);
                }
            } catch (IOException e) {
                // If we can't read the stored ETag, proceed without conditional header
            }
        }

        HttpRequest request = requestBuilder.build();

        try {
            HttpResponse<Path> response = HTTP_CLIENT.send(request,
                HttpResponse.BodyHandlers.ofFile(appFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE));

            int statusCode = response.statusCode();

            if (statusCode == 304) {
                // Not Modified - cached version is still valid, no download needed
                return appFile;
            } else if (statusCode == 200) {
                // File has changed or is new - save the new ETag for future conditional requests
                String etag = response.headers().firstValue("ETag").orElse(null);
                if (etag != null) {
                    Files.writeString(etagFile, etag, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
                return appFile;
            } else {
                throw new IOException(String.format("Failed to fetch %s: HTTP %s", releaseUrl, statusCode));
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Request interrupted", e);
        }
    }

    /**
     * Information about a GitHub release parsed from URL.
     */
    public static class GitHubReleaseInfo {
        public final String owner;
        public final String repo;
        public final String tag;
        public final String filename;

        public GitHubReleaseInfo(String owner, String repo, String tag, String filename) {
            this.owner = owner;
            this.repo = repo;
            this.tag = tag;
            this.filename = filename;
        }

        @Override
        public String toString() {
            return String.format("GitHubReleaseInfo{owner='%s', repo='%s', tag='%s', filename='%s'}",
                owner, repo, tag, filename);
        }
    }
}
