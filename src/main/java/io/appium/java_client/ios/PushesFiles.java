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

package io.appium.java_client.ios;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.pushFileCommand;

public interface PushesFiles extends ExecutesMethod {

    /**
     * Saves base64 encoded data as a media file on the remote mobile device.
     * This method is only supported on Simulator running Xcode SDK 8.1+.
     *
     * @param remotePath Path to file to write data to on remote device
     *                   Only the filename part matters there, so the remote end
     *                   can figure out which type of media data it is and save
     *                   it into a proper folder on the target device. Check
     *                   'xcrun simctl addmedia' output to get more details on
     *                   supported media types
     * @param base64Data Base64 encoded byte array of media file data to write to remote device
     */
    default void pushFile(String remotePath, byte[] base64Data) {
        CommandExecutionHelper.execute(this, pushFileCommand(remotePath, base64Data));
    }

    /**
     * Saves base64 encoded data as a media file on the remote mobile device.
     * This method is only supported on Simulator running Xcode SDK 8.1+.
     *
     * @param remotePath See the documentation on {@link #pushFile(String, byte[])}
     * @param file Is an existing local file to be written to the remote device
     * @throws IOException when there are problems with a file or current file system
     */
    default void pushFile(String remotePath, File file) throws IOException {
        checkNotNull(file, "A reference to file should not be NULL");
        if (!file.exists()) {
            throw new IOException(String.format("The given file %s doesn't exist", file.getAbsolutePath()));
        }
        pushFile(remotePath, Base64.encodeBase64(FileUtils.readFileToByteArray(file)));
    }

}
