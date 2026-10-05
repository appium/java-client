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

package io.appium.java_client.selenium;

import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumRemoteWebDriver;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.bidi.BiDiException;
import org.openqa.selenium.bidi.Connection;
import org.openqa.selenium.bidi.Handle;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * A Selenium {@link RemoteWebDriver} that works in the session of an Appium driver. Commands go through the
 * command executor of the Appium driver. It also implements {@code HasBiDi}, if the session was created
 * with the {@code webSocketUrl} capability, so Selenium BiDi modules and the {@code Augmenter} can be used.
 * Quitting it quits the session of the Appium driver.
 */
public class BridgedRemoteWebDriver extends RemoteWebDriver implements WrapsDriver {
    private static final Logger LOG = LoggerFactory.getLogger(BridgedRemoteWebDriver.class);

    private final AppiumRemoteWebDriver delegate;
    private final HttpClient.Factory webSocketClientFactory;
    private @Nullable Optional<BiDi> biDi;

    BridgedRemoteWebDriver(AppiumRemoteWebDriver delegate, HttpClient.Factory webSocketClientFactory) {
        super();
        this.delegate = delegate;
        this.webSocketClientFactory = webSocketClientFactory;
        var sessionId = delegate.getSessionId();
        if (sessionId == null) {
            throw new IllegalArgumentException("The Appium driver does not have a session");
        }
        setCommandExecutor(new SeleniumCommandExecutor(delegate.getCommandExecutor()));
        setSessionId(sessionId.toString());
        this.capabilities = delegate.getCapabilities();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return delegate;
    }

    /**
     * Returns the BiDi connection, which is opened on the first call.
     *
     * @return the connection, or empty if the session has no valid {@code webSocketUrl} capability
     *     or the connection could not be established
     */
    // Not annotated with @Override, so that it compiles after Selenium removes the method
    @SuppressWarnings("removal")
    public synchronized Optional<BiDi> maybeGetBiDi() {
        if (biDi == null) {
            biDi = createBiDi();
        }
        return biDi;
    }

    @Override
    public Handle getHandle() {
        return maybeGetBiDi()
                .map(BiDi::asHandle)
                .orElseThrow(() -> new BiDiException(
                        "Check if this driver supports BiDi and if the 'webSocketUrl: true' capability is set."));
    }

    @Override
    public void quit() {
        synchronized (this) {
            if (biDi != null) {
                biDi.ifPresent(BiDi::close);
                biDi = null;
            }
        }
        delegate.quit();
    }

    private Optional<BiDi> createBiDi() {
        var rawUrl = getCapabilities().getCapability("webSocketUrl");
        if (!(rawUrl instanceof String)) {
            return Optional.empty();
        }
        URI wsUri;
        try {
            wsUri = new URI(((String) rawUrl).trim());
        } catch (URISyntaxException e) {
            LOG.warn("BiDi was requested, but the webSocketUrl capability is not a valid URI: {}", rawUrl, e);
            return Optional.empty();
        }
        var scheme = wsUri.getScheme();
        if (scheme == null || !(scheme.equalsIgnoreCase("ws") || scheme.equalsIgnoreCase("wss"))) {
            LOG.warn("BiDi was requested, but the webSocketUrl capability is not a WebSocket address: {}", wsUri);
            return Optional.empty();
        }
        var config = toSeleniumConfig(wsUri);
        var client = webSocketClientFactory.createClient(config);
        try {
            return Optional.of(new BiDi(new Connection(client, wsUri.toString()), config.wsTimeout()));
        } catch (RuntimeException e) {
            client.close();
            LOG.warn("BiDi was requested, but the WebSocket connection could not be established", e);
            return Optional.empty();
        }
    }

    private ClientConfig toSeleniumConfig(URI wsUri) {
        var config = ClientConfig.defaultConfig().baseUri(wsUri);
        if (!(delegate.getCommandExecutor() instanceof AppiumCommandExecutor)) {
            return config;
        }
        var source = ((AppiumCommandExecutor) delegate.getCommandExecutor()).getClientConfig();
        config = config.connectionTimeout(source.connectionTimeout())
                .readTimeout(source.readTimeout())
                .wsTimeout(source.wsTimeout());
        if (source.proxy() != null) {
            config = config.proxy(source.proxy());
        }
        if (source.credentials() != null) {
            config = config.authenticateAs(source.credentials());
        }
        if (source.sslContext() != null) {
            config = config.sslContext(source.sslContext());
        }
        return config;
    }
}
