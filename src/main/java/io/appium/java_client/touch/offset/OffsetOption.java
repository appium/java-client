package io.appium.java_client.touch.offset;

import static java.util.Optional.ofNullable;

import org.openqa.selenium.Point;

import java.util.Map;

public class OffsetOption<T extends OffsetOption<T>> extends AbstractPositionOption<T> {

    /**
     * It creates a built instance of {@link PointOption} which takes x and y relative offsets.
     * This is offset from the upper left corner of an element or the position of the previous action.
     *
     * @param xOffset is x-offset value.
     * @param yOffset is y-offset value.
     * @return a built option
     */
    public static OffsetOption offset(int xOffset, int yOffset) {
        return new OffsetOption().position(xOffset, yOffset);
    }

    /**
     * It defines x and y relative offsets.
     * This is offset from the upper left corner of an element or from the position of the previous action.
     *
     * @param xOffset is x-offset value.
     * @param yOffset is y-offset value.
     * @return self-reference
     */
    @Override
    public T position(int xOffset, int yOffset) {
        this.offset = new Point(xOffset, yOffset);
        return (T) this;
    }

    @Override
    protected void verify() {
        ofNullable(offset).orElseThrow(() -> new IllegalArgumentException(
                "Relative X and Y offset values must be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.put("x", offset.x);
        result.put("y", offset.y);
        return result;
    }
}
