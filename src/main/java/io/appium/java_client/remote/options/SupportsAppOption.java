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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.net.URL;
import java.util.Optional;

public interface SupportsAppOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_OPTION = "app";

    /**
     * Set the absolute local path for the location of the App.
     * The app must be located on the same machine where Appium
     * server is running.
     *
     * @param path is a String representing the location of the App
     * @return self instance for chaining.
     */
    default T setApp(String path) {
        return amend(APP_OPTION, path);
    }

    /**
     * Set the remote http URL for the location of the App.
     *
     * @param url is the URL representing the location of the App
     * @return self instance for chaining.
     */
    default T setApp(URL url) {
        return setApp(url.toString());
    }

    /**
     * Get the app location.
     *
     * @return String representing app location
     */
    default Optional<String> getApp() {
        return Optional.ofNullable((String) getCapability(APP_OPTION));
    }
}
