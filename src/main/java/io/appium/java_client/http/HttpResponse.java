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

package io.appium.java_client.http;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * An HTTP response.
 */
public class HttpResponse extends HttpMessage<HttpResponse> {
    private int status = HTTP_OK;

    /**
     * Checks whether the status is in the 2xx range.
     *
     * @return true if the response is successful
     */
    public boolean isSuccessful() {
        return status >= HTTP_OK && status < 300;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status the status code
     * @return this response
     */
    public HttpResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        String content = super.toString();
        return content.isEmpty() ? String.valueOf(status) : String.format("%s: %s", status, content);
    }
}
