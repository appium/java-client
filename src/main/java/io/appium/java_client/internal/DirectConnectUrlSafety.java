/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

import org.openqa.selenium.SessionNotCreatedException;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Validates URLs used with {@code overrideServerUrl} (for example after a {@code directConnect}
 * response). Refuses the override when any resolved address is loopback, link-local (including
 * typical cloud metadata IPv4 link-local space), unspecified, or multicast.
 *
 * <p>This is not a full &quot;public internet only&quot; policy: RFC 1918 private space, shared
 * address space ({@code 100.64.0.0/10}), IPv6 unique-local ({@code fc00::/7}), and other addresses
 * outside the checks above are still accepted. Stricter control belongs at the application or
 * network layer (allowlists, egress rules, etc.).
 */
public final class DirectConnectUrlSafety {

    private DirectConnectUrlSafety() {
    }

    /**
     * Ensures the given URL's host does not resolve to loopback, link-local, unspecified, or
     * multicast addresses (see class documentation for what is still allowed).
     *
     * @param url candidate server URL
     * @throws SessionNotCreatedException if the host is missing, cannot be resolved, or resolves
     *         to any address in the disallowed categories
     */
    public static void requireSafeOverrideTarget(URL url) throws SessionNotCreatedException {
        String host = url.getHost();
        if (host == null || host.isEmpty()) {
            throw new SessionNotCreatedException(
                    "Refusing to override the server URL: the URL must include a non-empty host.");
        }

        final InetAddress[] addresses;
        try {
            addresses = InetAddress.getAllByName(host);
        } catch (UnknownHostException e) {
            throw new SessionNotCreatedException(
                    "Refusing to override the server URL: cannot resolve host '" + host + "'.", e);
        }

        for (InetAddress address : addresses) {
            if (isDisallowed(address)) {
                throw new SessionNotCreatedException(String.format(
                        "Refusing to override the server URL: host '%s' resolves to %s, which is not "
                                + "allowed (loopback, link-local, unspecified, or multicast address).",
                        host,
                        address.getHostAddress()));
            }
        }
    }

    private static boolean isDisallowed(InetAddress address) {
        return address.isLoopbackAddress()
                || address.isLinkLocalAddress()
                || address.isAnyLocalAddress()
                || address.isMulticastAddress();
    }
}
