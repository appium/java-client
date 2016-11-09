package io.appium.java_client.android;

import io.appium.java_client.CreatesSwipeAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;

public class AndroidTouchAction extends TouchAction implements CreatesSwipeAction {

    public AndroidTouchAction(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
    }

    @Override
    public TouchAction swipe(int startX, int startY, int endx, int endy, int duration) {
        return press(startX, startY).waitAction(duration).moveTo(endx, endy).release();
    }

    @Override
    public TouchAction swipe(int startX, int startY, WebElement element, int duration) {
        return press(startX, startY).waitAction(duration).moveTo(element).release();
    }

    @Override
    public TouchAction swipe(WebElement element1, WebElement element2, int duration) {
        return press(element1).waitAction(duration).moveTo(element2).release();
    }
}
