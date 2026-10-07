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

import java.util.Locale;

/**
 * Supported HTTP methods.
 */
public enum HttpMethod {
    /** The DELETE method. */
    DELETE,
    /** The GET method. */
    GET,
    /** The POST method. */
    POST,
    /** The PUT method. */
    PUT,
    /** The OPTIONS method. */
    OPTIONS,
    /** The PATCH method. */
    PATCH,
    /** The HEAD method. */
    HEAD,
    /** The CONNECT method. */
    CONNECT,
    /** The TRACE method. */
    TRACE;

    /**
     * Resolves a method by its case-insensitive name.
     *
     * @param method the method name
     * @return the matching constant
     * @throws IllegalArgumentException if there is no such method
     */
    public static HttpMethod getHttpMethod(String method) {
        try {
            return HttpMethod.valueOf(method.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No enum constant for method: " + method);
        }
    }
}
