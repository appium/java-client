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

public abstract class BaseStopScreenRecordingOptions<T extends BaseStopScreenRecordingOptions<T>>
        extends BaseScreenRecordingOptions<BaseStopScreenRecordingOptions<T>> {

    /**
     * The remotePath upload option is the path to the remote location,
     * where the resulting video should be uploaded.
     * The following protocols are supported: http/https (multipart), ftp.
     * Missing value (the default setting) means the content of resulting
     * file should be encoded as Base64 and passed as the endpoint response value, but
     * an exception will be thrown if the generated media file is too big to
     * fit into the available process memory.
     *
     * @param uploadOptions see the documentation on {@link ScreenRecordingUploadOptions}
     *                      for more details.
     * @return self instance for chaining.
     */
    @Override
    public T withUploadOptions(ScreenRecordingUploadOptions uploadOptions) {
        //noinspection unchecked
        return (T) super.withUploadOptions(uploadOptions);
    }
}
