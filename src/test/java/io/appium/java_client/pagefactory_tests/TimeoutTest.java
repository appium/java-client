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

package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.support.PageFactory.initElements;
import static java.time.Duration.ofMillis;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class TimeoutTest {

    private static final Duration DEFAULT_TIMEOUT = ofMillis(300);
    private static final long CLOCK_SLACK_MS = 50;
    private static final long ACCEPTABLE_OVERRUN_MS = 1500;

    @FindAll({
        @FindBy(className = "ClassWhichDoesNotExist"),
        @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
    private List<WebElement> stubElements;

    @WithTimeout(time = 800, chronoUnit = MILLIS)
    @FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
        @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
    private List<WebElement> stubElements2;

    private static void assertWaitsFor(Duration expected, Runnable runnable) {
        long start = System.nanoTime();
        runnable.run();
        long elapsedMs = Duration.ofNanos(System.nanoTime() - start).toMillis();
        assertThat("The lookup should wait for " + expected, elapsedMs,
                greaterThanOrEqualTo(expected.toMillis() - CLOCK_SLACK_MS));
        assertThat("The lookup should not wait much longer than " + expected, elapsedMs,
                lessThanOrEqualTo(expected.toMillis() + ACCEPTABLE_OVERRUN_MS));
    }

    @BeforeEach public void setUp() {
        WebDriver driver = new AbstractStubWebDriver() {
            @Override
            public List<WebElement> findElements(By by) {
                return List.of();
            }
        };
        initElements(new AppiumFieldDecorator(driver, DEFAULT_TIMEOUT), this);
    }

    @Test public void withCustomizedTimeOutTest() {
        assertWaitsFor(DEFAULT_TIMEOUT, () -> stubElements.size());
        assertWaitsFor(ofMillis(800), () -> stubElements2.size());
    }
}
