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

public interface InteractsWithFiles {

    /**
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data.
     */
    byte[] pullFile(String remotePath);

    /**
     * Pull a folder from the simulator/device. Does not work on iOS Real
     * Devices, but works on simulators
     *
     * @param remotePath On Android and iOS, this is either the path to the file
     *                   (relative to the root of the app's file system). On iOS only,
     *                   if path starts with /AppName.app, which will be replaced with
     *                   the application's .app directory
     * @return A byte array of Base64 encoded data, representing a ZIP ARCHIVE
     * of the contents of the requested folder.
     */
    byte[] pullFolder(String remotePath);

}
