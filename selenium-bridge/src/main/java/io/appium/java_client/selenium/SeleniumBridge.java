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

import io.appium.java_client.remote.AppiumRemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpClient;

import static java.util.Objects.requireNonNull;

/**
 * The entry point of the Selenium interoperability. Appium drivers are not Selenium {@link RemoteWebDriver}
 * instances. This class adapts them for the code that requires one, for example the Selenium
 * {@code Augmenter} or BiDi modules like {@code LogInspector}.
 */
public final class SeleniumBridge {
    private SeleniumBridge() {
    }

    /**
     * Adapts an Appium driver to a {@link RemoteWebDriver} that works in the same session. The WebSocket
     * connection of BiDi is made by the default Selenium HTTP client.
     *
     * @param driver the Appium driver, which must have a session
     * @return the Selenium driver, which also implements {@code HasBiDi}
     * @see BridgedRemoteWebDriver
     */
    public static RemoteWebDriver asRemoteWebDriver(AppiumRemoteWebDriver driver) {
        return asRemoteWebDriver(driver, HttpClient.Factory.createDefault());
    }

    /**
     * Adapts an Appium driver to a {@link RemoteWebDriver} that works in the same session.
     *
     * @param driver the Appium driver, which must have a session
     * @param webSocketClientFactory the factory of the HTTP clients that open the BiDi WebSocket connection
     * @return the Selenium driver, which also implements {@code HasBiDi}
     * @see BridgedRemoteWebDriver
     */
    public static RemoteWebDriver asRemoteWebDriver(AppiumRemoteWebDriver driver,
                                                    HttpClient.Factory webSocketClientFactory) {
        return new BridgedRemoteWebDriver(requireNonNull(driver, "Appium driver"),
                requireNonNull(webSocketClientFactory, "WebSocket client factory"));
    }
}
