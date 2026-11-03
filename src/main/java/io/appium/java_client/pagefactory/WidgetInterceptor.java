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
import io.appium.java_client.support.PageFactory;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.util.Optional.ofNullable;

/** Proxy interceptor class for widgets. */
public class WidgetInterceptor extends InterceptorOfASingleElement {

    private final Map<ContentType, Constructor<? extends Widget>> instantiationMap;
    private final Map<ContentType, Widget> cachedInstances = new HashMap<>();
    private final Duration duration;
    private final LocatorGroupStrategy defaultLocatorGroupStrategy;
    private WeakReference<WebElement> cachedElementReference;

    /**
     * Creates a new interceptor.
     *
     * @param locator the locator of the widget element
     * @param driverReference the reference to the driver
     * @param cachedElementReference the reference to the cached widget element
     * @param instantiationMap the widget constructors mapped by the content type
     * @param duration the timeout of waiting for an element presence
     */
    public WidgetInterceptor(
            @Nullable CacheableLocator locator,
            WeakReference<WebDriver> driverReference,
            @Nullable WeakReference<WebElement> cachedElementReference,
            Map<ContentType, Constructor<? extends Widget>> instantiationMap,
            Duration duration
    ) {
        this(
                locator,
                driverReference,
                cachedElementReference,
                instantiationMap,
                duration,
                AppiumFieldDecorator.currentDefaultStrategy()
        );
    }

    WidgetInterceptor(
            @Nullable CacheableLocator locator,
            WeakReference<WebDriver> driverReference,
            @Nullable WeakReference<WebElement> cachedElementReference,
            Map<ContentType, Constructor<? extends Widget>> instantiationMap,
            Duration duration,
            LocatorGroupStrategy defaultLocatorGroupStrategy
    ) {
        super(locator, driverReference);
        this.cachedElementReference = cachedElementReference;
        this.instantiationMap = instantiationMap;
        this.duration = duration;
        this.defaultLocatorGroupStrategy = defaultLocatorGroupStrategy;
    }

    @Override
    protected Object getObject(WebElement element, Method method, Object[] args) throws Throwable {
        ContentType type = getCurrentContentType(element);
        if (isCached(type)) {
            return invoke(cachedInstances.get(type), method, args);
        }

        rejectAbstractWidget(type);
        try {
            return bindAndInvoke(type, element, method, args);
        } catch (NoSuchElementException firstMiss) {
            // The first ALL_POSSIBLE root matched, but the nested lookup did not.
            // Try each later root with the same nested locators.
            NoSuchElementException lastMiss = firstMiss;
            for (WebElement root : furtherRoots(element)) {
                try {
                    return bindAndInvoke(type, root, method, args);
                } catch (NoSuchElementException laterMiss) {
                    lastMiss = laterMiss;
                }
            }
            throw lastMiss;
        }
    }

    private boolean isCached(ContentType type) {
        WebElement cachedElement = cachedElementReference == null ? null : cachedElementReference.get();
        if (cachedElement == null || !cachedInstances.containsKey(type)) {
            return false;
        }
        return locator == null || ((CacheableLocator) locator).isLookUpCached();
    }

    private void rejectAbstractWidget(ContentType type) throws InstantiationException {
        Class<? extends Widget> clazz = instantiationMap.get(type).getDeclaringClass();
        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new InstantiationException(
                    String.format("%s is abstract so it cannot be instantiated", clazz.getName())
            );
        }
    }

    private Object bindAndInvoke(ContentType type, WebElement root, Method method, Object[] args) throws Throwable {
        Widget widget = instantiationMap.get(type).newInstance(root);
        PageFactory.initElements(
                new AppiumFieldDecorator(new WeakReference<>(widget), duration, defaultLocatorGroupStrategy),
                widget
        );
        Object result = invoke(widget, method, args);
        cachedElementReference = new WeakReference<>(root);
        cachedInstances.put(type, widget);
        return result;
    }

    private static Object invoke(Widget widget, Method method, Object[] args) throws Throwable {
        try {
            method.setAccessible(true);
            return method.invoke(widget, args);
        } catch (Throwable thrown) {
            throw extractReadableException(thrown);
        }
    }

    private List<WebElement> furtherRoots(WebElement resolvedRoot) {
        if (!(locator instanceof AppiumElementLocator)) {
            return List.of();
        }
        return ((AppiumElementLocator) locator).findFurtherRoots(resolvedRoot);
    }

    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        WebElement element = ofNullable(cachedElementReference).map(WeakReference::get).orElse(null);
        return locator == null && element != null
                ? getObject(element, method, args)
                : super.call(obj, method, args, original);
    }
}
