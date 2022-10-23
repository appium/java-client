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

    private static final Filter DEFAULT_FILTER = new AppiumUserAgentFilter();

    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofMinutes(10);

    private static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(10);

    /**
     * Client side configuration.
     *
     * @param baseUri Base URL the client sends HTTP request to.
     * @param connectionTimeout The client connection timeout.
     * @param readTimeout The client read timeout.
     * @param filters Filters to modify incoming {@link org.openqa.selenium.remote.http.HttpRequest} or outgoing
     *                {@link org.openqa.selenium.remote.http.HttpResponse}.
     * @param proxy The client proxy preference.
     * @param credentials Credentials used for authenticating http requests
     * @param directConnect If directConnect is enabled.
     */
    protected AppiumClientConfig(
            URI baseUri,
            Duration connectionTimeout,
            Duration readTimeout,
            Filter filters,
            Proxy proxy,
            Credentials credentials,
            Boolean directConnect) {
        super(baseUri, connectionTimeout, readTimeout, filters, proxy, credentials);

        this.directConnect = Require.nonNull("Direct Connect", directConnect);
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
                DEFAULT_FILTER,
                null,
                null,
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
                clientConfig.filter(),
                clientConfig.proxy(),
                clientConfig.credentials(),
                false);
    }

    private AppiumClientConfig buildAppiumClientConfig(ClientConfig clientConfig, Boolean directConnect) {
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
    public AppiumClientConfig baseUri(URI baseUri) {
        ClientConfig clientConfig = super.baseUri(baseUri);
        return buildAppiumClientConfig(clientConfig, directConnect);
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
        ClientConfig clientConfig = super.connectionTimeout(timeout);
        return buildAppiumClientConfig(clientConfig, directConnect);
    }

    @Override
    public AppiumClientConfig readTimeout(Duration timeout) {
        ClientConfig clientConfig = super.connectionTimeout(timeout);
        return buildAppiumClientConfig(clientConfig, directConnect);
    }

    @Override
    public AppiumClientConfig withFilter(Filter filter) {
        ClientConfig clientConfig = super.withFilter(filter);
        return buildAppiumClientConfig(clientConfig, directConnect);
    }

    @Override
    public AppiumClientConfig withRetries() {
        ClientConfig clientConfig = super.withRetries();
        return buildAppiumClientConfig(clientConfig, directConnect);
    }


    @Override
    public ClientConfig proxy(Proxy proxy) {
        ClientConfig clientConfig = super.proxy(proxy);
        return buildAppiumClientConfig(clientConfig, directConnect);
    }

    @Override
    public AppiumClientConfig authenticateAs(Credentials credentials) {
        ClientConfig clientConfig = super.authenticateAs(credentials);
        return buildAppiumClientConfig(clientConfig, directConnect);
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
        // follows ClientConfig's design
        return new AppiumClientConfig(
            this.baseUri(),
            this.connectionTimeout(),
            this.readTimeout(),
            this.filter(),
            this.proxy(),
            this.credentials(),
            directConnect
        );
    }

    /**
     * Whether enable directConnect feature is enabled.
     *
     * @return If the directConnect is enabled. Defaults false.
     */
    public boolean isDirectConnectEnabled() {
        return directConnect;
    }
}
