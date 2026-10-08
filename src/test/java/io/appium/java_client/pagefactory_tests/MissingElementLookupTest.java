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

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.appium.java_client.support.PageFactory.initElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A page object field whose element is missing must be looked up once per attempt of the
 * decorator's own wait, see https://github.com/appium/java-client/issues/2034.
 */
public class MissingElementLookupTest {

    private static final class CountingAndroidDriver extends AbstractStubWebDriver.StubAndroidDriver {
        private final List<String> lookups = new ArrayList<>();

        @Override
        public StubWebElement findElement(By by) {
            if (!(by instanceof By.Remotable)) {
                // like a remote driver, let a composite locator run its own lookups
                return (StubWebElement) by.findElement(this);
            }
            lookups.add(by.toString());
            throw new NoSuchElementException("Cannot locate " + by);
        }
    }

    @AndroidFindBy(accessibility = "Not Exists")
    private WebElement missingElement;

    @Test
    public void zeroTimeoutLooksUpMissingElementOnce() {
        CountingAndroidDriver driver = new CountingAndroidDriver();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), this);

        long start = System.nanoTime();
        assertThrows(NoSuchElementException.class, () -> missingElement.click());
        long elapsedMs = Duration.ofNanos(System.nanoTime() - start).toMillis();

        assertEquals(List.of("AppiumBy.accessibilityId: Not Exists"), driver.lookups);
        assertThat("A zero timeout should not wait", elapsedMs, lessThan(400L));
    }
}
