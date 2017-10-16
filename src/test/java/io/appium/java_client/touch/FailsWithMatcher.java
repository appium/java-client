package io.appium.java_client.touch;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public final class FailsWithMatcher<Ex extends Throwable>
        extends TypeSafeMatcher<IThrowingRunnable<Ex>> {

    private final Matcher<? super Ex> matcher;

    private FailsWithMatcher(final Matcher<? super Ex> matcher) {
        this.matcher = matcher;
    }

    public static <Ex extends Throwable> Matcher<IThrowingRunnable<Ex>> failsWith(
            final Class<Ex> throwableType) {
        return new FailsWithMatcher<>(instanceOf(throwableType));
    }

    public static <Ex extends Throwable> Matcher<IThrowingRunnable<Ex>> failsWith(
            final Class<Ex> throwableType, final Matcher<? super Ex> throwableMatcher) {
        return new FailsWithMatcher<>(allOf(instanceOf(throwableType), throwableMatcher));
    }

    @Override
    protected boolean matchesSafely(final IThrowingRunnable<Ex> runnable) {
        try {
            runnable.run();
            return false;
        } catch (final Throwable ex) {
            return matcher.matches(ex);
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("fails with ").appendDescriptionOf(matcher);
    }

}
