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

package io.appium.java_client.proxy;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

class ProxyListenersContainer {
    private static ProxyListenersContainer INSTANCE;

    public static synchronized ProxyListenersContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProxyListenersContainer();
        }
        return INSTANCE;
    }

    private final Semaphore listenersGuard = new Semaphore(1);
    // Previously WeakHashMap has been used because of O(1) lookup performance, although
    // we had to change it to a list, which has O(N). The reason for that is that
    // maps implicitly call `hashCode` API on instances, which might not always
    // work as expected for arbitrary proxies
    // As of (this version?) we are now using a LinkedHashMap as a caching mechanism. This can be overridden by a System
    // Property value "appium.override.listener.limit": default 5000.
    private final LinkedHashMap<Object, HashSet<MethodCallListener>>
        listeners =
        new LinkedHashMap<Object, HashSet<MethodCallListener>>() {
            protected boolean removeEldestEntry(
                Map.Entry<Object, HashSet<MethodCallListener>> eldest) {
                return size() > Integer.parseInt(
                    System.getProperty("appium.override.listener.limit", "5000"));
            }
        };

    private ProxyListenersContainer() {
    }

    /**
     * Assign listeners for the particular proxied instance.
     *
     * @param proxyInstance The proxied instance.
     * @param listeners     Collection of listeners.
     * @return The same given instance.
     */
    public <T> T setListeners(T proxyInstance, Collection<MethodCallListener> listeners) {
        try {
            listenersGuard.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            this.listeners.put(proxyInstance.getClass(), new HashSet<>(listeners));
        } finally {
            listenersGuard.release();
        }
        return proxyInstance;
    }

    /**
     * Fetches listeners for the particular proxied instance.
     *
     * @param proxyInstance The proxied instance.
     */
    public Collection<MethodCallListener> getListeners(Object proxyInstance) {
        try {
            listenersGuard.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return listeners.get(proxyInstance.getClass());
        } finally {
            listenersGuard.release();
        }
    }

}
