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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class AppiumServerAvailabilityChecker {
    private static final Duration CONNECT_TIMEOUT = Duration.ofMillis(500);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(1);
    private static final Duration MAX_POLL_INTERVAL = Duration.ofMillis(320);
    private static final Duration MIN_POLL_INTERVAL = Duration.ofMillis(10);

    /**
     * Verifies a possibility of establishing a connection
     * to a running Appium server.
     *
     * @param serverStatusUrl The URL of /status endpoint.
     * @param timeout Wait timeout. If the server responds with non-200 error
     *                code then we are not going to retry, but throw an exception
     *                immediately.
     * @return true in case of success
     * @throws InterruptedException If the API is interrupted
     * @throws AppiumServerConnectionTimeout If it is not possible to successfully open
     * an HTTP connection to the server /status endpoint.
     * @throws AppiumServerConnectionError If an HTTP connection was opened successfully,
     * but non-200 error code was received.
     */
    public boolean waitUntilAvailable(URL serverStatusUrl, Duration timeout) throws InterruptedException {
        var interval = MIN_POLL_INTERVAL;
        var start = Instant.now();
        IOException lastError = null;
        while (Duration.between(start, Instant.now()).compareTo(timeout) <= 0) {
            HttpURLConnection connection = null;
            try {
                connection = connectToUrl(serverStatusUrl);
                return checkResponse(connection);
            } catch (IOException e) {
                lastError = e;
            } finally {
                Optional.ofNullable(connection).ifPresent(HttpURLConnection::disconnect);
            }
            //noinspection BusyWait
            Thread.sleep(interval.toMillis());
            interval = interval.compareTo(MAX_POLL_INTERVAL) >= 0 ? interval : interval.multipliedBy(2);
        }
        throw new AppiumServerConnectionTimeout(timeout, lastError);
    }

    private HttpURLConnection connectToUrl(URL url) throws IOException {
        var connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout((int) CONNECT_TIMEOUT.toMillis());
        connection.setReadTimeout((int) READ_TIMEOUT.toMillis());
        connection.connect();
        return connection;
    }

    private boolean checkResponse(HttpURLConnection connection) throws IOException {
        var responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        }
        var is = responseCode < HttpURLConnection.HTTP_BAD_REQUEST
                ? connection.getInputStream()
                : connection.getErrorStream();
        throw new AppiumServerConnectionError(connection.getURL(), responseCode, is);
    }
}
