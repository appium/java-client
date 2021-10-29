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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsSafariSocketChunkSizeOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_SOCKET_CHUNK_SIZE_OPTION = "safariSocketChunkSize";

    /**
     * The size, in bytes, of the data to be sent to the Web Inspector on
     * iOS 11+ real devices. Some devices hang when sending large amounts of
     * data to the Web Inspector, and breaking them into smaller parts can be
     * helpful in those cases. Defaults to 16384 (also the maximum possible).
     *
     * @param size Socket chunk size in range 1..16384.
     * @return self instance for chaining.
     */
    default T setSafariSocketChunkSize(int size) {
        return amend(SAFARI_SOCKET_CHUNK_SIZE_OPTION, size);
    }

    /**
     * Get the size of a single remote debugger socket chunk.
     *
     * @return Chunk size in bytes.
     */
    default Optional<Integer> getSafariSocketChunkSize() {
        return Optional.ofNullable(toInteger(getCapability(SAFARI_SOCKET_CHUNK_SIZE_OPTION)));
    }
}
