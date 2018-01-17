package io.appium.java_client.events;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.events.listeners.AppiumListener;
import io.appium.java_client.events.listeners.SingleListeners;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebDriverEventListenerCompatibilityTest extends BaseListenerTest {

    private static EmptyWebDriver driver;
    private static AppiumListener listener;
    private static final String WEBDRIVER_EVENT_LISTENER = "WebDriverEventListener: ";

    @BeforeClass public static void beforeClass() {
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
    public void alertEventTest() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            alert.dismiss();
            alert.sendKeys("Keys");

            assertThat(listener.messages,
                    hasItems(WEBDRIVER_EVENT_LISTENER + "Attempt to accept alert",
                            WEBDRIVER_EVENT_LISTENER + "The alert was accepted",
                            WEBDRIVER_EVENT_LISTENER + "Attempt to dismiss alert",
                            WEBDRIVER_EVENT_LISTENER + "The alert was dismissed"));
            assertThat(listener.messages.size(), is(4));
        } finally {
            listener.messages.clear();
        }
    }

    @Test
    public void exceptionEventTest() {
        assertThat(super.assertThatExceptionListenerWorks(driver, listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }
}
