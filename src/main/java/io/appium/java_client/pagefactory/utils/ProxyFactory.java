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


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Original class is a super class of a
 * proxy object here.
 */
public final class ProxyFactory {

    private ProxyFactory() {
        super();
    }

    public static <T> T getEnhancedProxy(Class<T> requiredClazz, MethodInterceptor interceptor) {
        return getEnhancedProxy(requiredClazz, new Class<?>[] {}, new Object[] {}, interceptor);
    }

    /**
     * It returns some proxies created by CGLIB.
     *
     * @param requiredClazz is a {@link java.lang.Class} whose instance should be created
     * @param params is an array of @link java.lang.Class}. It should be convenient to
     *               parameter types of some declared constructor which belongs to desired
     *               class.
     * @param values is an array of @link java.lang.Object}. It should be convenient to
     *               parameter types of some declared constructor which belongs to desired
     *               class.
     * @param interceptor is the instance of {@link net.sf.cglib.proxy.MethodInterceptor}
     * @return a proxied instance of the desired class
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEnhancedProxy(Class<T> requiredClazz, Class<?>[] params, Object[] values,
        MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(requiredClazz);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create(params, values);
    }
}
