package io.appium.java_client.events;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import io.appium.java_client.MobileBy;
import io.appium.java_client.events.listeners.TestListener;

public class BaseListenerTest {

    protected boolean assertThatSearchListenerWorks(EmptyWebDriver driver, TestListener listener,
        String prefix) {
        try {
            driver.findElement(By.id("someId"));
            assertThat(listener.messages,
                    contains(prefix + "Attempt to find something using By.id: someId. The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null"));

            driver.findElements(By.id("someId2"));

            assertThat(listener.messages,
                    contains(prefix + "Attempt to find something using By.id: someId. The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.id: someId2. The root element is null",
                            prefix + "The searching for something using By.id: someId2 has beed finished. "
                                    + "The root element was null"));

            driver.findElement(By.id("someId")).findElement(By.className("someClazz"));

            assertThat(listener.messages,
                    contains(prefix + "Attempt to find something using By.id: someId. The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.id: someId2. The root element is null",
                            prefix + "The searching for something using By.id: someId2 has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.id: someId. "
                                    + "The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.className: someClazz. "
                                    + "The root element is io.appium.java_client.events.StubWebElement",
                            prefix + "The searching for something using By.className: someClazz has beed finished. "
                                    + "The root element was io.appium.java_client.events.StubWebElement"));

            driver.findElements(By.id("someId2")).get(0).findElements(By.className("someClazz2"));
            assertThat(listener.messages,
                    contains(prefix + "Attempt to find something using By.id: someId. The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.id: someId2. The root element is null",
                            prefix + "The searching for something using By.id: someId2 has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.id: someId. The root element is null",
                            prefix + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.className: someClazz. "
                                    + "The root element is io.appium.java_client.events.StubWebElement",
                            prefix + "The searching for something using By.className: someClazz has beed finished. "
                                    + "The root element was io.appium.java_client.events.StubWebElement",
                            prefix + "Attempt to find something using By.id: someId2. The root element is null",
                            prefix + "The searching for something using By.id: someId2 has beed finished. "
                                    + "The root element was null",
                            prefix + "Attempt to find something using By.className: someClazz2. "
                                    + "The root element is io.appium.java_client.events.StubWebElement",
                            prefix + "The searching for something using By.className: someClazz2 has beed finished. "
                                    + "The root element was io.appium.java_client.events.StubWebElement"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatSearchListenerWorksAgainstElements(EmptyWebDriver driver, TestListener listener,
        String prefix) {
        try {
            List<StubWebElement> els = driver.findElementsByAccessibilityId("SomeAccessibility");
            StubWebElement e = driver.findElementByAccessibilityId("AnotherAccessibility");

            e.findElementByAccessibilityId("SomeAccessibility")
                    .findElement(MobileBy.AndroidUIAutomator("Android UI Automator"));

            assertThat(listener.messages,
                    contains(prefix + "Attempt to find something using By.AndroidUIAutomator: Android UI Automator. "
                                    + "The root element is io.appium.java_client.events.StubWebElement",
                            prefix + "The searching for something using By.AndroidUIAutomator: "
                                    + "Android UI Automator has "
                                    + "beed finished. "
                                    + "The root element was io.appium.java_client.events.StubWebElement"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatNavigationListenerWorks(EmptyWebDriver driver,
        TestListener listener,  String prefix) throws Exception {
        try {
            driver.get("www.google.com");
            driver.navigate().to("www.google2.com");
            driver.navigate().to(new URL("https://www.google3.com"));
            driver.navigate().forward();
            driver.navigate().back();
            driver.navigate().refresh();

            assertThat(listener.messages,
                    contains(prefix + "Attempt to navigate to www.google.com",
                            prefix + "Navigation to www.google.com was successful",
                            prefix + "Attempt to navigate to www.google2.com",
                            prefix + "Navigation to www.google2.com was successful",
                            prefix + "Attempt to navigate to https://www.google3.com",
                            prefix + "Navigation to https://www.google3.com was successful",
                            prefix + "Attempt to navigate forward",
                            prefix + "Navigation forward was successful",
                            prefix + "Attempt to navigate back",
                            prefix + "Navigation back was successful",
                            prefix + "Attempt to refresh",
                            prefix + "The refreshing was successful"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatElementListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            StubWebElement e = driver.findElementByAccessibilityId("SomeAccessibility");;
            e.click();
            e.sendKeys("Test keys");

            assertThat(listener.messages,
                    contains(prefix + "Attempt to click on the element",
                            prefix + "Thee element was clicked",
                            prefix + "Attempt to change value of the element",
                            prefix + "The value of the element was changed"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }


    protected boolean assertThatJavaScriptListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            driver.executeScript("Some test script");
            driver.executeAsyncScript("Some test async script");

            assertThat(listener.messages,
                    contains(prefix + "Attempt to perform java script: Some test script",
                            prefix + "Java script Some test script was performed",
                            prefix + "Attempt to perform java script: Some test async script",
                            prefix + "Java script Some test async script was performed"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatExceptionListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            try {
                driver.getPageSource();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }

            try {
                driver.findElementByAccessibilityId("SomeAccessibility").getRect();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }

            assertThat(listener.messages,
                    contains(prefix + "The exception was thrown: "
                                    + WebDriverException.class,
                            prefix + "The exception was thrown: "
                                    + WebDriverException.class));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatAlertListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            alert.dismiss();
            alert.sendKeys("Keys");

            assertThat(listener.messages,
                    contains(prefix + "Attempt to accept alert",
                            prefix + "The alert was accepted",
                            prefix + "Attempt to dismiss alert",
                            prefix + "The alert was dismissed",
                            prefix + "Attempt to send keys to alert",
                            prefix + "Keys were sent to alert"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatConrextListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            driver.context("NATIVE_APP");
            driver.context("WEB_VIEW");

            assertThat(listener.messages,
                    contains(prefix + "Attempt to change current context to NATIVE_APP",
                            prefix + "The previous context has been changed to NATIVE_APP",
                            prefix + "Attempt to change current context to WEB_VIEW",
                            prefix + "The previous context has been changed to WEB_VIEW"));
            return true;
        } finally {
            listener.messages.clear();
        }
    }


    protected boolean assertThatRotationListenerWorks(EmptyWebDriver driver, TestListener listener,
        String prefix) {
        try {
            driver.rotate(ScreenOrientation.LANDSCAPE);
            driver.rotate(ScreenOrientation.PORTRAIT);

            assertThat(listener.messages,
                    contains(prefix + "Attempt to change screen orientation. The new screen orientation is "
                                    + ScreenOrientation.LANDSCAPE.toString(),
                            prefix + "The screen orientation has been changed to "
                                    + ScreenOrientation.LANDSCAPE.toString(),
                            prefix + "Attempt to change screen orientation. The new screen orientation is "
                                    + ScreenOrientation.PORTRAIT.toString(),
                            prefix + "The screen orientation has been changed to "
                                    + ScreenOrientation.PORTRAIT.toString()));
            return true;
        } finally {
            listener.messages.clear();
        }
    }

    protected boolean assertThatWindowListenerWorks(EmptyWebDriver driver, TestListener listener, String prefix) {
        try {
            WebDriver.Window window = driver.manage().window();
            Dimension d = new Dimension(500, 500);
            window.setSize(d);

            Point p = new Point(50, 50);
            window.setPosition(p);

            window.maximize();

            driver.switchTo().window("Test window");

            assertThat(listener.messages,
                    contains(prefix + "Attempt to change size of the window. The height is " + d.getHeight()
                                    + " the width is " + d.getWidth(),
                            prefix + "Size of the window has been changed. The height is " + d.getHeight()
                                    + " the width is " + d.getWidth(),
                            prefix + "Attempt to change position of the window. The X is " + p.getX()
                                    + " the Y is " + p.getY(),
                            prefix + "The position the window has been changed. The X is " + p.getX()
                                    + " the Y is " + p.getY(),
                            prefix + "Attempt to maximize the window.",
                            prefix + "The window has been maximized",
                            prefix + "Attempt to switch to window Test window",
                            prefix + "driver is switched to window Test window"));
            return true;
        } finally {
            listener.messages.clear();
        }

    }
}
