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

import com.google.common.base.Preconditions;
import lombok.Value;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

public class Helpers {
    public static final Set<String> OBJECT_METHOD_NAMES = Stream.of(Object.class.getMethods())
            .map(Method::getName)
            .collect(Collectors.toSet());

    // Each proxy class created by ByteBuddy gets automatically cached by the
    // given class loader. It is important to have this cache here in order to improve
    // the performance and to avoid extensive memory usage for our case, where
    // the amount of instrumented proxy classes we create is low in comparison to the amount
    // of proxy instances.
    private static final ConcurrentMap<ProxyClassSignature, Class<?>> CACHED_PROXY_CLASSES = new ConcurrentHashMap<>();

    private Helpers() {
    }

    /**
     * Creates a transparent proxy instance for the given class.
     * It is possible to provide one or more method execution listeners
     * or replace particular method calls completely. Callbacks
     * defined in these listeners are going to be called when any
     * **public** method of the given class is invoked. Overridden callbacks
     * are expected to be skipped if they throw
     * {@link io.appium.java_client.proxy.NotImplementedException}.
     *
     * @param cls                 the class to which the proxy should be created.
     *                            Must not be an interface.
     * @param constructorArgs     Array of constructor arguments. Could be an
     *                            empty array if the class provides a constructor without arguments.
     * @param constructorArgTypes Array of constructor argument types. Must
     *                            represent types of constructorArgs.
     * @param listeners           One or more method invocation listeners.
     * @param <T>                 Any class derived from Object
     * @return Proxy instance
     */
    public static <T> T createProxy(
            Class<T> cls,
            Object[] constructorArgs,
            Class<?>[] constructorArgTypes,
            Collection<MethodCallListener> listeners
    ) {
        ElementMatcher<MethodDescription> extraMatcher = ElementMatchers.not(namedOneOf(
                OBJECT_METHOD_NAMES.toArray(new String[0])
        ));
        return createProxy(cls, constructorArgs, constructorArgTypes, listeners, extraMatcher);
    }

    /**
     * Creates a transparent proxy instance for the given class.
     * It is possible to provide one or more method execution listeners
     * or replace particular method calls completely. Callbacks
     * defined in these listeners are going to be called when any
     * **public** method of the given class is invoked. Overridden callbacks
     * are expected to be skipped if they throw
     * {@link io.appium.java_client.proxy.NotImplementedException}.
     * !!! This API is designed for private usage.
     *
     * @param cls                    The class to which the proxy should be created.
     *                               Must not be an interface.
     * @param constructorArgs        Array of constructor arguments. Could be an
     *                               empty array if the class provides a constructor without arguments.
     * @param constructorArgTypes    Array of constructor argument types. Must
     *                               represent types of constructorArgs.
     * @param listeners              One or more method invocation listeners.
     * @param extraMethodMatcher     Optional additional method proxy conditions
     * @param <T>                    Any class derived from Object
     * @return Proxy instance
     */
    public static <T> T createProxy(
            Class<T> cls,
            Object[] constructorArgs,
            Class<?>[] constructorArgTypes,
            Collection<MethodCallListener> listeners,
            @Nullable ElementMatcher<MethodDescription> extraMethodMatcher
    ) {
        var signature = ProxyClassSignature.of(cls, constructorArgTypes, extraMethodMatcher);
        var proxyClass = CACHED_PROXY_CLASSES.computeIfAbsent(signature, k -> {
            Preconditions.checkArgument(constructorArgs.length == constructorArgTypes.length,
                    String.format(
                            "Constructor arguments array length %d must be equal to the types array length %d",
                            constructorArgs.length, constructorArgTypes.length
                    )
            );
            Preconditions.checkArgument(!listeners.isEmpty(), "The collection of listeners must not be empty");
            requireNonNull(cls, "Class must not be null");
            Preconditions.checkArgument(!cls.isInterface(), "Class must not be an interface");

            ElementMatcher.Junction<MethodDescription> matcher = ElementMatchers.isPublic();
            //noinspection resource
            return new ByteBuddy()
                    .subclass(cls)
                    .method(extraMethodMatcher == null ? matcher : matcher.and(extraMethodMatcher))
                    .intercept(MethodDelegation.to(Interceptor.class))
                    // https://github.com/raphw/byte-buddy/blob/2caef35c172897cbdd21d163c55305a64649ce41/byte-buddy-dep/src/test/java/net/bytebuddy/ByteBuddyTutorialExamplesTest.java#L346
                    .defineField("methodCallListeners", MethodCallListener[].class, Visibility.PRIVATE)
                    .implement(HasMethodCallListeners.class).intercept(FieldAccessor.ofBeanProperty())
                    .make()
                    .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded()
                    .asSubclass(cls);
        });

        try {
            T result = cls.cast(proxyClass.getConstructor(constructorArgTypes).newInstance(constructorArgs));
            ((HasMethodCallListeners) result).setMethodCallListeners(listeners.toArray(MethodCallListener[]::new));
            return result;
        } catch (SecurityException | ReflectiveOperationException e) {
            throw new IllegalStateException(String.format("Unable to create a proxy of %s", cls.getName()), e);
        }
    }

    /**
     * Creates a transparent proxy instance for the given class.
     * It is possible to provide one or more method execution listeners
     * or replace particular method calls completely. Callbacks
     * defined in these listeners are going to be called when any
     * **public** method of the given class is invoked. Overridden callbacks
     * are expected to be skipped if they throw NotImplementedException.
     *
     * @param cls       the class to which the proxy should be created.
     *                  Must not be an interface. Must expose a constructor
     *                  without arguments.
     * @param listeners One or more method invocation listeners.
     * @param <T>       Any class derived from Object
     * @return Proxy instance
     */
    public static <T> T createProxy(Class<T> cls, Collection<MethodCallListener> listeners) {
        return createProxy(cls, new Object[]{}, new Class[]{}, listeners);
    }

    /**
     * Creates a transparent proxy instance for the given class.
     * It is possible to provide one or more method execution listeners
     * or replace particular method calls completely. Callbacks
     * defined in these listeners are going to be called when any
     * **public** method of the given class is invoked. Overridden callbacks
     * are expected to be skipped if they throw NotImplementedException.
     *
     * @param cls      the class to which the proxy should be created.
     *                 Must not be an interface. Must expose a constructor
     *                 without arguments.
     * @param listener Method invocation listener.
     * @param <T>      Any class derived from Object
     * @return Proxy instance
     */
    public static <T> T createProxy(Class<T> cls, MethodCallListener listener) {
        return createProxy(cls, new Object[]{}, new Class[]{}, Collections.singletonList(listener));
    }

    /**
     * Creates a transparent proxy instance for the given class.
     * It is possible to provide one or more method execution listeners
     * or replace particular method calls completely. Callbacks
     * defined in these listeners are going to be called when any
     * **public** method of the given class is invoked. Overridden callbacks
     * are expected to be skipped if they throw NotImplementedException.
     *
     * @param cls                 the class to which the proxy should be created.
     *                            Must not be an interface.
     * @param constructorArgs     Array of constructor arguments. Could be an
     *                            empty array if the class provides a constructor without arguments.
     * @param constructorArgTypes Array of constructor argument types. Must
     *                            represent types of constructorArgs.
     * @param listener            Method invocation listener.
     * @param <T>                 Any class derived from Object
     * @return Proxy instance
     */
    public static <T> T createProxy(
            Class<T> cls,
            Object[] constructorArgs,
            Class<?>[] constructorArgTypes,
            MethodCallListener listener
    ) {
        return createProxy(cls, constructorArgs, constructorArgTypes, Collections.singletonList(listener));
    }

    @Value(staticConstructor = "of")
    private static class ProxyClassSignature {
        Class<?> cls;
        Class<?>[] constructorArgTypes;
        ElementMatcher<MethodDescription> extraMethodMatcher;
    }
}
