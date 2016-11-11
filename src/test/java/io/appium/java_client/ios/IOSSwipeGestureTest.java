package io.appium.java_client.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IOSSwipeGestureTest extends BaseIOSTest {
    @Test
    public void horizontalSwipingTest() {
        MobileElement slider = driver.findElementByClassName("UIASlider");

        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(slider, SwipeElementDirection.LEFT, slider.getSize().getWidth() / 2, 0, 3000);
        assertEquals("1%", slider.getAttribute("value"));

        IOSTouchAction touchAction2 = new IOSTouchAction(driver);
        touchAction2.swipe(slider, SwipeElementDirection.RIGHT, 2, 0, 3000);
        assertEquals("100%", slider.getAttribute("value"));
    }
}
