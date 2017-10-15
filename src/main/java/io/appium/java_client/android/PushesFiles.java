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

package io.appium.java_client.android;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.pushFileCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.InteractsWithFiles;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public interface PushesFiles extends InteractsWithFiles, ExecutesMethod {

    /**
     * Saves base64 encoded data as a file on the remote mobile device.
     *
     * @param remotePath Path to file to write data to on remote device
     * @param base64Data Base64 encoded byte array of data to write to remote device
     */
    default void pushFile(String remotePath, byte[] base64Data) {
        CommandExecutionHelper.execute(this, pushFileCommand(remotePath, base64Data));
    }

    /**
     * Saves given file as a file on the remote mobile device.
     *
     * @param remotePath Path to file to write data to on remote device
     * @param file is a file to write to remote device
     * @throws IOException when there are problems with a file or current file system
     */
    default void pushFile(String remotePath, File file) throws IOException {
        checkNotNull(file, "A reference to file should not be NULL");
        if (!file.exists()) {
            throw new IOException("The given file "
                + file.getAbsolutePath() + " doesn't exist");
        }
        pushFile(remotePath,
            Base64.encodeBase64(FileUtils.readFileToByteArray(file)));
    }

}
