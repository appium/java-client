package io.appium.java_client.android;

import static io.appium.java_client.TestUtils.getCenter;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AndroidTouchTest extends BaseAndroidTest {

    @Before
    public void setUp() {
        driver.resetApp();
    }

    @Test public void dragNDropByElementTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
        driver.startActivity(activity);
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        TouchAction dragNDrop = new TouchAction(driver)
                        .longPress(element(dragDot1))
                        .moveTo(element(dragDot3))
                        .release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByElementAndDurationTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
        driver.startActivity(activity);
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        TouchAction dragNDrop = new TouchAction(driver)
                .longPress(longPressOptions()
                        .withElement(element(dragDot1))
                        .withDuration(ofSeconds(2)))
                .moveTo(element(dragDot3))
                .release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByCoordinatesTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
        driver.startActivity(activity);
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        Point center1 = getCenter(dragDot1);
        Point center2 = getCenter(dragDot3);

        TouchAction dragNDrop = new TouchAction(driver)
                .longPress(point(center1.x, center1.y))
                .moveTo(point(center2.x, center2.y))
                .release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void dragNDropByCoordinatesAndDurationTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
        driver.startActivity(activity);
        WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));

        WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
        assertEquals("Drag text not empty", "", dragText.getText());

        Point center1 = getCenter(dragDot1);
        Point center2 = getCenter(dragDot3);

        TouchAction dragNDrop = new TouchAction(driver)
                .longPress(longPressOptions()
                        .withPosition(point(center1.x, center1.y))
                        .withDuration(ofSeconds(2)))
                .moveTo(point(center2.x, center2.y))
                .release();
        dragNDrop.perform();
        assertNotEquals("Drag text empty", "", dragText.getText());
    }

    @Test public void pressByCoordinatesTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.Buttons1");
        driver.startActivity(activity);
        Point point = driver.findElement(By.id("io.appium.android.apis:id/button_toggle")).getLocation();
        new TouchAction(driver)
                .press(point(point.x + 20, point.y + 30))
                .waitAction(waitOptions(ofSeconds(1)))
                .release()
                .perform();
        assertEquals("ON", driver.findElement(By.id("io.appium.android.apis:id/button_toggle")).getText());
    }

    @Test public void pressByElementTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.Buttons1");
        driver.startActivity(activity);
        new TouchAction(driver)
                .press(element(driver.findElement(By.id("io.appium.android.apis:id/button_toggle"))))
                .waitAction(waitOptions(ofSeconds(1)))
                .release()
                .perform();
        assertEquals("ON", driver.findElement(By.id("io.appium.android.apis:id/button_toggle")).getText());
    }

    @Test public void tapActionTestByElement() throws Exception {
        Activity activity = new Activity("io.appium.android.apis", ".view.ChronometerDemo");
        driver.startActivity(activity);
        WebElement chronometer = driver.findElement(By.id("io.appium.android.apis:id/chronometer"));

        TouchAction startStop = new TouchAction(driver)
                .tap(tapOptions().withElement(element(driver.findElement(By.id("io.appium.android.apis:id/start")))))
                .waitAction(waitOptions(ofSeconds(2)))
                .tap(tapOptions().withElement(element(driver.findElement(By.id("io.appium.android.apis:id/stop")))));

        startStop.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());
    }

    @Test public void tapActionTestByCoordinates() throws Exception {
        Activity activity = new Activity("io.appium.android.apis", ".view.ChronometerDemo");
        driver.startActivity(activity);
        WebElement chronometer = driver.findElement(By.id("io.appium.android.apis:id/chronometer"));

        Point center1 = getCenter(driver.findElement(By.id("io.appium.android.apis:id/start")));

        TouchAction startStop = new TouchAction(driver)
                .tap(point(center1.x, center1.y))
                .tap(element(driver.findElement(By.id("io.appium.android.apis:id/stop")), 5, 5));
        startStop.perform();

        String time = chronometer.getText();
        assertNotEquals(time, "Initial format: 00:00");
        Thread.sleep(2500);
        assertEquals(time, chronometer.getText());
    }

    @Test public void horizontalSwipingTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.Gallery1");
        driver.startActivity(activity);

        WebElement gallery = driver.findElement(By.id("io.appium.android.apis:id/gallery"));
        List<WebElement> images = gallery.findElements(MobileBy.className("android.widget.ImageView"));
        int originalImageCount = images.size();
        Point location = gallery.getLocation();
        Point center = getCenter(gallery);

        TouchAction swipe = new TouchAction(driver)
                .press(element(images.get(2),-10, center.y - location.y))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(element(gallery,10,center.y - location.y))
                .release();
        swipe.perform();
        assertNotEquals(originalImageCount,
                gallery.findElements(MobileBy.className("android.widget.ImageView")).size());
    }

    @Test public void multiTouchTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.Buttons1");
        driver.startActivity(activity);
        TouchAction press = new TouchAction(driver)
                .press(element(driver.findElement(By.id("io.appium.android.apis:id/button_toggle"))))
                .waitAction(waitOptions(ofSeconds(1)))
                .release();
        new MultiTouchAction(driver)
                .add(press)
                .perform();
        assertEquals("ON", driver.findElement(By.id("io.appium.android.apis:id/button_toggle")).getText());
    }

}
