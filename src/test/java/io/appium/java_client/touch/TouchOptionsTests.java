package io.appium.java_client.touch;

import static io.appium.java_client.touch.AbsoluteOffsetOption.useAbsolute;
import static io.appium.java_client.touch.FailsWithMatcher.failsWith;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.RelativeOffsetOption.useRelative;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TouchOptionsTests {
    private static final WebElement DUMMY_ELEMENT = new DummyElement();

    @Test
    public void invalidAbsolutePositionOptionsShouldFailOnBuild() throws Exception {
        final List<ActionOptions> invalidOptions = new ArrayList<>();
        invalidOptions.add(new AbsoluteOffsetOption());
        for (ActionOptions opts : invalidOptions) {
            assertThat(opts::build, failsWith(IllegalArgumentException.class));
        }
    }

    @Test
    public void invalidRelativePositionOptionsShouldFailOnBuild() throws Exception {
        final List<ActionOptions> invalidOptions = new ArrayList<>();
        invalidOptions.add(new RelativeOffsetOption());
        for (ActionOptions opts : invalidOptions) {
            assertThat(opts::build, failsWith(IllegalArgumentException.class));
        }
    }

    @Test
    public void invalidOptionsArgumentsShouldFailOnAltering() throws Exception {
        final List<IThrowingRunnable<RuntimeException>> invalidOptions = new ArrayList<>();
        invalidOptions.add(() -> waitOptions(ofMillis(-1)));
        invalidOptions.add(() -> new RelativeOffsetOption().withRelativeOffset(null, 0, 0));
        invalidOptions.add(() -> new WaitOptions().withDuration(null));
        invalidOptions.add(() -> tapOptions().withTapsCount(-1));
        invalidOptions.add(() -> longPressOptions().withDuration(null));
        invalidOptions.add(() -> longPressOptions().withDuration(ofMillis(-1)));
        for (IThrowingRunnable<RuntimeException> item : invalidOptions) {
            assertThat(item, failsWith(RuntimeException.class));
        }
    }

    @Test
    public void longPressOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = longPressOptions()
                .withOffset(useRelative(DUMMY_ELEMENT).withRelativeOffset(0, 0))
                .withDuration(ofMillis(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("element", ((HasIdentity) DUMMY_ELEMENT).getId());
        expectedOpts.put("x", 0);
        expectedOpts.put("y", 0);
        expectedOpts.put("duration", 1L);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }

    @Test
    public void tapOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = tapOptions()
                .withOffset(useAbsolute(0, 0))
                .withTapsCount(2)
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("x", 0);
        expectedOpts.put("y", 0);
        expectedOpts.put("count", 2);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }

    @Test
    public void waitOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new WaitOptions()
                .withDuration(ofSeconds(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("ms", 1000L);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }
}
