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
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory.WithTimeout;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeOutResetTest {
    private static final long ACCEPTABLE_DELTA_MILLS = 1500;
    private WebDriver driver;
    @FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
        @FindBy(className = "OneAnotherClassWhichDoesNotExist")}) private List<WebElement>
        stubElements;

    @WithTimeout(time = 5, unit = TimeUnit.SECONDS)
    @FindAll({@FindBy(className = "ClassWhichDoesNotExist"),
        @FindBy(className = "OneAnotherClassWhichDoesNotExist")}) private List<WebElement>
        stubElements2;

    private TimeOutDuration timeOutDuration;

    private static void checkTimeDifference(long expectedTime, TimeUnit expectedTimeUnit,
        long currentMillis) {
        long expectedMillis = TimeUnit.MILLISECONDS.convert(expectedTime, expectedTimeUnit);
        try {
            Assert.assertEquals(true,
                ((currentMillis - expectedMillis) < ACCEPTABLE_DELTA_MILLS) && (
                    (currentMillis - expectedMillis) >= 0));
        } catch (Error e) {
            String message = String.valueOf(expectedTime) + " "
                + expectedTimeUnit.toString()
                + " current duration in millis "
                + String.valueOf(currentMillis) + " Failed";
            throw new RuntimeException(message, e);
        }
    }

    /**
     * The setting up.
     */
    @Before public void setUp() throws Exception {
        if (Platform.getCurrent().is(Platform.WINDOWS)) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver.exe");
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver");
        }
        driver = new ChromeDriver();
        timeOutDuration = new TimeOutDuration(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
            AppiumFieldDecorator.DEFAULT_TIMEUNIT);
        PageFactory.initElements(new AppiumFieldDecorator(driver, timeOutDuration), this);
    }

    /**
     * finishing.
     */
    @After public void tearDown() throws Exception {
        driver.quit();
    }

    private long getBenchMark(List<WebElement> stubElements) {
        long startMark = Calendar.getInstance().getTimeInMillis();
        stubElements.size();
        long endMark = Calendar.getInstance().getTimeInMillis();
        return endMark - startMark;
    }

    @Test public void test() {
        checkTimeDifference(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
            AppiumFieldDecorator.DEFAULT_TIMEUNIT, getBenchMark(stubElements));
        System.out.println(
            String.valueOf(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT) + " "
                + AppiumFieldDecorator.DEFAULT_TIMEUNIT.toString() + ": Fine");

        timeOutDuration.setTime(15500000, TimeUnit.MICROSECONDS);
        checkTimeDifference(15500000, TimeUnit.MICROSECONDS, getBenchMark(stubElements));
        System.out.println(
            "Change time: " + String.valueOf(15500000) + " " + TimeUnit.MICROSECONDS.toString()
                + ": Fine");

        timeOutDuration.setTime(3, TimeUnit.SECONDS);
        checkTimeDifference(3, TimeUnit.SECONDS, getBenchMark(stubElements));
        System.out.println(
            "Change time: " + String.valueOf(3) + " " + TimeUnit.SECONDS.toString() + ": Fine");

    }

    @Test public void test2() {
        checkTimeDifference(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
            AppiumFieldDecorator.DEFAULT_TIMEUNIT, getBenchMark(stubElements));
        System.out.println(
            String.valueOf(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT) + " "
                + AppiumFieldDecorator.DEFAULT_TIMEUNIT.toString() + ": Fine");

        checkTimeDifference(5, TimeUnit.SECONDS, getBenchMark(stubElements2));
        System.out.println(String.valueOf(5) + " " + TimeUnit.SECONDS.toString() + ": Fine");


        timeOutDuration.setTime(15500000, TimeUnit.MICROSECONDS);
        checkTimeDifference(15500000, TimeUnit.MICROSECONDS, getBenchMark(stubElements));
        System.out.println(
            "Change time: " + String.valueOf(15500000) + " " + TimeUnit.MICROSECONDS.toString()
                + ": Fine");

        checkTimeDifference(5, TimeUnit.SECONDS, getBenchMark(stubElements2));
        System.out.println(String.valueOf(5) + " " + TimeUnit.SECONDS.toString() + ": Fine");
    }

    @Test public void checkThatCustomizedTimeOutDoesNotChangeGeneralValue() {
        stubElements2.size(); //time out differs from the general value there

        long startMark = Calendar.getInstance().getTimeInMillis();
        driver.findElements(By.id("FakeId"));
        long endMark = Calendar.getInstance().getTimeInMillis();
        checkTimeDifference(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
                AppiumFieldDecorator.DEFAULT_TIMEUNIT, endMark - startMark);
    }

}
