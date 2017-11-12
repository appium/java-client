package io.appium.java_client.touch.offset;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import io.appium.java_client.touch.ActionOptions;
import org.openqa.selenium.Point;

import java.util.Map;

public class PointOption<T extends PointOption<T>> extends ActionOptions<T> {

    protected Point coordinates;

    private static final String ERROR_MESSAGE_TEMPLATE = "%s coordinate value should be equal or greater than zero";

    /**
     * It creates a built instance of {@link PointOption} which takes x and y coordinates.
     * This is offset from the upper left corner of the screen.
     *
     * @param xOffset is x value.
     * @param yOffset is y value.
     * @return a built option
     */
    public static PointOption point(int xOffset, int yOffset) {
        return new PointOption().withCoordinates(xOffset, yOffset);
    }

    /**
     * It defines x and y coordinates.
     * This is offset from the upper left corner of the screen.
     *
     * @param xOffset is x value.
     * @param yOffset is y value.
     * @return self-reference
     */
    public T withCoordinates(int xOffset, int yOffset) {
        checkArgument(xOffset >= 0, format(ERROR_MESSAGE_TEMPLATE, "X"));
        checkArgument(yOffset >= 0, format(ERROR_MESSAGE_TEMPLATE, "Y"));
        coordinates = new Point(xOffset, yOffset);
        return (T) this;
    }

    @Override
    protected void verify() {
        ofNullable(coordinates).orElseThrow(() -> new IllegalArgumentException(
                "Coordinate values must be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.put("x", coordinates.x);
        result.put("y", coordinates.y);
        return result;
    }
}
