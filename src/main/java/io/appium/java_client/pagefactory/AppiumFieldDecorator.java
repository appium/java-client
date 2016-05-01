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

import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getAutomation;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getPlatform;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility
    .unpackWebDriverFromSearchContext;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private static final List<Class<? extends WebElement>> availableElementClasses =
        new ArrayList<Class<? extends WebElement>>() {
            private static final long serialVersionUID = 1L;

            {
                add(WebElement.class);
                add(RemoteWebElement.class);
                add(MobileElement.class);
                add(TouchableElement.class);
                add(AndroidElement.class);
                add(IOSElement.class);
            }

        };

    private static final Map<Class<? extends SearchContext>, Class<? extends WebElement>>
        elementRuleMap =
        new HashMap<Class<? extends SearchContext>, Class<? extends WebElement>>() {
            private static final long serialVersionUID = 1L;

            {
                put(AndroidDriver.class, AndroidElement.class);
                put(IOSDriver.class, IOSElement.class);
            }
        };
    public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;
    public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
    private final WebDriver originalDriver;
    private final DefaultFieldDecorator defaultElementFieldDecoracor;
    private final AppiumElementLocatorFactory widgetLocatorFactory;
    private final String platform;
    private final String automation;
    private final TimeOutDuration timeOutDuration;

    public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut,
        TimeUnit timeUnit) {
        this(context, new TimeOutDuration(implicitlyWaitTimeOut, timeUnit));
    }

    /**
     * @param context is an instance of {@link org.openqa.selenium.SearchContext}
     *                It may be the instance of {@link org.openqa.selenium.WebDriver}
     *                or {@link org.openqa.selenium.WebElement} or
     *                {@link io.appium.java_client.pagefactory.Widget} or some other user's
     *                extension/implementation.
     * @param timeOutDuration is a desired duration of the waiting for an element presence.
     */
    public AppiumFieldDecorator(SearchContext context, TimeOutDuration timeOutDuration) {
        this.originalDriver = unpackWebDriverFromSearchContext(context);
        platform = getPlatform(originalDriver);
        automation = getAutomation(originalDriver);
        this.timeOutDuration = timeOutDuration;

        defaultElementFieldDecoracor = new DefaultFieldDecorator(
            new AppiumElementLocatorFactory(context, timeOutDuration, originalDriver,
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

                boolean result = false;
                for (Class<? extends WebElement> webElementClass : availableElementClasses) {
                    if (!webElementClass.equals(listType)) {
                        continue;
                    }
                    result = true;
                    break;
                }
                return result;
            }
        };

        widgetLocatorFactory =
            new AppiumElementLocatorFactory(context, timeOutDuration, originalDriver,
                new WidgetByBuilder(platform, automation));
    }

    public AppiumFieldDecorator(SearchContext context) {
        this(context, DEFAULT_IMPLICITLY_WAIT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    /**
     * @param ignored class loader is ignored by current implementation
     * @param field is {@link java.lang.reflect.Field} of page object which is supposed to be
     *              decorated.
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

        Class<? extends Widget> widgetType = null;
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
            OverrideWidgetReader.read(widgetType, field, platform, automation);

        if (isAlist) {
            return getEnhancedProxy(ArrayList.class,
                new WidgetListInterceptor(locator, originalDriver, map, widgetType,
                    timeOutDuration));
        }

        Constructor<? extends Widget> constructor =
            WidgetConstructorUtil.findConvenientConstructor(widgetType);
        return getEnhancedProxy(widgetType, new Class[] {constructor.getParameterTypes()[0]},
            new Object[] {proxyForAnElement(locator)},
            new WidgetInterceptor(locator, originalDriver, null, map, timeOutDuration));
    }

    private Class<?> getTypeForProxy() {
        Class<? extends SearchContext> driverClass = originalDriver.getClass();
        Iterable<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> rules =
            elementRuleMap.entrySet();
        //it will return MobileElement subclass when here is something
        for (Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>> e : rules) {
            //that extends AppiumDriver or MobileElement
            if (e.getKey().isAssignableFrom(driverClass)) {
                return e.getValue();
            }
        } //it is compatible with desktop browser. So at this case it returns RemoteWebElement.class
        return RemoteWebElement.class;
    }

    private WebElement proxyForAnElement(ElementLocator locator) {
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator, originalDriver);
        return (WebElement) getEnhancedProxy(getTypeForProxy(), elementInterceptor);
    }
}
