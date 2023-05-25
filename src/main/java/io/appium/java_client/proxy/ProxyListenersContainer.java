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

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

class ProxyListenersContainer {
    private static ProxyListenersContainer INSTANCE;
    private final Semaphore LISTENERS_GUARD = new Semaphore(1);
    // Previously WeakHashMap has been used because of O(1) lookup performance, although
    // we had to change it to a list, which has O(N). The reason for that is that
    // maps implicitly call `hashCode` API on instances, which might not always
    // work as expected for arbitrary proxies
    private final List<Pair<WeakReference<?>, Collection<MethodCallListener>>> LISTENERS = new LinkedList<>();

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private ProxyListenersContainer() {
    }

    public static synchronized ProxyListenersContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProxyListenersContainer();
        }
        return INSTANCE;
    }

    /**
     * Assign listeners for the particular proxied instance.
     *
     * @param proxyInstance The proxied instance.
     * @param listeners Collection of listeners.
     * @return The same given instance.
     */
    public <T> T setListeners(T proxyInstance, Collection<MethodCallListener> listeners) {
        try {
            LISTENERS_GUARD.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            int i = 0;
            boolean wasInstancePresent = false;
            while (i < LISTENERS.size()) {
                Pair<WeakReference<?>, Collection<MethodCallListener>> pair = LISTENERS.get(i);
                Object key = pair.getKey().get();
                if (key == null) {
                    // The instance has been garbage-collected
                    LISTENERS.remove(i);
                    continue;
                }

                if (key == proxyInstance) {
                    pair.getValue().clear();
                    pair.getValue().addAll(listeners);
                    wasInstancePresent = true;
                }
                i++;
            }
            if (!wasInstancePresent) {
                LISTENERS.add(new Pair<>(new WeakReference<>(proxyInstance), new HashSet<>(listeners)));
            }
        } finally {
            LISTENERS_GUARD.release();
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
            LISTENERS_GUARD.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            int i = 0;
            Collection<MethodCallListener> result = Collections.emptySet();
            while (i < LISTENERS.size()) {
                Pair<WeakReference<?>, Collection<MethodCallListener>> pair = LISTENERS.get(i);
                Object key = pair.getKey().get();
                if (key == null) {
                    // The instance has been garbage-collected
                    LISTENERS.remove(i);
                    continue;
                }

                if (key == proxyInstance) {
                    result = pair.getValue();
                }
                i++;
            }
            return result;
        } finally {
            LISTENERS_GUARD.release();
        }
    }

}
