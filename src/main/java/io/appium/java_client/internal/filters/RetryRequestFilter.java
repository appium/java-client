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

package io.appium.java_client.internal.filters;

import io.appium.java_client.http.Filter;
import io.appium.java_client.http.HttpHandler;
import io.appium.java_client.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;

/**
 * Retries requests that failed to connect or got an empty 500 / a 503 response.
 * Adapted from Selenium's {@code RetryRequest} (Apache License 2.0).
 */
public class RetryRequestFilter implements Filter {
    /**
     * Creates a new instance.
     */
    public RetryRequestFilter() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(RetryRequestFilter.class);
    private static final int RETRIES_ON_CONNECTION_FAILURE = 3;
    private static final int RETRIES_ON_SERVER_ERROR = 2;
    private static final int NEEDED_ATTEMPTS = Math.max(RETRIES_ON_CONNECTION_FAILURE, RETRIES_ON_SERVER_ERROR) + 1;

    @Override
    public HttpHandler apply(HttpHandler next) {
        return req -> {
            for (int i = 0; i < NEEDED_ATTEMPTS; i++) {
                HttpResponse response;
                try {
                    response = next.execute(req);
                } catch (RuntimeException ex) {
                    boolean isConnectionFailure = ex.getCause() instanceof ConnectException;
                    if (isConnectionFailure && i < RETRIES_ON_CONNECTION_FAILURE) {
                        LOG.debug("Retry #{} on ConnectException", i + 1, ex);
                        continue;
                    }
                    throw ex;
                }
                boolean isEmptyServerError = response.getStatus() == HTTP_INTERNAL_ERROR
                        && response.getContent().length() == 0;
                boolean isServerError = isEmptyServerError || response.getStatus() == HTTP_UNAVAILABLE;
                if (isServerError && i < RETRIES_ON_SERVER_ERROR) {
                    LOG.debug("Retry #{} on a server error: {}", i + 1, response.getStatus());
                    continue;
                }
                return response;
            }
            throw new IllegalStateException("Effectively unreachable code reached, check the constants");
        };
    }
}
