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

package io.appium.java_client;

import static io.appium.java_client.MobileCommand.PULL_FILE;
import static io.appium.java_client.MobileCommand.PULL_FOLDER;

import com.google.common.collect.ImmutableMap;

import org.openqa.seleniumone.remote.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface InteractsWithFiles extends ExecutesMethod {

    /**
     * Pull a file from the simulator/device.
     * On iOS the server should have ifuse
     * libraries installed and configured properly for this feature to work
     * on real devices.
     * On Android the application under test should be
     * built with debuggable flag enabled in order to get access to its container
     * on the internal file system.
     *
     * @see <a href="https://github.com/libimobiledevice/ifuse">iFuse GitHub page6</a>
     * @see <a href="https://github.com/osxfuse/osxfuse/wiki/FAQ">osxFuse FAQ</a>
     * @see <a href="https://developer.android.com/studio/debug/">'Debug Your App' developer article</a>
     *
     * @param remotePath If the path starts with <em>@applicationId/</em>/ prefix, then the file
     *                   will be pulled from the root of the corresponding application container.
     *                   Otherwise the root folder is considered as / on Android and
     *                   on iOS it is a media folder root (real devices only).
     * @return A byte array of Base64 encoded data.
     */
    default byte[] pullFile(String remotePath) {
        Response response = execute(PULL_FILE, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Pull a folder content from the simulator/device.
     * On iOS the server should have ifuse
     * libraries installed and configured properly for this feature to work
     * on real devices.
     * On Android the application under test should be
     * built with debuggable flag enabled in order to get access to its container
     * on the internal file system.
     *
     * @see <a href="https://github.com/libimobiledevice/ifuse">iFuse GitHub page6</a>
     * @see <a href="https://github.com/osxfuse/osxfuse/wiki/FAQ">osxFuse FAQ</a>
     * @see <a href="https://developer.android.com/studio/debug/">'Debug Your App' developer article</a>
     *
     * @param remotePath If the path starts with <em>@applicationId/</em> prefix, then the folder
     *                   will be pulled from the root of the corresponding application container.
     *                   Otherwise the root folder is considered as / on Android and
     *                   on iOS it is a media folder root (real devices only).
     * @return A byte array of Base64 encoded zip archive data.
     */
    default byte[] pullFolder(String remotePath) {
        Response response = execute(PULL_FOLDER, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
    }

}
