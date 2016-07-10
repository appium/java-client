package io.appium.java_client.events;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import io.appium.java_client.MobileBy;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;

import java.net.URL;
import java.util.List;

public class AbilityToDefineListenersExternally {

    private static StubWebDriver stubWebDriver;
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
        stubWebDriver = new StubWebDriver();
        stubWebDriver = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver,
            searchingListener, navigationListener, elementListener, javaScriptListener,
            exceptionListener, alertListener, contextListener, rotationListener, windowListener);
    }

    @Test
    public void searchContextEventTest() {
        stubWebDriver.findElement(By.id("someId"));

        assertThat(searchingListener.messages,
            hasItems("Externally defined listener: Attempt to find something using By.id: someId. "
                + "The root element is null",
                "Externally defined listener: The searching for something using By.id: someId has "
                    + "beed finished. "
                    + "The root element was null"));

        stubWebDriver.findElements(By.id("someId2"));

        assertThat(searchingListener.messages,
            hasItems("Externally defined listener: Attempt to find something using By.id: someId. "
                + "The root element is null",
                "Externally defined listener: The searching for something using By.id: someId has beed finished. "
                    + "The root element was null",
                "Externally defined listener: Attempt to find something using By.id: someId2. "
                    + "The root element is null",
                "Externally defined listener: The searching for something using By.id: someId2 "
                    + "has beed finished. "
                    + "The root element was null"));

        stubWebDriver.findElement(By.id("someId")).findElement(By.className("someClazz"));

        assertThat(searchingListener.messages,
            hasItems("Externally defined listener: Attempt to find something using By.id: someId. "
                + "The root element is null",
                "Externally defined listener: The searching for something using By.id: "
                    + "someId has beed finished. "
                    + "The root element was null",
                "Externally defined listener: Attempt to find something using By.id: someId2. "
                    + "The root element is null",
                "Externally defined listener: The searching for something using By.id: someId2 "
                    + "has beed finished. "
                    + "The root element was null",
                "Externally defined listener: Attempt to find something using By.className: "
                    + "someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "Externally defined listener: The searching for something using By.className: "
                    + "someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement"));

        stubWebDriver.findElements(By.id("someId2")).get(0).findElements(By.className("someClazz2"));
        assertThat(searchingListener.messages,
            hasItems("Externally defined listener: Attempt to find something using By.id: someId. "
                + "The root element is null",
                "Externally defined listener: The searching for something using By.id: someId "
                    + "has beed finished. "
                    + "The root element was null",
                "Externally defined listener: Attempt to find something using By.id: someId2. "
                    + "The root element is null",
                "Externally defined listener: The searching for something using By.id: "
                    + "someId2 has beed finished. "
                    + "The root element was null",
                "Externally defined listener: Attempt to find something using By.className: "
                    + "someClazz. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "Externally defined listener: The searching for something using By.className: "
                    + "someClazz has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement",
                "Externally defined listener: Attempt to find something using By.className: "
                    + "someClazz2. The root element is "
                    + "io.appium.java_client.events.StubWebElement",
                "Externally defined listener: The searching for something using By.className: "
                    + "someClazz2 has beed finished. "
                    + "The root element was "
                    + "io.appium.java_client.events.StubWebElement"));
    }

    @Test
    public void searchContextEventTest2() {
        List<StubWebElement> els = stubWebDriver.findElementsByAccessibilityId("SomeAccessibility");
        StubWebElement e = stubWebDriver.findElementByXPath("Some Path");

        e.findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.AndroidUIAutomator("Android UI Automator"));
        els.get(0).findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.IosUIAutomation("iOS UI Automation"));

        assertThat(searchingListener.messages,
            hasItems("Externally defined listener: Attempt to find something using "
                + "By.AndroidUIAutomator: Android UI Automator. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "Externally defined listener: The searching for something using By."
                    + "AndroidUIAutomator: Android UI Automator has "
                    + "beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement",
                "Externally defined listener: Attempt to find something using By."
                    + "IosUIAutomation: iOS UI Automation. "
                    + "The root element is io.appium.java_client.events.StubWebElement",
                "Externally defined listener: The searching for something using By."
                    + "IosUIAutomation: iOS UI Automation "
                    + "has beed finished. "
                    + "The root element was io.appium.java_client.events.StubWebElement"));
    }

    @Test
    public void searchNavigationEventTest() throws Exception {
        stubWebDriver.get("www.google.com");
        stubWebDriver.navigate().to("www.google2.com");
        stubWebDriver.navigate().to(new URL("https://www.google3.com"));
        stubWebDriver.navigate().forward();
        stubWebDriver.navigate().back();
        stubWebDriver.navigate().refresh();

        assertThat(navigationListener.messages,
            hasItems("Externally defined listener: Attempt to navigate to www.google.com",
                "Externally defined listener: Navigation to www.google.com was successful",
                "Externally defined listener: Attempt to navigate to www.google2.com",
                "Externally defined listener: Navigation to www.google2.com was successful",
                "Externally defined listener: Attempt to navigate to https://www.google3.com",
                "Externally defined listener: Navigation to https://www.google3.com was successful",
                "Externally defined listener: Attempt to navigate forward",
                "Externally defined listener: Navigation forward was successful",
                "Externally defined listener: Attempt to navigate back",
                "Externally defined listener: Navigation back was successful",
                "Externally defined listener: Attempt to refresh",
                "Externally defined listener: The refreshing was successful"));
    }

    @Test
    public void elementEventTest() {
        StubWebElement e = stubWebDriver.findElementByXPath("Some Path");
        e.click();
        e.sendKeys("Test keys");

        assertThat(elementListener.messages,
            hasItems("Externally defined listener: Attempt to click on the element",
                "Externally defined listener: Thee element was clicked",
                "Externally defined listener: Attempt to change value of the element",
                "Externally defined listener: The value of the element was changed"));
    }

    @Test
    public void javaScriptEventTest() {
        stubWebDriver.executeScript("Some test script");
        stubWebDriver.executeAsyncScript("Some test async script");

        assertThat(javaScriptListener.messages,
            hasItems("Externally defined listener: Attempt to perform java script: "
                + "Some test script",
                "Externally defined listener: Java script Some test script was performed",
                "Externally defined listener: Attempt to perform java script: "
                    + "Some test async script",
                "Externally defined listener: Java script Some test async script was performed"));
    }

    @Test
    public void exceptionEventTest() {
        try {
            stubWebDriver.getWindowHandle();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        try {
            stubWebDriver.findElementByXPath("Some Path").getScreenshotAs(OutputType.BASE64);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        assertThat(exceptionListener.messages,
            hasItems("Externally defined listener: The exception was thrown: "
                    + new RuntimeException("Test WebDriver exception").toString(),
                "Externally defined listener: The exception was thrown: "
                    + new RuntimeException("Test web element exception").toString()));
    }

    @Test
    public void alertEventTest() {
        Alert alert = stubWebDriver.switchTo().alert();
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

        assertThat(alertListener.messages,
            hasItems("Externally defined listener: Attempt to accept alert",
                "Externally defined listener: The alert was accepted",
                "Externally defined listener: Attempt to dismiss alert",
                "Externally defined listener: The alert was dismissed",
                "Externally defined listener: Attempt to send keys to alert",
                "Externally defined listener: Keys were sent to alert",
                "Externally defined listener: Attempt to send credentials " + credentials.toString() + " to alert",
                "Externally defined listener: Credentials " + credentials.toString()
                    + " were sent to alert",
                "Externally defined listener: Attempt to send credentials "
                    + credentials2.toString() + " to alert",
                "Externally defined listener: Credentials " + credentials2.toString()
                    + " were sent to alert"));
    }

    @Test
    public void contextEventListener() {
        stubWebDriver.context("NATIVE_APP");
        stubWebDriver.context("WEB_VIEW");

        assertThat(contextListener.messages,
            hasItems("Externally defined listener: Attempt to change current context to NATIVE_APP",
                "Externally defined listener: The previous context has been changed to NATIVE_APP",
                "Externally defined listener: Attempt to change current context to WEB_VIEW",
                "Externally defined listener: The previous context has been changed to WEB_VIEW"));
    }

    @Test
    public void rotationEventListener() {
        stubWebDriver.rotate(ScreenOrientation.LANDSCAPE);
        stubWebDriver.rotate(ScreenOrientation.PORTRAIT);

        assertThat(rotationListener.messages,
            hasItems("Externally defined listener: Attempt to change screen orientation. "
                + "The new screen orientation is "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "Externally defined listener: The screen orientation has been changed to "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "Externally defined listener: Attempt to change screen orientation. "
                    + "The new screen orientation is "
                    + ScreenOrientation.PORTRAIT.toString(),
                "Externally defined listener: The screen orientation has been changed to "
                    + ScreenOrientation.PORTRAIT.toString()));
    }

    @Test
    public void windowEventListener() {
        WebDriver.Window window = stubWebDriver.manage().window();
        Dimension d = new Dimension(500, 500);
        window.setSize(d);

        Point p = new Point(50, 50);
        window.setPosition(p);

        window.maximize();

        assertThat(windowListener.messages,
            hasItems("Externally defined listener: Attempt to change size of the window. "
                    + "The height is " + d.getHeight()
                    + " the width is " + d.getWidth(),
                "Externally defined listener: Size of the window has been changed. The height is "
                    + d.getHeight()
                    + " the width is " + d.getWidth(),
                "Externally defined listener: Attempt to change position of the window. The X is "
                    + p.getX()
                    + " the Y is " + p.getY(),
                "Externally defined listener: The position the window has been changed. The X is "
                    + p.getX()
                    + " the Y is " + p.getY(),
                "Externally defined listener: Attempt to maximize the window.",
                "Externally defined listener: The window has been maximized"));
    }
}
