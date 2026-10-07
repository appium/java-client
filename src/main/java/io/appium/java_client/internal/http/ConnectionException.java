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

package io.appium.java_client.internal.http;

import java.io.UncheckedIOException;
import java.net.ConnectException;

/**
 * Thrown if a connection to the server cannot be established.
 */
public class ConnectionException extends UncheckedIOException {
    /** The URI the connection was attempted to. */
    private final String uri;

    /**
     * Creates an exception.
     *
     * @param message the detail message
     * @param uri     the URI the connection was attempted to
     * @param cause   the cause
     */
    public ConnectionException(String message, String uri, ConnectException cause) {
        super(message, cause);
        this.uri = uri;
    }

    /**
     * Returns the URI the connection was attempted to.
     *
     * @return the URI
     */
    public String uri() {
        return uri;
    }
}
