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

import static io.appium.java_client.internal.ElementMap.getElementClass;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;
import static java.time.Duration.ofSeconds;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.HasSessionDetails;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.selenium.SearchContext;
import io.appium.java_client.selenium.WebDriver;
import io.appium.java_client.selenium.WebElement;
import io.appium.java_client.selenium.remote.RemoteWebElement;
import io.appium.java_client.selenium.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.selenium.support.pagefactory.ElementLocator;
import io.appium.java_client.selenium.support.pagefactory.FieldDecorator;
import io.appium.java_client.windows.WindowsElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List of WebElement that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 * Please pay attention: fields of {@link WebElement}, {@link RemoteWebElement},
 * {@link MobileElement}, {@link AndroidElement} and {@link IOSElement} are allowed
 * to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator {

    private static final List<Class<? extends WebElement>> availableElementClasses = ImmutableList.of(WebElement.class,
            RemoteWebElement.class, MobileElement.class, AndroidElement.class,
            IOSElement.class, WindowsElement.class);
    public static final Duration DEFAULT_WAITING_TIMEOUT = ofSeconds(1);
    private final WebDriver webDriver;
    private final DefaultFieldDecorator defaultElementFieldDecoracor;
    private final AppiumElementLocatorFactory widgetLocatorFactory;
    private final String platform;
    private final String automation;
    private final Duration duration;


    /**
     * Creates field decorator based on {@link SearchContext} and timeout {@code duration}.
     *
     * @param context is an instance of {@link SearchContext}
     *                It may be the instance of {@link WebDriver} or {@link WebElement} or
     *                {@link Widget} or some other user's extension/implementation.
     * @param duration is a desired duration of the waiting for an element presence.
     */
    public AppiumFieldDecorator(SearchContext context, Duration duration) {
        this.webDriver = unpackWebDriverFromSearchContext(context);
        HasSessionDetails hasSessionDetails = ofNullable(this.webDriver).map(webDriver -> {
            if (!HasSessionDetails.class.isAssignableFrom(webDriver.getClass())) {
                return null;
            }
            return HasSessionDetails.class.cast(webDriver);
        }).orElse(null);

        if (hasSessionDetails == null) {
            platform = null;
            automation = null;
        } else {
            platform = hasSessionDetails.getPlatformName();
            automation = hasSessionDetails.getAutomationName();
        }

        this.duration = duration;

        defaultElementFieldDecoracor = new DefaultFieldDecorator(
                new AppiumElementLocatorFactory(context, duration,
                        new DefaultElementByBuilder(platform, automation))) {
            @Override
            protected WebElement proxyForLocator(ClassLoader ignored, ElementLocator locator) {
                return proxyForAnElement(locator);
            }

            @Override
            @SuppressWarnings("unchecked")
            protected List<WebElement> proxyForListLocator(ClassLoader ignored,
                                                           ElementLocator locator) {
                ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
                return getEnhancedProxy(ArrayList.class, elementInterceptor);
            }

            @Override protected boolean isDecoratableList(Field field) {
                if (!List.class.isAssignableFrom(field.getType())) {
                    return false;
                }

                Type genericType = field.getGenericType();
                if (!(genericType instanceof ParameterizedType)) {
                    return false;
                }

                Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

                for (Class<? extends WebElement> webElementClass : availableElementClasses) {
                    if (webElementClass.equals(listType)) {
                        return true;
                    }
                }
                return false;
            }
        };

        widgetLocatorFactory =
                new AppiumElementLocatorFactory(context, duration, new WidgetByBuilder(platform, automation));
    }

    public AppiumFieldDecorator(SearchContext context) {
        this(context, DEFAULT_WAITING_TIMEOUT);
    }

    /**
     * Decorated page object {@code field}.
     *
     * @param ignored class loader is ignored by current implementation
     * @param field is {@link Field} of page object which is supposed to be decorated.
     * @return a field value or null.
     */
    public Object decorate(ClassLoader ignored, Field field) {
        Object result = defaultElementFieldDecoracor.decorate(ignored, field);
        if (result != null) {
            return result;
        }

        return decorateWidget(field);
    }

    @SuppressWarnings("unchecked")
    private Object decorateWidget(Field field) {
        Class<?> type = field.getType();
        if (!Widget.class.isAssignableFrom(type) && !List.class.isAssignableFrom(type)) {
            return null;
        }

        Class<? extends Widget> widgetType;
        boolean isAlist = false;
        if (List.class.isAssignableFrom(type)) {
            isAlist = true;
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return null;
            }

            Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

            if (ParameterizedType.class.isAssignableFrom(listType.getClass())) {
                listType = ((ParameterizedType) listType).getRawType();
            }

            if (listType instanceof Class) {
                if (!Widget.class.isAssignableFrom((Class) listType)) {
                    return null;
                }
                widgetType = Class.class.cast(listType);
            } else {
                return null;
            }

        } else {
            widgetType = (Class<? extends Widget>) field.getType();
        }

        CacheableLocator locator = widgetLocatorFactory.createLocator(field);
        Map<ContentType, Constructor<? extends Widget>> map =
            OverrideWidgetReader.read(widgetType, field, platform);

        if (isAlist) {
            return getEnhancedProxy(ArrayList.class,
                new WidgetListInterceptor(locator, webDriver, map, widgetType,
                        duration));
        }

        Constructor<? extends Widget> constructor =
            WidgetConstructorUtil.findConvenientConstructor(widgetType);
        return getEnhancedProxy(widgetType, new Class[] {constructor.getParameterTypes()[0]},
            new Object[] {proxyForAnElement(locator)},
            new WidgetInterceptor(locator, webDriver, null, map, duration));
    }

    private WebElement proxyForAnElement(ElementLocator locator) {
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator, webDriver);
        return getEnhancedProxy(getElementClass(platform, automation), elementInterceptor);
    }
}
