package io.appium.java_client.flutter.commands;

import com.google.common.base.Preconditions;
import io.appium.java_client.AppiumBy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Accessors(chain = true)
@Getter
@Setter
public class ScrollOptions implements FlutterCommandOptions {
    AppiumBy.FlutterBy scrollTo;
    WebElement scrollView;
    ScrollDirection scrollDirection;
    Integer delta;
    Integer maxScrolls;
    Integer settleBetweenScrollsTimeout;
    Duration dragDuration;

    private ScrollOptions() {
    }

    /**
     * Constructs a new ScrollOptions object with the given parameters.
     *
     * @param scrollTo        the locator used for scrolling to a specific element
     * @param scrollDirection the direction in which to scroll (e.g., ScrollDirection.DOWN)
     * @throws IllegalArgumentException if scrollTo is null
     */
    public ScrollOptions(AppiumBy.FlutterBy scrollTo, ScrollDirection scrollDirection) {
        Preconditions.checkArgument(scrollTo != null, "Must supply a valid locator for scrollTo");
        this.scrollTo = scrollTo;
        this.scrollDirection = scrollDirection;
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of(
                "finder", scrollTo.toJson(),
                "scrollView", scrollView,
                "delta", delta,
                "maxScrolls", maxScrolls,
                "settleBetweenScrollsTimeout", settleBetweenScrollsTimeout,
                "scrollDirection", Optional.ofNullable(scrollDirection)
                        .orElse(ScrollDirection.UP).getDirection(),
                "dragDuration", Optional.ofNullable(dragDuration)
                        .orElse(Duration.ZERO).getSeconds()
        );
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
