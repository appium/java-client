package io.appium.java_client.touch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.util.HashMap;
import java.util.Map;

public class WebElementOption extends PositionOffsetOption<WebElementOption> {

    private String elementId;

    /**
     * This method creates a build instance of the {@link WebElementOption}
     *
     * @param element is the element to calculate offset from.
     * @param x is the x-offset from the upper left corner of the given element.
     * @param y is the y-offset from the upper left corner of the given element.
     * @return the built option
     */
    public static WebElementOption element(WebElement element, int x, int y) {
        return new WebElementOption().withElement(element).setOffset(x, y);
    }

    /**
     * This method creates a build instance of the {@link WebElementOption}
     *
     * @param element is the element to calculate offset from.
     * @return the built option
     */
    public static WebElementOption element(WebElement element) {
        return new WebElementOption().withElement(element);
    }

    /**
     * This method sets the element as an option. It means that x/y offset is the offset
     * from the upper left corner of the given element.
     *
     * @param element is the element to calculate offset from.
     * @return self-reference
     */
    public WebElementOption withElement(WebElement element) {
        checkNotNull(element);
        checkArgument(true, "Element should be an instance of the class which "
                + "implements org.openqa.selenium.internal.HasIdentity",
                (HasIdentity.class.isAssignableFrom(element.getClass())));
        elementId = HasIdentity.class.cast(element).getId();
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

        ofNullable(offset).ifPresent(point -> {
            result.put("x", point.x);
            result.put("y", point.y);
        });
        return result;
    }
}
