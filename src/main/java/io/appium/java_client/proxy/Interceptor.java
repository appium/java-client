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

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
    // Previously WeakHashMap has been used because of O(1) lookup performance, although
    // we had to change it to a list, which has O(N). The reason for that is that
    // maps implicitly call `hashCode` API on instances, which might not always
    // work as expected for arbitrary proxies
    private static final List<Pair<WeakReference<?>, Collection<MethodCallListener>>> LISTENERS = new ArrayList<>();
    private static final Semaphore LISTENERS_GUARD = new Semaphore(1);

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


    /**
     * Assign listeners for the particular proxied instance.
     *
     * @param instance The proxied instance.
     * @param listeners Collection of listeners.
     * @return The same given instance.
     */
    public static <T> T setListeners(T instance, Collection<MethodCallListener> listeners) {
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

                if (key == instance) {
                    pair.getValue().clear();
                    pair.getValue().addAll(listeners);
                    wasInstancePresent = true;
                }
                i++;
            }
            if (!wasInstancePresent) {
                LISTENERS.add(new Pair<>(new WeakReference<>(instance), new HashSet<>(listeners)));
            }
        } finally {
            LISTENERS_GUARD.release();
        }
        return instance;
    }

    /**
     * Fetches listeners for the particular proxied instance.
     *
     * @param instance The proxied instance.
     */
    public static Collection<MethodCallListener> getListeners(Object instance) {
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

                if (key == instance) {
                    result = pair.getValue();
                }
                i++;
            }
            return result;
        } finally {
            LISTENERS_GUARD.release();
        }
    }

    /**
     * A magic method used to wrap public method calls in classes
     * patched by ByteBuddy and acting as proxies.
     *
     * @param self The reference to the original instance.
     * @param method The reference to the original method.
     * @param args The reference to method args.
     * @param callable The reference to the non-patched callable to avoid call recursion.
     * @return Either the original method result or the patched one.
     */
    @SuppressWarnings("unused")
    @RuntimeType
    public static Object intercept(
            @This Object self,
            @Origin Method method,
            @AllArguments Object[] args,
            @SuperCall Callable<?> callable
    ) throws Throwable {
        Collection<MethodCallListener> listeners = getListeners(self);
        if (listeners.isEmpty()) {
            return callable.call();
        }

        listeners.forEach(listener -> {
            try {
                listener.beforeCall(self, method, args);
            } catch (NotImplementedException e) {
                // ignore
            } catch (Exception e) {
                logger.atError()
                        .addArgument(() -> self.getClass().getName())
                        .addArgument(method::getName)
                        .log("Got an unexpected error in beforeCall listener of {}.{} method", e);
            }
        });

        final UUID noResult = UUID.randomUUID();
        Object result = noResult;
        for (MethodCallListener listener : listeners) {
            try {
                result = listener.call(self, method, args, callable);
                break;
            } catch (NotImplementedException e) {
                // ignore
            } catch (Exception e) {
                try {
                    return listener.onError(self, method, args, e);
                } catch (NotImplementedException ignore) {
                    // ignore
                }
                throw e;
            }
        }
        if (noResult.equals(result)) {
            try {
                result = callable.call();
            } catch (Exception e) {
                for (MethodCallListener listener : listeners) {
                    try {
                        return listener.onError(self, method, args, e);
                    } catch (NotImplementedException ignore) {
                        // ignore
                    }
                }
                throw e;
            }
        }

        final Object endResult = result == noResult ? null : result;
        listeners.forEach(listener -> {
            try {
                listener.afterCall(self, method, args, endResult);
            } catch (NotImplementedException e) {
                // ignore
            } catch (Exception e) {
                logger.atError()
                        .addArgument(() -> self.getClass().getName())
                        .addArgument(method::getName)
                        .log("Got an unexpected error in afterCall listener of {}.{} method", e);
            }
        });
        return endResult;
    }
}
