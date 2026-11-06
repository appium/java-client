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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.HasBrowserCheck;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.interceptors.InterceptorOfAListOfElements;
import io.appium.java_client.pagefactory.interceptors.InterceptorOfASingleElement;
import io.appium.java_client.proxy.MethodCallListener;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.remote.CapabilityType;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.PageFactory;
import io.appium.java_client.support.pagefactory.ByAll;
import io.appium.java_client.support.pagefactory.ByChained;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        private final List<By> bys = new ArrayList<>();
        private final List<String> locators = new ArrayList<>();
        private final WebDriver driver;

        private RecordingDriver() {
            this(new MutableCapabilities());
        }

        private RecordingDriver(MutableCapabilities capabilities) {
            this.driver = (WebDriver) Proxy.newProxyInstance(
                    AppiumFieldDecoratorTest.class.getClassLoader(),
                    new Class<?>[]{WebDriver.class, HasCapabilities.class, HasBrowserCheck.class},
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "findElement":
                                bys.add((By) args[0]);
                                locators.add(args[0].toString());
                                return new FoundElement((WebDriver) proxy);
                            case "findElements":
                                bys.add((By) args[0]);
                                locators.add(args[0].toString());
                                return List.of(
                                        new FoundElement((WebDriver) proxy),
                                        new FoundElement((WebDriver) proxy)
                                );
                            case "getCapabilities":
                                return capabilities;
                            case "isBrowser":
                                return capabilities.getCapability(CapabilityType.PLATFORM_NAME) == null;
                            default:
                                throw new UnsupportedOperationException(method.getName());
                        }
                    });
        }
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

        @FindBy(css = ".lower-bounded-wildcard")
        List<? super WebElement> lowerBoundedWildcardList;

        @FindBy(css = ".kotlin-widgets")
        List<? extends SampleWidget> wildcardWidgets;

        @FindBy(css = ".lower-bounded-widgets")
        List<? super SampleWidget> lowerBoundedWidgets;

        List<? extends SampleWidget> unannotatedWildcardWidgets;

        List<? extends ChainedWidget> chainedClassAnnotatedWidgets;

        List<? extends AllPossibleWidget> allPossibleClassAnnotatedWidgets;

        @FindBy(css = ".kotlin-generic-widgets")
        List<? extends GenericSampleWidget<String>> wildcardGenericWidgets;

        @FindBy(css = ".overridden-wildcard-widgets")
        @OverrideWidget(html = CustomSampleWidget.class)
        List<? extends SampleWidget> overriddenWildcardWidgets;

        List<? extends AllPossibleAndroidWidget> androidAllPossibleWidgets;

        @FindBy(css = ".timeout-wildcard-widgets")
        List<? extends FastTimeoutWidget> timeoutWildcardWidgets;

        @FindBy(css = ".unbounded-wildcard")
        List<?> unboundedWildcardList;

        @FindBy(css = ".object-super-wildcard")
        List<? super Object> objectSuperWildcardList;

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

    public static class CustomSampleWidget extends SampleWidget {
        protected CustomSampleWidget(WebElement element) {
            super(element);
        }
    }

    @FindBys({@FindBy(css = ".outer"), @FindBy(css = ".inner")})
    public static class ChainedWidget extends Widget {
        protected ChainedWidget(WebElement element) {
            super(element);
        }
    }

    @FindAll({@FindBy(css = ".first"), @FindBy(css = ".second")})
    public static class AllPossibleWidget extends Widget {
        protected AllPossibleWidget(WebElement element) {
            super(element);
        }
    }

    @FindBy(css = ".generic-widget-root")
    public static class GenericSampleWidget<T> extends Widget {
        protected GenericSampleWidget(WebElement element) {
            super(element);
        }
    }

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(id = "android-w1")
    @AndroidFindBy(id = "android-w2")
    public static class AllPossibleAndroidWidget extends Widget {
        protected AllPossibleAndroidWidget(WebElement element) {
            super(element);
        }
    }

    @WithTimeout(time = 50, chronoUnit = ChronoUnit.MILLIS)
    @FindBy(css = ".fast-timeout-widget")
    public static class FastTimeoutWidget extends SampleWidget {
        protected FastTimeoutWidget(WebElement element) {
            super(element);
        }
    }

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @WithTimeout(time = 75, chronoUnit = ChronoUnit.MILLIS)
    @FindBy(css = ".fancy-widget-root")
    @AndroidFindBy(id = "fancy-a1")
    @AndroidFindBy(id = "fancy-a2")
    public static class FancyWidget extends SampleWidget {
        protected FancyWidget(WebElement element) {
            super(element);
        }
    }

    public static class CustomFancyWidget extends FancyWidget {
        protected CustomFancyWidget(WebElement element) {
            super(element);
        }
    }

    @SuppressWarnings("unused")
    private static class GenericPage<T extends WebElement, W extends SampleWidget> {
        @FindBy(css = ".type-var-elements")
        List<T> typeVarElements;

        @FindBy(css = ".wildcard-type-var-elements")
        List<? extends T> wildcardTypeVarElements;

        W singleTypeVarWidget;

        @FindBy(css = ".overridden-single-type-var-widget")
        @OverrideWidget(html = CustomFancyWidget.class)
        W overriddenSingleTypeVarWidget;

        @FindBy(css = ".type-var-widgets")
        List<W> typeVarWidgets;

        @FindBy(css = ".wildcard-type-var-widgets")
        List<? extends W> wildcardTypeVarWidgets;

        List<W> unannotatedTypeVarWidgets;

        @FindBy(css = ".overridden-type-var-widgets")
        @OverrideWidget(html = CustomFancyWidget.class)
        List<? extends W> overriddenTypeVarWidgets;
    }

    private static class ConcreteGenericPage extends GenericPage<AppiumWebElement, FancyWidget> {
    }

    private static class SubtypeElementGenericPage extends GenericPage<FoundElement, FancyWidget> {
    }

    private static Page initialized(RecordingDriver recorder) {
        Page page = new Page();
        PageFactory.initElements(new AppiumFieldDecorator(recorder.driver, Duration.ofMillis(100)), page);
        return page;
    }

    private static Duration extractWaitingDuration(Object decoratedProxy) throws Exception {
        Field listenersField = decoratedProxy.getClass().getDeclaredField("methodCallListeners");
        listenersField.setAccessible(true);
        MethodCallListener[] listeners = (MethodCallListener[]) listenersField.get(decoratedProxy);
        Class<?> interceptorBase = listeners[0] instanceof InterceptorOfAListOfElements
                ? InterceptorOfAListOfElements.class
                : InterceptorOfASingleElement.class;
        Field locatorField = interceptorBase.getDeclaredField("locator");
        locatorField.setAccessible(true);
        AppiumElementLocator locator = (AppiumElementLocator) locatorField.get(listeners[0]);
        assertNotNull(locator);
        Field durationField = AppiumElementLocator.class.getDeclaredField("duration");
        durationField.setAccessible(true);
        return (Duration) durationField.get(locator);
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
    void decoratesWildcardElementLists() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertNotNull(page.wildcardWebElements);
        List<String> iteratedTexts = new ArrayList<>();
        for (WebElement item : page.wildcardWebElements) {
            iteratedTexts.add(item.getText());
        }
        assertEquals(List.of("text", "text"), iteratedTexts);

        assertNotNull(page.wildcardAppiumElements);
        assertEquals(2, page.wildcardAppiumElements.size());
        assertEquals("text", page.wildcardAppiumElements.get(0).getText());

        assertNotNull(page.lowerBoundedWildcardList);
        assertEquals(2, page.lowerBoundedWildcardList.size());
        assertEquals("text", assertInstanceOf(WebElement.class, page.lowerBoundedWildcardList.get(0)).getText());

        assertTrue(recorder.locators.contains(By.cssSelector(".kotlin-web-elements").toString()));
        assertTrue(recorder.locators.contains(By.cssSelector(".kotlin-appium-elements").toString()));
        assertTrue(recorder.locators.contains(By.cssSelector(".lower-bounded-wildcard").toString()));
    }

    @Test
    void decoratesWildcardWidgetListsAndHonorsWidgetAnnotations() throws Exception {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertNotNull(page.wildcardWidgets);
        assertEquals(2, page.wildcardWidgets.size());
        assertEquals("text", page.wildcardWidgets.get(0).getWrappedElement().getText());

        assertNotNull(page.lowerBoundedWidgets);
        assertEquals(2, page.lowerBoundedWidgets.size());
        assertInstanceOf(SampleWidget.class, page.lowerBoundedWidgets.get(0));

        assertNotNull(page.unannotatedWildcardWidgets);
        assertEquals(2, page.unannotatedWildcardWidgets.size());
        assertTrue(recorder.locators.contains(By.cssSelector(".widget-root").toString()));

        assertNotNull(page.chainedClassAnnotatedWidgets);
        assertEquals(2, page.chainedClassAnnotatedWidgets.size());
        assertTrue(recorder.locators.contains(
                new ByChained(By.cssSelector(".outer"), By.cssSelector(".inner")).toString()
        ));

        assertNotNull(page.allPossibleClassAnnotatedWidgets);
        assertEquals(2, page.allPossibleClassAnnotatedWidgets.size());
        assertTrue(recorder.locators.contains(
                new ByAll(By.cssSelector(".first"), By.cssSelector(".second")).toString()
        ));

        assertNotNull(page.wildcardGenericWidgets);
        assertEquals(2, page.wildcardGenericWidgets.size());

        assertNotNull(page.overriddenWildcardWidgets);
        assertInstanceOf(CustomSampleWidget.class, page.overriddenWildcardWidgets.get(0).getSelfReference());

        assertEquals(
                Duration.ofMillis(50),
                extractWaitingDuration(page.timeoutWildcardWidgets)
        );

        MutableCapabilities androidCaps = new MutableCapabilities(Map.of(
                CapabilityType.PLATFORM_NAME, "Android",
                AUTOMATION_NAME_OPTION, "UiAutomator2"
        ));
        RecordingDriver androidRecorder = new RecordingDriver(androidCaps);
        Page androidPage = initialized(androidRecorder);
        assertEquals(2, androidPage.androidAllPossibleWidgets.size());
        assertInstanceOf(ContentMappedBy.class, androidRecorder.bys.get(0));
        assertEquals(
                new ByAll(AppiumBy.id("android-w1"), AppiumBy.id("android-w2")).toString(),
                androidRecorder.locators.get(0)
        );
    }

    @Test
    void doesNotDecorateUnboundedOrNonElementWildcardLists() {
        RecordingDriver recorder = new RecordingDriver();
        Page page = initialized(recorder);

        assertNull(page.unboundedWildcardList);
        assertNull(page.objectSuperWildcardList);
        assertNull(page.stringWildcardList);
    }

    @Test
    void resolvesConcreteTypeArgumentsOnParameterizedGenericPageSubclasses() throws Exception {
        MutableCapabilities androidCaps = new MutableCapabilities(Map.of(
                CapabilityType.PLATFORM_NAME, "Android",
                AUTOMATION_NAME_OPTION, "UiAutomator2"
        ));
        RecordingDriver recorder = new RecordingDriver(androidCaps);
        ConcreteGenericPage page = new ConcreteGenericPage();
        PageFactory.initElements(new AppiumFieldDecorator(recorder.driver, Duration.ofSeconds(5)), page);

        assertNotNull(page.typeVarElements);
        for (AppiumWebElement element : page.typeVarElements) {
            assertEquals("text", element.getText());
        }

        assertNotNull(page.wildcardTypeVarElements);
        assertEquals(2, page.wildcardTypeVarElements.size());

        assertNotNull(page.typeVarWidgets);
        for (FancyWidget widget : page.typeVarWidgets) {
            assertInstanceOf(FancyWidget.class, widget);
            assertEquals("text", widget.getWrappedElement().getText());
        }

        assertNotNull(page.wildcardTypeVarWidgets);
        for (FancyWidget widget : page.wildcardTypeVarWidgets) {
            assertInstanceOf(FancyWidget.class, widget);
        }
        assertNotNull(page.singleTypeVarWidget);
        assertInstanceOf(FancyWidget.class, page.singleTypeVarWidget);
        assertEquals(Duration.ofMillis(75), extractWaitingDuration(page.singleTypeVarWidget));
        assertEquals(Duration.ofMillis(75), extractWaitingDuration(page.typeVarWidgets));
        assertEquals(Duration.ofMillis(75), extractWaitingDuration(page.wildcardTypeVarWidgets));

        recorder.bys.clear();
        recorder.locators.clear();
        assertEquals("text", page.singleTypeVarWidget.getWrappedElement().getText());
        assertEquals(
                new ByAll(AppiumBy.id("fancy-a1"), AppiumBy.id("fancy-a2")).toString(),
                recorder.locators.get(0)
        );

        recorder.bys.clear();
        recorder.locators.clear();
        assertEquals(2, page.unannotatedTypeVarWidgets.size());
        assertInstanceOf(FancyWidget.class, page.unannotatedTypeVarWidgets.get(0));
        assertInstanceOf(ContentMappedBy.class, recorder.bys.get(0));
        assertEquals(
                new ByAll(AppiumBy.id("fancy-a1"), AppiumBy.id("fancy-a2")).toString(),
                recorder.locators.get(0)
        );

        RecordingDriver htmlRecorder = new RecordingDriver();
        ConcreteGenericPage htmlPage = new ConcreteGenericPage();
        PageFactory.initElements(new AppiumFieldDecorator(htmlRecorder.driver, Duration.ofSeconds(5)), htmlPage);
        assertEquals("text", htmlPage.singleTypeVarWidget.getWrappedElement().getText());
        assertEquals(2, htmlPage.unannotatedTypeVarWidgets.size());
        assertEquals(
                List.of(
                        By.cssSelector(".fancy-widget-root").toString(),
                        By.cssSelector(".fancy-widget-root").toString()
                ),
                htmlRecorder.locators
        );
        assertInstanceOf(CustomFancyWidget.class, htmlPage.overriddenSingleTypeVarWidget.getSelfReference());
        assertInstanceOf(CustomFancyWidget.class, htmlPage.overriddenTypeVarWidgets.get(0).getSelfReference());

        SubtypeElementGenericPage subtypePage = new SubtypeElementGenericPage();
        PageFactory.initElements(new AppiumFieldDecorator(htmlRecorder.driver, Duration.ofMillis(100)), subtypePage);
        assertNotNull(subtypePage.typeVarElements);
        assertEquals(2, subtypePage.typeVarElements.size());
        assertNotNull(subtypePage.wildcardTypeVarElements);
        assertEquals(2, subtypePage.wildcardTypeVarElements.size());

        GenericPage<AppiumWebElement, FancyWidget> rawGenericPage = new GenericPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(htmlRecorder.driver, Duration.ofMillis(100)), rawGenericPage);
        assertNotNull(rawGenericPage.singleTypeVarWidget);
        assertInstanceOf(SampleWidget.class, rawGenericPage.singleTypeVarWidget);
        assertNotNull(rawGenericPage.typeVarWidgets);
        assertInstanceOf(SampleWidget.class, ((List<?>) rawGenericPage.typeVarWidgets).get(0));
    }
}
