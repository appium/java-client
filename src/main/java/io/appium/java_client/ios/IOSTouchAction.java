package io.appium.java_client.ios;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.TouchableElement;


public class IOSTouchAction extends TouchAction {

    public IOSTouchAction(MobileDriver driver) {
        super(driver);
    }

    /**
     * @see TouchableElement#swipe(int, int, int, int, int).
     */
    @Deprecated protected TouchAction swipe(int startx, int starty, int endx, int endy, int duration) {
        int endX = endx - startx;
        int endY = endy - starty;

        // appium converts press-wait-moveto-release to a swipe action
        return press(startx, starty).waitAction(duration).moveTo(endX, endY).release();
    }
}
