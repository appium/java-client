package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FinderTests extends BaseFlutterTest {


    @Test
    public void testFlutterByKey() {
        WebElement userNameField = driver.findElement(AppiumBy.flutterKey("username_text_field"));
        assertEquals("admin", userNameField.getText());
        userNameField.clear();
        driver.findElement(AppiumBy.flutterKey("username_text_field")).sendKeys("admin123");
        assertEquals("admin123", userNameField.getText());
    }

    @Test
    public void testFlutterByType() {
        WebElement loginButton = driver.findElement(AppiumBy.flutterType("ElevatedButton"));
        assertEquals(loginButton.findElement(AppiumBy.flutterType("Text")).getText(), "Login");
    }

    @Test
    public void testFlutterText() {
        WebElement loginButton = driver.findElement(AppiumBy.flutterText("Login"));
        assertEquals(loginButton.getText(), "Login");
        loginButton.click();

        assertEquals(1, driver.findElements(AppiumBy.flutterText("Slider")).size());
    }

    @Test
    public void testFlutterTextContaining() {
        WebElement loginButton = driver.findElement(this.loginButton);
        loginButton.click();
        assertEquals(driver.findElement(AppiumBy.flutterTextContaining("Vertical")).getText(),
                "Vertical Swiping");
    }

    @Test
    public void testFlutterSemanticsLabel() {
        WebElement loginButton = driver.findElement(this.loginButton);
        loginButton.click();
        openScreen("Lazy Loading");

        WebElement messageField = driver.findElement(AppiumBy.flutterSemanticsLabel("message_field"));
        assertEquals(messageField.getText(),
                "Hello world");
    }
}
