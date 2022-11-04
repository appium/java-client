package io.appium.java_client.touch.offset;

import io.appium.java_client.touch.ActionOptions;
import org.openqa.selenium.Point;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class PointOption<T extends PointOption<T>> extends ActionOptions<T> {

    protected Point coordinates;

    /**
     * It creates a built instance of {@link PointOption} which takes x and y coordinates.
     * This is offset from the upper left corner of the screen.
     *
     * @param offset is an offset value.
     * @return a built option
     */
    public static PointOption point(Point offset) {
        return new PointOption().withCoordinates(offset);
    }

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
     * @param offset is an offset value.
     * @return self-reference
     */
    public T withCoordinates(Point offset) {
        return withCoordinates(offset.x, offset.y);
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
        coordinates = new Point(xOffset, yOffset);
        //noinspection unchecked
        return (T) this;
    }

    @Override
    protected void verify() {
        //noinspection ResultOfMethodCallIgnored
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
