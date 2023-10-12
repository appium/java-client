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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);

    private Interceptor() {
    }

    /**
     * A magic method used to wrap public method calls in classes
     * patched by ByteBuddy and acting as proxies.
     *
     * @param self     The reference to the original instance.
     * @param method   The reference to the original method.
     * @param args     The reference to method args.
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
        Collection<MethodCallListener> listeners = ProxyListenersContainer.getInstance().getListeners(self);
        if (listeners.isEmpty()) {
            return callable.call();
        }

        listeners.forEach(listener -> {
            try {
                listener.beforeCall(self, method, args);
            } catch (NotImplementedException e) {
                // ignore
            } catch (Exception e) {
                LOGGER.atError().log("Got an unexpected error in beforeCall listener of {}.{} method",
                        self.getClass().getName(), method.getName(), e
                );
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
                LOGGER.atError().log("Got an unexpected error in afterCall listener of {}.{} method",
                        self.getClass().getName(), method.getName(), e
                );
            }
        });
        return endResult;
    }
}
