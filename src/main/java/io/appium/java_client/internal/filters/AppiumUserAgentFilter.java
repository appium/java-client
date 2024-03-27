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

import com.google.common.net.HttpHeaders;
import io.appium.java_client.internal.Config;
import org.openqa.selenium.remote.http.AddSeleniumUserAgent;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Manage Appium Client configurations.
 */

public class AppiumUserAgentFilter implements Filter {

    public static final String VERSION_KEY = "appiumClient.version";

    private static final String USER_AGENT_PREFIX = "appium/";

    /**
     * A default User Agent name for Appium Java client.
     * e.g. appium/8.2.0 (selenium/4.5.0 (java mac))
     */
    public static final String USER_AGENT = buildUserAgentHeaderValue(AddSeleniumUserAgent.USER_AGENT);

    private static String buildUserAgentHeaderValue(@Nonnull String previousUA) {
        return String.format("%s%s (%s)",
                USER_AGENT_PREFIX, Config.main().getValue(VERSION_KEY, String.class), previousUA);
    }

    /**
     * Returns true if the given User Agent includes "appium/", which
     * implies the User Agent already has the Appium UA by this method.
     * The matching is case-insensitive.
     * @param userAgent the User Agent in the request headers.
     * @return whether the given User Agent includes Appium UA
     *         like by this filter.
     */
    private static boolean containsAppiumName(@Nullable String userAgent) {
        return userAgent != null && userAgent.toLowerCase().contains(USER_AGENT_PREFIX.toLowerCase());
    }

    /**
     * Returns the User Agent. If the given UA already has
     * {@link USER_AGENT_PREFIX}, it returns the UA.
     * IF the given UA does not have {@link USER_AGENT_PREFIX},
     * it returns UA with the Appium prefix.
     * @param userAgent the User Agent in the request headers.
     * @return the User Agent for the request
     */
    public static String buildUserAgent(@Nullable String userAgent) {
        if (userAgent == null) {
            return USER_AGENT;
        }

        if (containsAppiumName(userAgent)) {
            return userAgent;
        }

        return buildUserAgentHeaderValue(userAgent);
    }

    @Override
    public HttpHandler apply(HttpHandler next) {
        return req -> {
            req.setHeader(HttpHeaders.USER_AGENT, buildUserAgent(req.getHeader(HttpHeaders.USER_AGENT)));
            return next.execute(req);
        };
    }
}
