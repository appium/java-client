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

package io.appium.java_client.screenrecording;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public abstract class BaseScreenRecordingOptions<T extends BaseScreenRecordingOptions<T>> {
    private ScreenRecordingUploadOptions uploadOptions;

    /**
     * Upload options set for the recorded screen capture.
     *
     * @param uploadOptions see the documentation on {@link ScreenRecordingUploadOptions}
     *                      for more details.
     * @return self instance for chaining.
     */
    protected T withUploadOptions(ScreenRecordingUploadOptions uploadOptions) {
        this.uploadOptions = requireNonNull(uploadOptions);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Builds a map, which is ready to be passed to the subordinated
     * Appium API.
     *
     * @return arguments mapping.
     */
    public Map<String, Object> build() {
        final Map<String, Object> map = new HashMap<>();
        //noinspection unchecked
        ofNullable(uploadOptions).map(ScreenRecordingUploadOptions::build).ifPresent(map::putAll);
        return Collections.unmodifiableMap(map);
    }
}
