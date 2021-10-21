package io.appium.java_client.events;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.OutputType.BASE64;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;

import io.appium.java_client.events.listeners.AppiumListener;
import io.appium.java_client.events.listeners.SingleListeners;

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
        } finally {
            listener.messages.clear();
        }
    }

    @Test
    public void takeScreenShotEventTest() {
        try {
            driver.getScreenshotAs(BASE64);
            driver.findElement(xpath(".//some//path")).findElement(id("someId")).getScreenshotAs(BASE64);

            assertThat(listener.messages,
                    contains("WebDriverEventListener: Attempt to take screen shot. Target type is OutputType.BASE64",
                            "WebDriverEventListener: Screen shot was taken successfully. Target type is "
                                    + "OutputType.BASE64, result is AQI=",
                            "WebDriverEventListener: Attempt to find something using By.xpath: .//some//path. "
                                    + "The root element is null",
                            "WebDriverEventListener: The searching for something using "
                                    + "By.xpath: .//some//path has beed finished. "
                                    + "The root element was null",
                            "WebDriverEventListener: Attempt to find something using By.id: someId. "
                                    + "The root element is io.appium.java_client.events.StubWebElement",
                            "WebDriverEventListener: The searching for something using "
                                    + "By.id: someId has beed finished. "
                                    + "The root element was io.appium.java_client.events.StubWebElement",
                            "WebDriverEventListener: Attempt to take screen shot. Target type is OutputType.BASE64",
                            "WebDriverEventListener: Screen shot was taken successfully. "
                                    + "Target type is OutputType.BASE64, result is AQI="));
        } finally {
            listener.messages.clear();
        }
    }

    @Test
    public void exceptionEventTest() {
        assertThat(super.assertThatExceptionListenerWorks(driver, listener, WEBDRIVER_EVENT_LISTENER),
            is(true));
    }

    @Test
    public void windowListenerTest() {
        try {
            driver.switchTo().window("Test window");
            assertThat(listener.messages,
                    hasItems(WEBDRIVER_EVENT_LISTENER + "Attempt to switch to window Test window",
                            WEBDRIVER_EVENT_LISTENER + "driver is switched to window Test window"));
        } finally {
            listener.messages.clear();
        }

    }
}
