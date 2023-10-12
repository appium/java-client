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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ProxyListenersContainer {
    private static ProxyListenersContainer INSTANCE;

    public static synchronized ProxyListenersContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProxyListenersContainer();
        }
        return INSTANCE;
    }

    private final Semaphore listenersGuard = new Semaphore(1);
    private final List<Pair<WeakReference<?>, Collection<MethodCallListener>>> listenerPairs = new LinkedList<>();

    @Getter
    @AllArgsConstructor
    private static class Pair<K, V> {
        private final K key;
        @Setter
        private V value;
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
            listenersGuard.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            int i = 0;
            boolean wasInstancePresent = false;
            while (i < listenerPairs.size()) {
                var pair = listenerPairs.get(i);
                Object key = pair.getKey().get();
                if (key == null) {
                    // The instance has been garbage-collected
                    listenerPairs.remove(i);
                    continue;
                }

                if (key == proxyInstance) {
                    pair.setValue(List.copyOf(listeners));
                    wasInstancePresent = true;
                }
                i++;
            }
            if (!wasInstancePresent) {
                listenerPairs.add(new Pair<>(new WeakReference<>(proxyInstance), List.copyOf(listeners)));
            }
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
            int i = 0;
            Collection<MethodCallListener> result = Collections.emptySet();
            while (i < listenerPairs.size()) {
                var pair = listenerPairs.get(i);
                Object key = pair.getKey().get();
                if (key == null) {
                    // The instance has been garbage-collected
                    listenerPairs.remove(i);
                    continue;
                }

                if (key == proxyInstance) {
                    result = pair.getValue();
                }
                i++;
            }
            return List.copyOf(result);
        } finally {
            listenersGuard.release();
        }
    }

}
