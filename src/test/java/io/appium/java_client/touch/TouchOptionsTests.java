package io.appium.java_client.touch;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.touch.FailsWithMatcher.failsWith;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;

public class TouchOptionsTests {
    private static final WebElement dummyElement = new DummyElement();

    @Test
    public void invalidAbsolutePositionOptionsShouldFailOnBuild() throws Exception {
        final List<ActionOptions> invalidOptions = new ArrayList<>();
        invalidOptions.add(new PressOptions()
                .withElement(dummyElement)
                .withAbsoluteOffset(0, 0));
        invalidOptions.add(new LongPressOptions()
                .withRelativeOffset(0, 0));
        invalidOptions.add(new TapOptions());
        invalidOptions.add(new TapOptions()
                .withAbsoluteOffset(0, 0)
                .withRelativeOffset(0, 0));
        invalidOptions.forEach(opts -> assertThat(opts::build, failsWith(IllegalArgumentException.class)));
    }

    @Test
    public void invalidRelativePositionOptionsShouldFailOnBuild() throws Exception {
        final List<ActionOptions> invalidOptions = new ArrayList<>();
        invalidOptions.add(new MoveToOptions());
        invalidOptions.forEach(opts -> assertThat(opts::build, failsWith(IllegalArgumentException.class)));
    }

    @Test
    public void invalidOptionsArgumentsShouldFailOnAltering() throws Exception {
        final List<IThrowingRunnable<RuntimeException>> invalidOptions = new ArrayList<>();
        invalidOptions.add(() -> new WaitOptions().withDuration(Duration.ofMillis(-1)));
        invalidOptions.add(() -> new PressOptions().withElement(null));
        invalidOptions.add(() -> new MoveToOptions().withElement(null));
        invalidOptions.add(() -> new WaitOptions().withDuration(null));
        for (IThrowingRunnable<RuntimeException> item : invalidOptions) {
            assertThat(item, failsWith(RuntimeException.class));
        }
    }

    @Test
    public void longPressOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new LongPressOptions()
                .withElement(dummyElement)
                .withRelativeOffset(0, 0)
                .withDuration(Duration.ofMillis(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("element", ((HasIdentity) dummyElement).getId());
        expectedOpts.put("x", 0);
        expectedOpts.put("y", 0);
        expectedOpts.put("duration", 1L);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }

    @Test
    public void tapOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new TapOptions()
                .withAbsoluteOffset(0, 0)
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
    public void pressOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new PressOptions()
                .withElement(dummyElement)
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("element", ((HasIdentity) dummyElement).getId());
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }

    @Test
    public void moveToOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new MoveToOptions()
                .withElement(dummyElement)
                .withRelativeOffset(-1,-1)
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("element", ((HasIdentity) dummyElement).getId());
        expectedOpts.put("x", -1);
        expectedOpts.put("y", -1);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }

    @Test
    public void waitOptionsShouldBuildProperly() throws Exception {
        final Map<String, Object> actualOpts = new WaitOptions()
                .withDuration(Duration.ofSeconds(1))
                .build();
        final Map<String, Object> expectedOpts = new HashMap<>();
        expectedOpts.put("ms", 1000L);
        assertThat(actualOpts.entrySet(), everyItem(isIn(expectedOpts.entrySet())));
        assertThat(expectedOpts.entrySet(), everyItem(isIn(actualOpts.entrySet())));
    }
}
