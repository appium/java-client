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

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ScreenRecordingUploadOptions {
    private String remotePath;
    private String user;
    private String pass;
    private String method;

    public static ScreenRecordingUploadOptions uploadOptions() {
        return new ScreenRecordingUploadOptions();
    }

    /**
     * The path to the remote location, where the resulting video should be uploaded.
     *
     * @param remotePath The path to a writable remote location.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withRemotePath(String remotePath) {
        this.remotePath = checkNotNull(remotePath);
        return this;
    }

    /**
     * Sets the credentials for remote ftp/http authentication (if needed).
     * This option only has an effect if remotePath is provided.
     *
     * @param user The name of the user for the remote authentication.
     * @param pass The password for the remote authentication.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withAuthCredentials(String user, String pass) {
        this.user = checkNotNull(user);
        this.pass = checkNotNull(pass);
        return this;
    }

    public enum RequestMethod {
        POST, PUT
    }

    /**
     * Sets the method name for http(s) upload. PUT is used by default.
     * This option only has an effect if remotePath is provided.
     *
     * @param method The HTTP method name.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withHttpMethod(RequestMethod method) {
        this.method = checkNotNull(method).name();
        return this;
    }

    /**
     * Builds a map, which is ready to be passed to the subordinated
     * Appium API.
     *
     * @return arguments mapping.
     */
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(remotePath).map(x -> builder.put("remotePath", x));
        ofNullable(user).map(x -> builder.put("user", x));
        ofNullable(pass).map(x -> builder.put("pass", x));
        ofNullable(method).map(x -> builder.put("method", x));
        return builder.build();
    }
}
