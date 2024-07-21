package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.flutter.commands.ScrollParameter;
import io.appium.java_client.flutter.commands.WaitParameter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest extends BaseFlutterTest {

    AppiumBy.FlutterBy messageFieldLocator = AppiumBy.flutterKey("message_field");
    AppiumBy.FlutterBy toggleButtonLocator = AppiumBy.flutterKey("toggle_button");

    @Test
    public void testWaitCommand() {
        WebElement loginButton = driver.findElement(this.loginButton);
        loginButton.click();
        openScreen("Lazy Loading");

        WebElement messageField = driver.findElement(messageFieldLocator);
        WebElement toggleButton = driver.findElement(toggleButtonLocator);

        assertEquals(messageField.getText(), "Hello world");
        toggleButton.click();
        assertEquals(messageField.getText(), "Hello world");

        WaitParameter waitParameter = new WaitParameter().setLocator(messageFieldLocator);

        driver.waitForInVisible(waitParameter);
        assertEquals(0, driver.findElements(messageFieldLocator).size());
        toggleButton.click();
        driver.waitForVisible(waitParameter);
        assertEquals(1, driver.findElements(messageFieldLocator).size());
        assertEquals(messageField.getText(), "Hello world");
    }

    @Test
    public void testScrollTillVisibleCommand() {
        WebElement loginButton = driver.findElement(this.loginButton);
        loginButton.click();
        openScreen("Vertical Swiping");

        WebElement firstElement = driver.scrollTillVisible(new ScrollParameter(AppiumBy.flutterText("Java")));
        assertTrue(Boolean.parseBoolean(firstElement.getAttribute("displayed")));

        WebElement lastElement = driver.scrollTillVisible(new ScrollParameter(AppiumBy.flutterText("Protractor")));
        assertTrue(Boolean.parseBoolean(lastElement.getAttribute("displayed")));
        assertFalse(Boolean.parseBoolean(firstElement.getAttribute("displayed")));

        firstElement = driver.scrollTillVisible(
                new ScrollParameter(AppiumBy.flutterText("Java"),
                        ScrollParameter.ScrollDirection.UP)
        );
        assertTrue(Boolean.parseBoolean(firstElement.getAttribute("displayed")));
        assertFalse(Boolean.parseBoolean(lastElement.getAttribute("displayed")));
    }

}
