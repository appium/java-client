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

import org.openqa.selenium.Credentials;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.Filter;

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

/**
 * A class to store the appium http client configuration.
 */
public class AppiumClientConfig extends ClientConfig {
    private final boolean directConnect;

    // TODO: Update to use Appium Java UserAgent
    private static final Filter DEFAULT_FILTER = new AddAppiumUserAgent();

    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofMinutes(10);

    protected AppiumClientConfig(
            URI baseUri,
            Duration connectionTimeout,
            Duration readTimeout,
            Filter filters,
            Proxy proxy,
            Credentials credentials,
            boolean directConnect) {
        super(baseUri, connectionTimeout, readTimeout, filters, proxy, credentials);

        this.directConnect = directConnect;
     }

    /**
     * Return the instance of {@link AppiumClientConfig} with a default config.
     * @return the instance of {@link AppiumClientConfig}.
     */
    public static AppiumClientConfig defaultConfig() {
        return new AppiumClientConfig(
                null,
                Duration.ofSeconds(10),
                DEFAULT_READ_TIMEOUT,
                DEFAULT_FILTER,
                null,
                null,
                false);
    }

    /**
     * Return the instance of {@link AppiumClientConfig} from the given {@link ClientConfig} parameters.
     * @return the instance of {@link AppiumClientConfig}.
     */
    public static AppiumClientConfig fromClientConfig(ClientConfig clientConfig) {
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                false);
    }

    @Override
    public AppiumClientConfig baseUri(URI baseUri) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.baseUri(baseUri);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }

    @Override
    public AppiumClientConfig baseUrl(URL baseUrl) {
        try {
            return baseUri(Require.nonNull("Base URL", baseUrl).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppiumClientConfig connectionTimeout(Duration timeout) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.connectionTimeout(timeout);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }

    @Override
    public AppiumClientConfig readTimeout(Duration timeout) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.connectionTimeout(timeout);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }

    @Override
    public AppiumClientConfig withFilter(Filter filter) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.withFilter(filter);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }

    @Override
    public AppiumClientConfig withRetries() {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.withRetries();
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }


    @Override
    public ClientConfig proxy(Proxy proxy) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.proxy(proxy);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }

    @Override
    public AppiumClientConfig authenticateAs(Credentials credentials) {
        // ClientConfig returns a new instance
        ClientConfig clientConfig = super.authenticateAs(credentials);
        return new AppiumClientConfig(
                clientConfig.baseUri(),
                clientConfig.connectionTimeout(),
                clientConfig.readTimeout(),
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                directConnect);
    }


    /**
     * Whether enable directConnect feature described in
     * <a href="https://appiumpro.com/editions/86-connecting-directly-to-appium-hosts-in-distributed-environments">
     *     Connecting Directly to Appium Hosts in Distributed Environments</a>.
     *
     * @param directConnect if enable the directConnect feature
     * @return A new instance of AppiumClientConfig
     */
    public AppiumClientConfig directConnect(boolean directConnect) {
        return new AppiumClientConfig(
            this.baseUri(),
            this.connectionTimeout(),
            this.readTimeout(),
            this.filter(),
            this.proxy(),
            this.credentials(),
            Require.nonNull("Direct Connect", directConnect)
        );
    }

    /**
     * Whether enable directConnect feature is enabled.
     *
     * @return If the directConnect is enabled.Defaults false.
     */
    public boolean isDirectConnectEnabled() {
        return directConnect;
    }
}
