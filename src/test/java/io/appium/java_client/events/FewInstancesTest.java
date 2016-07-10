package io.appium.java_client.events;

import static com.thoughtworks.selenium.SeleneseTestCase.assertNotEquals;
import static org.hamcrest.core.Is.is;
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

public class FewInstancesTest {

    private static StubWebDriver stubWebDriver1;
    private static SearchingListener searchingListener1;
    private static NavigationListener navigationListener1;
    private static ElementListener elementListener1;
    private static JavaScriptListener javaScriptListener1;
    private static ExceptionListener exceptionListener1;
    private static AlertListener alertListener1;
    private static ContextListener contextListener1;
    private static RotationListener rotationListener1;
    private static WindowListener windowListener1;

    private static SearchingListener searchingListener2;
    private static NavigationListener navigationListener2;
    private static ElementListener elementListener2;
    private static JavaScriptListener javaScriptListener2;
    private static ExceptionListener exceptionListener2;
    private static AlertListener alertListener2;
    private static ContextListener contextListener2;
    private static RotationListener rotationListener2;
    private static WindowListener windowListener2;

    @BeforeClass public static void beforeClass() throws Exception {
        stubWebDriver1 = new StubWebDriver();
        stubWebDriver1 = EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver1);

        searchingListener1 = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        navigationListener1 = (NavigationListener) SingleListeners
            .listeners.get(NavigationListener.class);
        elementListener1 = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);
        javaScriptListener1 = (JavaScriptListener) SingleListeners
            .listeners.get(JavaScriptListener.class);
        exceptionListener1 = (ExceptionListener) SingleListeners
            .listeners.get(ExceptionListener.class);
        alertListener1 = (AlertListener) SingleListeners
            .listeners.get(AlertListener.class);
        contextListener1 = (ContextListener) SingleListeners
            .listeners.get(ContextListener.class);
        rotationListener1 = (RotationListener) SingleListeners
            .listeners.get(RotationListener.class);
        windowListener1 =
            (WindowListener) SingleListeners.listeners.get(WindowListener.class);

        WebDriver stubWebDriver2 = new StubWebDriver();
        EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver2);

        searchingListener2 = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        navigationListener2 = (NavigationListener) SingleListeners
            .listeners.get(NavigationListener.class);
        elementListener2 = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);
        javaScriptListener2 = (JavaScriptListener) SingleListeners
            .listeners.get(JavaScriptListener.class);
        exceptionListener2 = (ExceptionListener) SingleListeners
            .listeners.get(ExceptionListener.class);
        alertListener2 = (AlertListener) SingleListeners
            .listeners.get(AlertListener.class);
        contextListener2 = (ContextListener) SingleListeners
            .listeners.get(ContextListener.class);
        rotationListener2 = (RotationListener) SingleListeners
            .listeners.get(RotationListener.class);
        windowListener2 =
            (WindowListener) SingleListeners.listeners.get(WindowListener.class);
    }

    @Test
    public void listenersAreDifferent() {
        assertNotEquals(searchingListener1, searchingListener2);
        assertNotEquals(elementListener1, elementListener2);
        assertNotEquals(navigationListener1, navigationListener2);
        assertNotEquals(javaScriptListener1, javaScriptListener2);
        assertNotEquals(exceptionListener1, exceptionListener2);
        assertNotEquals(alertListener1, alertListener2);
        assertNotEquals(contextListener1, contextListener2);
        assertNotEquals(rotationListener1, rotationListener2);
        assertNotEquals(windowListener1, windowListener2);
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother() {
        stubWebDriver1.findElement(By.id("someId"));
        stubWebDriver1.findElements(By.id("someId2"));
        stubWebDriver1.findElement(By.id("someId")).findElement(By.className("someClazz"));
        stubWebDriver1.findElements(By.id("someId2")).get(0).findElements(By.className("someClazz2"));

        assertThat(searchingListener1.messages,
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
        assertThat("The second searching listener should have no messages",
            searchingListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother2() {
        List<StubWebElement> els = stubWebDriver1.findElementsByAccessibilityId("SomeAccessibility");
        StubWebElement e = stubWebDriver1.findElementByXPath("Some Path");

        e.findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.AndroidUIAutomator("Android UI Automator"));
        els.get(0).findElementByAccessibilityId("SomeAccessibility")
            .findElement(MobileBy.IosUIAutomation("iOS UI Automation"));

        assertThat(searchingListener1.messages,
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

        assertThat("The second searching listener should have no messages",
            searchingListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother3() throws Exception {
        stubWebDriver1.get("www.google.com");
        stubWebDriver1.navigate().to("www.google2.com");
        stubWebDriver1.navigate().to(new URL("https://www.google3.com"));
        stubWebDriver1.navigate().forward();
        stubWebDriver1.navigate().back();
        stubWebDriver1.navigate().refresh();

        assertThat(navigationListener1.messages,
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
        assertThat("The second navigation listener should have no messages",
            navigationListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother4() {
        stubWebDriver1.executeScript("Some test script");
        stubWebDriver1.executeAsyncScript("Some test async script");

        assertThat(javaScriptListener1.messages,
            hasItems("Attempt to perform java script: Some test script",
                "Java script Some test script was performed",
                "Attempt to perform java script: Some test async script",
                "Java script Some test async script was performed"));

        assertThat("The second java script listener should have no messages",
            javaScriptListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother5() {
        try {
            stubWebDriver1.getWindowHandle();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        try {
            stubWebDriver1.findElementByXPath("Some Path").getScreenshotAs(OutputType.BASE64);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        assertThat(exceptionListener1.messages,
            hasItems("The exception was thrown: "
                    + new RuntimeException("Test WebDriver exception").toString(),
                "The exception was thrown: "
                    + new RuntimeException("Test web element exception").toString()));
        assertThat("The second exception listener should have no messages",
            exceptionListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother6() {
        Alert alert = stubWebDriver1.switchTo().alert();
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

        assertThat(alertListener1.messages,
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

        assertThat("The second alert listener should have no messages",
            alertListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother7() {
        stubWebDriver1.context("NATIVE_APP");
        stubWebDriver1.context("WEB_VIEW");

        assertThat(contextListener1.messages,
            hasItems("Attempt to change current context to NATIVE_APP",
                "The previous context has been changed to NATIVE_APP",
                "Attempt to change current context to WEB_VIEW",
                "The previous context has been changed to WEB_VIEW"));

        assertThat("The second context listener should have no messages",
            contextListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother8() {
        stubWebDriver1.rotate(ScreenOrientation.LANDSCAPE);
        stubWebDriver1.rotate(ScreenOrientation.PORTRAIT);

        assertThat(rotationListener1.messages,
            hasItems("Attempt to change screen orientation. The new screen orientation is "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "The screen orientation has been changed to "
                    + ScreenOrientation.LANDSCAPE.toString(),
                "Attempt to change screen orientation. The new screen orientation is "
                    + ScreenOrientation.PORTRAIT.toString(),
                "The screen orientation has been changed to "
                    + ScreenOrientation.PORTRAIT.toString()));
        assertThat("The second rotation listener should have no messages",
            rotationListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother9() {
        WebDriver.Window window = stubWebDriver1.manage().window();
        Dimension d = new Dimension(500, 500);
        window.setSize(d);

        Point p = new Point(50, 50);
        window.setPosition(p);

        window.maximize();

        assertThat(windowListener1.messages,
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
        assertThat("The second window listener should have no messages",
            windowListener2.messages.size(), is(0));
    }
}
