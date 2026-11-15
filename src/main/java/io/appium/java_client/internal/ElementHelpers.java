package io.appium.java_client.internal;

import io.appium.java_client.proxy.HasMethodCallListeners;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/** Internal helpers for unwrapping element and Selenium decorator wrappers. */
public final class ElementHelpers {
    private static final String DECORATED_INTERFACE_NAME =
            "org.openqa.selenium.support.decorators.Decorated";
    private static final int MAX_UNALLOCATED_DEPTH = 4;
    private static final Set<Object> CIRCULAR_SENTINEL = Collections.emptySet();

    private static final ClassValue<DecoratedAccess> DECORATED_ACCESS = new ClassValue<>() {
        @Override
        protected DecoratedAccess computeValue(Class<?> type) {
            Class<?> decoratedInterface = findInInterfaces(
                    type, iface -> DECORATED_INTERFACE_NAME.equals(iface.getName()) ? iface : null
            );
            if (decoratedInterface == null) {
                return DecoratedAccess.NONE;
            }
            Method getOriginal = findMethodOrNull(decoratedInterface, "getOriginal");
            if (getOriginal == null) {
                return DecoratedAccess.NONE;
            }
            return new DecoratedAccess(getOriginal, findMethodOrNull(decoratedInterface, "getDecorator"));
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
        for (int depth = 0; current != null; depth++) {
            visited = recordVisited(current, depth, visited);
            if (visited == CIRCULAR_SENTINEL) {
                break;
            }
            Object next = unwrapSingleElementStep(current);
            if (next == null || next == current) {
                break;
            }
            current = next;
        }
        return current;
    }

    /** Checks whether two references resolve to equal underlying elements.
     * @param first the first element or wrapper
     * @param second the second element or wrapper
     * @return true if both references resolve to equal underlying elements */
    public static boolean sameElement(@Nullable Object first, @Nullable Object second) {
        if (first == second) {
            return first != null;
        }
        if (first == null || second == null) {
            return false;
        }
        Object unwrappedFirst = unwrapElement(first);
        Object unwrappedSecond = unwrapElement(second);
        if (unwrappedFirst == unwrappedSecond) {
            return unwrappedFirst != null;
        }
        if (unwrappedFirst instanceof HasMethodCallListeners
                && !(unwrappedSecond instanceof HasMethodCallListeners)) {
            return unwrappedFirst.equals(unwrappedSecond);
        }
        if (!(unwrappedFirst instanceof HasMethodCallListeners)
                && unwrappedSecond instanceof HasMethodCallListeners) {
            return unwrappedSecond.equals(unwrappedFirst);
        }
        return Objects.equals(unwrappedFirst, unwrappedSecond);
    }

    /** Walks wrapped search contexts in priority order and returns the first non-null match.
     * @param <C> candidate type to match
     * @param <R> extracted result type
     * @param searchContext root search context
     * @param targetType required type on the candidate and any decorated target
     * @param extractor function extracting a value from each matching candidate
     * @return the first non-null extracted value, or empty */
    public static <C, R> Optional<R> findInSearchContextChain(
            @Nullable SearchContext searchContext,
            Class<C> targetType,
            Function<C, @Nullable R> extractor
    ) {
        return Optional.ofNullable(doFindInSearchContextChain(searchContext, targetType, extractor, 0, null));
    }

    /** Resolves a public method callable on the given target class or its public interfaces.
     * @param targetClass the runtime target class
     * @param method the intercepted method
     * @return a public callable method, or null */
    @Nullable
    public static Method resolveTargetMethod(Class<?> targetClass, Method method) {
        if (method.getDeclaringClass().isAssignableFrom(targetClass)
                && Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method interfaceMethod = findInInterfaces(targetClass, iface -> Modifier.isPublic(iface.getModifiers())
                ? findMethodOrNull(iface, method.getName(), parameterTypes)
                : null);
        if (interfaceMethod != null) {
            return interfaceMethod;
        }
        if (Modifier.isPublic(targetClass.getModifiers())) {
            return findMethodOrNull(targetClass, method.getName(), parameterTypes);
        }
        return null;
    }

    @Nullable
    private static Object extractDecoratedOriginal(@Nullable Object candidate) {
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

    @Nullable
    private static SearchContext extractDecoratedDriver(@Nullable Object candidate) {
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

    @Nullable
    private static <C, R> R doFindInSearchContextChain(
            @Nullable SearchContext searchContext,
            Class<C> targetType,
            Function<C, @Nullable R> extractor,
            int depth,
            @Nullable Set<Object> visited
    ) {
        if (searchContext == null) {
            return null;
        }
        Set<Object> activeVisited = recordVisited(searchContext, depth, visited);
        if (activeVisited == CIRCULAR_SENTINEL) {
            return null;
        }
        Object decoratedOriginal = extractDecoratedOriginal(searchContext);
        if (targetType.isInstance(searchContext)
                && (decoratedOriginal == null || targetType.isInstance(decoratedOriginal))) {
            R matched = extractor.apply(targetType.cast(searchContext));
            if (matched != null) {
                return matched;
            }
        }
        int nextDepth = depth + 1;
        if (searchContext instanceof WrapsDriver) {
            WebDriver wrappedDriver = ((WrapsDriver) searchContext).getWrappedDriver();
            if (wrappedDriver != searchContext) {
                R fromDriver = doFindInSearchContextChain(
                        wrappedDriver, targetType, extractor, nextDepth, activeVisited
                );
                if (fromDriver != null) {
                    return fromDriver;
                }
            }
        }
        if (searchContext instanceof WrapsElement) {
            WebElement wrappedElement = ((WrapsElement) searchContext).getWrappedElement();
            if (wrappedElement != searchContext) {
                R fromElement = doFindInSearchContextChain(
                        wrappedElement, targetType, extractor, nextDepth, activeVisited
                );
                if (fromElement != null) {
                    return fromElement;
                }
            }
        }
        if (decoratedOriginal instanceof SearchContext && decoratedOriginal != searchContext) {
            R fromOriginal = doFindInSearchContextChain(
                    (SearchContext) decoratedOriginal, targetType, extractor, nextDepth, activeVisited
            );
            if (fromOriginal != null) {
                return fromOriginal;
            }
        }
        SearchContext decoratedDriver = extractDecoratedDriver(searchContext);
        if (decoratedDriver != null && decoratedDriver != searchContext) {
            return doFindInSearchContextChain(
                    decoratedDriver, targetType, extractor, nextDepth, activeVisited
            );
        }
        return null;
    }

    @Nullable
    private static Set<Object> recordVisited(Object candidate, int depth, @Nullable Set<Object> visited) {
        if (depth < MAX_UNALLOCATED_DEPTH) {
            return visited;
        }
        Set<Object> active = visited != null ? visited : Collections.newSetFromMap(new IdentityHashMap<>());
        return active.add(candidate) ? active : CIRCULAR_SENTINEL;
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
    private static Method findMethodOrNull(Class<?> owner, String name, Class<?>... parameterTypes) {
        try {
            return owner.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Nullable
    private static <R> R findInInterfaces(@Nullable Class<?> type, Function<Class<?>, @Nullable R> mapper) {
        for (Class<?> current = type; current != null && !Object.class.equals(current);
                current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                R found = findOnInterfaceHierarchy(iface, mapper);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Nullable
    private static <R> R findOnInterfaceHierarchy(Class<?> iface, Function<Class<?>, @Nullable R> mapper) {
        R direct = mapper.apply(iface);
        if (direct != null) {
            return direct;
        }
        for (Class<?> parent : iface.getInterfaces()) {
            R found = findOnInterfaceHierarchy(parent, mapper);
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
