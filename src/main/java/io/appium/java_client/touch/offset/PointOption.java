package io.appium.java_client.touch.offset;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import org.openqa.selenium.Point;

import java.util.Map;

public class PointOption extends AbstractPositionOption<PointOption> {

    private static final String ERROR_MESSAGE_TEMPLATE = "%s coordinate value should be equal or greater than zero";

    /**
     * It creates a built instance of {@link PointOption} which takes x and y coordinates.
     * This is offset from the upper left corner of the screen.
     *
     * @param xOffset is x value.
     * @param yOffset is y value.
     * @return a built option
     */
    public static PointOption coordinates(int xOffset, int yOffset) {
        return new PointOption().position(xOffset, yOffset);
    }

    /**
     * It defines x and y coordinates.
     * This is offset from the upper left corner of the screen.
     *
     * @param xOffset is x value.
     * @param yOffset is y value.
     * @return self-reference
     */
    @Override
    public PointOption position(int xOffset, int yOffset) {
        checkArgument(xOffset >= 0, format(ERROR_MESSAGE_TEMPLATE, "X"));
        checkArgument(yOffset >= 0, format(ERROR_MESSAGE_TEMPLATE, "Y"));
        offset = new Point(xOffset, yOffset);
        return this;
    }

    @Override
    protected void verify() {
        ofNullable(offset).orElseThrow(() -> new IllegalArgumentException(
                "Coordinate values must be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.put("x", offset.x);
        result.put("y", offset.y);
        return result;
    }
}
