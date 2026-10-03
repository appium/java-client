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

package io.appium.java_client;

import io.appium.java_client.http.ClientConfig;
import io.appium.java_client.http.Filter;
import io.appium.java_client.internal.filters.AppiumIdempotencyFilter;
import io.appium.java_client.internal.filters.AppiumUserAgentFilter;
import io.appium.java_client.internal.filters.RetryRequestFilter;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Credentials;

import javax.net.ssl.SSLContext;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import static java.util.Objects.requireNonNull;

/**
 * A class to store the appium http client configuration. The instances are immutable:
 * each of the methods that change a setting returns a new instance.
 */
public class AppiumClientConfig implements ClientConfig {
    private static final Filter DEFAULT_FILTERS = new AppiumUserAgentFilter()
            .andThen(new AppiumIdempotencyFilter());

    private static final Filter RETRY_FILTER = new RetryRequestFilter();

    private static final String DEFAULT_HTTP_VERSION = "HTTP_1_1";

    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofMinutes(10);

    private static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(10);

    private static final Duration DEFAULT_WS_TIMEOUT = Duration.ofSeconds(30);

    private final @Nullable URI baseUri;
    private final Duration connectionTimeout;
    private final Duration readTimeout;
    private final Duration wsTimeout;
    private final Filter filters;
    private final @Nullable Proxy proxy;
    private final @Nullable Credentials credentials;
    private final @Nullable SSLContext sslContext;
    private final @Nullable String version;
    private final boolean directConnect;

    /**
     * Client side configuration.
     *
     * @param baseUri Base URL the client sends HTTP request to.
     * @param connectionTimeout The client connection timeout.
     * @param readTimeout The client read timeout.
     * @param wsTimeout The client WebSocket timeout.
     * @param filters Filters to modify outgoing {@link io.appium.java_client.http.HttpRequest} or incoming
     *                {@link io.appium.java_client.http.HttpResponse}.
     * @param proxy The client proxy preference.
     * @param credentials Credentials used for authenticating http requests
     * @param sslContext SSL context (if present)
     * @param version The HTTP protocol version name, e.g. HTTP_1_1
     * @param directConnect If directConnect is enabled.
     */
    protected AppiumClientConfig(
            @Nullable URI baseUri,
            Duration connectionTimeout,
            Duration readTimeout,
            Duration wsTimeout,
            Filter filters,
            @Nullable Proxy proxy,
            @Nullable Credentials credentials,
            @Nullable SSLContext sslContext,
            @Nullable String version,
            boolean directConnect) {
        this.baseUri = baseUri;
        this.connectionTimeout = requireNonNegative("Connection timeout", connectionTimeout);
        this.readTimeout = requireNonNegative("Read timeout", readTimeout);
        this.wsTimeout = requireNonNegative("WebSocket timeout", wsTimeout);
        this.filters = requireNonNull(filters, "Filters");
        this.proxy = proxy;
        this.credentials = credentials;
        this.sslContext = sslContext;
        this.version = version;
        this.directConnect = directConnect;
    }

    private static Duration requireNonNegative(String name, Duration value) {
        requireNonNull(value, name);
        if (value.isNegative()) {
            throw new IllegalArgumentException(name + " must be set to 0 or more");
        }
        return value;
    }

    /**
     * Return the instance of {@link AppiumClientConfig} with a default config.
     * @return the instance of {@link AppiumClientConfig}.
     */
    public static AppiumClientConfig defaultConfig() {
        return new AppiumClientConfig(
                null,
                DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_WS_TIMEOUT,
                DEFAULT_FILTERS,
                null,
                null,
                null,
                DEFAULT_HTTP_VERSION,
                false);
    }

    /**
     * Return the instance of {@link AppiumClientConfig} from the given {@link ClientConfig} parameters.
     * @param clientConfig take a look at {@link ClientConfig}
     * @return the instance of {@link AppiumClientConfig}.
     */
    public static AppiumClientConfig fromClientConfig(ClientConfig clientConfig) {
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.wsTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                clientConfig.sslContext(),
                clientConfig.version(),
                false);
    }

    @Override
    @Nullable
    public URI baseUri() {
        return baseUri;
    }

    public AppiumClientConfig baseUri(URI baseUri) {
        return new AppiumClientConfig(requireNonNull(baseUri, "Base URI"), connectionTimeout, readTimeout, wsTimeout,
                filters, proxy, credentials, sslContext, version, directConnect);
    }

    /**
     * Sets the base URL.
     *
     * @param baseUrl the URL requests are sent to
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig baseUrl(URL baseUrl) {
        try {
            return baseUri(requireNonNull(baseUrl, "Base URL").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The base URI as URL.
     *
     * @return the base URL or null if the base URI is not set
     */
    @Nullable
    public URL baseUrl() {
        if (baseUri == null) {
            return null;
        }
        try {
            return baseUri.toURL();
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Duration connectionTimeout() {
        return connectionTimeout;
    }

    public AppiumClientConfig connectionTimeout(Duration timeout) {
        return new AppiumClientConfig(baseUri, timeout, readTimeout, wsTimeout, filters, proxy, credentials,
                sslContext, version, directConnect);
    }

    @Override
    public Duration readTimeout() {
        return readTimeout;
    }

    public AppiumClientConfig readTimeout(Duration timeout) {
        return new AppiumClientConfig(baseUri, connectionTimeout, timeout, wsTimeout, filters, proxy, credentials,
                sslContext, version, directConnect);
    }

    @Override
    public Duration wsTimeout() {
        return wsTimeout;
    }

    public AppiumClientConfig wsTimeout(Duration timeout) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, timeout, filters, proxy, credentials,
                sslContext, version, directConnect);
    }

    @Override
    public Filter filter() {
        return filters;
    }

    /**
     * Sets the filter that is applied to the requests before the default filters of the client.
     * The filter replaces the one set earlier.
     *
     * @param filter the filter to apply
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig withFilter(Filter filter) {
        requireNonNull(filter, "Filter");
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout,
                filter.andThen(DEFAULT_FILTERS), proxy, credentials, sslContext, version, directConnect);
    }

    /**
     * Retries the requests that fail because of connection problems or server errors.
     *
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig withRetries() {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout,
                filters.andThen(RETRY_FILTER), proxy, credentials, sslContext, version, directConnect);
    }

    @Override
    @Nullable
    public Proxy proxy() {
        return proxy;
    }

    public AppiumClientConfig proxy(Proxy proxy) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout, filters,
                requireNonNull(proxy, "Proxy"), credentials, sslContext, version, directConnect);
    }

    @Override
    @Nullable
    public Credentials credentials() {
        return credentials;
    }

    public AppiumClientConfig authenticateAs(Credentials credentials) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout, filters, proxy,
                requireNonNull(credentials, "Credentials"), sslContext, version, directConnect);
    }

    @Override
    @Nullable
    public SSLContext sslContext() {
        return sslContext;
    }

    public AppiumClientConfig sslContext(SSLContext sslContext) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout, filters, proxy,
                credentials, requireNonNull(sslContext, "SSL Context"), version, directConnect);
    }

    @Override
    @Nullable
    public String version() {
        return version;
    }

    /**
     * Sets the HTTP protocol version.
     *
     * @param version the version name as in {@code java.net.http.HttpClient.Version}, e.g. HTTP_2
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig version(String version) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout, filters, proxy,
                credentials, sslContext, requireNonNull(version, "Version"), directConnect);
    }

    /**
     * Whether enable directConnect feature described in
     * <a href="https://www.headspin.io/blog/connecting-directly-to-appium-hosts-in-distributed-environments">
     *     Connecting Directly to Appium Hosts in Distributed Environments</a>.
     *
     * @param directConnect if enable the directConnect feature
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig directConnect(boolean directConnect) {
        return new AppiumClientConfig(baseUri, connectionTimeout, readTimeout, wsTimeout, filters, proxy,
                credentials, sslContext, version, directConnect);
    }

    /**
     * Whether enable directConnect feature is enabled.
     *
     * @return If the directConnect is enabled. Defaults false.
     */
    public boolean isDirectConnectEnabled() {
        return directConnect;
    }

    @Override
    public String toString() {
        return "AppiumClientConfig{"
                + "baseUri=" + baseUri
                + ", connectionTimeout=" + connectionTimeout
                + ", readTimeout=" + readTimeout
                + ", wsTimeout=" + wsTimeout
                + ", filters=" + filters
                + ", proxy=" + proxy
                + ", credentials=" + credentials
                + ", sslcontext=" + sslContext
                + ", version=" + version
                + ", directConnect=" + directConnect
                + '}';
    }
}
