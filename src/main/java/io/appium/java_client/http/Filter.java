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

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Wraps a handler to modify requests and/or responses.
 */
@FunctionalInterface
public interface Filter extends Function<HttpHandler, HttpHandler> {
    /**
     * Chains filters; this filter runs first.
     *
     * @param next the filter to run after this one
     * @return the combined filter
     */
    default Filter andThen(Filter next) {
        requireNonNull(next, "Next filter");
        return req -> apply(next.apply(req));
    }

    /**
     * Applies this filter to the final handler.
     *
     * @param end the handler to run after this filter
     * @return the resulting handler
     */
    default HttpHandler andFinally(HttpHandler end) {
        requireNonNull(end, "HTTP handler");
        return request -> Filter.this.apply(end).execute(request);
    }
}
