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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class ScreenRecordingUploadOptions {
    private String remotePath;
    private String user;
    private String pass;
    private String method;
    private String fileFieldName;
    private Map<String, String> headers;
    private Map<String, Object> formFields;

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
        this.remotePath = requireNonNull(remotePath);
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
        this.user = requireNonNull(user);
        this.pass = requireNonNull(pass);
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
        this.method = requireNonNull(method).name();
        return this;
    }

    /**
     * Sets the form field name containing the binary payload in multipart/form-data
     * requests.
     *
     * @since Appium 1.18.0
     * @param fileFieldName The name of the form field containing the binary payload.
     *                      "file" by default.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withFileFieldName(String fileFieldName) {
        this.fileFieldName = requireNonNull(fileFieldName);
        return this;
    }

    /**
     * Sets additional form fields in multipart/form-data requests.
     *
     * @since Appium 1.18.0
     * @param formFields form fields mapping. If any entry has the same key as
     *                   `fileFieldName` then it is going to be ignored.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withFormFields(Map<String, Object> formFields) {
        this.formFields = requireNonNull(formFields);
        return this;
    }

    /**
     * Sets additional headers in multipart/form-data requests.
     *
     * @since Appium 1.18.0
     * @param headers headers mapping.
     * @return self instance for chaining.
     */
    public ScreenRecordingUploadOptions withHeaders(Map<String, String> headers) {
        this.headers = requireNonNull(headers);
        return this;
    }

    /**
     * Builds a map, which is ready to be passed to the subordinated
     * Appium API.
     *
     * @return arguments mapping.
     */
    public Map<String, Object> build() {
        var map = new HashMap<String, Object>();
        ofNullable(remotePath).map(x -> map.put("remotePath", x));
        ofNullable(user).map(x -> map.put("user", x));
        ofNullable(pass).map(x -> map.put("pass", x));
        ofNullable(method).map(x -> map.put("method", x));
        ofNullable(fileFieldName).map(x -> map.put("fileFieldName", x));
        ofNullable(formFields).map(x -> map.put("formFields", x));
        ofNullable(headers).map(x -> map.put("headers", x));
        return Collections.unmodifiableMap(map);
    }
}
