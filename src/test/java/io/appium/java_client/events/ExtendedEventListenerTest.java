package io.appium.java_client.events;

import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.MobileBy;
import io.appium.java_client.events.listeners.SearchingListener;
import io.appium.java_client.events.listeners.SingleListeners;
import io.appium.java_client.selenium.By;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtendedEventListenerTest {

    private static EmptyWebDriver stubWebDriver;

    @BeforeClass public static void beforeClass() {
        stubWebDriver = new EmptyWebDriver();
        stubWebDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver);
    }

    @Test
    public void searchingTest() {
        StubWebElement androidElement = stubWebDriver.findElement(By.id("someId"));
        androidElement.findElement("-some-criteria", "some value")
            .findElements(MobileBy.AndroidUIAutomator("Android UI Automator"));
        androidElement.findElements("-some-criteria2", "some value2").get(0)
            .findElements(MobileBy.AndroidUIAutomator("Android UI Automator2"));

        SearchingListener listener = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        assertThat(listener.messages,
            hasItems("Attempt to find something using By.AndroidUIAutomator: Android UI Automator. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "The searching for something using By.AndroidUIAutomator: Android UI Automator has "
                    + "beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement",
                "Attempt to find something using By.AndroidUIAutomator: Android UI Automator2. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "The searching for something using By.AndroidUIAutomator: Android UI Automator2 "
                    + "has beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement"));
    }
}
