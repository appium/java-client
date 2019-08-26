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
//
//import static io.appium.java_client.pagefactory.AppiumFieldDecorator.DEFAULT_WAITING_TIMEOUT;
//import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
//import static java.lang.Math.abs;
//import static java.lang.String.format;
//import static java.lang.System.currentTimeMillis;
//import static java.time.Duration.ofSeconds;
//import static java.time.temporal.ChronoUnit.SECONDS;
//import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;
//import static org.hamcrest.Matchers.lessThanOrEqualTo;
//import static org.junit.Assert.assertThat;
//import static org.openqa.selenium.support.PageFactory.initElements;
//
//import io.appium.java_client.pagefactory.AppiumFieldDecorator;
//import io.appium.java_client.pagefactory.WithTimeout;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.FindAll;
//import org.openqa.selenium.support.FindBy;
//
//import java.time.Duration;
//import java.util.List;

public class TimeoutTest {

//    private static final long ACCEPTABLE_TIME_DIFF_MS = 1500;
//
//    private WebDriver driver;
//
//    @FindAll({
//            @FindBy(className = "ClassWhichDoesNotExist"),
//            @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
//    private List<WebElement> stubElements;
//
//    @WithTimeout(time = 5, chronoUnit = SECONDS)
//    @FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
//            @FindBy(className = "OneAnotherClassWhichDoesNotExist")})
//    private List<WebElement> stubElements2;
//
//    private Duration timeOutDuration;
//
//    private static long getExpectedMillis(Duration duration) {
//        return duration.toMillis();
//    }
//
//    private static long getPerformanceDiff(long expectedMs, Runnable runnable) {
//        long startMark = currentTimeMillis();
//        runnable.run();
//        long endMark = currentTimeMillis();
//        return abs(expectedMs  - (endMark - startMark));
//    }
//
//    private static String assertionMessage(Duration expectedDuration) {
//        return format("Check difference from the expected waiting duration %s",
//                formatDuration(expectedDuration.toMillis(), "H:mm:ss:SSS", true));
//    }
//
//    @BeforeClass
//    public static void beforeAll() {
//        chromedriver().setup();
//    }
//
//    /**
//     * The setting up.
//     */
//    @Before public void setUp() {
//        driver = new ChromeDriver();
//        timeOutDuration = DEFAULT_WAITING_TIMEOUT;
//        initElements(new AppiumFieldDecorator(driver, timeOutDuration), this);
//    }
//
//    /**
//     * finishing.
//     */
//    @After public void tearDown() {
//        driver.quit();
//    }
//
//    @Test public void withCustomizedTimeOutTest() {
//        assertThat(assertionMessage(DEFAULT_WAITING_TIMEOUT),
//                getPerformanceDiff(getExpectedMillis(DEFAULT_WAITING_TIMEOUT), () -> stubElements.size()),
//                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
//
//        assertThat(assertionMessage(ofSeconds(5)),
//                getPerformanceDiff(getExpectedMillis(ofSeconds(5)), () -> stubElements2.size()),
//                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
//
//        timeOutDuration.plus(ofSeconds(10));
//
//        assertThat(assertionMessage(timeOutDuration),
//                getPerformanceDiff(getExpectedMillis(timeOutDuration), () -> stubElements.size()),
//                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
//
//        assertThat(assertionMessage(ofSeconds(5)),
//                getPerformanceDiff(getExpectedMillis(ofSeconds(5)), () -> stubElements2.size()),
//                lessThanOrEqualTo(ACCEPTABLE_TIME_DIFF_MS));
//    }
}
