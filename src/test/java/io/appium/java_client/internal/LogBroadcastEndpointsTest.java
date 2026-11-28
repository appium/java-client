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

import io.appium.java_client.remote.SessionId;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("removal")
class LogBroadcastEndpointsTest {

    @Test
    void derivesWebSocketSchemeFromServerUrlAndFormatsIpv6Hosts() throws MalformedURLException {
        SessionId sessionId = new SessionId("s1");

        URI nullServer = LogBroadcastEndpoints.buildBroadcastUri(
                null, "127.0.0.1", 4723, sessionId, "logcat"
        );
        assertEquals("ws://127.0.0.1:4723/ws/session/s1/appium/device/logcat", nullServer.toString());

        URI httpServer = LogBroadcastEndpoints.buildBroadcastUri(
                new URL("http://hub.example.com:4723"), "hub.example.com", 4723, sessionId, "logcat"
        );
        assertEquals("ws://hub.example.com:4723/ws/session/s1/appium/device/logcat", httpServer.toString());

        URI httpsServer = LogBroadcastEndpoints.buildBroadcastUri(
                new URL("https://direct.example.com:4443"), "2001:db8::1", 4443, sessionId, "syslog"
        );
        assertEquals("wss://[2001:db8::1]:4443/ws/session/s1/appium/device/syslog", httpsServer.toString());

        URI uppercaseHttpsServer = LogBroadcastEndpoints.buildBroadcastUri(
                new URL("HTTPS://direct.example.com:4443"), "[2001:db8::1]", 4443, sessionId, "logcat"
        );
        assertEquals("wss://[2001:db8::1]:4443/ws/session/s1/appium/device/logcat", uppercaseHttpsServer.toString());

        URI omittedPort = LogBroadcastEndpoints.buildBroadcastUri(
                new URL("https://direct.example.com/wd/hub"), "direct.example.com", -1, sessionId, "logcat"
        );
        assertEquals("wss://direct.example.com/ws/session/s1/appium/device/logcat", omittedPort.toString());
    }

    @Test
    void rejectsBlankOrMalformedHosts() {
        SessionId sessionId = new SessionId("s1");
        for (String invalidHost : List.of(
                "", "   ", "evil.com:9000", "user@host", "host/path", "host?q", "host#frag")) {
            assertThrows(IllegalArgumentException.class,
                    () -> LogBroadcastEndpoints.buildBroadcastUri(null, invalidHost, 4723, sessionId, "logcat"));
            assertThrows(IllegalArgumentException.class,
                    () -> LogBroadcastEndpoints.buildBroadcastUri(null, invalidHost, -1, sessionId, "logcat"));
        }
    }
}
