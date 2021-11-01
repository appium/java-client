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

package io.appium.java_client.gecko.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsAndroidStorageOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ANDROID_STORAGE_OPTION = "androidStorage";

    /**
     * See
     * https://firefox-source-docs.mozilla.org/testing/geckodriver
     * /Flags.html#code-android-storage-var-android-storage-var-code
     *
     * @param storage One of supported Android storage types.
     * @return self instance for chaining.
     */
    default T setAndroidStorage(String storage) {
        return amend(ANDROID_STORAGE_OPTION, storage);
    }

    /**
     * Get the currently set storage type.
     *
     * @return String representing the name of the device.
     */
    default Optional<String> getAndroidStorage() {
        return Optional.ofNullable((String) getCapability(ANDROID_STORAGE_OPTION));
    }
}
