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
 * the BiDi connection is made by Selenium itself, so Selenium BiDi modules and the {@code Augmenter} can be used.
 * Quitting it quits the session of the Appium driver.
 */
public class BridgedRemoteWebDriver extends RemoteWebDriver implements WrapsDriver {
    private final AppiumRemoteWebDriver delegate;

    BridgedRemoteWebDriver(AppiumRemoteWebDriver delegate, HttpClient.Factory webSocketClientFactory) {
        super(new SeleniumCommandExecutor(delegate), new ImmutableCapabilities(), webSocketClientFactory,
                toSeleniumConfig(delegate));
        this.delegate = delegate;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return delegate;
    }

    private static ClientConfig toSeleniumConfig(AppiumRemoteWebDriver delegate) {
        var config = ClientConfig.defaultConfig();
        if (!(delegate.getCommandExecutor() instanceof AppiumCommandExecutor)) {
            return config;
        }
        var source = ((AppiumCommandExecutor) delegate.getCommandExecutor()).getClientConfig();
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
