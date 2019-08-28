// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package io.appium.java_client.selenium.support.ui;

import io.appium.java_client.selenium.WebDriver;
import io.appium.java_client.selenium.WrapsDriver;
import io.appium.java_client.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;

/**
 * A specialization of {@link org.openqa.selenium.support.ui.FluentWait} that uses WebDriver instances.
 */
public class WebDriverWait extends FluentWait<WebDriver> {
    private final WebDriver driver;

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeoutInSeconds The timeout in seconds when an expectation is called
     * @see WebDriverWait#ignoring(java.lang.Class)
     * @deprecated Instead, use {@link WebDriverWait#WebDriverWait(WebDriver, Duration)}.
     */
    @Deprecated
    public WebDriverWait(WebDriver driver, long timeoutInSeconds) {
        this(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeout The timeout when an expectation is called
     * @see WebDriverWait#ignoring(java.lang.Class)
     */
    public WebDriverWait(WebDriver driver, Duration timeout) {
        this(
                driver,
                timeout,
                Duration.ofMillis(DEFAULT_SLEEP_TIMEOUT),
                Clock.systemDefaultZone(),
                Sleeper.SYSTEM_SLEEPER);
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeoutInSeconds The timeout in seconds when an expectation is called
     * @param sleepInMillis The duration in milliseconds to sleep between polls.
     * @see WebDriverWait#ignoring(java.lang.Class)
     * @deprecated Instead, use {@link WebDriverWait#WebDriverWait(WebDriver, Duration, Duration)}.
     */
    @Deprecated
    public WebDriverWait(WebDriver driver, long timeoutInSeconds, long sleepInMillis) {
        this(driver, Duration.ofSeconds(timeoutInSeconds), Duration.ofMillis(sleepInMillis));
    }

    /**
     * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in
     * the 'until' condition, and immediately propagate all others.  You can add more to the ignore
     * list by calling ignoring(exceptions to add).
     *
     * @param driver The WebDriver instance to pass to the expected conditions
     * @param timeout The timeout in seconds when an expectation is called
     * @param sleep The duration in milliseconds to sleep between polls.
     * @see WebDriverWait#ignoring(java.lang.Class)
     */
    public WebDriverWait(WebDriver driver, Duration timeout, Duration sleep) {
        this(driver, timeout, sleep, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER);
    }

    /**
     * Constructor to to create wait object.
     *
     * @param driver the WebDriver instance to pass to the expected conditions
     * @param clock used when measuring the timeout
     * @param sleeper used to make the current thread go to sleep
     * @param timeoutInSeconds the timeout in seconds when an expectation is called
     * @param sleepInMillis the timeout in millis used whilst sleeping
     * @deprecated Instead, use {@link WebDriverWait#WebDriverWait(WebDriver, Duration, Duration,
     *     Clock, Sleeper)}.
     */
    @Deprecated
    public WebDriverWait(
            WebDriver driver, Clock clock, Sleeper sleeper, long timeoutInSeconds, long sleepInMillis) {
        this(
                driver,
                Duration.ofSeconds(timeoutInSeconds),
                Duration.ofMillis(sleepInMillis),
                clock,
                sleeper);
    }

    /**
     * Constructor to to create wait object.
     *
     * @param driver the WebDriver instance to pass to the expected conditions
     * @param clock used when measuring the timeout
     * @param sleeper used to make the current thread go to sleep
     * @param timeout the timeout when an expectation is called
     * @param sleep the timeout used whilst sleeping
     */
    public WebDriverWait(
            WebDriver driver, Duration timeout, Duration sleep, Clock clock, Sleeper sleeper) {
        super(driver, clock, sleeper);
        withTimeout(timeout);
        pollingEvery(sleep);
        ignoring(NotFoundException.class);
        this.driver = driver;
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        WebDriver exceptionDriver = driver;
        TimeoutException ex = new TimeoutException(message, lastException);
        ex.addInfo(WebDriverException.DRIVER_INFO, exceptionDriver.getClass().getName());
        while (exceptionDriver instanceof WrapsDriver) {
            exceptionDriver = ((WrapsDriver) exceptionDriver).getWrappedDriver();
        }
        if (exceptionDriver instanceof RemoteWebDriver) {
            RemoteWebDriver remote = (RemoteWebDriver) exceptionDriver;
            if (remote.getSessionId() != null) {
                ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
            }
            if (remote.getCapabilities() != null) {
                ex.addInfo("Capabilities", remote.getCapabilities().toString());
            }
        }
        throw ex;
    }
}