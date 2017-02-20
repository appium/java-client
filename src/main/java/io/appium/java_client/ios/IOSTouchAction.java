package io.appium.java_client.ios;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;


public class IOSTouchAction extends TouchAction {

    public IOSTouchAction(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
    }

    /**
     * Double taps an element, offset from upper left corner.
     *
     * @param el element to tap.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public IOSTouchAction doubleTap(WebElement el, int x, int y) {
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
    public IOSTouchAction doubleTap(WebElement el) {
        ActionParameter action = new ActionParameter("doubleTap", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }
}
