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

package io.appium.java_client;

import com.google.common.net.HttpHeaders;
import io.appium.java_client.internal.Config;
import org.openqa.selenium.remote.http.AddSeleniumUserAgent;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpHandler;

import javax.annotation.Nullable;

/**
 * Manage Appium Client configurations.
 */

public class AppiumUserAgentFilter implements Filter {

    public static final String VERSION_KEY = "appiumClient.version";

    /**
     * A custom User Agent name for Appium Java client.
     * e.g. appium/8.2.0 (selenium/4.5.0 (java mac))
     */
    public static final String USER_AGENT = String.format(
            "appium/%s (%s)",
            Config.main().getValue(VERSION_KEY, String.class),
            AddSeleniumUserAgent.USER_AGENT
    );

    /**
     * Returns true if the given User Agent includes "appium/", which
     * implies the User Agent already has Appium UA by this method.
     * @param userAgent the User Agent in the request headers.
     * @return whether the given User Agent includes Appium UA
     *         like by this filter.
     */
    public boolean containsAppiumName(@Nullable String userAgent) {
        return userAgent != null && userAgent.contains("appium/");
    }

    @Override
    public HttpHandler apply(HttpHandler next) {

        return req -> {
            if (containsAppiumName(req.getHeader(HttpHeaders.USER_AGENT))) {
                req.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            }

            return next.execute(req);
        };
    }
}