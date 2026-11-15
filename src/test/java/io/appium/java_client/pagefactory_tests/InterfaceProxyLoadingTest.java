package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.proxy.Helpers;
import io.appium.java_client.proxy.MethodCallListener;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.Callable;

import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedInterfaceProxy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InterfaceProxyLoadingTest {

    public interface Probe {
        String ping();
    }

    @Test
    void reusesOneProxyClassForTheSameInterface() {
        MethodCallListener listener = new MethodCallListener() {
        };
        Object first = getEnhancedInterfaceProxy(Probe.class, listener);
        Object second = getEnhancedInterfaceProxy(Probe.class, listener);

        assertSame(first.getClass(), second.getClass());
    }

    @Test
    void cacheKeyIsNotTheInterfaceClass() throws Exception {
        getEnhancedInterfaceProxy(Probe.class, new MethodCallListener() {
        });
        Field cacheField = Helpers.class.getDeclaredField("CACHED_PROXY_CLASSES");
        cacheField.setAccessible(true);
        Map<?, ?> cache = (Map<?, ?>) cacheField.get(null);

        assertTrue(cache.keySet().stream().noneMatch(key -> key instanceof Class));
        assertThrows(NoSuchFieldException.class, () -> Class
                .forName("io.appium.java_client.pagefactory.utils.ProxyFactory")
                .getDeclaredField("CACHED_INTERFACE_PROXIES"));
    }

    @Test
    void proxiesPublicInterfaceLoadedByAChildOfTheLibraryLoader() throws Exception {
        ClassLoader child = new URLClassLoader(new URL[0], Helpers.class.getClassLoader());
        Class<?> iface = new ByteBuddy()
                .makeInterface()
                .name("io.appium.java_client.pagefactory_tests.ChildProbe")
                .defineMethod("ping", String.class, Visibility.PUBLIC)
                .withoutCode()
                .make()
                .load(child, ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        Object proxy = getEnhancedInterfaceProxy(iface, new MethodCallListener() {
            @Override
            public Object call(Object obj, Method method, Object[] args, Callable<?> original) {
                return "ping".equals(method.getName()) ? "pong" : MethodCallListener.UNSET;
            }
        });

        assertEquals("pong", iface.getMethod("ping").invoke(proxy));
    }

    @Test
    void rejectsInterfaceWhoseLoaderCannotSeeTheLibrary() throws Exception {
        ClassLoader isolated = new URLClassLoader(new URL[0], ClassLoader.getPlatformClassLoader());
        Class<?> iface = new ByteBuddy()
                .makeInterface()
                .name("io.appium.java_client.pagefactory_tests.IsolatedProbe")
                .defineMethod("ping", String.class, Visibility.PUBLIC)
                .withoutCode()
                .make()
                .load(isolated, ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> getEnhancedInterfaceProxy(iface, new MethodCallListener() {
                })
        );
        assertTrue(error.getMessage().contains("class loader"));
        assertNull(error.getCause());
    }
}
