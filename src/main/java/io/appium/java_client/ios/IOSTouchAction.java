package io.appium.java_client.ios;

import io.appium.java_client.CreatesSwipeAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;


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

    /**
     * Double taps an element, offset from upper left corner.
     *
     * @param el element to tap.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public TouchAction doubleTap(WebElement el, int x, int y) {
        ActionParameter action = new ActionParameter("doubleTap", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Double taps an element, offset from upper left corner.
     *
     * @param el element to tap.
     * @return this TouchAction, for chaining.
     */
    public TouchAction doubleTap(WebElement el) {
        ActionParameter action = new ActionParameter("doubleTap", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }
}
