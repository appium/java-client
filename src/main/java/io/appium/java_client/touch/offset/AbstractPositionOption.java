package io.appium.java_client.touch.offset;

import io.appium.java_client.touch.ActionOptions;
import org.openqa.selenium.Point;

public abstract class AbstractPositionOption<T extends AbstractPositionOption<T>> extends ActionOptions<T> {
    protected Point offset;

    /**
     * Defines the offset or coordinates
     * from the corresponding action.
     *
     * @param xOffset is x offset/value.
     * @param yOffset is y offset/value.
     * @return this instance for chaining.
     */
    public abstract T position(int xOffset, int yOffset);
}
