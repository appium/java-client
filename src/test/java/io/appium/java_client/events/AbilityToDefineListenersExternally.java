package io.appium.java_client.events;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import io.appium.java_client.events.listeners.AlertListener2;
import io.appium.java_client.events.listeners.ContextListener2;
import io.appium.java_client.events.listeners.ElementListener2;
import io.appium.java_client.events.listeners.ExceptionListener2;
import io.appium.java_client.events.listeners.JavaScriptListener2;
import io.appium.java_client.events.listeners.NavigationListener2;
import io.appium.java_client.events.listeners.RotationListener2;
import io.appium.java_client.events.listeners.SearchingListener2;
import io.appium.java_client.events.listeners.WindowListener2;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbilityToDefineListenersExternally extends BaseListenerTest {

    private static final String PREFIX = "Externally defined listener: ";
    private static EmptyWebDriver emptyWebDriver;
    private static SearchingListener2 searchingListener = new SearchingListener2();
    private static NavigationListener2 navigationListener = new NavigationListener2();
    private static ElementListener2 elementListener = new ElementListener2();
    private static JavaScriptListener2 javaScriptListener = new JavaScriptListener2();
    private static ExceptionListener2 exceptionListener = new ExceptionListener2();
    private static AlertListener2 alertListener = new AlertListener2();
    private static ContextListener2 contextListener = new ContextListener2();
    private static RotationListener2 rotationListener = new RotationListener2();
    private static WindowListener2 windowListener = new WindowListener2();

    @BeforeClass public static void beforeClass() throws Exception {
        emptyWebDriver = new EmptyWebDriver();
        emptyWebDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(emptyWebDriver,
            searchingListener, navigationListener, elementListener, javaScriptListener,
            exceptionListener, alertListener, contextListener, rotationListener, windowListener);
    }

    @Test
    public void searchContextEventTest() {
        assertThat(super.assertThatSearchListenerWorks(emptyWebDriver, searchingListener, PREFIX),
            is(true));
    }

    @Test
    public void searchContextEventTest2() {
        assertThat(
            super
                .assertThatSearchListenerWorksAgainstElements(emptyWebDriver, searchingListener, PREFIX),
            is(true));
    }

    @Test
    public void navigationEventTest() throws Exception {
        assertThat(
            super
                .assertThatNavigationListenerWorks(emptyWebDriver, navigationListener, PREFIX),
            is(true));
    }

    @Test
    public void elementEventTest() {
        assertThat(super.assertThatElementListenerWorks(emptyWebDriver, elementListener, PREFIX),
            is(true));
    }

    @Test
    public void javaScriptEventTest() {
        assertThat(super
            .assertThatJavaScriptListenerWorks(emptyWebDriver, javaScriptListener, PREFIX),
            is(true));
    }

    @Test
    public void exceptionEventTest() {
        assertThat(super.assertThatExceptionListenerWorks(emptyWebDriver, exceptionListener, PREFIX),
            is(true));
    }

    @Test
    public void alertEventTest() {
        assertThat(super.assertThatAlertListenerWorks(emptyWebDriver, alertListener, PREFIX),
            is(true));
    }

    @Test
    public void contextEventListener() {
        assertThat(super.assertThatConrextListenerWorks(emptyWebDriver, contextListener, PREFIX),
            is(true));
    }

    @Test
    public void rotationEventListener() {
        assertThat(super.assertThatRotationListenerWorks(emptyWebDriver, rotationListener, PREFIX),
            is(true));
    }

    @Test
    public void windowEventListener() {
        assertThat(super.assertThatWindowListenerWorks(emptyWebDriver, windowListener, PREFIX),
            is(true));
    }
}
