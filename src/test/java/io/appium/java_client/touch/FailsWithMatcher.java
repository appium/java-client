package io.appium.java_client.touch;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class FailsWithMatcher<E extends Throwable> extends TypeSafeMatcher<Runnable> {

    private final Matcher<? super E> matcher;

    private FailsWithMatcher(final Matcher<? super E> matcher) {
        this.matcher = matcher;
    }

    public static <E extends Throwable> Matcher<Runnable> failsWith(
            final Class<E> throwableType) {
        return new FailsWithMatcher<>(instanceOf(throwableType));
    }

    public static <E extends Throwable> Matcher<Runnable> failsWith(
            final Class<E> throwableType, final Matcher<? super E> throwableMatcher) {
        return new FailsWithMatcher<E>(allOf(instanceOf(throwableType), throwableMatcher));
    }

    @Override
    protected boolean matchesSafely(final Runnable runnable) {
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
