package io.appium.java_client.touch.offset;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class ElementOption extends PointOption<ElementOption> {

    private String elementId;

    /**
     * This method creates a build instance of the {@link ElementOption}.
     *
     * @param element is the element to calculate offset from.
     * @param offset is the offset from the upper left corner of the given element.
     * @return the built option
     */
    public static ElementOption element(WebElement element, Point offset) {
        return new ElementOption().withElement(element).withCoordinates(offset);
    }


    /**
     * This method creates a build instance of the {@link ElementOption}.
     *
     * @param element is the element to calculate offset from.
     * @param x is the x-offset from the upper left corner of the given element.
     * @param y is the y-offset from the upper left corner of the given element.
     * @return the built option
     */
    public static ElementOption element(WebElement element, int x, int y) {
        return new ElementOption().withElement(element).withCoordinates(x, y);
    }

    /**
     * This method creates a build instance of the {@link ElementOption}.
     *
     * @param element is the element to calculate offset from.
     * @return the built option
     */
    public static ElementOption element(WebElement element) {
        return new ElementOption().withElement(element);
    }

    /**
     * It defines x and y offset from the upper left corner of an element.
     *
     * @param offset is the offset from the upper left corner of the given element.
     * @return self-reference
     */
    @Override
    public ElementOption withCoordinates(Point offset) {
        super.withCoordinates(offset);
        return this;
    }

    /**
     * It defines x and y offset from the upper left corner of an element.
     *
     * @param xOffset is the x-offset from the upper left corner of the given element.
     * @param yOffset is the y-offset from the upper left corner of the given element.
     * @return self-reference
     */
    @Override
    public ElementOption withCoordinates(int xOffset, int yOffset) {
        super.withCoordinates(xOffset, yOffset);
        return this;
    }

    /**
     * This method sets the element as an option. It means that x/y offset is the offset
     * from the upper left corner of the given element.
     *
     * @param element is the element to calculate offset from.
     * @return self-reference
     */
    public ElementOption withElement(WebElement element) {
        requireNonNull(element);
        checkArgument(true, "Element should be an instance of the class which "
                + "extends org.openqa.selenium.remote.RemoteWebElement",
            element instanceof RemoteWebElement);
        elementId = ((RemoteWebElement) element).getId();
        return this;
    }

    @Override
    protected void verify() {
        ofNullable(elementId).orElseThrow(() ->
            new IllegalArgumentException("Element should be defined"));
    }

    @Override
    public Map<String, Object> build() {
        verify();
        final Map<String, Object> result = new HashMap<>();
        result.put("element", elementId);

        ofNullable(coordinates).ifPresent(point -> {
            result.put("x", point.x);
            result.put("y", point.y);
        });
        return result;
    }
}
