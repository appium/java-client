package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.util.List;

public class AndroidTouchTest extends BaseAndroidTest {

    @Before
    public void setUp() throws Exception {
        driver.resetApp();
    }

    @Test public void dragNDropByElementTest() throws Exception  {
        driver.startActivity("io.appium.android.apis", ".view.DragAndDropDemo");
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        TouchAction dragNDrop =
                new TouchAction(driver).longPress(dragDot1).moveTo(dragDot3).release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByElementAndDurationTest() throws Exception  {
        driver.startActivity("io.appium.android.apis", ".view.DragAndDropDemo");
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        TouchAction dragNDrop =
                new TouchAction(driver).longPress(dragDot1, 2000).moveTo(dragDot3).release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByCoordinatesTest() throws Exception  {
        driver.startActivity("io.appium.android.apis", ".view.DragAndDropDemo");
        AndroidElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        AndroidElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        Point center1 = dragDot1.getCenter();
        Point center2 = dragDot3.getCenter();

        TouchAction dragNDrop =
                new TouchAction(driver).longPress(center1.x, center1.y).moveTo(center2.x, center2.y).release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByCoordinatesAndDurationTest() throws Exception  {
        driver.startActivity("io.appium.android.apis", ".view.DragAndDropDemo");
        AndroidElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        AndroidElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        Point center1 = dragDot1.getCenter();
        Point center2 = dragDot3.getCenter();

        TouchAction dragNDrop =
                new TouchAction(driver).longPress(center1.x, center1.y, 2000).moveTo(center2.x, center2.y).release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void pressByCoordinatesTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        Point point =
                driver.findElementById("io.appium.android.apis:id/button_toggle").getLocation();
        new TouchAction(driver).press(point.x + 20, point.y + 30).waitAction(1000).release().perform();
        assertEquals("ON" ,driver
                .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void pressByElementTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        new TouchAction(driver).press(driver.findElementById("io.appium.android.apis:id/button_toggle"))
                .waitAction(1000).release().perform();
        assertEquals("ON" ,driver
                .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }
    
    @Test public void clickAndHoldTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        new TouchActions(driver)
        		.clickAndHold(driver.findElementById("io.appium.android.apis:id/button_toggle"))
				.perform();
        assertEquals("ON" ,driver
                .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

    @Test public void tapActionTestByElement() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.ChronometerDemo");
        AndroidElement chronometer =
                driver.findElementById("io.appium.android.apis:id/chronometer");

        TouchAction startStop = new TouchAction(driver)
                .tap(driver.findElementById("io.appium.android.apis:id/start")).waitAction(2000)
                .tap(driver.findElementById("io.appium.android.apis:id/stop"));

        startStop.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());
    }

    @Test public void tapActionTestByCoordinates() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.ChronometerDemo");
        AndroidElement chronometer =
                driver.findElementById("io.appium.android.apis:id/chronometer");

        Point center1 = driver.findElementById("io.appium.android.apis:id/start").getCenter();

        TouchAction startStop = new TouchAction(driver)
                .tap(center1.x, center1.y)
                .tap(driver.findElementById("io.appium.android.apis:id/stop"), 5, 5);
        startStop.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());
    }
    
    @Test public void horizontalflickTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Gallery1");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
        int originalImageCount = images.size();
        Point location = gallery.getLocation();
        Point center = gallery.getCenter();
        
        TouchActions actions = new TouchActions(driver);
        actions.flick(gallery, -10, center.y - location.y, 1000)
        		.perform();
        
        assertNotEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());
    }

    @Test public void horizontalSwipingTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Gallery1");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
        int originalImageCount = images.size();
        Point location = gallery.getLocation();
        Point center = gallery.getCenter();

        TouchAction swipe = new TouchAction(driver).press(images.get(2), -10, center.y - location.y)
                .waitAction(2000).moveTo(gallery, 10, center.y - location.y).release();
        swipe.perform();
        assertNotEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());
    }

    @Test public void multiTouchTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        TouchAction press = new TouchAction(driver);
        press.press(driver.findElementById("io.appium.android.apis:id/button_toggle")).waitAction(1000).release();
        new MultiTouchAction(driver).add(press)
                .perform();
        assertEquals("ON" ,driver
                .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }

}
