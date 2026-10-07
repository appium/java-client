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

package io.appium.java_client.support.ui;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link Wait} interface that may have its timeout and polling interval
 * configured on the fly. Each FluentWait instance defines the maximum amount of time to wait for a
 * condition, as well as the frequency with which to check the condition. Furthermore, the user may
 * configure the wait to ignore specific types of exceptions whilst waiting.
 * Adapted from Selenium's {@code org.openqa.selenium.support.ui.FluentWait} (Apache License 2.0).
 *
 * @param <T> The input type for each condition used with this instance.
 */
public class FluentWait<T> implements Wait<T> {
    /** The default sleep timeout in milliseconds. */
    protected static final long DEFAULT_SLEEP_TIMEOUT = 500;

    private static final Duration DEFAULT_WAIT_DURATION = Duration.ofMillis(DEFAULT_SLEEP_TIMEOUT);

    /** The input value passed to the evaluated conditions. */
    protected final T input;
    /** The clock used to measure the timeout. */
    protected final Clock clock;
    /** The sleeper used between the condition evaluations. */
    protected final Sleeper sleeper;

    /** The maximum time to wait. */
    protected Duration timeout = DEFAULT_WAIT_DURATION;
    /** The interval between the condition evaluations. */
    protected Duration interval = DEFAULT_WAIT_DURATION;
    /** The supplier of the custom timeout message. */
    protected Supplier<@Nullable String> messageSupplier = () -> null;

    /** The exception types ignored while waiting. */
    protected final List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();

    /**
     * Creates a wait that uses the system clock and {@link Sleeper#SYSTEM_SLEEPER}.
     *
     * @param input The input value to pass to the evaluated conditions.
     */
    public FluentWait(T input) {
        this(input, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER);
    }

    /**
     * Creates a wait with a custom clock and sleeper, mainly useful for testing.
     *
     * @param input The input value to pass to the evaluated conditions.
     * @param clock The clock to use when measuring the timeout.
     * @param sleeper Used to put the thread to sleep between evaluation loops.
     */
    public FluentWait(T input, Clock clock, Sleeper sleeper) {
        this.input = input;
        this.clock = requireNonNull(clock, "Clock must not be null");
        this.sleeper = requireNonNull(sleeper, "Sleeper must not be null");
    }

    /**
     * Sets how long to wait for the evaluated condition to be true. The default timeout is
     * {@link #DEFAULT_SLEEP_TIMEOUT}.
     *
     * @param timeout The timeout duration.
     * @return A self reference.
     */
    public FluentWait<T> withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Sets the message to be appended to the {@link TimeoutException} on timeout.
     *
     * @param message The failing message.
     * @return A self reference.
     */
    public FluentWait<T> withMessage(final String message) {
        requireNonNull(message, "Message must not be null");
        this.messageSupplier = () -> message;
        return this;
    }

    /**
     * Sets the supplier of the message to be appended to the {@link TimeoutException} on timeout.
     *
     * @param messageSupplier The supplier of the failing message.
     * @return A self reference.
     */
    public FluentWait<T> withMessage(Supplier<String> messageSupplier) {
        this.messageSupplier = requireNonNull(messageSupplier, "Message supplier must not be null");
        return this;
    }

    /**
     * Sets how often the condition should be evaluated. The default interval is
     * {@link #DEFAULT_SLEEP_TIMEOUT}.
     *
     * @param interval The interval duration.
     * @return A self reference.
     */
    public FluentWait<T> pollingEvery(Duration interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Configures this instance to ignore specific types of exceptions while waiting for a condition.
     *
     * @param types The types of exceptions to ignore.
     * @param <K> an exception that extends Throwable
     * @return A self reference.
     */
    public <K extends Throwable> FluentWait<T> ignoreAll(Collection<Class<? extends K>> types) {
        ignoredExceptions.addAll(types);
        return this;
    }

    /**
     * Configures this instance to ignore a specific type of exception while waiting for a condition.
     *
     * @param exceptionType exception to ignore
     * @return a self reference
     */
    public FluentWait<T> ignoring(Class<? extends Throwable> exceptionType) {
        return this.ignoreAll(List.of(exceptionType));
    }

    /**
     * Configures this instance to ignore two types of exceptions while waiting for a condition.
     *
     * @param firstType exception to ignore
     * @param secondType another exception to ignore
     * @return a self reference
     */
    public FluentWait<T> ignoring(
            Class<? extends Throwable> firstType, Class<? extends Throwable> secondType) {
        return this.ignoreAll(List.of(firstType, secondType));
    }

    /**
     * Repeatedly applies this instance's input value to the given function until one of the following
     * occurs: the function returns neither null nor false, the function throws an unignored exception,
     * or the timeout expires.
     *
     * @param isTrue the parameter to pass to the function
     * @param <V> The function's expected return type.
     * @return The function's return value if the function returned something different from null or
     *     false before the timeout expired.
     * @throws TimeoutException If the timeout expires.
     */
    @Override
    public <V extends @Nullable Object> @NonNull V until(Function<? super T, ? extends V> isTrue) {
        Instant end = clock.instant().plus(timeout);

        Throwable lastException;
        while (true) {
            try {
                V value = isTrue.apply(input);
                if (value != null && (Boolean.class != value.getClass() || Boolean.TRUE.equals(value))) {
                    return value;
                }

                // The last exception is not the cause of a timeout triggered by a false or null value
                lastException = null;
            } catch (Throwable e) {
                lastException = propagateIfNotIgnored(e);
            }

            // Checked after the evaluation so that conditions with a zero timeout can succeed
            if (end.isBefore(clock.instant())) {
                String message = messageSupplier.get();

                String timeoutMessage = String.format(
                        "Expected condition failed: %s%n(tried for %s with %d milliseconds interval)",
                        message == null ? "waiting for " + isTrue : message,
                        formatTimeout(timeout),
                        interval.toMillis());
                throw timeoutException(timeoutMessage, lastException);
            }

            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    static String formatTimeout(Duration timeout) {
        BigDecimal seconds = BigDecimal.valueOf(timeout.toMillis())
                .divide(BigDecimal.valueOf(1000), 3, HALF_UP);
        String value = seconds.stripTrailingZeros().toPlainString();
        boolean singular = seconds.compareTo(ONE) == 0;
        return value + (singular ? " second" : " seconds");
    }

    private Throwable propagateIfNotIgnored(Throwable e) {
        for (Class<? extends Throwable> ignoredException : ignoredExceptions) {
            if (ignoredException.isInstance(e)) {
                return e;
            }
        }
        if (e instanceof Error) {
            throw (Error) e;
        }
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }
        throw new RuntimeException(e);
    }

    /**
     * Throws a timeout exception. This method may be overridden to throw an exception that is
     * idiomatic for a particular test infrastructure.
     *
     * @param message The timeout message.
     * @param lastException The last exception that was ignored while waiting, if any.
     * @return nothing will ever be returned; this return type is only specified as a convenience.
     */
    protected RuntimeException timeoutException(String message, @Nullable Throwable lastException) {
        throw new TimeoutException(message, lastException);
    }
}
