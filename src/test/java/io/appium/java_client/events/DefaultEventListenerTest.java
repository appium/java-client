package io.appium.java_client.events;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import io.appium.java_client.events.listeners.AlertListener;
import io.appium.java_client.events.listeners.ContextListener;
import io.appium.java_client.events.listeners.ElementListener;
import io.appium.java_client.events.listeners.ExceptionListener;
import io.appium.java_client.events.listeners.JavaScriptListener;
import io.appium.java_client.events.listeners.NavigationListener;
import io.appium.java_client.events.listeners.RotationListener;
import io.appium.java_client.events.listeners.SearchingListener;
import io.appium.java_client.events.listeners.SingleListeners;
import io.appium.java_client.events.listeners.WindowListener;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Capabilities;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultEventListenerTest extends BaseListenerTest {

    private static EmptyWebDriver driver;

    @BeforeClass public static void beforeClass() throws Exception {
        EmptyWebDriver emptyWebDriver = new EmptyWebDriver();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(emptyWebDriver);
    }

    @Test
    public void searchContextEventTest() {
        assertThat(super.assertThatSearchListenerWorks(driver, SingleListeners
            .listeners.get(SearchingListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void searchContextEventTest2() {
        assertThat(super.assertThatSearchListenerWorksAgainstElements(driver, SingleListeners
            .listeners.get(SearchingListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void navigationEventTest() throws Exception {
        assertThat(super.assertThatNavigationListenerWorks(driver, SingleListeners
            .listeners.get(NavigationListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void elementEventTest() {
        assertThat(super.assertThatElementListenerWorks(driver, SingleListeners
            .listeners.get(ElementListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void javaScriptEventTest() {
        assertThat(super.assertThatJavaScriptListenerWorks(driver, SingleListeners
            .listeners.get(JavaScriptListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void exceptionEventTest() {
        assertThat(super.assertThatExceptionListenerWorks(driver, SingleListeners
            .listeners.get(ExceptionListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void alertEventTest() {
        assertThat(super.assertThatAlertListenerWorks(driver, SingleListeners
            .listeners.get(AlertListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void contextEventListener() {
        assertThat(super.assertThatConrextListenerWorks(driver, SingleListeners
            .listeners.get(ContextListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void rotationEventListener() {
        assertThat(super.assertThatRotationListenerWorks(driver, SingleListeners
            .listeners.get(RotationListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void windowEventListener() {
        assertThat(super.assertThatWindowListenerWorks(driver, SingleListeners
            .listeners.get(WindowListener.class), StringUtils.EMPTY), is(true));
    }

    @Test
    public void whenNonListenableObjectIsReturned() {
        Capabilities capabilities = driver.getCapabilities();
        assertNotNull(capabilities);
        assertEquals(capabilities.asMap().size(), 2);
    }
}
