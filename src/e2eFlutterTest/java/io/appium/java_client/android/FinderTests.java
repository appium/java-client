package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FinderTests extends BaseFlutterTest {

    @Test
    void testFlutterByKey() {
        WebElement userNameField = driver.findElement(AppiumBy.flutterKey("username_text_field"));
        assertEquals("admin", userNameField.getText());
        userNameField.clear();
        driver.findElement(AppiumBy.flutterKey("username_text_field")).sendKeys("admin123");
        assertEquals("admin123", userNameField.getText());
    }

    @Test
    void testFlutterByType() {
        WebElement loginButton = driver.findElement(AppiumBy.flutterType("ElevatedButton"));
        assertEquals(loginButton.findElement(AppiumBy.flutterType("Text")).getText(), "Login");
    }

    @Test
    void testFlutterText() {
        WebElement loginButton = driver.findElement(AppiumBy.flutterText("Login"));
        assertEquals(loginButton.getText(), "Login");
        loginButton.click();

        assertEquals(1, driver.findElements(AppiumBy.flutterText("Slider")).size());
    }

    @Test
    void testFlutterTextContaining() {
        WebElement loginButton = driver.findElement(BaseFlutterTest.LOGIN_BUTTON);
        loginButton.click();
        assertEquals(driver.findElement(AppiumBy.flutterTextContaining("Vertical")).getText(),
                "Vertical Swiping");
    }

    @Test
    void testFlutterSemanticsLabel() {
        WebElement loginButton = driver.findElement(BaseFlutterTest.LOGIN_BUTTON);
        loginButton.click();
        openScreen("Lazy Loading");

        WebElement messageField = driver.findElement(AppiumBy.flutterSemanticsLabel("message_field"));
        assertEquals(messageField.getText(),
                "Hello world");
    }

    @Test
    void testFlutterDescendant() {
        WebElement loginButton = driver.findElement(BaseFlutterTest.LOGIN_BUTTON);
        loginButton.click();
        openScreen("Nested Scroll");

        AppiumBy descendantBy = AppiumBy.flutterDescendant(
                AppiumBy.flutterKey("parent_card_1"),
                AppiumBy.flutterText("Child 2")
        );
        WebElement childElement = driver.findElement(descendantBy);
        assertEquals("Child 2",
                childElement.getText());
    }

    @Test
    void testFlutterAncestor() {
        WebElement loginButton = driver.findElement(BaseFlutterTest.LOGIN_BUTTON);
        loginButton.click();
        openScreen("Nested Scroll");

        AppiumBy ancestorBy = AppiumBy.flutterAncestor(
                AppiumBy.flutterText("Child 2"),
                AppiumBy.flutterKey("parent_card_1")
        );
        WebElement parentElement = driver.findElement(ancestorBy);
        assertTrue(parentElement.isDisplayed());
    }
}
