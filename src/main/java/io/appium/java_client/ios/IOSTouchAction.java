package io.appium.java_client.ios;

import io.appium.java_client.CreatesSwipeAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;


public class IOSTouchAction extends TouchAction implements CreatesSwipeAction {

    public IOSTouchAction(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
    }

    @Override
    public TouchAction swipe(int startX, int startY, int endX, int endY, int duration) {
        int xOffset = endX - startX;
        int yOffset = endY - startY;
        return press(startX, startY).waitAction(duration).moveTo(xOffset, yOffset).release();
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
