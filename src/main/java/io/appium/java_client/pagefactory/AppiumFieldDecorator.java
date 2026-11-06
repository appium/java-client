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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.remote.CapabilityType;
import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocator;
import io.appium.java_client.support.pagefactory.ElementLocatorFactory;
import io.appium.java_client.support.pagefactory.FieldDecorator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.internal.Preconditions.checkNotNull;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackObjectFromSearchContext;
import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static java.time.Duration.ofSeconds;
import static java.util.Objects.requireNonNull;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List of WebElement that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 * Please pay attention: fields of {@link WebElement} or {@link AppiumWebElement}
 * to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator {

    private static final List<Class<? extends WebElement>> AVAILABLE_ELEMENT_CLASSES = List.of(
            WebElement.class,
            AppiumWebElement.class
    );
    /** The default timeout of waiting for an element presence. */
    public static final Duration DEFAULT_WAITING_TIMEOUT = ofSeconds(1);
    private static final ThreadLocal<LocatorGroupStrategy> CURRENT_DEFAULT_STRATEGY =
            ThreadLocal.withInitial(() -> LocatorGroupStrategy.CHAIN);
    private static final ThreadLocal<FieldDecorationContext> CURRENT_FIELD_CONTEXT =
            new ThreadLocal<>();
    private final WeakReference<WebDriver> webDriverReference;
    private final DefaultFieldDecorator defaultElementFieldDecorator;
    private final AppiumElementLocatorFactory widgetLocatorFactory;
    private final String platform;
    private final String automation;
    private final Duration duration;
    private final LocatorGroupStrategy defaultLocatorGroupStrategy;

    static LocatorGroupStrategy currentDefaultStrategy() {
        return CURRENT_DEFAULT_STRATEGY.get();
    }

    /**
     * Creates field decorator based on {@link SearchContext} and timeout {@code duration}.
     *
     * @param context  is an instance of {@link SearchContext}
     *                 It may be the instance of {@link WebDriver} or {@link WebElement} or
     *                 {@link Widget} or some other user's extension/implementation.
     * @param duration is a desired duration of the waiting for an element presence.
     */
    public AppiumFieldDecorator(SearchContext context, Duration duration) {
        this(context, duration, LocatorGroupStrategy.CHAIN);
    }

    /**
     * Creates field decorator based on {@link SearchContext}, timeout {@code duration},
     * and a custom default {@link LocatorGroupStrategy}.
     *
     * <p>Precedence order: field-level {@link HowToUseLocators} &gt; widget/page/declaring class
     * {@link HowToUseLocators} (including superclasses, interfaces, and enclosing classes)
     * &gt; {@code defaultLocatorGroupStrategy}. Note that when {@link HowToUseLocators} is present,
     * both {@link HowToUseLocators#androidAutomation()} and {@link HowToUseLocators#iOSXCUITAutomation()}
     * default to {@link LocatorGroupStrategy#CHAIN} for any platform attribute omitted in the annotation.
     *
     * @param context                     is an instance of {@link SearchContext}
     * @param duration                    is a desired duration of the waiting for an element presence.
     * @param defaultLocatorGroupStrategy is the default strategy used when {@link HowToUseLocators} is not present.
     */
    public AppiumFieldDecorator(
            SearchContext context,
            Duration duration,
            LocatorGroupStrategy defaultLocatorGroupStrategy
    ) {
        this.webDriverReference = requireWebDriverReference(context);
        this.platform = readStringCapability(context, CapabilityType.PLATFORM_NAME);
        this.automation = readStringCapability(context, AUTOMATION_NAME_OPTION);
        this.duration = duration;
        this.defaultLocatorGroupStrategy = requireNonNull(
                defaultLocatorGroupStrategy,
                "Default locator group strategy must not be null"
        );

        defaultElementFieldDecorator = createFieldDecorator(new AppiumElementLocatorFactory(
                context, duration, new DefaultElementByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
        ));
        widgetLocatorFactory = new AppiumElementLocatorFactory(
                context, duration, new WidgetByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
        );
    }

    /**
     * Creates field decorator based on {@link SearchContext} and the default timeout.
     *
     * @param context is an instance of {@link SearchContext}, for example {@link WebDriver},
     *                {@link WebElement} or {@link Widget}.
     */
    public AppiumFieldDecorator(SearchContext context) {
        this(context, DEFAULT_WAITING_TIMEOUT);
    }

    /**
     * Creates field decorator based on {@link SearchContext}, the default timeout,
     * and a custom default {@link LocatorGroupStrategy}.
     *
     * @param context                     is an instance of {@link SearchContext}
     * @param defaultLocatorGroupStrategy is the default strategy used when {@link HowToUseLocators} is not present.
     */
    public AppiumFieldDecorator(SearchContext context, LocatorGroupStrategy defaultLocatorGroupStrategy) {
        this(context, DEFAULT_WAITING_TIMEOUT, defaultLocatorGroupStrategy);
    }

    /**
     * Creates field decorator based on {@link SearchContext} and timeout {@code duration}.
     *
     * @param contextReference  reference to {@link SearchContext}
     *                          It may be the instance of {@link WebDriver} or {@link WebElement} or
     *                          {@link Widget} or some other user's extension/implementation.
     * @param duration is a desired duration of the waiting for an element presence.
     */
    AppiumFieldDecorator(WeakReference<SearchContext> contextReference, Duration duration) {
        this(contextReference, duration, currentDefaultStrategy());
    }

    AppiumFieldDecorator(
            WeakReference<SearchContext> contextReference,
            Duration duration,
            LocatorGroupStrategy defaultLocatorGroupStrategy
    ) {
        var cr = contextReference.get();
        this.webDriverReference = requireWebDriverReference(cr);
        this.platform = readStringCapability(cr, CapabilityType.PLATFORM_NAME);
        this.automation = readStringCapability(cr, AUTOMATION_NAME_OPTION);
        this.duration = duration;
        this.defaultLocatorGroupStrategy = requireNonNull(
                defaultLocatorGroupStrategy,
                "Default locator group strategy must not be null"
        );

        defaultElementFieldDecorator = createFieldDecorator(new AppiumElementLocatorFactory(
                contextReference,
                duration,
                new DefaultElementByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
        ));
        widgetLocatorFactory = new AppiumElementLocatorFactory(
                contextReference,
                duration,
                new WidgetByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
        );
    }

    @NonNull
    private static WeakReference<WebDriver> requireWebDriverReference(SearchContext searchContext) {
        var wd = unpackObjectFromSearchContext(
                checkNotNull(searchContext, "The provided search context cannot be null"),
                WebDriver.class
        );
        return wd.map(WeakReference::new)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(
                                "No driver implementing %s interface could be extracted from the %s instance. "
                                        + "Is the provided search context valid?",
                                WebDriver.class.getName(), searchContext.getClass().getName()
                        )
                ));
    }

    @Nullable
    private String readStringCapability(SearchContext searchContext, String capName) {
        if (searchContext == null) {
            return null;
        }
        return unpackObjectFromSearchContext(searchContext, HasCapabilities.class)
                .map(HasCapabilities::getCapabilities)
                .map(caps -> CapabilityHelpers.getCapability(caps, capName, String.class))
                .orElse(null);
    }

    private DefaultFieldDecorator createFieldDecorator(ElementLocatorFactory factory) {
        return new DefaultFieldDecorator(factory) {
            @Override
            protected WebElement proxyForLocator(ClassLoader ignored, ElementLocator locator) {
                return proxyForAnElement(locator);
            }

            @Override
            protected List<WebElement> proxyForListLocator(ClassLoader ignored, ElementLocator locator) {
                ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
                //noinspection unchecked
                return getEnhancedProxy(ArrayList.class, elementInterceptor);
            }

            @Override
            protected boolean isDecoratableList(Field field) {
                return listElementClassesFor(field).stream()
                        .anyMatch(AVAILABLE_ELEMENT_CLASSES::contains);
            }
        };
    }

    private static List<Class<?>> listElementClassesFor(Field field) {
        FieldDecorationContext context = CURRENT_FIELD_CONTEXT.get();
        if (context != null && context.field.equals(field)) {
            return context.listElementClasses;
        }
        return DefaultFieldDecorator.resolveListElementClasses(field);
    }

    @Nullable
    static Class<? extends Widget> extractWidgetClass(Field field) {
        FieldDecorationContext context = CURRENT_FIELD_CONTEXT.get();
        if (context != null && context.field.equals(field)) {
            return context.widgetClass;
        }
        return resolveWidgetClass(field, DefaultFieldDecorator.resolveListElementClasses(field));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static Class<? extends Widget> resolveWidgetClass(Field field, List<Class<?>> listElementClasses) {
        Class<?> fieldType = field.getType();
        if (Widget.class.isAssignableFrom(fieldType)) {
            return (Class<? extends Widget>) fieldType;
        }
        for (Class<?> candidate : listElementClasses) {
            if (Widget.class.isAssignableFrom(candidate)) {
                return (Class<? extends Widget>) candidate;
            }
        }
        return null;
    }

    private static final class FieldDecorationContext {
        private final Field field;
        private final List<Class<?>> listElementClasses;
        @Nullable
        private final Class<? extends Widget> widgetClass;

        private FieldDecorationContext(Field field) {
            this.field = field;
            this.listElementClasses = DefaultFieldDecorator.resolveListElementClasses(field);
            this.widgetClass = resolveWidgetClass(field, this.listElementClasses);
        }
    }

    /**
     * Decorated page object {@code field}.
     *
     * @param ignored class loader is ignored by current implementation
     * @param field   is {@link Field} of page object which is supposed to be decorated.
     * @return a field value or null.
     */
    public Object decorate(ClassLoader ignored, Field field) {
        LocatorGroupStrategy previous = CURRENT_DEFAULT_STRATEGY.get();
        FieldDecorationContext previousFieldContext = CURRENT_FIELD_CONTEXT.get();
        FieldDecorationContext fieldContext = new FieldDecorationContext(field);
        CURRENT_DEFAULT_STRATEGY.set(defaultLocatorGroupStrategy);
        CURRENT_FIELD_CONTEXT.set(fieldContext);
        try {
            Object result = decorateWidget(field, fieldContext.widgetClass);
            return result == null ? defaultElementFieldDecorator.decorate(ignored, field) : result;
        } finally {
            if (previousFieldContext == null) {
                CURRENT_FIELD_CONTEXT.remove();
            } else {
                CURRENT_FIELD_CONTEXT.set(previousFieldContext);
            }
            if (previous == LocatorGroupStrategy.CHAIN) {
                CURRENT_DEFAULT_STRATEGY.remove();
            } else {
                CURRENT_DEFAULT_STRATEGY.set(previous);
            }
        }
    }

    @Nullable
    private Object decorateWidget(Field field, @Nullable Class<? extends Widget> widgetType) {
        if (widgetType == null) {
            return null;
        }
        boolean isAlist = List.class.isAssignableFrom(field.getType());

        CacheableLocator locator = widgetLocatorFactory.createLocator(field);
        Map<ContentType, Constructor<? extends Widget>> map = OverrideWidgetReader.read(widgetType, field, platform);

        if (isAlist) {
            return getEnhancedProxy(
                    ArrayList.class,
                    new WidgetListInterceptor(
                            locator,
                            webDriverReference,
                            map,
                            widgetType,
                            duration,
                            defaultLocatorGroupStrategy
                    )
            );
        }

        Constructor<? extends Widget> constructor = WidgetConstructorUtil.findConvenientConstructor(widgetType);
        return getEnhancedProxy(
                widgetType,
                new Class[]{constructor.getParameterTypes()[0]},
                new Object[]{proxyForAnElement(locator)},
                new WidgetInterceptor(
                        locator,
                        webDriverReference,
                        null,
                        map,
                        duration,
                        defaultLocatorGroupStrategy
                )
        );
    }

    private WebElement proxyForAnElement(ElementLocator locator) {
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator, webDriverReference);
        return getEnhancedProxy(AppiumWebElement.class, elementInterceptor);
    }
}
