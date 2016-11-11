package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Point;

public class AndroidSwipeGestureTest extends BaseAndroidTest {

    @Before
    public void setUp() throws Exception {
        driver.resetApp();
    }

    @Test
    public void verticalSwipingTest() throws Exception {
        driver.findElementByAccessibilityId("Views").click();
        AndroidElement listView = driver.findElementByClassName("android.widget.ListView");
        MobileElement textView = driver.findElementById("android:id/text1");

        String originalText = textView.getText();

        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        touchAction.swipe(listView, SwipeElementDirection.UP, 20, 15, 1000).perform();
        assertNotEquals(originalText, textView.getText());

        AndroidTouchAction touchAction2 = new AndroidTouchAction(driver);
        touchAction2.swipe(listView, SwipeElementDirection.DOWN, 20, 15, 1000).perform();

        Thread.sleep(5000);
        assertEquals(originalText, textView.getText());
    }

    @Test public void horizontalSwipingTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Gallery1");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        int originalImageCount = gallery
                .findElementsByClassName("android.widget.ImageView").size();

        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        touchAction.swipe(gallery, SwipeElementDirection.LEFT, 5, 5, 2000).perform();
        assertNotEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());

        AndroidTouchAction touchAction2 = new AndroidTouchAction(driver);
        touchAction2.swipe(gallery, SwipeElementDirection.RIGHT, 5, 5, 2000).perform();
        assertEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());
    }

    @Test public void swipeFromElementToElement() {
        driver.findElementByAccessibilityId("Views").click();

        AndroidElement e1 = driver.findElementByAccessibilityId("Chronometer");
        AndroidElement e2 = driver.findElementByAccessibilityId("Focus");

        Point originalLocation = e2.getLocation();
        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        touchAction.swipe(e2, e1, 2000).perform();

        Point newLocation = e2.getLocation();
        assertNotEquals(originalLocation, newLocation);
    }

    @Test public void swipeFromCoordinatesToElement() {
        driver.findElementByAccessibilityId("Views").click();

        AndroidElement e1 = driver.findElementByAccessibilityId("Chronometer");
        AndroidElement e2 = driver.findElementByAccessibilityId("Focus");

        Point originalLocation = e2.getLocation();
        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        touchAction.swipe(originalLocation.x + 50, originalLocation.y, e1, 2000).perform();

        Point newLocation = e2.getLocation();
        assertNotEquals(originalLocation, newLocation);
    }

}
