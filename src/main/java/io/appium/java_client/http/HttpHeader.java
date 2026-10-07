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

/**
 * Well-known HTTP header names.
 */
public enum HttpHeader {
    /**
     * The {@code cache-control} header.
     */
    CacheControl("cache-control"),
    /**
     * The {@code content-length} header.
     */
    ContentLength("content-length"),
    /**
     * The {@code content-type} header.
     */
    ContentType("content-type"),
    /**
     * The {@code expires} header.
     */
    Expires("expires"),
    /**
     * The {@code host} header.
     */
    Host("host"),
    /**
     * The {@code user-agent} header.
     */
    UserAgent("user-agent"),
    /**
     * The {@code x-forwarded-for} header.
     */
    XForwardedFor("x-forwarded-for");

    private final String name;

    HttpHeader(String name) {
        this.name = name;
    }

    /**
     * Returns the lower-case header name.
     *
     * @return the header name
     */
    public String getName() {
        return name;
    }
}
