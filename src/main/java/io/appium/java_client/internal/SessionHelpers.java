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

package io.appium.java_client.internal;

import lombok.Data;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionHelpers {
    private static final Pattern SESSION = Pattern.compile("/session/([^/]+)");

    private SessionHelpers() {
    }

    @Data public static class SessionAddress {
        private final URL serverUrl;
        private final String id;
    }

    /**
     * Parses the address of a running remote session.
     *
     * @param address The address string containing /session/id suffix.
     * @return Parsed address object.
     * @throws InvalidArgumentException If no session identifier could be parsed.
     */
    public static SessionAddress parseSessionAddress(URL address) {
        String addressString = address.toString();
        Matcher matcher = SESSION.matcher(addressString);
        if (!matcher.find()) {
            throw new InvalidArgumentException(
                    String.format("The server URL '%s' must include /session/<id> suffix", addressString)
            );
        }
        try {
            return new SessionAddress(
                    new URL(addressString.replace(matcher.group(), "")), matcher.group(1)
            );
        } catch (MalformedURLException e) {
            throw new WebDriverException(e);
        }
    }
}
