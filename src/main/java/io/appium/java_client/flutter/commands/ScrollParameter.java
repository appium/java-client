package io.appium.java_client.flutter.commands;

import com.google.common.base.Preconditions;
import io.appium.java_client.AppiumBy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Accessors(chain = true)
@Getter
@Setter
public class ScrollParameter extends FlutterCommandParameter {
    AppiumBy.FlutterBy scrollTo;
    WebElement scrollView;
    ScrollDirection scrollDirection;
    Integer delta;
    Integer maxScrolls;
    Integer settleBetweenScrollsTimeout;
    Duration dragDuration;

    private ScrollParameter() {
    }

    /**
     * Constructs a new ScrollOptions object with the given parameters.
     *
     * @param scrollTo the locator used for scrolling to a specific element
     */
    public ScrollParameter(AppiumBy.FlutterBy scrollTo) {
        this(scrollTo, ScrollDirection.DOWN);
    }

    /**
     * Constructs a new ScrollOptions object with the given parameters.
     *
     * @param scrollTo        the locator used for scrolling to a specific element
     * @param scrollDirection the direction in which to scroll (e.g., ScrollDirection.DOWN)
     * @throws IllegalArgumentException if scrollTo is null
     */
    public ScrollParameter(AppiumBy.FlutterBy scrollTo, ScrollDirection scrollDirection) {
        Preconditions.checkArgument(scrollTo != null, "Must supply a valid locator for scrollTo");
        this.scrollTo = scrollTo;
        this.scrollDirection = scrollDirection;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> params = new HashMap<>();

        params.put("finder", parseFlutterLocator(scrollTo));
        params.put("scrollView", scrollView);
        params.put("delta", delta);
        params.put("maxScrolls", maxScrolls);
        params.put("settleBetweenScrollsTimeout", settleBetweenScrollsTimeout);
        Optional.ofNullable(scrollDirection)
                .ifPresent(direction -> params.put("scrollDirection", direction.getDirection()));
        Optional.ofNullable(dragDuration)
                .ifPresent(direction -> params.put("dragDuration", dragDuration.getSeconds()));

        params.entrySet().removeIf(entry -> entry.getValue() == null);
        return Collections.unmodifiableMap(params);
    }

    @Getter
    public static enum ScrollDirection {
        UP("up"),
        RIGHT("right"),
        DOWN("down"),
        LEFT("left");

        private final String direction;

        ScrollDirection(String direction) {
            this.direction = direction;
        }
    }
}
