/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client;

import com.google.common.base.Throwables;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class AppiumFluentWait<T> extends FluentWait<T> {
    private Function<IterationInfo, Duration> pollingStrategy = null;

    public static class IterationInfo {
        private final long number;
        private final Duration elapsed;
        private final Duration total;
        private final Duration interval;

        /**
         * The class is used to represent information about a single loop iteration in {@link #until(Function)}
         * method.
         *
         * @param number loop iteration number, starts from 1
         * @param elapsed the amount of elapsed time since the loop started
         * @param total the amount of total time to run the loop
         * @param interval the default time interval for each loop iteration
         */
        public IterationInfo(long number, Duration elapsed, Duration total, Duration interval) {
            this.number = number;
            this.elapsed = elapsed;
            this.total = total;
            this.interval = interval;
        }

        /**
         * The current iteration number.
         *
         * @return current iteration number. It starts from 1
         */
        public long getNumber() {
            return number;
        }

        /**
         * The amount of elapsed time.
         *
         * @return the amount of elapsed time
         */
        public Duration getElapsed() {
            return elapsed;
        }

        /**
         * The amount of total time.
         *
         * @return the amount of total time
         */
        public Duration getTotal() {
            return total;
        }

        /**
         * The current interval.
         *
         * @return The actual value of current interval or the default one if it is not set
         */
        public Duration getInterval() {
            return interval;
        }
    }

    /**
     * @param input The input value to pass to the evaluated conditions.
     */
    public AppiumFluentWait(T input) {
        super(input);
    }

    /**
     * @param input   The input value to pass to the evaluated conditions.
     * @param clock   The clock to use when measuring the timeout.
     * @param sleeper Used to put the thread to sleep between evaluation loops.
     */
    public AppiumFluentWait(T input, Clock clock, Sleeper sleeper) {
        super(input, clock, sleeper);
    }

    private <B> B getPrivateFieldValue(String fieldName, Class<B> fieldType) {
        try {
            final Field f = getClass().getSuperclass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return fieldType.cast(f.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    private Object getPrivateFieldValue(String fieldName) {
        try {
            final Field f = getClass().getSuperclass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }

    protected Clock getClock() {
        return getPrivateFieldValue("clock", Clock.class);
    }

    protected Duration getTimeout() {
        return getPrivateFieldValue("timeout", Duration.class);
    }

    protected Duration getInterval() {
        return getPrivateFieldValue("interval", Duration.class);
    }

    protected Sleeper getSleeper() {
        return getPrivateFieldValue("sleeper", Sleeper.class);
    }

    @SuppressWarnings("unchecked")
    protected List<Class<? extends Throwable>> getIgnoredExceptions() {
        return getPrivateFieldValue("ignoredExceptions", List.class);
    }

    @SuppressWarnings("unchecked")
    protected Supplier<String> getMessageSupplier() {
        return getPrivateFieldValue("messageSupplier", Supplier.class);
    }

    @SuppressWarnings("unchecked")
    protected T getInput() {
        return (T) getPrivateFieldValue("input");
    }

    /**
     * Sets the strategy for polling. The default strategy is null,
     * which means, that polling interval is always a constant value and is
     * set by {@link #pollingEvery(long, TimeUnit)} method. Otherwise the value set by that
     * method might be just a helper to calculate the actual interval.
     * Although, by setting an alternative polling strategy you may flexibly control
     * the duration of this interval for each polling round.
     * For example we'd like to wait two times longer than before each time we cannot find
     * an element:
     * <code>
     *   final Wait&lt;WebElement&gt; wait = new AppiumFluentWait&lt;&gt;(el)
     *     .withPollingStrategy(info -&gt; new Duration(info.getNumber() * 2, TimeUnit.SECONDS))
     *     .withTimeout(6, TimeUnit.SECONDS);
     *   wait.until(WebElement::isDisplayed);
     * </code>
     * Or we want the next time period is Euler's number e raised to the power of current iteration
     * number:
     * <code>
     *   final Wait&lt;WebElement&gt; wait = new AppiumFluentWait&lt;&gt;(el)
     *     .withPollingStrategy(info -&gt; new Duration((long) Math.exp(info.getNumber()), TimeUnit.SECONDS))
     *     .withTimeout(6, TimeUnit.SECONDS);
     *   wait.until(WebElement::isDisplayed);
     * </code>
     * Or we'd like to have some advanced algorithm, which waits longer first, but then use the default interval when it
     * reaches some constant:
     * <code>
     *   final Wait&lt;WebElement&gt; wait = new AppiumFluentWait&lt;&gt;(el)
     *     .withPollingStrategy(info -&gt; new Duration(info.getNumber() &lt; 5
     *       ? 4 - info.getNumber() : info.getInterval().in(TimeUnit.SECONDS), TimeUnit.SECONDS))
     *     .withTimeout(30, TimeUnit.SECONDS)
     *     .pollingEvery(1, TimeUnit.SECONDS);
     *   wait.until(WebElement::isDisplayed);
     * </code>
     *
     * @param pollingStrategy Function instance, where the first parameter
     *                        is the information about the current loop iteration (see {@link IterationInfo})
     *                        and the expected result is the calculated interval. It is highly
     *                        recommended that the value returned by this lambda is greater than zero.
     * @return A self reference.
     */
    public AppiumFluentWait<T> withPollingStrategy(Function<IterationInfo, Duration> pollingStrategy) {
        this.pollingStrategy = pollingStrategy;
        return this;
    }

    /**
     * Repeatedly applies this instance's input value to the given function until one of the following
     * occurs:
     * <ol>
     * <li>the function returns neither null nor false,</li>
     * <li>the function throws an unignored exception,</li>
     * <li>the timeout expires,
     * <li>
     * <li>the current thread is interrupted</li>
     * </ol>
     *
     * @param isTrue the parameter to pass to the expected condition
     * @param <V>    The function's expected return type.
     * @return The functions' return value if the function returned something different
     *         from null or false before the timeout expired.
     * @throws TimeoutException If the timeout expires.
     */
    @Override
    public <V> V until(Function<? super T, V> isTrue) {
        final long start = getClock().now();
        final long end = getClock().laterBy(getTimeout().in(TimeUnit.MILLISECONDS));
        long iterationNumber = 1;
        Throwable lastException;
        while (true) {
            try {
                V value = isTrue.apply(getInput());
                if (value != null && (Boolean.class != value.getClass() || Boolean.TRUE.equals(value))) {
                    return value;
                }

                // Clear the last exception; if another retry or timeout exception would
                // be caused by a false or null value, the last exception is not the
                // cause of the timeout.
                lastException = null;
            } catch (Throwable e) {
                lastException = propagateIfNotIgnored(e);
            }

            // Check the timeout after evaluating the function to ensure conditions
            // with a zero timeout can succeed.
            if (!getClock().isNowBefore(end)) {
                String message = getMessageSupplier() != null ? getMessageSupplier().get() : null;

                String timeoutMessage = String.format(
                        "Expected condition failed: %s (tried for %d second(s) with %s interval)",
                        message == null ? "waiting for " + isTrue : message,
                        getTimeout().in(TimeUnit.SECONDS), getInterval());
                throw timeoutException(timeoutMessage, lastException);
            }

            try {
                Duration interval = getInterval();
                if (pollingStrategy != null) {
                    final IterationInfo info = new IterationInfo(iterationNumber,
                            new Duration(getClock().now() - start, TimeUnit.MILLISECONDS), getTimeout(),
                            interval);
                    interval = pollingStrategy.apply(info);
                }
                getSleeper().sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
            ++iterationNumber;
        }
    }

    protected Throwable propagateIfNotIgnored(Throwable e) {
        for (Class<? extends Throwable> ignoredException : getIgnoredExceptions()) {
            if (ignoredException.isInstance(e)) {
                return e;
            }
        }
        Throwables.throwIfUnchecked(e);
        throw new WebDriverException(e);
    }
}
