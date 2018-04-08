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

package io.appium.java_client.appium;

import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import io.appium.java_client.AppiumFluentWait;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class AppiumFluentWaitTest {
    
    private static class FakeElement {
        public boolean isDisplayed() {
            return false;
        }
    }

    @Test(expected = TimeoutException.class)
    public void testDefaultStrategy() {
        final FakeElement el = new FakeElement();
        final Wait<FakeElement> wait = new AppiumFluentWait<>(el, new SystemClock(), duration -> {
            assertThat(duration.getSeconds(), is(equalTo(1L)));
            Thread.sleep(duration.toMillis());
        }).withPollingStrategy(AppiumFluentWait.IterationInfo::getInterval)
                .withTimeout(ofSeconds(3))
                .pollingEvery(ofSeconds(1));
        wait.until(FakeElement::isDisplayed);
        Assert.fail("TimeoutException is expected");
    }

    @Test
    public void testCustomStrategyOverridesDefaultInterval() {
        final FakeElement el = new FakeElement();
        final AtomicInteger callsCounter = new AtomicInteger(0);
        final Wait<FakeElement> wait = new AppiumFluentWait<>(el, new SystemClock(), duration -> {
            callsCounter.incrementAndGet();
            assertThat(duration.getSeconds(), is(equalTo(2L)));
            Thread.sleep(duration.toMillis());
        }).withPollingStrategy(info -> ofSeconds(2))
                .withTimeout(ofSeconds(3))
                .pollingEvery(ofSeconds(1));
        try {
            wait.until(FakeElement::isDisplayed);
            Assert.fail("TimeoutException is expected");
        } catch (TimeoutException e) {
            // this is expected
            assertThat(callsCounter.get(), is(equalTo(2)));
        }
    }

    @Test
    public void testIntervalCalculationForCustomStrategy() {
        final FakeElement el = new FakeElement();
        final AtomicInteger callsCounter = new AtomicInteger(0);
        // Linear dependency
        final Function<Long, Long> pollingStrategy = x -> x * 2;
        final Wait<FakeElement> wait = new AppiumFluentWait<>(el, new SystemClock(), duration -> {
            int callNumber = callsCounter.incrementAndGet();
            assertThat(duration.getSeconds(), is(equalTo(pollingStrategy.apply((long) callNumber))));
            Thread.sleep(duration.toMillis());
        }).withPollingStrategy(info -> ofSeconds(pollingStrategy.apply(info.getNumber())))
                .withTimeout(ofSeconds(4))
                .pollingEvery(ofSeconds(1));
        try {
            wait.until(FakeElement::isDisplayed);
            Assert.fail("TimeoutException is expected");
        } catch (TimeoutException e) {
            // this is expected
            assertThat(callsCounter.get(), is(equalTo(2)));
        }
    }
}
