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

import net.bytebuddy.matcher.ElementMatchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.appium.java_client.proxy.Helpers.OBJECT_METHOD_NAMES;
import static io.appium.java_client.proxy.Helpers.createProxy;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

public class ElementAwareWebDriverListener implements MethodCallListener, ProxyAwareListener {
    private WebDriver parent;

    /**
     * Attaches the WebDriver proxy instance to this listener.
     * <p>
     * The listener stores the WebDriver instance to associate it as parent to RemoteWebElement proxies.
     *
     * @param proxy A proxy instance of {@link WebDriver}.
     */
    @Override
    public void attachProxyInstance(Object proxy) {
        if (proxy instanceof WebDriver) {
            this.parent = (WebDriver) proxy;
        }
    }

    /**
     * Intercepts method calls on a proxied WebDriver.
     * <p>
     * If the result of the method call is a {@link RemoteWebElement},
     * it is wrapped with a proxy to allow further interception of RemoteWebElement method calls.
     * If the result is a list, each item is checked, and all RemoteWebElements are
     * individually proxied. All other return types are passed through unmodified.
     * Avoid overriding this method, it will alter the behaviour of the listener.
     *
     * @param obj      The object on which the method was invoked.
     * @param method   The method being invoked.
     * @param args     The arguments passed to the method.
     * @param original A {@link Callable} that represents the original method execution.
     * @return The (possibly wrapped) result of the method call.
     * @throws Throwable if the original method or any wrapping logic throws an exception.
     */
    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        Object result = original.call();

        if (result instanceof RemoteWebElement) {
            return wrapElement((RemoteWebElement) result);
        }

        if (result instanceof List) {
            return ((List<?>) result).stream()
                    .map(item -> item instanceof RemoteWebElement ? wrapElement(
                            (RemoteWebElement) item) : item)
                    .collect(Collectors.toList());
        }

        return result;
    }

    private RemoteWebElement wrapElement(
            RemoteWebElement original
    ) {
        RemoteWebElement proxy = createProxy(
                RemoteWebElement.class,
                new Object[]{},
                new Class[]{},
                Collections.singletonList(this),
                ElementMatchers.not(
                        namedOneOf(
                                OBJECT_METHOD_NAMES.toArray(new String[0]))
                                .or(ElementMatchers.named("setId").or(ElementMatchers.named("setParent")))
                )
        );

        proxy.setId(original.getId());

        proxy.setParent((RemoteWebDriver) parent);

        return proxy;
    }

}
