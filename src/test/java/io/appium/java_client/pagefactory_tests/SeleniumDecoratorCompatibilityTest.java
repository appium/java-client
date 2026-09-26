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

import io.appium.java_client.events.stubs.EmptyWebDriver;
import io.appium.java_client.events.stubs.StubWebElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumDecoratorCompatibilityTest {
    @AndroidFindBy(id = "some_id")
    private WebElement element;

    @Test
    public void canInvokeElementReturnedBySeleniumDecorator() {
        CountingDriver originalDriver = new CountingDriver();
        CountingListener listener = new CountingListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        element.click();

        assertEquals(1, originalDriver.element.clickCount);
        assertEquals(1, listener.clickCount);
    }

    public static class CountingDriver extends EmptyWebDriver {
        private final CountingElement element = new CountingElement();

        @Override
        public StubWebElement findElement(By by) {
            return element;
        }
    }

    public static class CountingElement extends StubWebElement {
        private int clickCount;

        @Override
        public void click() {
            clickCount++;
        }
    }

    public static class CountingListener implements WebDriverListener {
        private int clickCount;

        @Override
        public void afterClick(WebElement element) {
            clickCount++;
        }
    }
}
