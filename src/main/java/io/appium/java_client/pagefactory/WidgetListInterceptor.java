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

package io.appium.java_client.pagefactory;

import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.interceptors.InterceptorOfAListOfElements;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedInterfaceProxy;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.util.Optional.ofNullable;

/** Proxy interceptor class for lists of widgets. */
public class WidgetListInterceptor extends InterceptorOfAListOfElements {
    private final Map<ContentType, Constructor<? extends Widget>> instantiationMap;
    private final List<Object> cachedWidgets = new ArrayList<>();
    private final Class<?> declaredType;
    private final Duration duration;
    private final WeakReference<WebDriver> driver;
    private final List<WeakReference<WebElement>> cachedElementReferences = new ArrayList<>();

    /**
     * Creates a new interceptor.
     *
     * @param locator the locator of the widget elements
     * @param driver the reference to the driver
     * @param instantiationMap the widget constructors mapped by the content type
     * @param declaredType the declared widget class, or an interface implemented by the override classes
     * @param duration the timeout of waiting for an element presence
     */
    public WidgetListInterceptor(
            @Nullable CacheableLocator locator,
            WeakReference<WebDriver> driver,
            Map<ContentType, Constructor<? extends Widget>> instantiationMap,
            Class<?> declaredType,
            Duration duration
    ) {
        super(locator);
        this.instantiationMap = instantiationMap;
        this.declaredType = declaredType;
        this.duration = duration;
        this.driver = driver;
    }

    @Override
    protected Object getObject(List<WebElement> elements, Method method, Object[] args) throws Throwable {
        List<WebElement> cachedElements = cachedElementReferences.stream()
                    .map(WeakReference::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        if (cachedElements.size() != cachedWidgets.size()
                || locator != null && !((CacheableLocator) locator).isLookUpCached()) {
            cachedWidgets.clear();
            cachedElementReferences.clear();

            ContentType type = null;
            for (WebElement element : elements) {
                type = ofNullable(type).orElseGet(() -> getCurrentContentType(element));
                WeakReference<WebElement> elementReference = new WeakReference<>(element);
                WidgetInterceptor interceptor = new WidgetInterceptor(
                        null, driver, elementReference, instantiationMap, duration);
                if (declaredType.isInterface()) {
                    cachedWidgets.add(getEnhancedInterfaceProxy(declaredType, interceptor));
                } else {
                    Class<?>[] params = new Class<?>[] {instantiationMap.get(type).getParameterTypes()[0]};
                    cachedWidgets.add(proxyWidget(declaredType, params, element, interceptor));
                }
                cachedElementReferences.add(elementReference);
            }
        }
        try {
            return method.invoke(cachedWidgets, args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }

    @SuppressWarnings("unchecked")
    private static Widget proxyWidget(
            Class<?> declaredType,
            Class<?>[] params,
            WebElement element,
            WidgetInterceptor interceptor
    ) {
        return getEnhancedProxy((Class<Widget>) declaredType, params, new Object[] {element}, interceptor);
    }
}
