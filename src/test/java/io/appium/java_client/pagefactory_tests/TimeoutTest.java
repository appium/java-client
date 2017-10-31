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

import static io.appium.java_client.ChromeDriverPathUtil.getChromeDriver;
import static io.appium.java_client.pagefactory.AppiumFieldDecorator.DEFAULT_TIMEOUT;
import static io.appium.java_client.pagefactory.AppiumFieldDecorator.DEFAULT_TIMEUNIT;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.setProperty;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory.WithTimeout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeoutTest {

    private static final long ACCEPTABLE_TIME_DIFF_MS = 1500;
    private static final String MESSAGE = "Check difference from the expected waiting duration %s %s";

    private WebDriver driver;

    @FindAll({
            @FindBy(className = "ClassWhichDoesNotExist"),
            @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
    private List<WebElement> stubElements;

    @WithTimeout(time = 5, unit = SECONDS)
    @FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
            @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
    private List<WebElement> stubElements2;

    private TimeOutDuration timeOutDuration;

    private static long getExpectedMillis(long value, TimeUnit sourceTimeUnit) {
        return MILLISECONDS.convert(value, sourceTimeUnit);
    }

    private static long getPerformanceDiff(long expectedMs, Runnable runnable) {
        long startMark = currentTimeMillis();
        runnable.run();
        long endMark = currentTimeMillis();
        return abs(expectedMs  - (endMark - startMark));
    }

    /**
     * The setting up.
     */
    @Before public void setUp() throws Exception {
        setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                getChromeDriver().getAbsolutePath());
        driver = new ChromeDriver();
        timeOutDuration = new TimeOutDuration(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
        initElements(new AppiumFieldDecorator(driver, timeOutDuration), this);
    }

    /**
     * finishing.
     */
    @After public void tearDown() throws Exception {
        driver.quit();
    }

    @Test public void defaultTimeOutTest() {
        assertThat(format(MESSAGE, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT),
                getPerformanceDiff(getExpectedMillis(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT), () -> stubElements.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));

        timeOutDuration.setTime(15500000, MICROSECONDS);
        assertThat(format(MESSAGE, 15500000, MICROSECONDS),
                getPerformanceDiff(getExpectedMillis(15500000, MICROSECONDS), () -> stubElements.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));

        timeOutDuration.setTime(3, SECONDS);
        assertThat(format(MESSAGE, 3, SECONDS),
                getPerformanceDiff(getExpectedMillis(3, SECONDS), () -> stubElements.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
    }

    @Test public void withCustomizedTimeOutTest() {
        assertThat(format(MESSAGE, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT),
                getPerformanceDiff(getExpectedMillis(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT), () -> stubElements.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));

        assertThat(format(MESSAGE, 5, SECONDS),
                getPerformanceDiff(getExpectedMillis(5, SECONDS), () -> stubElements2.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));

        timeOutDuration.setTime(15500000, MICROSECONDS);

        assertThat(format(MESSAGE, 15500000, MICROSECONDS),
                getPerformanceDiff(getExpectedMillis(15500000, MICROSECONDS), () -> stubElements.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));

        assertThat(format(MESSAGE, 5, SECONDS),
                getPerformanceDiff(getExpectedMillis(5, SECONDS), () -> stubElements2.size()),
                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
    }
}
