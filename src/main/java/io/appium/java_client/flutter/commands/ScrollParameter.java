package io.appium.java_client.flutter.commands;

import io.appium.java_client.AppiumBy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.internal.Require;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Accessors(chain = true)
@Getter
@Setter
public class ScrollParameter extends FlutterCommandParameter {
    private AppiumBy.FlutterBy scrollTo;
    private AppiumBy.FlutterBy scrollView;
    private ScrollDirection scrollDirection;
    private Integer delta;
    private Integer maxScrolls;
    private Integer settleBetweenScrollsTimeout;
    private Duration dragDuration;

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
        Require.precondition(scrollTo != null, "Must supply a valid locator for scrollTo");
        this.scrollTo = scrollTo;
        this.scrollDirection = scrollDirection;
    }

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> params = new HashMap<>();

        params.put("finder", parseFlutterLocator(scrollTo));
        Optional.ofNullable(scrollView)
                .ifPresent(scrollView -> params.put("scrollView", parseFlutterLocator(scrollView)));
        Optional.ofNullable(delta)
                .ifPresent(delta -> params.put("delta", delta));
        Optional.ofNullable(maxScrolls)
                .ifPresent(maxScrolls -> params.put("maxScrolls", maxScrolls));
        Optional.ofNullable(settleBetweenScrollsTimeout)
                .ifPresent(timeout -> params.put("settleBetweenScrollsTimeout", settleBetweenScrollsTimeout));
        Optional.ofNullable(scrollDirection)
                .ifPresent(direction -> params.put("scrollDirection", direction.getDirection()));
        Optional.ofNullable(dragDuration)
                .ifPresent(direction -> params.put("dragDuration", dragDuration.getSeconds()));

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
