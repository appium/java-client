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
 * Validates URLs supplied for {@code overrideServerUrl} so that session traffic cannot be
 * silently redirected to loopback, link-local (including cloud metadata), wildcard, or multicast
 * destinations.
 */
public final class DirectConnectUrlSafety {

    private DirectConnectUrlSafety() {
    }

    /**
     * Ensures the given URL's host does not resolve to an address that is unsafe for automatic
     * redirection after session creation.
     *
     * @param url candidate server URL
     * @throws SessionNotCreatedException if the host is missing, cannot be resolved, or resolves
     *         to a disallowed address
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
