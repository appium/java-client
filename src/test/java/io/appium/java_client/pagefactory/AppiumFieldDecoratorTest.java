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

package io.appium.java_client.pagefactory;

import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.PageFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppiumFieldDecoratorTest {

    private static final class FoundElement extends AppiumWebElement {
        @Override
        public String getText() {
            return "text";
        }
    }

    private static final class RecordingDriver {
        private final List<String> locators = new ArrayList<>();
        private final WebDriver driver = (WebDriver) Proxy.newProxyInstance(
                AppiumFieldDecoratorTest.class.getClassLoader(),
                new Class<?>[]{WebDriver.class, HasCapabilities.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "findElement":
                            locators.add(args[0].toString());
                            return new FoundElement();
                        case "findElements":
                            locators.add(args[0].toString());
                            return List.of(new FoundElement(), new FoundElement());
                        case "getCapabilities":
                            return new MutableCapabilities();
                        default:
                            throw new UnsupportedOperationException(method.getName());
                    }
                });
    }

    @SuppressWarnings("unused")
    private static class Page {
        @FindBy(id = "appium")
        WebElement appiumAnnotated;

        @org.openqa.selenium.support.FindBy(id = "selenium")
        @org.openqa.selenium.support.CacheLookup
        WebElement seleniumAnnotated;

        @FindBy(css = ".many")
        List<WebElement> many;

        @CacheLookup
        WebElement nameOrIdFallback;
    }

    private static Page initialized(RecordingDriver recorder) {
        Page page = new Page();
        PageFactory.initElements(new AppiumFieldDecorator(recorder.driver, Duration.ofMillis(100)), page);
        return page;
    }

    @Test
    void locatesElementsAnnotatedWithAppiumAnnotations() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertEquals("text", page.appiumAnnotated.getText());

        assertEquals(List.of(By.id("appium").toString()), recorder.locators);
    }

    @Test
    void locatesElementsAnnotatedWithSeleniumAnnotationsAndCachesThem() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        page.seleniumAnnotated.getText();
        page.seleniumAnnotated.getText();

        assertEquals(List.of(By.id("selenium").toString()), recorder.locators);
    }

    @Test
    void locatesListsOfElements() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertEquals(2, page.many.size());

        assertEquals(List.of(By.cssSelector(".many").toString()), recorder.locators);
    }

    @Test
    void fallsBackToTheFieldNameWhenThereAreNoLocatorAnnotations() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        page.nameOrIdFallback.getText();

        assertEquals(1, recorder.locators.size());
    }
}
