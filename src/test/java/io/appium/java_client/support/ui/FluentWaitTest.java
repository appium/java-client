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

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FluentWaitTest {

    private static final class FakeTime extends Clock implements Sleeper {
        private Instant now = Instant.EPOCH;

        @Override
        public ZoneOffset getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(java.time.ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return now;
        }

        @Override
        public void sleep(Duration duration) {
            now = now.plus(duration);
        }
    }

    private static FluentWait<String> newWait(FakeTime time) {
        return new FluentWait<>("input", time, time)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1));
    }

    @Test
    void returnsTheFirstTruthyValue() {
        assertEquals("INPUT", newWait(new FakeTime()).until(String::toUpperCase));
    }

    @Test
    void pollsUntilTheConditionIsMet() {
        AtomicInteger calls = new AtomicInteger();

        Integer result = newWait(new FakeTime()).until(input -> calls.incrementAndGet() < 3 ? null : calls.get());

        assertEquals(3, result);
    }

    @Test
    void falseIsNotTruthy() {
        AtomicInteger calls = new AtomicInteger();

        assertEquals(true, newWait(new FakeTime()).until(input -> calls.incrementAndGet() >= 2));
        assertEquals(2, calls.get());
    }

    @Test
    void ignoredExceptionsAreRetried() {
        AtomicInteger calls = new AtomicInteger();

        String result = newWait(new FakeTime()).ignoring(NoSuchElementException.class).until(input -> {
            if (calls.incrementAndGet() < 3) {
                throw new NoSuchElementException("not yet");
            }
            return "found";
        });

        assertEquals("found", result);
    }

    @Test
    void notIgnoredExceptionsArePropagated() {
        assertThrows(IllegalStateException.class, () -> newWait(new FakeTime()).until(input -> {
            throw new IllegalStateException("boom");
        }));
    }

    @Test
    void timesOutWithTheLastIgnoredExceptionAsTheCause() {
        FakeTime time = new FakeTime();
        NoSuchElementException cause = new NoSuchElementException("never there");

        TimeoutException e = assertThrows(TimeoutException.class, () -> newWait(time)
                .ignoring(NoSuchElementException.class)
                .withMessage("the element")
                .until(input -> {
                    throw cause;
                }));

        assertTrue(e.getMessage().contains("Expected condition failed: the element"), e.getMessage());
        assertTrue(e.getMessage().contains("tried for 5 seconds with 1000 milliseconds interval"), e.getMessage());
        assertEquals(cause, e.getCause());
        assertTrue(time.instant().isAfter(Instant.EPOCH.plusSeconds(5)));
    }

    @Test
    void zeroTimeoutStillEvaluatesTheConditionOnce() {
        assertEquals("INPUT", newWait(new FakeTime()).withTimeout(Duration.ZERO).until(String::toUpperCase));
    }

    @Test
    void interruptionIsReportedAsAWebDriverException() {
        Sleeper interrupted = duration -> {
            throw new InterruptedException();
        };
        FluentWait<String> wait = new FluentWait<>("input", Clock.systemDefaultZone(), interrupted)
                .withTimeout(Duration.ofSeconds(5));

        try {
            assertInstanceOf(org.openqa.selenium.WebDriverException.class,
                    assertThrows(RuntimeException.class, () -> wait.until(input -> null)));
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }

    @Test
    void formatsTimeouts() {
        assertEquals("1 second", FluentWait.formatTimeout(Duration.ofSeconds(1)));
        assertEquals("2 seconds", FluentWait.formatTimeout(Duration.ofSeconds(2)));
        assertEquals("0.5 seconds", FluentWait.formatTimeout(Duration.ofMillis(500)));
    }
}
