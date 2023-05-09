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
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.UnsupportedCommandException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.appium.java_client.MobileCommand.pushFileCommand;

public interface PushesFiles extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Saves base64-encoded data as a file on the remote system.
     *
     * @param remotePath Path to file to write data to on remote device.
     *                   Check the documentation on `mobile: pushFile`
     *                   extension for more details on possible values
     *                   for different platforms.
     * @param base64Data Base64 encoded byte array of media file data to write to remote device
     */
    default void pushFile(String remotePath, byte[] base64Data) {
        final String extName = "mobile: pushFile";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "remotePath", remotePath,
                    "payload", new String(base64Data, StandardCharsets.UTF_8)
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), pushFileCommand(remotePath, base64Data));
        }
    }

    /**
     * Sends the file to the remote device.
     *
     * @param remotePath See the documentation on {@link #pushFile(String, byte[])}
     * @param file Is an existing local file to be written to the remote device
     * @throws IOException when there are problems with a file on current file system
     */
    default void pushFile(String remotePath, File file) throws IOException {
        pushFile(remotePath, Base64.getEncoder().encode(FileUtils.readFileToByteArray(file)));
    }

}
