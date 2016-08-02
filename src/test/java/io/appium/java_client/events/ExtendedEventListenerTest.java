package io.appium.java_client.events;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.events.listeners.ElementListener;
import io.appium.java_client.events.listeners.SearchingListener;
import io.appium.java_client.events.listeners.SingleListeners;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

public class ExtendedEventListenerTest {

    private static ExtendedWebDriver stubWebDriver;

    @BeforeClass public static void beforeClass() throws Exception {
        stubWebDriver = new ExtendedWebDriver();
        stubWebDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver);
    }

    @Test
    public void replaceValueTest() {
        AndroidElement androidElement = stubWebDriver.findElement(By.id("someId"));
        androidElement.replaceValue("New value");

        ElementListener listener = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);

        assertThat(listener.messages,
            hasItems(
                "Attempt to change value of the element",
                "The value of the element was changed"));
    }

    @Test
    public void searchingTest() {
        AndroidElement androidElement = stubWebDriver.findElement(By.id("someId"));
        androidElement.findElement("-some-criteria", "some value")
            .findElements(MobileBy.AndroidUIAutomator("Android UI Automator"));
        androidElement.findElements("-some-criteria2", "some value2").get(0)
            .findElements(MobileBy.AndroidUIAutomator("Android UI Automator2"));

        SearchingListener listener = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        assertThat(listener.messages,
            hasItems("Attempt to find something using By.AndroidUIAutomator: Android UI Automator. "
                    + "The root element is io.appium.java_client.events.StubAndroidElement",
                "The searching for something using By.AndroidUIAutomator: Android UI Automator has "
                    + "beed finished. "
                    + "The root element was io.appium.java_client.events.StubAndroidElement",
                "Attempt to find something using By.AndroidUIAutomator: Android UI Automator2. "
                    + "The root element is io.appium.java_client.events.StubAndroidElement",
                "The searching for something using By.AndroidUIAutomator: Android UI Automator2 "
                    + "has beed finished. "
                    + "The root element was io.appium.java_client.events.StubAndroidElement"));
    }
}
