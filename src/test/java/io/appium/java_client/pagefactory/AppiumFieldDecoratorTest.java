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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AppiumFieldDecoratorTest {

    private static final class FoundElement extends AppiumWebElement {
        private final WebDriver parent;

        private FoundElement(WebDriver parent) {
            this.parent = parent;
        }

        @Override
        public WebDriver getWrappedDriver() {
            return parent;
        }

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
                            return new FoundElement((WebDriver) proxy);
                        case "findElements":
                            locators.add(args[0].toString());
                            return List.of(new FoundElement((WebDriver) proxy), new FoundElement((WebDriver) proxy));
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

        @FindBy(css = ".kotlin-web-elements")
        List<? extends WebElement> wildcardWebElements;

        @FindBy(css = ".kotlin-appium-elements")
        List<? extends AppiumWebElement> wildcardAppiumElements;

        @FindBy(css = ".kotlin-widgets")
        List<? extends SampleWidget> wildcardWidgets;

        List<? extends SampleWidget> unannotatedWildcardWidgets;

        @FindBy(css = ".kotlin-generic-widgets")
        List<? extends GenericSampleWidget<String>> wildcardGenericWidgets;

        @FindBy(css = ".unbounded-wildcard")
        List<?> unboundedWildcardList;

        @FindBy(css = ".lower-bounded-wildcard")
        List<? super WebElement> lowerBoundedWildcardList;

        @FindBy(css = ".string-wildcard")
        List<? extends String> stringWildcardList;

        @CacheLookup
        WebElement nameOrIdFallback;
    }

    @FindBy(css = ".widget-root")
    public static class SampleWidget extends Widget {
        protected SampleWidget(WebElement element) {
            super(element);
        }
    }

    @FindBy(css = ".generic-widget-root")
    public static class GenericSampleWidget<T> extends Widget {
        protected GenericSampleWidget(WebElement element) {
            super(element);
        }
    }

    @SuppressWarnings("unused")
    private static class GenericPage<T extends WebElement, W extends SampleWidget> {
        @FindBy(css = ".type-var-elements")
        List<T> typeVarElements;

        @FindBy(css = ".wildcard-type-var-elements")
        List<? extends T> wildcardTypeVarElements;

        @FindBy(css = ".type-var-widgets")
        List<W> typeVarWidgets;

        @FindBy(css = ".wildcard-type-var-widgets")
        List<? extends W> wildcardTypeVarWidgets;
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

    @Test
    void decoratesUpperBoundedWildcardAndTypeVariableListsForKotlinCompatibility() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertNotNull(page.wildcardWebElements);
        assertEquals(2, page.wildcardWebElements.size());
        assertEquals("text", page.wildcardWebElements.get(0).getText());
        List<String> iteratedTexts = new ArrayList<>();
        for (WebElement item : page.wildcardWebElements) {
            iteratedTexts.add(item.getText());
        }
        assertEquals(List.of("text", "text"), iteratedTexts);

        assertNotNull(page.wildcardAppiumElements);
        assertEquals(2, page.wildcardAppiumElements.size());

        assertNotNull(page.wildcardWidgets);
        assertEquals(2, page.wildcardWidgets.size());
        assertEquals("text", page.wildcardWidgets.get(0).getWrappedElement().getText());

        assertNotNull(page.unannotatedWildcardWidgets);
        assertEquals(2, page.unannotatedWildcardWidgets.size());

        assertNotNull(page.wildcardGenericWidgets);
        assertEquals(2, page.wildcardGenericWidgets.size());

        assertNull(page.unboundedWildcardList);
        assertNull(page.lowerBoundedWildcardList);
        assertNull(page.stringWildcardList);

        GenericPage<AppiumWebElement, SampleWidget> genericPage = new GenericPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(recorder.driver, Duration.ofMillis(100)), genericPage);

        assertNotNull(genericPage.typeVarElements);
        assertEquals(2, genericPage.typeVarElements.size());
        assertNotNull(genericPage.wildcardTypeVarElements);
        assertEquals(2, genericPage.wildcardTypeVarElements.size());
        assertNotNull(genericPage.typeVarWidgets);
        assertEquals(2, genericPage.typeVarWidgets.size());
        assertNotNull(genericPage.wildcardTypeVarWidgets);
        assertEquals(2, genericPage.wildcardTypeVarWidgets.size());
    }
}
