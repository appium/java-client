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

package io.appium.java_client.internal.filters;

import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpHandler;
import org.openqa.selenium.remote.http.HttpMethod;

import static java.util.Locale.ROOT;
import static java.util.UUID.randomUUID;

public class AppiumIdempotencyFilter implements Filter {
    // https://github.com/appium/appium-base-driver/pull/400
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    @Override
    public HttpHandler apply(HttpHandler next) {
        return req -> {
            if (req.getMethod() == HttpMethod.POST && req.getUri().endsWith("/session")) {
                req.setHeader(IDEMPOTENCY_KEY_HEADER, randomUUID().toString().toLowerCase(ROOT));
            }
            return next.execute(req);
        };
    }
}
