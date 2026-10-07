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

package io.appium.java_client.http;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Credentials;

import javax.net.ssl.SSLContext;

import java.net.Proxy;
import java.net.URI;
import java.time.Duration;

/**
 * The settings a {@link HttpClient} is created with.
 */
public interface ClientConfig {
    /**
     * The URI relative request paths are resolved against.
     *
     * @return the base URI or null if only absolute URIs are used
     */
    @Nullable
    URI baseUri();

    /**
     * The maximum time to wait for a connection to be established.
     *
     * @return the connection timeout
     */
    Duration connectionTimeout();

    /**
     * The maximum time to wait for a response to be read.
     *
     * @return the read timeout
     */
    Duration readTimeout();

    /**
     * The maximum time to wait for a WebSocket connection to be established.
     *
     * @return the WebSocket timeout
     */
    Duration wsTimeout();

    /**
     * The filter applied to every request.
     *
     * @return the filter chain
     */
    Filter filter();

    /**
     * The proxy to route requests through.
     *
     * @return the proxy or null if none is used
     */
    @Nullable
    Proxy proxy();

    /**
     * The credentials to authenticate requests with.
     *
     * @return the credentials or null if none are configured
     */
    @Nullable
    Credentials credentials();

    /**
     * The SSL context used for secure connections.
     *
     * @return the SSL context or null for the transport default
     */
    @Nullable
    SSLContext sslContext();

    /**
     * The HTTP protocol version name as in {@code java.net.http.HttpClient.Version}, e.g. HTTP_1_1.
     *
     * @return the version name or null for the transport default
     */
    @Nullable
    String version();
}
