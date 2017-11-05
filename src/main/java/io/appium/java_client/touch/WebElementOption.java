package io.appium.java_client.touch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.util.Map;

public class WebElementOption extends PositionOffsetOption<WebElementOption> {

    private HasIdentity elementId;

    /**
     * This method creates a build instance of the {@link WebElementOption}
     *
     * @param element is the element to calculate offset from.
     * @param x is the x-offset from the upper left corner of the given element.
     * @param y is the y-offset from the upper left corner of the given element.
     * @return the built option
     */
    public static WebElementOption elementOption(WebElement element, int x, int y) {
        return new WebElementOption().withElement(element).withOffet(x, y);
    }

    /**
     * This method creates a build instance of the {@link WebElementOption}
     *
     * @param element is the element to calculate offset from.
     * @return the built option
     */
    public static WebElementOption elementOption(WebElement element) {
        return elementOption(element, 0, 0);
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
        elementId = HasIdentity.class.cast(element);
        return this;
    }

    @Override
    protected void verify() {
        ofNullable(elementId).orElseThrow(() ->
            new IllegalArgumentException("Element should be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.put("element", elementId.getId());
        return result;
    }
}
