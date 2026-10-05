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
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

/**
 * A Selenium {@link RemoteWebDriver} that works in the session of an Appium driver. Commands go through the
 * command executor of the Appium driver. If the session was created with the {@code webSocketUrl} capability,
 * the BiDi connection is made by Selenium itself when the bridge is created, so Selenium BiDi modules and the
 * {@code Augmenter} can be used.
 *
 * <p>The BiDi connection stays open until {@link #closeBiDi()} or {@link #quit()}. Quitting the bridge quits
 * the session of the Appium driver. Elements are not interchangeable between the drivers.
 */
public class BridgedRemoteWebDriver extends RemoteWebDriver implements WrapsDriver {
    private AppiumRemoteWebDriver delegate;
    private WebSocketClients webSocketClients;
    private volatile boolean biDiClosed;

    /**
     * Used by the Selenium {@code Augmenter}, which subclasses the driver and copies its fields.
     */
    protected BridgedRemoteWebDriver() {
        super();
    }

    BridgedRemoteWebDriver(AppiumRemoteWebDriver delegate, HttpClient.Factory webSocketClientFactory) {
        this(delegate, new SeleniumCommandExecutor(delegate), new WebSocketClients(webSocketClientFactory));
    }

    private BridgedRemoteWebDriver(AppiumRemoteWebDriver delegate, SeleniumCommandExecutor executor,
                                   WebSocketClients webSocketClients) {
        super(executor, new ImmutableCapabilities(), webSocketClients.asFactory(), toSeleniumConfig(delegate));
        this.delegate = delegate;
        this.webSocketClients = webSocketClients;
        executor.enableQuit();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return delegate;
    }

    /**
     * Closes the BiDi connection, while the session stays open. The BiDi modules that were created with
     * this driver cannot be used afterwards.
     */
    public void closeBiDi() {
        biDiClosed = true;
        webSocketClients.closeAll();
    }

    boolean isBiDiClosed() {
        return biDiClosed;
    }

    /**
     * Quits the session of the Appium driver and closes the BiDi connection.
     */
    @Override
    public void quit() {
        try {
            super.quit();
        } finally {
            closeBiDi();
        }
    }

    private static ClientConfig toSeleniumConfig(AppiumRemoteWebDriver delegate) {
        var config = ClientConfig.defaultConfig();
        if (!(delegate.getCommandExecutor() instanceof AppiumCommandExecutor)) {
            return config;
        }
        var source = ((AppiumCommandExecutor) delegate.getCommandExecutor()).getAppiumClientConfig();
        config = config.baseUri(source.baseUri())
                .connectionTimeout(source.connectionTimeout())
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
