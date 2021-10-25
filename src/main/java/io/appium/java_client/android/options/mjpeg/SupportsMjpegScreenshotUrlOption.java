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

package io.appium.java_client.android.options.mjpeg;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public interface SupportsMjpegScreenshotUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MJPEG_SCREENSHOT_URL_OPTION = "mjpegScreenshotUrl";

    /**
     * The URL of a service that provides realtime device screenshots in MJPEG format.
     * If provided then the actual command to retrieve a screenshot will be
     * requesting pictures from this service rather than directly from the server.
     *
     * @param url URL value.
     * @return self instance for chaining.
     */
    default T setMjpegScreenshotUrl(URL url) {
        return amend(MJPEG_SCREENSHOT_URL_OPTION, url.toString());
    }

    /**
     * The URL of a service that provides realtime device screenshots in MJPEG format.
     * If provided then the actual command to retrieve a screenshot will be
     * requesting pictures from this service rather than directly from the server.
     *
     * @param url URL value.
     * @return self instance for chaining.
     */
    default T setMjpegScreenshotUrl(String url) {
        return amend(MJPEG_SCREENSHOT_URL_OPTION, url);
    }

    /**
     * Get URL of a service that provides realtime device screenshots in MJPEG format.
     *
     * @return URL value.
     */
    default Optional<URL> getMjpegScreenshotUrl() {
        return Optional.ofNullable(getCapability(MJPEG_SCREENSHOT_URL_OPTION))
                .map((v) -> {
                    try {
                        return new URL(String.valueOf(v));
                    } catch (MalformedURLException e) {
                        throw new IllegalArgumentException(e);
                    }
                });
    }
}
