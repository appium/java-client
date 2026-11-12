package io.appium.java_client.support.pagefactory;

import io.appium.java_client.support.AbstractFindByBuilder;
import io.appium.java_client.support.ByIdOrName;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.PageFactoryFinder;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/** Processes locator and cache annotations on a page object field. */
public class Annotations extends AbstractAnnotations {
    private static final String SELENIUM_SUPPORT_PACKAGE = "org.openqa.selenium.support.";
    private static final String SELENIUM_PAGE_FACTORY_FINDER = SELENIUM_SUPPORT_PACKAGE + "PageFactoryFinder";
    private static final String SELENIUM_CACHE_LOOKUP = SELENIUM_SUPPORT_PACKAGE + "CacheLookup";
    private static final String SELENIUM_FIND_BY = SELENIUM_SUPPORT_PACKAGE + "FindBy";
    private static final String SELENIUM_FIND_BYS = SELENIUM_SUPPORT_PACKAGE + "FindBys";
    private static final String SELENIUM_FIND_ALL = SELENIUM_SUPPORT_PACKAGE + "FindAll";

    private final Field field;

    /** Creates an annotation processor for the given field.
     * @param field the field to inspect */
    public Annotations(Field field) {
        this.field = requireNonNull(field, "Field must not be null");
    }

    @Override
    public boolean isLookupCached() {
        if (field.getAnnotation(CacheLookup.class) != null) {
            return true;
        }
        for (Annotation annotation : field.getAnnotations()) {
            if (SELENIUM_CACHE_LOOKUP.equals(annotation.annotationType().getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public By buildBy() {
        assertValidAnnotations();

        By ans = null;
        List<Annotation> finders = collectFinderAnnotations(field);
        if (!finders.isEmpty()) {
            ans = buildFromFinder(finders.get(0), field);
        }
        if (ans == null) {
            ans = buildByFromDefault();
        }
        return ans;
    }

    /** Gets the annotated field handled by this instance.
     * @return the field */
    protected Field getField() {
        return field;
    }

    /** Builds the fallback locator when no locator annotation produces one.
     * @return a locator using the field name */
    protected By buildByFromDefault() {
        return new ByIdOrName(field.getName());
    }

    /** Verifies that mutually exclusive locator annotations are not combined on the field. */
    protected void assertValidAnnotations() {
        List<Annotation> finders = collectFinderAnnotations(field);
        if (finders.size() > 1) {
            Annotation first = finders.get(0);
            Annotation second = finders.get(1);
            throw new IllegalArgumentException(
                    "If you use a '@" + first.annotationType().getSimpleName() + "' annotation, "
                            + "you must not also use a '@" + second.annotationType().getSimpleName()
                            + "' annotation"
            );
        }
    }

    static boolean hasLocatorAnnotation(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(PageFactoryFinder.class)
                    || findLegacyFinderMeta(annotationType) != null) {
                return true;
            }
        }
        return false;
    }

    private static List<Annotation> collectFinderAnnotations(Field field) {
        List<Annotation> finders = new ArrayList<>();
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(PageFactoryFinder.class)) {
                finders.add(annotation);
            } else if (findLegacyFinderMeta(annotationType) != null
                    && !isSupersededByAppiumCounterpart(field, annotationType)) {
                finders.add(annotation);
            }
        }
        return finders;
    }

    private static boolean isSupersededByAppiumCounterpart(
            Field field,
            Class<? extends Annotation> annotationType
    ) {
        String name = annotationType.getName();
        if (SELENIUM_FIND_BY.equals(name)) {
            return field.getAnnotation(FindBy.class) != null;
        }
        if (SELENIUM_FIND_BYS.equals(name)) {
            return field.getAnnotation(FindBys.class) != null;
        }
        if (SELENIUM_FIND_ALL.equals(name)) {
            return field.getAnnotation(FindAll.class) != null;
        }
        return false;
    }

    @Nullable
    private static Annotation findLegacyFinderMeta(Class<? extends Annotation> annotationType) {
        for (Annotation meta : annotationType.getAnnotations()) {
            if (SELENIUM_PAGE_FACTORY_FINDER.equals(meta.annotationType().getName())) {
                return meta;
            }
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static By buildFromFinder(Annotation annotation, Field field) {
        PageFactoryFinder finder = annotation.annotationType().getAnnotation(PageFactoryFinder.class);
        if (finder != null) {
            Class<? extends AbstractFindByBuilder<?>> builderClass = finder.value();
            AbstractFindByBuilder<Annotation> builder =
                    (AbstractFindByBuilder<Annotation>) instantiateBuilder(builderClass);
            return builder.buildIt(annotation, field);
        }

        Annotation legacyFinder = requireNonNull(findLegacyFinderMeta(annotation.annotationType()));
        return invokeLegacyBuilder(legacyFinder, annotation, field);
    }

    private static <B> B instantiateBuilder(Class<B> builderClass) {
        try {
            Constructor<B> constructor = builderClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw rethrowUnchecked(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Cannot instantiate PageFactoryFinder builder " + builderClass.getName(), e
            );
        }
    }

    @Nullable
    private static By invokeLegacyBuilder(Annotation legacyFinder, Annotation annotation, Field field) {
        Class<?> builderClass;
        try {
            builderClass = (Class<?>) legacyFinder.annotationType().getMethod("value").invoke(legacyFinder);
        } catch (InvocationTargetException e) {
            throw rethrowUnchecked(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Cannot read builder class from " + legacyFinder.annotationType().getName(), e
            );
        }

        Object builder = instantiateBuilder(builderClass);
        try {
            Method buildIt = builderClass.getMethod("buildIt", Object.class, Field.class);
            buildIt.setAccessible(true);
            return (By) buildIt.invoke(builder, annotation, field);
        } catch (InvocationTargetException e) {
            throw rethrowUnchecked(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Cannot invoke buildIt on PageFactoryFinder builder " + builderClass.getName(), e
            );
        }
    }

    private static RuntimeException rethrowUnchecked(@Nullable Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }
        if (cause instanceof Error) {
            throw (Error) cause;
        }
        return new IllegalStateException(cause);
    }
}
