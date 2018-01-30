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

import org.openqa.selenium.remote.Response;

import javax.xml.bind.DatatypeConverter;

public interface InteractsWithFiles extends ExecutesMethod {

    /**
     * Pull a file from the simulator/device.
     *
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data.
     */
    default byte[] pullFile(String remotePath) {
        Response response = execute(PULL_FILE, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

    /**
     * Pull a folder from the simulator/device. Does not work on iOS Real
     * Devices, but works on simulators
     *
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data, representing a ZIP ARCHIVE
     *         of the contents of the requested folder.
     */
    default byte[] pullFolder(String remotePath) {
        Response response = execute(PULL_FOLDER, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

}
