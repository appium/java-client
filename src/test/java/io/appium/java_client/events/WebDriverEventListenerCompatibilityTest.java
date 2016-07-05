package io.appium.java_client.events;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.MobileBy;
import io.appium.java_client.events.listeners.AppiumListener;
import io.appium.java_client.events.listeners.SingleListeners;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import java.net.URL;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebDriverEventListenerCompatibilityTest {

    private static StubWebDriver driver;
    private static AppiumListener listener;

    @BeforeClass public static void beforeClass() throws Exception {
        StubWebDriver stubWebDriver = new StubWebDriver();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver);
        listener = (AppiumListener) SingleListeners.listeners.get(AppiumListener.class);
    }

    @Test
    public void searchContextEventTest() {
        driver.findElement(By.id("someId"));
        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to find something using By.id: someId. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId has beed finished. "
                    + "The root element was null"));

        driver.findElements(By.id("someId2"));

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to find something using By.id: someId. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "WebDriverEventListener: Attempt to find something using By.id: someId2. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null"));

        driver.findElement(By.id("someId")).findElement(By.className("someClazz"));

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to find something using By.id: someId. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "WebDriverEventListener: Attempt to find something using By.id: someId2. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null",
                "WebDriverEventListener: Attempt to find something using By.className: someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: The searching for something using By.className: someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement"));

        driver.findElements(By.id("someId2")).get(0).findElements(By.className("someClazz2"));
        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to find something using By.id: someId. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "WebDriverEventListener: Attempt to find something using By.id: someId2. The root element is null",
                "WebDriverEventListener: The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null",
                "WebDriverEventListener: Attempt to find something using By.className: someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: The searching for something using By.className: someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: Attempt to find something using By.className: someClazz2. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: The searching for something using By.className: someClazz2 has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement"));
    }

    @Test
    public void searchContextEventTest2() {
        List<StubWebElement> els = driver.findElementsByAccessibilityId("SomeAccessibility");
        StubWebElement e = driver.findElementByXPath("Some Path");

        e.findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.AndroidUIAutomator("Android UI Automator"));
        els.get(0).findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.IosUIAutomation("iOS UI Automation"));

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to find something using By.AndroidUIAutomator:"
                    + " Android UI Automator. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: The searching for something using By.AndroidUIAutomator: "
                    + "Android UI Automator has "
                    + "beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: Attempt to find something using By.IosUIAutomation: iOS UI Automation. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "WebDriverEventListener: The searching for something using By.IosUIAutomation: iOS UI Automation "
                    + "has beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement"));
    }

    @Test
    public void searchNavigationEventTest() throws Exception {
        driver.get("www.google.com");
        driver.navigate().to("www.google2.com");
        driver.navigate().to(new URL("https://www.google3.com"));
        driver.navigate().forward();
        driver.navigate().back();
        driver.navigate().refresh();

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to navigate to www.google.com",
                "WebDriverEventListener: Navigation to www.google.com was successful",
                "WebDriverEventListener: Attempt to navigate to www.google2.com",
                "WebDriverEventListener: Navigation to www.google2.com was successful",
                "WebDriverEventListener: Attempt to navigate to https://www.google3.com",
                "WebDriverEventListener: Navigation to https://www.google3.com was successful",
                "WebDriverEventListener: Attempt to navigate forward",
                "WebDriverEventListener: Navigation forward was successful",
                "WebDriverEventListener: Attempt to navigate back",
                "WebDriverEventListener: Navigation back was successful",
                "WebDriverEventListener: Attempt to refresh",
                "WebDriverEventListener: The refreshing was successful"));
    }

    @Test
    public void elementEventTest() {
        StubWebElement e = driver.findElementByXPath("Some Path");
        e.click();
        e.sendKeys("Test keys");

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to click on the element",
                "WebDriverEventListener: Thee element was clicked",
                "WebDriverEventListener: Attempt to change value of the element",
                "WebDriverEventListener: The value of the element was changed"));
    }

    @Test
    public void javaScriptEventTest() {
        driver.executeScript("Some test script");
        driver.executeAsyncScript("Some test async script");

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: Attempt to perform java script: Some test script",
                "WebDriverEventListener: Java script Some test script was performed",
                "WebDriverEventListener: Attempt to perform java script: Some test async script",
                "WebDriverEventListener: Java script Some test async script was performed"));
    }

    @Test
    public void exceptionEventTest() {
        try {
            driver.getWindowHandle();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        try {
            driver.findElementByXPath("Some Path").getScreenshotAs(OutputType.BASE64);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        assertThat(listener.messages,
            hasItems("WebDriverEventListener: The exception was thrown: "
                    + new RuntimeException("Test WebDriver exception").toString(),
                "WebDriverEventListener: The exception was thrown: "
                    + new RuntimeException("Test web element exception").toString()));
    }
}
