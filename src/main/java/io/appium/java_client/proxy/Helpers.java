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

import io.appium.java_client.internal.Preconditions;
import lombok.Value;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

/**
 * Helpers for creating transparent proxies of classes.
 *
 * <p>A public interface is proxied when this library's class loader can see the interface,
 * or when the interface's class loader can see this library.
 * A package-private or protected interface, or a public interface for which neither is true,
 * is rejected with {@link IllegalArgumentException}.
 */
public class Helpers {
    /**
     * The names of the methods declared by {@link Object}, which are not proxied by default.
     */
    public static final Set<String> OBJECT_METHOD_NAMES = Stream.of(Object.class.getMethods())
            .map(Method::getName)
            .collect(Collectors.toSet());

    // Each proxy class created by ByteBuddy gets automatically cached by the
    // given class loader. It is important to have this cache here in order to improve
    // the performance and to avoid extensive memory usage for our case, where
    // the amount of instrumented proxy classes we create is low in comparison to the amount
    // of proxy instances.
    // The key is a signature object. The generated class does not reference that object,
    // so collecting the weak key does not depend on the interface or widget class.
    private static final Map<ProxyClassSignature, Class<?>> CACHED_PROXY_CLASSES =
        Collections.synchronizedMap(new WeakHashMap<>());

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
     * @param cls                 the class to proxy, or a public interface.
     *                            A non-public interface is rejected with
     *                            {@link IllegalArgumentException}. A public interface is also
     *                            rejected unless this library's loader can see it, or its loader
     *                            can see this library.
     * @param constructorArgs     Array of constructor arguments. Could be an
     *                            empty array if the class provides a constructor without arguments.
     *                            Must be empty when {@code cls} is an interface.
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
     * @param cls                    The class to proxy, or a public interface.
     *                               A non-public interface is rejected with
     *                               {@link IllegalArgumentException}. A public interface is also
     *                               rejected unless this library's loader can see it, or its loader
     *                               can see this library.
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
        requireNonNull(cls, "Class must not be null");
        if (cls.isInterface()) {
            Preconditions.checkArgument(Modifier.isPublic(cls.getModifiers()), String.format(
                    "Cannot proxy non-public interface %s. The generated proxy is defined in its own class loader, "
                            + "so it cannot implement a package-private or protected interface. Declare %s public.",
                    cls.getName(), cls.getSimpleName()
            ));
            Preconditions.checkArgument(constructorArgs.length == 0 && constructorArgTypes.length == 0,
                    cls.getName() + " is an interface, so the proxy has no constructor arguments");
        }
        var signature = ProxyClassSignature.of(cls, constructorArgTypes, extraMethodMatcher);
        var proxyClass = CACHED_PROXY_CLASSES.computeIfAbsent(signature, k -> {
            Preconditions.checkArgument(constructorArgs.length == constructorArgTypes.length,
                    String.format(
                            "Constructor arguments array length %d must be equal to the types array length %d",
                            constructorArgs.length, constructorArgTypes.length
                    )
            );
            Preconditions.checkArgument(!listeners.isEmpty(), "The collection of listeners must not be empty");

            ElementMatcher.Junction<MethodDescription> matcher = ElementMatchers.isPublic();
            return defineProxyClass(cls, extraMethodMatcher == null ? matcher : matcher.and(extraMethodMatcher));
        });

        try {
            T result = cls.cast(proxyClass.getConstructor(constructorArgTypes).newInstance(constructorArgs));
            ((HasMethodCallListeners) result).setMethodCallListeners(listeners.toArray(MethodCallListener[]::new));

            listeners.stream()
                    .filter(ProxyAwareListener.class::isInstance)
                    .map(ProxyAwareListener.class::cast)
                    .forEach(listener -> listener.attachProxyInstance(result));

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
     * @param cls       the class to proxy, or a public interface.
     *                  A class must expose a constructor without arguments.
     *                  A non-public interface is rejected with {@link IllegalArgumentException}.
     *                  A public interface is also rejected unless this library's loader can see it,
     *                  or its loader can see this library.
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
     * @param cls      the class to proxy, or a public interface.
     *                 A class must expose a constructor without arguments.
     *                 A non-public interface is rejected with {@link IllegalArgumentException}.
     *                 A public interface is also rejected unless this library's loader can see it,
     *                 or its loader can see this library.
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
     * @param cls                 the class to proxy, or a public interface.
     *                            A non-public interface is rejected with
     *                            {@link IllegalArgumentException}. A public interface is also
     *                            rejected unless this library's loader can see it, or its loader
     *                            can see this library.
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

    /**
     * Builds a proxy class for a class or a public interface.
     * An interface proxy subclasses {@link Object} and is loaded where both sides are visible.
     */
    private static <T> Class<? extends T> defineProxyClass(
            Class<T> cls,
            ElementMatcher<? super MethodDescription> methodMatcher
    ) {
        boolean interfaceProxy = cls.isInterface();
        ClassLoader loader = interfaceProxy ? loaderForInterfaceProxy(cls) : Helpers.class.getClassLoader();
        var byteBuddy = new ByteBuddy();
        var subclassed = byteBuddy.subclass(interfaceProxy ? Object.class : cls);
        var instrumented = interfaceProxy ? subclassed.implement(cls) : subclassed;
        //noinspection resource
        return instrumented
                .method(methodMatcher)
                .intercept(MethodDelegation.to(Interceptor.class))
                // https://github.com/raphw/byte-buddy/blob/2caef35c172897cbdd21d163c55305a64649ce41/byte-buddy-dep/src/test/java/net/bytebuddy/ByteBuddyTutorialExamplesTest.java#L346
                .defineField("methodCallListeners", MethodCallListener[].class, Visibility.PRIVATE)
                .implement(HasMethodCallListeners.class).intercept(FieldAccessor.ofBeanProperty())
                .make()
                .load(loader, ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .asSubclass(cls);
    }

    /**
     * Prefers the library loader, matching class proxies, when it can see the interface.
     * Otherwise uses the interface loader when that loader can see {@link Interceptor}.
     */
    private static ClassLoader loaderForInterfaceProxy(Class<?> iface) {
        ClassLoader libraryLoader = Helpers.class.getClassLoader();
        if (sameTypeVisible(libraryLoader, iface)) {
            return libraryLoader;
        }
        ClassLoader interfaceLoader = iface.getClassLoader();
        if (interfaceLoader != null && sameTypeVisible(interfaceLoader, Interceptor.class)) {
            return interfaceLoader;
        }
        throw new IllegalArgumentException(String.format(
                "Cannot proxy %s. The generated proxy must see both this interface and %s. "
                        + "Interface class loader: %s. Library class loader: %s. "
                        + "Either this library's loader must see the interface, "
                        + "or the interface's loader must see this library.",
                iface.getName(),
                Interceptor.class.getName(),
                interfaceLoader,
                libraryLoader
        ));
    }

    private static boolean sameTypeVisible(ClassLoader loader, Class<?> type) {
        if (type.getClassLoader() == loader) {
            return true;
        }
        if (loader == null) {
            return false;
        }
        try {
            return Class.forName(type.getName(), false, loader) == type;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Value(staticConstructor = "of")
    private static class ProxyClassSignature {
        Class<?> cls;
        Class<?>[] constructorArgTypes;
        ElementMatcher<MethodDescription> extraMethodMatcher;
    }
}
