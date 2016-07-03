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

package io.appium.java_client.events;


import com.google.common.collect.ImmutableList;

import io.appium.java_client.events.api.Listener;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.Credentials;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Aspect
class DefaultAspect {

    private static final List<Class<?>> listenable = ImmutableList.of(WebDriver.class,
        WebElement.class, WebDriver.Navigation.class, WebDriver.TargetLocator.class,
        ContextAware.class, Alert.class, WebDriver.Options.class, WebDriver.Window.class);
    
    private final AbstractApplicationContext context;
    private final WebDriver driver;
    final DefaultListener listener = new DefaultListener();

    private static Throwable getRootCause(Throwable thrown) {
        Class<? extends Throwable> throwableClass = thrown.getClass();

        if (!InvocationTargetException.class.equals(throwableClass) && !RuntimeException.class.equals(throwableClass)) {
            return thrown;
        }
        if (thrown.getCause() != null) {
            return getRootCause(thrown.getCause());
        }
        return thrown;
    }

    private static Class<?> getClassForProxy(Class<?> classOfObject) {
        for (Class<?> c : listenable) {
            if (!c.isAssignableFrom(classOfObject)) {
                continue;
            }
            return c;
        }
        return null;
    }

    DefaultAspect(AbstractApplicationContext context, WebDriver driver) {
        this.context = context;
        this.driver = driver;
    }

    private Object transformToListenable(Object result) {
        if (result == null) {
            return result;
        }

        if (getClassForProxy(result.getClass()) != null) {
            result = context.getBean(DefaultBeanConfiguration.COMPONENT_BEAN, result);
        }
        return result;
    }

    private List<Object> returnProxyList(List<Object> originalList) {
        try {
            List<Object> proxyList = new ArrayList<>();
            for (Object o : originalList) {
                if (getClassForProxy(o.getClass()) == null) {
                    proxyList.add(o);
                } else {
                    proxyList.add(context.getBean(DefaultBeanConfiguration.COMPONENT_BEAN, o));
                }
            }
            return proxyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    void add(List<Listener> listeners) {
        listener.add(listeners);
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Navigation.get(..))  || "
        + "execution(* org.openqa.selenium.WebDriver.Navigation.to(..))")
    public void beforeNavigateTo(JoinPoint joinPoint) throws Throwable {
        try {
            String url = String.valueOf(joinPoint.getArgs()[0]);
            listener.beforeNavigateTo(url, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Navigation.get(..))  || "
        + "execution(* org.openqa.selenium.WebDriver.Navigation.to(..))")
    public void afterNavigateTo(JoinPoint joinPoint)  throws Throwable {
        try {
            String url = String.valueOf(joinPoint.getArgs()[0]);
            listener.afterNavigateTo(url, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Navigation.back(..))")
    public void beforeNavigateBack(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeNavigateBack(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Navigation.back(..))")
    public void afterNavigateBack(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateBack(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Navigation.forward(..))")
    public void beforeNavigateForward(JoinPoint joinPoint)  throws Throwable {
        try {
            listener.beforeNavigateForward(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Navigation.forward(..))")
    public void afterNavigateForward(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateForward(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Navigation.refresh(..))")
    public void beforeNavigateRefresh(JoinPoint joinPoint)  throws Throwable {
        try {
            listener.beforeNavigateRefresh(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Navigation.refresh(..))")
    public void afterNavigateRefresh(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateRefresh(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.SearchContext.findElement(..)) || "
        + "execution(* org.openqa.selenium.SearchContext.findElements(..))")
    public void beforeFindBy(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            By by = (By) joinPoint.getArgs()[0];
            if (!WebElement.class.isAssignableFrom(target.getClass())) {
                listener.beforeFindBy(by, null, driver);
            } else {
                listener.beforeFindBy(by, (WebElement) target, driver);
            }
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.SearchContext.findElement(..)) || "
        + "execution(* org.openqa.selenium.SearchContext.findElements(..))")
    public void afterFindBy(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            By by = (By) joinPoint.getArgs()[0];
            if (!WebElement.class.isAssignableFrom(target.getClass())) {
                listener.afterFindBy(by, null, driver);
            } else {
                listener.afterFindBy(by, (WebElement) target, driver);
            }
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebElement.click(..))")
    public void beforeClickOn(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.beforeClickOn((WebElement) target, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebElement.click(..))")
    public void afterClickOn(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.afterClickOn((WebElement) target, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebElement.sendKeys(..)) || "
        + "execution(* org.openqa.selenium.WebElement.clear(..))  || "
        + "execution(* io.appium.java_client.android.AndroidElement.replaceValue(..))  || "
        + "execution(* io.appium.java_client.ios.IOSElement.setValue(..))")
    public void beforeChangeValueOf(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.beforeChangeValueOf((WebElement) target, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebElement.sendKeys(..)) || "
        + "execution(* org.openqa.selenium.WebElement.clear(..))  || "
        + "execution(* io.appium.java_client.android.AndroidElement.replaceValue(..))  || "
        + "execution(* io.appium.java_client.ios.IOSElement.setValue(..))")
    public void afterChangeValueOf(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.afterChangeValueOf((WebElement) target, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.JavascriptExecutor.executeScript(..)) || "
        + "execution(* org.openqa.selenium.JavascriptExecutor.executeAsyncScript(..))")
    public void beforeScript(JoinPoint joinPoint) throws Throwable {
        try {
            String script = String.valueOf(joinPoint.getArgs()[0]);
            listener.beforeScript(script, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.JavascriptExecutor.executeScript(..)) || "
        + "execution(* org.openqa.selenium.JavascriptExecutor.executeAsyncScript(..))")
    public void afterScript(JoinPoint joinPoint) throws Throwable {
        try {
            String script = String.valueOf(joinPoint.getArgs()[0]);
            listener.afterScript(script, driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.Alert.accept(..))")
    public void beforeAlertAccept(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.beforeAlertAccept(driver, (Alert) target);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.Alert.accept(..))")
    public void afterAlertAccept(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.afterAlertAccept(driver, (Alert) target);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.Alert.dismiss(..))")
    public void beforeAlertDismiss(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.beforeAlertDismiss(driver, (Alert) target);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.Alert.dismiss(..))")
    public void afterAlertDismiss(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            listener.afterAlertDismiss(driver, (Alert) target);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.Alert.sendKeys(..))")
    public void beforeAlertSendKeys(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            String keys = String.valueOf(joinPoint.getArgs()[0]);
            listener.beforeAlertSendKeys(driver, (Alert) target, keys);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.Alert.sendKeys(..))")
    public void afterAlertSendKeys(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            String keys = String.valueOf(joinPoint.getArgs()[0]);
            listener.afterAlertSendKeys(driver, (Alert) target, keys);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.Alert.setCredentials(..)) || "
        + "execution(* org.openqa.selenium.Alert.authenticateUsing(..))")
    public void beforeAlertAuthentication(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            Credentials credentials = (Credentials) joinPoint.getArgs()[0];
            listener.beforeAuthentication(driver, (Alert) target, credentials);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.Alert.setCredentials(..)) || "
        + "execution(* org.openqa.selenium.Alert.authenticateUsing(..))")
    public void afterAlertAuthentication(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            Credentials credentials = (Credentials) joinPoint.getArgs()[0];
            listener.afterAuthentication(driver, (Alert) target, credentials);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Window.setSize(..))")
    public void beforeWindowIsResized(JoinPoint joinPoint) throws Throwable {
        try {
            WebDriver.Window window = (WebDriver.Window) joinPoint.getTarget();
            Dimension dimension = (Dimension) joinPoint.getArgs()[0];
            listener.beforeWindowChangeSize(driver, window, dimension);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Window.setSize(..))")
    public void afterWindowIsResized(JoinPoint joinPoint) throws Throwable {
        try {
            WebDriver.Window window = (WebDriver.Window) joinPoint.getTarget();
            Dimension dimension = (Dimension) joinPoint.getArgs()[0];
            listener.afterWindowChangeSize(driver, window, dimension);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.WebDriver.Window.setPosition(..))")
    public void beforeWindowIsMoved(JoinPoint joinPoint) throws Throwable {
        try {
            WebDriver.Window window = (WebDriver.Window) joinPoint.getTarget();
            Point point = (Point) joinPoint.getArgs()[0];
            listener.beforeWindowIsMoved(driver, window, point);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After("execution(* org.openqa.selenium.WebDriver.Window.setPosition(..))")
    public void afterWindowIsMoved(JoinPoint joinPoint) throws Throwable {
        try {
            WebDriver.Window window = (WebDriver.Window) joinPoint.getTarget();
            Point point = (Point) joinPoint.getArgs()[0];
            listener.afterWindowIsMoved(driver, window, point);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before("execution(* org.openqa.selenium.Rotatable.rotate(..))")
    public void beforeRotation(JoinPoint joinPoint) throws Throwable {
        try {
            ScreenOrientation orientation = (ScreenOrientation) joinPoint.getArgs()[0];
            listener.beforeRotation(driver, orientation);
        } catch (Throwable t) {
            throw getRootCause(t);
        }

    }

    @After("execution(* org.openqa.selenium.Rotatable.rotate(..))")
    public void afterRotation(JoinPoint joinPoint) throws Throwable {
        try {
            ScreenOrientation orientation = (ScreenOrientation) joinPoint.getArgs()[0];
            listener.afterRotation(driver, orientation);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Around("execution(* org.openqa.selenium.WebDriver.*(..)) || "
        + "execution(* org.openqa.selenium.WebElement.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.Navigation.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.Options.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
        + "execution(* org.openqa.selenium.JavascriptExecutor.*(..)) || "
        + "execution(* org.openqa.selenium.ContextAware.*(..)) || "
        + "execution(* io.appium.java_client.FindsByAccessibilityId.*(..)) || "
        + "execution(* io.appium.java_client.FindsByAndroidUIAutomator.*(..)) || "
        + "execution(* io.appium.java_client.FindsByIosUIAutomation.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByClassName.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByCssSelector.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsById.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByLinkText.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByName.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByTagName.*(..)) || "
        + "execution(* org.openqa.selenium.internal.FindsByXPath.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.Window.*(..)) || "
        + "execution(* io.appium.java_client.android.AndroidElement.*(..)) || "
        + "execution(* io.appium.java_client.ios.IOSElement.*(..)) || "
        + "execution(* org.openqa.selenium.Alert.*(..))")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Throwable t = null;
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            t = e;
        }
        if (t != null) {
            Throwable rootCause = getRootCause(t);
            listener.onException(rootCause, driver);
            throw rootCause;
        }

        if (result == null) { // maybe it was "void"
            return result;
        }
        if (List.class.isAssignableFrom(result.getClass())) {
            return returnProxyList((List<Object>) result);
        }

        return transformToListenable(result);
    }
}
