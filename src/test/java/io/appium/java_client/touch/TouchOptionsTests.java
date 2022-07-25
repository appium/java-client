package io.appium.java_client.touch;

import static io.appium.java_client.touch.FailsWithMatcher.failsWith;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

import io.appium.java_client.touch.offset.ElementOption;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TouchOptionsTests {
    private static final RemoteWebElement DUMMY_ELEMENT = new DummyElement();

    @Test
    public void invalidOptionsArgumentsShouldFailOnAltering() {
        final List<Runnable> invalidOptions = new ArrayList<>();
        invalidOptions.add(() -> waitOptions(ofMillis(-1)));
        invalidOptions.add(() -> new ElementOption().withCoordinates(new Point(0, 0)).withElement(null));
        invalidOptions.add(() -> new WaitOptions().withDuration(null));
        invalidOptions.add(() -> tapOptions().withTapsCount(-1));
        invalidOptions.add(() -> longPressOptions().withDuration(null));
        invalidOptions.add(() -> longPressOptions().withDuration(ofMillis(-1)));
        for (Runnable item : invalidOptions) {
            assertThat(item, failsWith(RuntimeException.class));
        }
    }

    @Test
    public void longPressOptionsShouldBuildProperly() {
        final Map<String, Object> actualOpts = longPressOptions()
                .withElement(element(DUMMY_ELEMENT).withCoordinates(0, 0))
                .withDuration(ofMillis(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("element", DUMMY_ELEMENT.getId());
        expectedOpts.put("x", 0);
        expectedOpts.put("y", 0);
        expectedOpts.put("duration", 1L);
        assertThat(actualOpts.entrySet(), everyItem(is(in(expectedOpts.entrySet()))));
        assertThat(expectedOpts.entrySet(), everyItem(is(in(actualOpts.entrySet()))));
    }

    @Test
    public void tapOptionsShouldBuildProperly() {
        final Map<String, Object> actualOpts = tapOptions()
                .withPosition(point(new Point(0, 0)))
                .withTapsCount(2)
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("x", 0);
        expectedOpts.put("y", 0);
        expectedOpts.put("count", 2);
        assertThat(actualOpts.entrySet(), everyItem(is(in(expectedOpts.entrySet()))));
        assertThat(expectedOpts.entrySet(), everyItem(is(in(actualOpts.entrySet()))));
    }

    @Test
    public void waitOptionsShouldBuildProperly() {
        final Map<String, Object> actualOpts = new WaitOptions()
                .withDuration(ofSeconds(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("ms", 1000L);
        assertThat(actualOpts.entrySet(), everyItem(is(in(expectedOpts.entrySet()))));
        assertThat(expectedOpts.entrySet(), everyItem(is(in(actualOpts.entrySet()))));
    }
}
