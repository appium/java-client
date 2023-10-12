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
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

class ProxyListenersContainer {
    private static ProxyListenersContainer INSTANCE;

    public static synchronized ProxyListenersContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProxyListenersContainer();
        }
        return INSTANCE;
    }

    private final Map<Object, Collection<MethodCallListener>> listenersMap = new WeakHashMap<>();

    private ProxyListenersContainer() {
    }

    /**
     * Assign listeners for the particular proxied instance.
     *
     * @param proxyInstance The proxied instance.
     * @param listeners Collection of listeners.
     * @return The same given instance.
     */
    public <T> T setListeners(T proxyInstance, Collection<MethodCallListener> listeners) {
        synchronized (listenersMap) {
            listenersMap.put(proxyInstance, listeners);
        }
        return proxyInstance;
    }

    /**
     * Fetches listeners for the particular proxied instance.
     *
     * @param proxyInstance The proxied instance.
     */
    public Collection<MethodCallListener> getListeners(Object proxyInstance) {
        synchronized (listenersMap) {
            return listenersMap.getOrDefault(proxyInstance, Collections.emptySet());
        }
    }

}
