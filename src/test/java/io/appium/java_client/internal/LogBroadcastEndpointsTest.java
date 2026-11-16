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

class LogBroadcastEndpointsTest {

    @Test
    void mapsWildcardHostsToLoopbackFallbackAndPreservesExplicitHosts() throws MalformedURLException {
        assertEquals("127.0.0.1", LogBroadcastEndpoints.resolveHost(null, "127.0.0.1"));
        assertEquals("127.0.0.1", LogBroadcastEndpoints.resolveHost(new URL("http://0.0.0.0:4723"), "127.0.0.1"));
        assertEquals("localhost", LogBroadcastEndpoints.resolveHost(new URL("http://[::]:4723"), "localhost"));
        assertEquals(
                "127.0.0.1",
                LogBroadcastEndpoints.resolveHost(new URL("http://[0:0:0:0:0:0:0:0]:4723"), "127.0.0.1")
        );
        assertEquals("grid.example.com",
                LogBroadcastEndpoints.resolveHost(new URL("https://grid.example.com:4443"), "127.0.0.1"));
    }

    @Test
    void resolvesExplicitAndDefaultPortsBeforeFallback() throws MalformedURLException {
        assertEquals(4723, LogBroadcastEndpoints.resolvePort(null, 4723));
        assertEquals(443, LogBroadcastEndpoints.resolvePort(new URL("https://grid.example.com"), 4723));
        assertEquals(4444, LogBroadcastEndpoints.resolvePort(new URL("http://hub.example.com:4444"), 4723));
    }

    @Test
    void keepsWssForHttpsUnlessTargetingReplacedHttpEndpoint() throws MalformedURLException {
        URL httpsServer = new URL("https://direct.example.com:4443");
        URL replacedHttpHub = new URL("http://hub.example.com:4723");
        SessionId sessionId = new SessionId("s1");

        URI separateLogHost = LogBroadcastEndpoints.buildBroadcastUri(
                httpsServer, List.of(), "10.0.0.5", 443, sessionId, "logcat"
        );
        assertEquals("wss://10.0.0.5:443/ws/session/s1/appium/device/logcat", separateLogHost.toString());

        URI staleHubEndpoint = LogBroadcastEndpoints.buildBroadcastUri(
                httpsServer, List.of(replacedHttpHub), "hub.example.com", 4723, sessionId, "logcat"
        );
        assertEquals("ws://hub.example.com:4723/ws/session/s1/appium/device/logcat", staleHubEndpoint.toString());

        URI directTargetIpv6 = LogBroadcastEndpoints.buildBroadcastUri(
                httpsServer, List.of(replacedHttpHub), "2001:db8::1", 4443, sessionId, "syslog"
        );
        assertEquals("wss://[2001:db8::1]:4443/ws/session/s1/appium/device/syslog", directTargetIpv6.toString());
    }
}
