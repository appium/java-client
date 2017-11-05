package io.appium.java_client.touch;

import static java.util.Optional.ofNullable;

import org.openqa.selenium.Point;

import java.util.Map;

public class PositionOffsetOption<T extends PositionOffsetOption<T>> extends ActionOptions<T> {
    private Point offset = new Point(0, 0);

    /**
     * It creates a built instance of {@link PositionOffsetOption} which takes x and y position
     * offsets. This is offset from the upper left corner of the screen/element or
     * from the corresponding action.
     *
     * @param xOffset is x-axis offset.
     * @param yOffset is y-axis offset.
     * @return a built option
     */
    public static PositionOffsetOption positionOffsetOption(int xOffset, int yOffset) {
        return new PositionOffsetOption().withOffet(xOffset, yOffset);
    }

    /**
     * Sets the offset from the upper left corner of the screen/element or
     * from the corresponding action.
     *
     * @param xOffset is x-axis offset.
     * @param yOffset is y-axis offset.
     * @return this instance for chaining.
     */
    public T withOffet(int xOffset, int yOffset) {
        this.offset = new Point(xOffset, yOffset);
        return (T) this;
    }

    @Override
    protected void verify() {
        ofNullable(offset).orElseThrow(() -> new IllegalArgumentException(
                "X and Y offset values must be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.put("x", offset.x);
        result.put("y", offset.y);
        return result;
    }
}
