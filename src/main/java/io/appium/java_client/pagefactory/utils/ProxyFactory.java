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

package io.appium.java_client.pagefactory.utils;

import io.appium.java_client.proxy.MethodCallListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.appium.java_client.proxy.Helpers.OBJECT_METHOD_NAMES;
import static io.appium.java_client.proxy.Helpers.createProxy;
import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * Original class is a super class of a
 * proxy object here.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProxyFactory {
    private static final Set<String> NON_PROXYABLE_METHODS = setWith(
            setWithout(OBJECT_METHOD_NAMES, "toString"),
            "iterator"
    );

    @SafeVarargs
    private static <T> Set<T> setWithout(@SuppressWarnings("SameParameterValue") Set<T> source, T... items) {
        Set<T> result = new HashSet<>(source);
        Arrays.asList(items).forEach(result::remove);
        return Collections.unmodifiableSet(result);
    }

    @SafeVarargs
    private static <T> Set<T> setWith(@SuppressWarnings("SameParameterValue") Set<T> source, T... items) {
        Set<T> result = new HashSet<>(source);
        result.addAll(List.of(items));
        return Collections.unmodifiableSet(result);
    }

    /**
     * Creates a proxy instance for the given class with an empty constructor.
     *
     * @param <T> The proxy object class.
     * @param requiredClazz is a {@link java.lang.Class} whose instance should be created
     * @param listener is the instance of a method listener class
     * @return a proxied instance of the desired class
     */
    public static <T> T getEnhancedProxy(Class<T> requiredClazz, MethodCallListener listener) {
        return getEnhancedProxy(requiredClazz, new Class<?>[] {}, new Object[] {}, listener);
    }

    /**
     * Creates a proxy instance for the given class.
     *
     * @param <T> The proxy object class.
     * @param cls is a {@link java.lang.Class} whose instance should be created
     * @param params is an array of @link java.lang.Class}. It should be convenient to
     *               parameter types of some declared constructor which belongs to desired
     *               class.
     * @param values is an array of @link java.lang.Object}. It should be convenient to
     *               parameter types of some declared constructor which belongs to desired
     *               class.
     * @param listener is the instance of a method listener class
     * @return a proxied instance of the desired class
     */
    public static <T> T getEnhancedProxy(
            Class<T> cls, Class<?>[] params, Object[] values, MethodCallListener listener
    ) {
        ElementMatcher<MethodDescription> extraMatcher = not(
                namedOneOf(NON_PROXYABLE_METHODS.toArray(new String[0]))
        ).and(
                not(isAbstract())
        );
        return createProxy(
                cls,
                values,
                params,
                Collections.singletonList(listener),
                extraMatcher
        );
    }
}
