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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.net.URL;
import java.util.Optional;

/**
 * Provides getters and setters for the {@code mjpegScreenshotUrl} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsMjpegScreenshotUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code mjpegScreenshotUrl} capability.
     */
    String MJPEG_SCREENSHOT_URL_OPTION = "mjpegScreenshotUrl";

    /**
     * Sets the URL of a service that provides realtime device screenshots in MJPEG format.
     * Appium does not manage port forwarding for this URL.
     *
     * @param url The MJPEG screenshot service URL.
     * @return self instance for chaining.
     */
    default T setMjpegScreenshotUrl(URL url) {
        return amend(MJPEG_SCREENSHOT_URL_OPTION, url.toString());
    }

    /**
     * Sets the URL of a service that provides realtime device screenshots in MJPEG format.
     * Appium does not manage port forwarding for this URL.
     *
     * @param url The MJPEG screenshot service URL.
     * @return self instance for chaining.
     */
    default T setMjpegScreenshotUrl(String url) {
        return amend(MJPEG_SCREENSHOT_URL_OPTION, url);
    }

    /**
     * Get the MJPEG screenshot service URL.
     *
     * @return The MJPEG screenshot service URL.
     */
    default Optional<URL> getMjpegScreenshotUrl() {
        return Optional.ofNullable(getCapability(MJPEG_SCREENSHOT_URL_OPTION))
                .map(CapabilityHelpers::toUrl);
    }
}
