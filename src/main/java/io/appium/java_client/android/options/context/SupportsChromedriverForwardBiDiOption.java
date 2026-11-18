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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code chromedriverForwardBiDi} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsChromedriverForwardBiDiOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code chromedriverForwardBiDi} capability.
     */
    String CHROMEDRIVER_FORWARD_BIDI_OPTION = "chromedriverForwardBiDi";

    /**
     * Enables forwarding the ChromeDriver BiDi WebSocket to the driver WebSocket
     * when switching to a webview context. Requires BiDi to be enabled
     * ({@code webSocketUrl} set to {@code true}, e.g. via {@code enableBiDi()})
     * to take effect. Supported since {@code appium-android-driver} 6.1.0.
     *
     * @return self instance for chaining.
     */
    default T chromedriverForwardBiDi() {
        return amend(CHROMEDRIVER_FORWARD_BIDI_OPTION, true);
    }

    /**
     * Whether to forward the ChromeDriver BiDi WebSocket to the driver WebSocket
     * when switching to a webview context. Requires BiDi to be enabled
     * ({@code webSocketUrl} set to {@code true}, e.g. via {@code enableBiDi()})
     * to take effect; calling only this setter without enabling BiDi produces no
     * forwarding and no error. {@code false} by default. Supported since
     * {@code appium-android-driver} 6.1.0.
     *
     * @param value True to enable ChromeDriver BiDi forwarding.
     * @return self instance for chaining.
     */
    default T setChromedriverForwardBiDi(boolean value) {
        return amend(CHROMEDRIVER_FORWARD_BIDI_OPTION, value);
    }

    /**
     * Get whether ChromeDriver BiDi WebSocket forwarding is enabled.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesChromedriverForwardBiDi() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CHROMEDRIVER_FORWARD_BIDI_OPTION)));
    }
}
