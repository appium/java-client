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
import io.appium.java_client.pagefactory.interceptors.InterceptorOfASingleElement;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;

import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.util.Optional.ofNullable;

public class WidgetInterceptor extends InterceptorOfASingleElement {

    private final Map<ContentType, Constructor<? extends Widget>> instantiationMap;
    private final Map<WebElement, Map<ContentType, Widget>> cachedInstances = new WeakHashMap<>();
    private final Duration duration;
    private WeakReference<WebElement> cachedElementReference;

    /**
     * Proxy interceptor class for widgets.
     */
    public WidgetInterceptor(
            @Nullable
            CacheableLocator locator,
            WeakReference<WebDriver> driverReference,
            @Nullable
            WeakReference<WebElement> cachedElementReference,
            Map<ContentType, Constructor<? extends Widget>> instantiationMap,
            Duration duration
    ) {
        super(locator, driverReference);
        this.cachedElementReference = cachedElementReference;
        this.instantiationMap = instantiationMap;
        this.duration = duration;
    }

    @Override
    protected Object getObject(WebElement element, Method method, Object[] args) throws Throwable {
        ContentType type = getCurrentContentType(element);
        if (cachedElementReference == null || cachedElementReference.get() == null
            || cachedInstances.isEmpty()
            || (locator != null && !((CacheableLocator) locator).isLookUpCached())
        ) {
            cachedElementReference = new WeakReference<>(element);

            Constructor<? extends Widget> constructor = instantiationMap.get(type);
            Class<? extends Widget> clazz = constructor.getDeclaringClass();

            if (Modifier.isAbstract(clazz.getModifiers())) {
                throw new InstantiationException(
                        String.format("%s is abstract so it cannot be instantiated", clazz.getName())
                );
            }

            Widget widget = constructor.newInstance(element);
            Map<ContentType, Widget> typeMap = ofNullable(cachedInstances.get(element)).orElseGet(HashMap::new);
            typeMap.put(type, widget);
            cachedInstances.put(element, typeMap);
            PageFactory.initElements(new AppiumFieldDecorator(new WeakReference<>(widget), duration), widget);
        }
        try {
            method.setAccessible(true);
            return method.invoke(cachedInstances.get(cachedElementReference.get()).get(type), args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }

    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        WebElement element = ofNullable(cachedElementReference).map(WeakReference::get).orElse(null);
        return locator == null && element != null
                ? getObject(element, method, args)
                : super.call(obj, method, args, original);
    }
}
