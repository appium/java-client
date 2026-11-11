package io.appium.java_client.internal;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/** Internal helpers for unwrapping element and Selenium decorator wrappers. */
public final class ElementHelpers {
    private static final String DECORATED_INTERFACE_NAME =
            "org.openqa.selenium.support.decorators.Decorated";
    private static final int MAX_UNALLOCATED_DEPTH = 4;

    private static final ClassValue<DecoratedAccess> DECORATED_ACCESS = new ClassValue<>() {
        @Override
        protected DecoratedAccess computeValue(Class<?> type) {
            Class<?> decoratedInterface = findDecoratedInterface(type);
            if (decoratedInterface == null) {
                return DecoratedAccess.NONE;
            }
            try {
                Method getOriginal = decoratedInterface.getMethod("getOriginal");
                Method getDecorator = findMethodOrNull(decoratedInterface, "getDecorator");
                return new DecoratedAccess(getOriginal, getDecorator);
            } catch (NoSuchMethodException e) {
                return DecoratedAccess.NONE;
            }
        }
    };

    private static final ClassValue<MethodHolder> DECORATED_DRIVER_METHOD = new ClassValue<>() {
        @Override
        protected MethodHolder computeValue(Class<?> type) {
            return new MethodHolder(findMethodOrNull(type, "getDecoratedDriver"));
        }
    };

    private ElementHelpers() {
    }

    /** Unwraps nested element and Selenium decorator wrappers.
     * @param element the candidate object to unwrap
     * @return the innermost unwrapped object, or null */
    @Nullable
    public static Object unwrapElement(@Nullable Object element) {
        Object current = element;
        Set<Object> visited = null;
        int depth = 0;
        while (current != null) {
            if (depth >= MAX_UNALLOCATED_DEPTH) {
                if (visited == null) {
                    visited = Collections.newSetFromMap(new IdentityHashMap<>());
                    recordPrefix(element, depth, visited);
                }
                if (!visited.add(current)) {
                    break;
                }
            }
            Object next = unwrapSingleElementStep(current);
            if (next == null || next == current) {
                break;
            }
            current = next;
            depth++;
        }
        return current;
    }

    /** Extracts the immediate target wrapped by a Selenium decorator proxy.
     * @param candidate the object to inspect
     * @return the wrapped target, or null */
    @Nullable
    public static Object extractDecoratedOriginal(@Nullable Object candidate) {
        if (candidate == null) {
            return null;
        }
        Method getOriginal = DECORATED_ACCESS.get(candidate.getClass()).getOriginal;
        if (getOriginal == null) {
            return null;
        }
        try {
            return getOriginal.invoke(candidate);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    /** Extracts the decorated driver search context from a Selenium decorator proxy.
     * @param candidate the object to inspect
     * @return the decorated driver search context, or null */
    @Nullable
    public static SearchContext extractDecoratedDriver(@Nullable Object candidate) {
        if (candidate == null) {
            return null;
        }
        Method getDecorator = DECORATED_ACCESS.get(candidate.getClass()).getDecorator;
        if (getDecorator == null) {
            return null;
        }
        try {
            Object decorator = getDecorator.invoke(candidate);
            if (decorator == null) {
                return null;
            }
            Method getDecoratedDriver = DECORATED_DRIVER_METHOD.get(decorator.getClass()).method;
            if (getDecoratedDriver == null) {
                return null;
            }
            Object decoratedDriver = getDecoratedDriver.invoke(decorator);
            if (decoratedDriver instanceof SearchContext) {
                return (SearchContext) decoratedDriver;
            }
            if (decoratedDriver != null) {
                Object originalDriver = extractDecoratedOriginal(decoratedDriver);
                if (originalDriver instanceof SearchContext) {
                    return (SearchContext) originalDriver;
                }
            }
        } catch (ReflectiveOperationException e) {
            return null;
        }
        return null;
    }

    private static void recordPrefix(@Nullable Object start, int steps, Set<Object> visited) {
        Object cursor = start;
        for (int i = 0; i < steps && cursor != null; i++) {
            visited.add(cursor);
            cursor = unwrapSingleElementStep(cursor);
        }
    }

    @Nullable
    private static Object unwrapSingleElementStep(Object current) {
        if (current instanceof WrapsElement) {
            WebElement wrapped = ((WrapsElement) current).getWrappedElement();
            if (wrapped != null && wrapped != current) {
                return wrapped;
            }
        }
        Object decoratedOriginal = extractDecoratedOriginal(current);
        if (decoratedOriginal instanceof WebElement && decoratedOriginal != current) {
            return decoratedOriginal;
        }
        return null;
    }

    @Nullable
    private static Method findMethodOrNull(Class<?> owner, String name) {
        try {
            return owner.getMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Nullable
    private static Class<?> findDecoratedInterface(@Nullable Class<?> type) {
        for (Class<?> current = type; current != null && !Object.class.equals(current);
                current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                Class<?> found = findDecoratedOnInterfaceHierarchy(iface);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Nullable
    private static Class<?> findDecoratedOnInterfaceHierarchy(Class<?> iface) {
        if (DECORATED_INTERFACE_NAME.equals(iface.getName())) {
            return iface;
        }
        for (Class<?> parent : iface.getInterfaces()) {
            Class<?> found = findDecoratedOnInterfaceHierarchy(parent);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private static final class DecoratedAccess {
        private static final DecoratedAccess NONE = new DecoratedAccess(null, null);

        @Nullable
        private final Method getOriginal;
        @Nullable
        private final Method getDecorator;

        private DecoratedAccess(@Nullable Method getOriginal, @Nullable Method getDecorator) {
            this.getOriginal = getOriginal;
            this.getDecorator = getDecorator;
        }
    }

    private static final class MethodHolder {
        @Nullable
        private final Method method;

        private MethodHolder(@Nullable Method method) {
            this.method = method;
        }
    }
}
