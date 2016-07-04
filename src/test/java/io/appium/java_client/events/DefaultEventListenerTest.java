package io.appium.java_client.events;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.MobileBy;
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
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultEventListenerTest {

    private static StubWebDriver driver;

    @BeforeClass public static void beforeClass() throws Exception {
        StubWebDriver stubWebDriver = new StubWebDriver();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver);
    }

    @Test
    public void searchContextEventTest() {
        driver.findElement(By.id("someId"));
        SearchingListener listener = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        assertThat(listener.messages,
            hasItems("Attempt to find something using By.id: someId. The root element is null",
                "The searching for something using By.id: someId has beed finished. "
                    + "The root element was null"));

        driver.findElements(By.id("someId2"));

        assertThat(listener.messages,
            hasItems("Attempt to find something using By.id: someId. The root element is null",
                "The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "Attempt to find something using By.id: someId2. The root element is null",
                "The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null"));

        driver.findElement(By.id("someId")).findElement(By.className("someClazz"));

        assertThat(listener.messages,
            hasItems("Attempt to find something using By.id: someId. The root element is null",
                "The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "Attempt to find something using By.id: someId2. The root element is null",
                "The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null",
                "Attempt to find something using By.className: someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "The searching for something using By.className: someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement"));

        driver.findElements(By.id("someId2")).get(0).findElements(By.className("someClazz2"));
        assertThat(listener.messages,
            hasItems("Attempt to find something using By.id: someId. The root element is null",
                "The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "Attempt to find something using By.id: someId2. The root element is null",
                "The searching for something using By.id: someId2 has beed finished. "
                    + "The root element was null",
                "Attempt to find something using By.className: someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "The searching for something using By.className: someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement",
                "Attempt to find something using By.className: someClazz2. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "The searching for something using By.className: someClazz2 has beed finished. "
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

        SearchingListener listener = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        assertThat(listener.messages,
            hasItems("Attempt to find something using By.AndroidUIAutomator: Android UI Automator. "
                + "The root element is io.appium.java_client.events.StubWebElement",
                "The searching for something using By.AndroidUIAutomator: Android UI Automator has "
                    + "beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement",
                "Attempt to find something using By.IosUIAutomation: iOS UI Automation. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "The searching for something using By.IosUIAutomation: iOS UI Automation "
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

        NavigationListener navigationListener = (NavigationListener) SingleListeners
            .listeners.get(NavigationListener.class);

        assertThat(navigationListener.messages,
            hasItems("Attempt to navigate to www.google.com",
                "Navigation to www.google.com was successful",
                "Attempt to navigate to www.google2.com",
                "Navigation to www.google2.com was successful",
                "Attempt to navigate to https://www.google3.com",
                "Navigation to https://www.google3.com was successful",
                "Attempt to navigate forward",
                "Navigation forward was successful",
                "Attempt to navigate back",
                "Navigation back was successful",
                "Attempt to refresh",
                "The refreshing was successful"));
    }

    @Test
    public void elementEventTest() {
        StubWebElement e = driver.findElementByXPath("Some Path");
        e.click();
        e.sendKeys("Test keys");

        ElementListener listener = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to click on the element",
                "Thee element was clicked",
                "Attempt to change value of the element",
                "The value of the element was changed"));
    }

    @Test
    public void javaScriptEventTest() {
        driver.executeScript("Some test script");
        driver.executeAsyncScript("Some test async script");

        JavaScriptListener listener = (JavaScriptListener) SingleListeners
            .listeners.get(JavaScriptListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to perform java script: Some test script",
                "Java script Some test script was performed",
                "Attempt to perform java script: Some test async script",
                "Java script Some test async script was performed"));
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

        ExceptionListener listener = (ExceptionListener) SingleListeners
            .listeners.get(ExceptionListener.class);

        assertThat(listener.messages,
            hasItems("The exception was thrown: "
                    + new RuntimeException("Test WebDriver exception").toString(),
                "The exception was thrown: "
                    + new RuntimeException("Test web element exception").toString()));
    }

    @Test
    public void alertEventTest() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
        alert.dismiss();
        alert.sendKeys("Keys");
        Credentials credentials = new Credentials() {
            @Override public int hashCode() {
                return super.hashCode();
            }

            @Override public String toString() {
                return "Test credentials 1";
            }
        };

        Credentials credentials2 = new Credentials() {
            @Override public int hashCode() {
                return super.hashCode();
            }

            @Override public String toString() {
                return "Test credentials 2";
            }
        };

        alert.setCredentials(credentials);
        alert.authenticateUsing(credentials2);

        AlertListener listener = (AlertListener) SingleListeners
            .listeners.get(AlertListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to accept alert",
                "The alert was accepted",
                "Attempt to dismiss alert",
                "The alert was dismissed",
                "Attempt to send keys to alert",
                "Keys were sent to alert",
                "Attempt to send credentials " + credentials.toString() + " to alert",
                "Credentials " + credentials.toString() + " were sent to alert",
                "Attempt to send credentials " + credentials2.toString() + " to alert",
                "Credentials " + credentials2.toString() + " were sent to alert"));
    }

    @Test
    public void contextEventListener() {
        driver.context("NATIVE_APP");
        driver.context("WEB_VIEW");

        ContextListener listener = (ContextListener) SingleListeners
            .listeners.get(ContextListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to change current context to NATIVE_APP",
                "The previous context has been changed to NATIVE_APP",
                "Attempt to change current context to WEB_VIEW",
                "The previous context has been changed to WEB_VIEW"));
    }

    @Test
    public void rotationEventListener() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
        driver.rotate(ScreenOrientation.PORTRAIT);

        RotationListener listener = (RotationListener) SingleListeners
            .listeners.get(RotationListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to change screen orientation. The new screen orientation is "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "The screen orientation has been changed to "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "Attempt to change screen orientation. The new screen orientation is "
                    + ScreenOrientation.PORTRAIT.toString(),
                "The screen orientation has been changed to "
                    + ScreenOrientation.PORTRAIT.toString()));
    }

    @Test
    public void windowEventListener() {
        WebDriver.Window window = driver.manage().window();
        Dimension d = new Dimension(500, 500);
        window.setSize(d);

        Point p = new Point(50, 50);
        window.setPosition(p);

        window.maximize();

        WindowListener listener = (WindowListener) SingleListeners
            .listeners.get(WindowListener.class);

        assertThat(listener.messages,
            hasItems("Attempt to change size of the window. The height is " + d.getHeight()
                    + " the width is " + d.getWidth(),
                "Size of the window has been changed. The height is " + d.getHeight()
                    + " the width is " + d.getWidth(),
                "Attempt to change position of the window. The X is " + p.getX()
                    + " the Y is " + p.getY(),
                "The position the window has been changed. The X is " + p.getX()
                    + " the Y is " + p.getY(),
                "Attempt to maximize the window.",
                "The window has been maximized"));
    }
}
