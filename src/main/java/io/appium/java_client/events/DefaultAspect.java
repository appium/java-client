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

import static io.appium.java_client.events.DefaultBeanConfiguration.COMPONENT_BEAN;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.events.api.Listener;
import io.appium.java_client.selenium.ContextAware;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
@Aspect
class DefaultAspect {

    private static final List<Class<?>> listenable = ImmutableList.of(WebDriver.class,
        WebElement.class, WebDriver.Navigation.class, WebDriver.TargetLocator.class,
        ContextAware.class, Alert.class, WebDriver.Options.class, WebDriver.Window.class);

    private static final String EXECUTION_NAVIGATION_TO = "execution(* org.openqa.selenium.WebDriver."
        + "Navigation.get(..))  || "
        + "execution(* org.openqa.selenium.WebDriver.Navigation.to(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.get(..))";
    private static final String EXECUTION_NAVIGATION_BACK = "execution(* org.openqa.selenium.WebDriver."
        + "Navigation.back(..))";
    private static final String EXECUTION_NAVIGATION_FORWARD = "execution(* org.openqa.selenium.WebDriver."
        + "Navigation.forward(..))";
    private static final String EXECUTION_NAVIGATION_REFRESH = "execution(* org.openqa.selenium.WebDriver."
        + "Navigation.refresh(..))";
    private static final String EXECUTION_SEARCH = "execution(* org.openqa.selenium.SearchContext."
        + "findElement(..)) || "
        + "execution(* org.openqa.selenium.SearchContext.findElements(..))";
    private static final String EXECUTION_CLICK = "execution(* org.openqa.selenium.WebElement.click(..))";
    private static final String EXECUTION_CHANGE_VALUE = "execution(* org.openqa.selenium.WebElement."
        + "sendKeys(..)) || "
        + "execution(* org.openqa.selenium.WebElement.clear(..))  || "
        + "execution(* io.appium.java_client.android.AndroidElement.replaceValue(..))  || "
        + "execution(* io.appium.java_client.MobileElement.setValue(..))";
    private static final String EXECUTION_SCRIPT = "execution(* org.openqa.selenium.JavascriptExecutor."
        + "executeScript(..)) || "
        + "execution(* org.openqa.selenium.JavascriptExecutor.executeAsyncScript(..))";
    private static final String EXECUTION_ALERT_ACCEPT = "execution(* org.openqa.selenium.Alert."
        + "accept(..))";
    private static final String EXECUTION_ALERT_DISMISS = "execution(* org.openqa.selenium.Alert."
        + "dismiss(..))";
    private static final String EXECUTION_ALERT_SEND_KEYS = "execution(* org.openqa.selenium.Alert."
        + "sendKeys(..))";
    private static final String EXECUTION_WINDOW_SET_SIZE = "execution(* org.openqa.selenium."
        + "WebDriver.Window.setSize(..))";
    private static final String EXECUTION_WINDOW_SET_POSITION = "execution(* org.openqa.selenium.WebDriver."
        + "Window.setPosition(..))";
    private static final String EXECUTION_WINDOW_MAXIMIZE = "execution(* org.openqa.selenium.WebDriver."
        + "Window.maximize(..))";
    private static final String EXECUTION_ROTATE = "execution(* org.openqa.selenium.Rotatable"
        + ".rotate(..))";
    private static final String EXECUTION_CONTEXT = "execution(* org.openqa.selenium.ContextAware."
        + "context(..))";
    private static final String EXECUTION_SWITCH_TO_WINDOW = "execution(* org.openqa.selenium.WebDriver.TargetLocator"
            + ".window(..))";
    private static final String EXECUTION_TAKE_SCREENSHOT_AS = "execution(* org.openqa.selenium.TakesScreenshot"
            + ".getScreenshotAs(..))";
    private static final String AROUND = "execution(* org.openqa.selenium.WebDriver.*(..)) || "
        + "execution(* org.openqa.selenium.WebElement.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.Navigation.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.Options.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
        + "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
        + "execution(* org.openqa.selenium.JavascriptExecutor.*(..)) || "
        + "execution(* org.openqa.selenium.ContextAware.*(..)) || "
        + "execution(* io.appium.java_client.FindsByAccessibilityId.*(..)) || "
        + "execution(* io.appium.java_client.FindsByAndroidUIAutomator.*(..)) || "
        + "execution(* io.appium.java_client.FindsByWindowsAutomation.*(..)) || "
        + "execution(* io.appium.java_client.FindsByIosNSPredicate.*(..)) || "
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
        + "execution(* io.appium.java_client.android.AndroidDriver.*(..)) || "
        + "execution(* io.appium.java_client.ios.IOSDriver.*(..)) || "
        + "execution(* io.appium.java_client.AppiumDriver.*(..)) || "
        + "execution(* io.appium.java_client.MobileElement.*(..)) || "
        + "execution(* org.openqa.selenium.remote.RemoteWebDriver.*(..)) || "
        + "execution(* org.openqa.selenium.remote.RemoteWebElement.*(..)) || "
        + "execution(* org.openqa.selenium.Alert.*(..)) || "
        + "execution(* org.openqa.selenium.TakesScreenshot.*(..))";

    private final AbstractApplicationContext context;
    private final WebDriver driver;
    private final DefaultListener listener = new DefaultListener();

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
        Class<?> returnStatement = null;
        for (Class<?> c : listenable) {
            if (!c.isAssignableFrom(classOfObject)) {
                continue;
            }
            returnStatement =  c;
        }
        return returnStatement;
    }

    DefaultAspect(AbstractApplicationContext context, WebDriver driver) {
        this.context = context;
        this.driver = driver;
    }

    private Object transformToListenable(Object toBeTransformed) {
        if (toBeTransformed == null) {
            return null;
        }

        Object result = toBeTransformed;
        if (getClassForProxy(toBeTransformed.getClass()) != null) {
            result = context.getBean(COMPONENT_BEAN, toBeTransformed);
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
                    proxyList.add(context.getBean(COMPONENT_BEAN, o));
                }
            }
            return proxyList;
        } catch (Exception e) {
            throw e;
        }

    }

    public void add(Collection<Listener> listeners) {
        listener.add(listeners);
    }

    @Before(EXECUTION_NAVIGATION_TO)
    public void beforeNavigateTo(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeNavigateTo(String.valueOf(joinPoint.getArgs()[0]), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_NAVIGATION_TO)
    public void afterNavigateTo(JoinPoint joinPoint)  throws Throwable {
        try {
            listener.afterNavigateTo(String.valueOf(joinPoint.getArgs()[0]), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_NAVIGATION_BACK)
    public void beforeNavigateBack(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeNavigateBack(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_NAVIGATION_BACK)
    public void afterNavigateBack(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateBack(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_NAVIGATION_FORWARD)
    public void beforeNavigateForward(JoinPoint joinPoint)  throws Throwable {
        try {
            listener.beforeNavigateForward(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_NAVIGATION_FORWARD)
    public void afterNavigateForward(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateForward(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_NAVIGATION_REFRESH)
    public void beforeNavigateRefresh(JoinPoint joinPoint)  throws Throwable {
        try {
            listener.beforeNavigateRefresh(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_NAVIGATION_REFRESH)
    public void afterNavigateRefresh(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterNavigateRefresh(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T castArgument(JoinPoint joinPoint, int argIndex) {
        return (T) joinPoint.getArgs()[argIndex];
    }

    @SuppressWarnings("unchecked")
    private <T> T castTarget(JoinPoint joinPoint) {
        return (T) joinPoint.getTarget();
    }

    @Before(EXECUTION_SEARCH)
    public void beforeFindBy(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            if (!WebElement.class.isAssignableFrom(target.getClass())) {
                listener.beforeFindBy(castArgument(joinPoint, 0), null, driver);
            } else {
                listener.beforeFindBy(castArgument(joinPoint, 0),
                    castTarget(joinPoint), driver);
            }
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_SEARCH)
    public void afterFindBy(JoinPoint joinPoint) throws Throwable {
        try {
            Object target =  joinPoint.getTarget();
            if (!WebElement.class.isAssignableFrom(target.getClass())) {
                listener.afterFindBy(castArgument(joinPoint, 0), null, driver);
            } else {
                listener.afterFindBy(castArgument(joinPoint, 0),
                    castTarget(joinPoint), driver);
            }
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_CLICK)
    public void beforeClickOn(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeClickOn(castTarget(joinPoint), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_CLICK)
    public void afterClickOn(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterClickOn(castTarget(joinPoint), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_CHANGE_VALUE)
    public void beforeChangeValueOf(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeChangeValueOf(castTarget(joinPoint), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_CHANGE_VALUE)
    public void afterChangeValueOf(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterChangeValueOf(castTarget(joinPoint), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_SCRIPT)
    public void beforeScript(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeScript(String.valueOf(joinPoint.getArgs()[0]), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_SCRIPT)
    public void afterScript(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterScript(String.valueOf(joinPoint.getArgs()[0]), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_ALERT_ACCEPT)
    public void beforeAlertAccept(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeAlertAccept(driver, castTarget(joinPoint));
            listener.beforeAlertAccept(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_ALERT_ACCEPT)
    public void afterAlertAccept(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterAlertAccept(driver, castTarget(joinPoint));
            listener.afterAlertAccept(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_ALERT_DISMISS)
    public void beforeAlertDismiss(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeAlertDismiss(driver, castTarget(joinPoint));
            listener.beforeAlertDismiss(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_ALERT_DISMISS)
    public void afterAlertDismiss(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterAlertDismiss(driver, castTarget(joinPoint));
            listener.afterAlertDismiss(driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_ALERT_SEND_KEYS)
    public void beforeAlertSendKeys(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeAlertSendKeys(driver, castTarget(joinPoint),
                String.valueOf(joinPoint.getArgs()[0]));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_ALERT_SEND_KEYS)
    public void afterAlertSendKeys(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterAlertSendKeys(driver, castTarget(joinPoint),
                String.valueOf(joinPoint.getArgs()[0]));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_WINDOW_SET_SIZE)
    public void beforeWindowIsResized(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeWindowChangeSize(driver,
                castTarget(joinPoint), castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_WINDOW_SET_SIZE)
    public void afterWindowIsResized(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterWindowChangeSize(driver, castTarget(joinPoint),
                castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_WINDOW_SET_POSITION)
    public void beforeWindowIsMoved(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeWindowIsMoved(driver, castTarget(joinPoint),
                castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_WINDOW_SET_POSITION)
    public void afterWindowIsMoved(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterWindowIsMoved(driver, castTarget(joinPoint),
                castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_WINDOW_MAXIMIZE)
    public void beforeMaximization(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeWindowIsMaximized(driver, castTarget(joinPoint));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_WINDOW_MAXIMIZE)
    public void afterMaximization(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterWindowIsMaximized(driver, castTarget(joinPoint));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_SWITCH_TO_WINDOW)
    public void beforeSwitchToWindow(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeSwitchToWindow(castArgument(joinPoint, 0), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @After(EXECUTION_SWITCH_TO_WINDOW)
    public void afterSwitchToWindow(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterSwitchToWindow(castArgument(joinPoint, 0), driver);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_TAKE_SCREENSHOT_AS)
    public void beforeTakeScreenShot(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeGetScreenshotAs(castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @AfterReturning(value = EXECUTION_TAKE_SCREENSHOT_AS, returning = "result")
    public void afterTakeScreenShot(JoinPoint joinPoint, Object result) throws Throwable {
        try {
            listener.afterGetScreenshotAs(castArgument(joinPoint, 0), result);
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_ROTATE)
    public void beforeRotation(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeRotation(driver, castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }

    }

    @After(EXECUTION_ROTATE)
    public void afterRotation(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterRotation(driver, castArgument(joinPoint, 0));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Before(EXECUTION_CONTEXT)
    public void beforeSwitchingToContext(JoinPoint joinPoint) throws Throwable {
        try {
            listener.beforeSwitchingToContext(driver, String.valueOf(joinPoint.getArgs()[0]));
        } catch (Throwable t) {
            throw getRootCause(t);
        }

    }

    @After(EXECUTION_CONTEXT)
    public void afterSwitchingToContextn(JoinPoint joinPoint) throws Throwable {
        try {
            listener.afterSwitchingToContext(driver, String.valueOf(joinPoint.getArgs()[0]));
        } catch (Throwable t) {
            throw getRootCause(t);
        }
    }

    @Around(AROUND)
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
            return null;
        }
        if (List.class.isAssignableFrom(result.getClass())) {
            return returnProxyList((List<Object>) (result));
        }

        return transformToListenable(result);
    }
}
