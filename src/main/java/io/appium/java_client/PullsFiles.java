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

import com.google.common.collect.ImmutableMap;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface PullsFiles extends ExecutesMethod {

    /**
     * Pull a file from the remote system.
     * On Android the application under test should be
     * built with debuggable flag enabled in order to get access to its container
     * on the internal file system.
     *
     * @param remotePath Path to file to read data from the remote device.
     *                   Check the documentation on `mobile: pullFile`
     *                   extension for more details on possible values
     *                   for different platforms.
     * @return A byte array of Base64 encoded data.
     */
    default byte[] pullFile(String remotePath) {
        String base64String = CommandExecutionHelper.executeScript(this, "mobile: pullFile", ImmutableMap.of(
                "remotePath", remotePath
        ));
        return Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Pull a folder content from the remote system.
     * On Android the application under test should be
     * built with debuggable flag enabled in order to get access to its container
     * on the internal file system.
     *
     * @param remotePath Path to a folder to read data from the remote device.
     *                   Check the documentation on `mobile: pullFolder`
     *                   extension for more details on possible values
     *                   for different platforms.
     * @return A byte array of Base64 encoded zip archive data.
     */
    default byte[] pullFolder(String remotePath) {
        String base64String = CommandExecutionHelper.executeScript(this, "mobile: pullFolder", ImmutableMap.of(
                "remotePath", remotePath
        ));
        return Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
    }

}
