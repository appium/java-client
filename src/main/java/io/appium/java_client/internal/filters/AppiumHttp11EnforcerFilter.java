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

import java.util.List;

public class AppiumHttp11EnforcerFilter implements Filter {
    private static final List<String> HTTP2_UPGRADE_HEADERS = List.of(
            "Upgrade", "HTTP2-Settings", "Connection"
    );

    @Override
    public HttpHandler apply(HttpHandler next) {
        return req -> {
            var upgrade = req.getHeader("Upgrade");
            if (upgrade != null && upgrade.trim().equalsIgnoreCase("h2c")) {
                HTTP2_UPGRADE_HEADERS.forEach(x -> req.removeHeader(x));
            }
            return next.execute(req);
        };
    }
}
