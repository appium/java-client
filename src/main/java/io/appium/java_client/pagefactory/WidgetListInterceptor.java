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
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.util.Optional.ofNullable;

public class WidgetListInterceptor extends InterceptorOfAListOfElements {
    private final Map<ContentType, Constructor<? extends Widget>> instantiationMap;
    private final List<Widget> cachedWidgets = new ArrayList<>();
    private final Class<? extends Widget> declaredType;
    private final Duration duration;
    private final WeakReference<WebDriver> driver;
    private final List<WeakReference<WebElement>> cachedElementReferences = new ArrayList<>();

    /**
     * Proxy interceptor class for lists of widgets.
     */
    public WidgetListInterceptor(
            @Nullable CacheableLocator locator,
            WeakReference<WebDriver> driver,
            Map<ContentType, Constructor<? extends Widget>> instantiationMap,
            Class<? extends Widget> declaredType,
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
                Class<?>[] params = new Class<?>[] {instantiationMap.get(type).getParameterTypes()[0]};
                WeakReference<WebElement> elementReference = new WeakReference<>(element);
                cachedWidgets.add(
                        getEnhancedProxy(
                                declaredType, params, new Object[] {element},
                                new WidgetInterceptor(null, driver, elementReference, instantiationMap, duration)
                        )
                );
                cachedElementReferences.add(elementReference);
            }
        }
        try {
            return method.invoke(cachedWidgets, args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }
}
