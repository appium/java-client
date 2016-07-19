package io.appium.java_client.events;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import io.appium.java_client.events.listeners.AppiumListener;
import io.appium.java_client.events.listeners.SingleListeners;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebDriverEventListenerCompatibilityTest extends BaseListenerTest {

    private static EmptyWebDriver driver;
    private static AppiumListener listener;
    private static final String WEBDRIVER_EVENT_LISTENER = "WebDriverEventListener: ";

    @BeforeClass public static void beforeClass() throws Exception {
        EmptyWebDriver emptyWebDriver = new EmptyWebDriver();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(emptyWebDriver);
        listener = (AppiumListener) SingleListeners.listeners.get(AppiumListener.class);
    }

    @Test
    public void searchContextEventTest() {
        assertThat(super.assertThatSearchListenerWorks(driver, listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void searchContextEventTest2() {
        assertThat(super.assertThatSearchListenerWorksAgainstElements(driver,
            listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void navigationEventTest() throws Exception {
        assertThat(super.assertThatNavigationListenerWorks(driver,
            listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void elementEventTest() {
        assertThat(super.assertThatElementListenerWorks(driver, listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void javaScriptEventTest() {
        assertThat(super.assertThatJavaScriptListenerWorks(driver,
            listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void exceptionEventTest() {
        assertThat(super.assertThatExceptionListenerWorks(driver, listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }
}
