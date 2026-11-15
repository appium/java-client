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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.remote.CapabilityType;
import io.appium.java_client.support.PageFactory;
import io.appium.java_client.support.pagefactory.ByAll;
import io.appium.java_client.support.pagefactory.ByChained;
import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocator;
import io.appium.java_client.support.pagefactory.FieldDecorator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.internal.Preconditions.checkNotNull;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedInterfaceProxy;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackObjectFromSearchContext;
import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static java.time.Duration.ofSeconds;
import static java.util.Objects.requireNonNull;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields, 2) List of WebElement that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory, and 3) non-static, non-final,
 * uninitialized {@link By} / {@link AppiumBy} fields annotated with Appium or Selenium
 * locator annotations (pre-initialized {@link By} fields are preserved when initialized via
 * {@link PageFactory#initElements}). When a field is declared with a narrow {@link By} subtype
 * (such as {@link AppiumBy}) and the built locator cannot be coerced to that subtype,
 * a warning is logged and the field is left {@code null}.
 * Please pay attention: fields of {@link WebElement}, {@link AppiumWebElement},
 * {@link By}, or {@link AppiumBy} are supported by this decorator.
 */
public class AppiumFieldDecorator implements FieldDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(AppiumFieldDecorator.class);

    private static final List<Class<? extends WebElement>> AVAILABLE_ELEMENT_CLASSES = List.of(
            WebElement.class,
            AppiumWebElement.class
    );
    /** The default timeout of waiting for an element presence. */
    public static final Duration DEFAULT_WAITING_TIMEOUT = ofSeconds(1);
    private static final ThreadLocal<LocatorGroupStrategy> CURRENT_DEFAULT_STRATEGY =
            ThreadLocal.withInitial(() -> LocatorGroupStrategy.CHAIN);
    private final WeakReference<WebDriver> webDriverReference;
    private final ElementFieldDecorator defaultElementFieldDecorator;
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

        defaultElementFieldDecorator = new ElementFieldDecorator(
                new AppiumElementLocatorFactory(
                        context,
                        duration,
                        new DefaultElementByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
                ),
                this.webDriverReference
        );
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

        defaultElementFieldDecorator = new ElementFieldDecorator(
                new AppiumElementLocatorFactory(
                        contextReference,
                        duration,
                        new DefaultElementByBuilder(platform, automation, this.defaultLocatorGroupStrategy)
                ),
                this.webDriverReference
        );
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

    private static final class ElementFieldDecorator extends DefaultFieldDecorator {
        private final AppiumElementLocatorFactory locatorFactory;
        private final WeakReference<WebDriver> webDriverReference;

        private ElementFieldDecorator(
                AppiumElementLocatorFactory factory,
                WeakReference<WebDriver> webDriverReference
        ) {
            super(factory);
            this.locatorFactory = factory;
            this.webDriverReference = webDriverReference;
        }

        private static List<Class<?>> resolveSingleFieldClasses(Field field, @Nullable Class<?> pageClass) {
            return resolveFieldClasses(field, pageClass);
        }

        private static List<Class<?>> resolveListClasses(Field field, @Nullable Class<?> pageClass) {
            return resolveListElementClasses(field, pageClass);
        }

        @Override
        protected WebElement proxyForLocator(ClassLoader ignored, ElementLocator locator) {
            return proxyForAnElement(locator, webDriverReference);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected List<WebElement> proxyForListLocator(ClassLoader ignored, ElementLocator locator) {
            ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
            return getEnhancedProxy(ArrayList.class, elementInterceptor);
        }

        @Override
        protected boolean isDecoratableList(Field field) {
            return resolveListClasses(field, PageFactory.getCurrentPageClass()).stream()
                    .anyMatch(AVAILABLE_ELEMENT_CLASSES::contains);
        }

        @Nullable
        private Object decorateElement(
                Field field,
                @Nullable Class<?> pageClass,
                List<Class<?>> fieldClasses,
                List<Class<?>> listElementClasses
        ) {
            boolean isElement = !List.class.isAssignableFrom(field.getType())
                    && (WebElement.class.isAssignableFrom(field.getType())
                            || fieldClasses.stream().anyMatch(AVAILABLE_ELEMENT_CLASSES::contains));
            boolean isList = !isElement
                    && listElementClasses.stream().anyMatch(AVAILABLE_ELEMENT_CLASSES::contains);
            if (!(isElement || isList)) {
                return null;
            }

            ElementLocator locator = locatorFactory.createLocator(field, pageClass, null);
            if (locator == null) {
                return null;
            }

            if (isElement) {
                return proxyForLocator(null, locator);
            }
            return proxyForListLocator(null, locator);
        }
    }

    @Nullable
    static Class<? extends Widget> extractWidgetClass(Field field) {
        return extractWidgetClass(field, PageFactory.getCurrentPageClass());
    }

    @Nullable
    static Class<? extends Widget> extractWidgetClass(Field field, @Nullable Class<?> pageClass) {
        return resolveWidgetClass(
                field,
                ElementFieldDecorator.resolveSingleFieldClasses(field, pageClass),
                ElementFieldDecorator.resolveListClasses(field, pageClass)
        );
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static Class<? extends Widget> resolveWidgetClass(
            Field field,
            List<Class<?>> fieldClasses,
            List<Class<?>> listElementClasses
    ) {
        List<Class<?>> candidates = List.class.isAssignableFrom(field.getType())
                ? listElementClasses
                : fieldClasses;
        for (Class<?> candidate : candidates) {
            if (Widget.class.isAssignableFrom(candidate)) {
                return (Class<? extends Widget>) candidate;
            }
        }
        return null;
    }

    /**
     * Widget class for a field, including an {@code @OverrideWidget} interface.
     *
     * @param field the page object field
     * @return the widget class or interface, or null
     */
    @Nullable
    static Class<?> declaredWidgetType(Field field) {
        Class<?> pageClass = PageFactory.getCurrentPageClass();
        return declaredWidgetType(
                field,
                ElementFieldDecorator.resolveSingleFieldClasses(field, pageClass),
                ElementFieldDecorator.resolveListClasses(field, pageClass)
        );
    }

    @Nullable
    private static Class<?> declaredWidgetType(
            Field field,
            List<Class<?>> fieldClasses,
            List<Class<?>> listElementClasses
    ) {
        Class<? extends Widget> widgetClass = resolveWidgetClass(field, fieldClasses, listElementClasses);
        if (widgetClass != null) {
            return widgetClass;
        }
        List<Class<?>> candidates = List.class.isAssignableFrom(field.getType())
                ? listElementClasses
                : fieldClasses;
        for (Class<?> candidate : candidates) {
            if (isOverriddenWidgetInterface(candidate, field)) {
                return candidate;
            }
        }
        return null;
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
        CURRENT_DEFAULT_STRATEGY.set(defaultLocatorGroupStrategy);
        try {
            Class<?> pageClass = PageFactory.getCurrentPageClass();
            List<Class<?>> fieldClasses = ElementFieldDecorator.resolveSingleFieldClasses(field, pageClass);
            List<Class<?>> listElementClasses = ElementFieldDecorator.resolveListClasses(field, pageClass);
            Class<?> widgetClass = declaredWidgetType(field, fieldClasses, listElementClasses);
            Object result = decorateWidget(field, widgetClass, pageClass);
            if (result == null) {
                result = decorateBy(field, pageClass);
            }
            return result == null
                    ? defaultElementFieldDecorator.decorateElement(field, pageClass, fieldClasses, listElementClasses)
                    : result;
        } finally {
            if (previous == LocatorGroupStrategy.CHAIN) {
                CURRENT_DEFAULT_STRATEGY.remove();
            } else {
                CURRENT_DEFAULT_STRATEGY.set(previous);
            }
        }
    }

    @Nullable
    private Object decorateBy(Field field, @Nullable Class<?> pageClass) {
        Class<?> type = field.getType();
        int modifiers = field.getModifiers();
        if (!By.class.isAssignableFrom(type)
                || Modifier.isStatic(modifiers)
                || Modifier.isFinal(modifiers)
                || PageFactory.isCurrentPageFieldInitialized(field)) {
            return null;
        }

        DefaultElementByBuilder builder = new DefaultElementByBuilder(
                platform, automation, defaultLocatorGroupStrategy
        );
        builder.setAnnotated(field);
        builder.setDecorationContext(pageClass, null);
        builder.assertValidAnnotations();

        By mobileNativeBy = builder.buildMobileNativeBy();
        By defaultBy = builder.buildDefaultBy();
        if (mobileNativeBy == null && defaultBy == null) {
            return null;
        }
        if (type.isAssignableFrom(ContentMappedBy.class)) {
            return builder.buildMappedBy(defaultBy, mobileNativeBy);
        }

        By result = coerceToType(type, mobileNativeBy);
        if (result == null) {
            result = coerceToType(type, defaultBy);
        }
        if (result == null) {
            By built = mobileNativeBy != null ? unwrapSingleBy(mobileNativeBy) : unwrapSingleBy(defaultBy);
            LOG.warn(
                    "Cannot decorate field '{}.{}' of narrow type {}: built locator {} ({}) is not assignable to {}",
                    field.getDeclaringClass().getName(),
                    field.getName(),
                    type.getName(),
                    built,
                    built != null ? built.getClass().getName() : "null",
                    type.getName()
            );
        }
        return result;
    }

    @Nullable
    private static By coerceToType(Class<?> type, @Nullable By candidate) {
        if (candidate == null) {
            return null;
        }
        if (type.isInstance(candidate)) {
            return candidate;
        }
        By unwrapped = unwrapSingleBy(candidate);
        return type.isInstance(unwrapped) ? unwrapped : null;
    }

    @Nullable
    private static By unwrapSingleBy(@Nullable By by) {
        By current = by;
        while (true) {
            By[] bys;
            if (current instanceof ByChained) {
                bys = ((ByChained) current).getBys();
            } else if (current instanceof ByAll) {
                bys = ((ByAll) current).getBys();
            } else {
                break;
            }
            if (bys.length != 1) {
                break;
            }
            current = bys[0];
        }
        return current;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Object decorateWidget(
            Field field,
            @Nullable Class<?> widgetType,
            @Nullable Class<?> pageClass
    ) {
        if (widgetType == null) {
            return null;
        }
        boolean isAlist = List.class.isAssignableFrom(field.getType());
        Class<? extends Widget> locatorType = widgetType.isInterface()
                ? null
                : (Class<? extends Widget>) widgetType;

        CacheableLocator locator = widgetLocatorFactory.createLocator(field, pageClass, locatorType);
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

        WidgetInterceptor interceptor = new WidgetInterceptor(
                locator,
                webDriverReference,
                null,
                map,
                duration,
                defaultLocatorGroupStrategy
        );
        if (widgetType.isInterface()) {
            return getEnhancedInterfaceProxy(widgetType, interceptor);
        }

        //noinspection unchecked
        Class<? extends Widget> widgetClass = (Class<? extends Widget>) widgetType;
        Constructor<? extends Widget> constructor = WidgetConstructorUtil.findConvenientConstructor(widgetClass);
        return getEnhancedProxy(
                widgetClass,
                new Class[]{constructor.getParameterTypes()[0]},
                new Object[]{proxyForAnElement(locator, webDriverReference)},
                interceptor
        );
    }

    /**
     * True when {@code type} is a widget contract rather than a {@link WebElement}.
     * {@code @OverrideWidget} on an element field keeps the normal element proxy.
     *
     * @param type the field type, or the list element type
     * @param field the annotated field
     * @return whether the type should be decorated as an interface widget
     */
    static boolean isOverriddenWidgetInterface(Class<?> type, Field field) {
        return type.isInterface()
                && !WebElement.class.isAssignableFrom(type)
                && field.isAnnotationPresent(OverrideWidget.class);
    }

    private static WebElement proxyForAnElement(
            ElementLocator locator,
            WeakReference<WebDriver> webDriverReference
    ) {
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator, webDriverReference);
        return getEnhancedProxy(AppiumWebElement.class, elementInterceptor);
    }
}
