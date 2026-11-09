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

import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.SearchingWidgetRoot;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.WidgetInterceptor;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.pagefactory.locator.FurtherRoots;
import io.appium.java_client.pagefactory.utils.ProxyFactory;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.pagefactory.ByAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Widgets looked up with {@link LocatorGroupStrategy#ALL_POSSIBLE} must try every widget root
 * together with every nested locator. See https://github.com/appium/java-client/issues/1816.
 */
public class WidgetMultipleLocatorLookupTest {

    private static final String WIDGET_1 = "widget1";
    private static final String WIDGET_2 = "widget2";
    private static final String ELEMENT_1 = "element1";
    private static final String ELEMENT_2 = "element2";
    private static final String OTHER = "other";
    private static final String FOUND_TEXT = "from-second-widget";

    private static final List<String> EVERY_COMBINATION = List.of(
            lookup(WIDGET_1, ELEMENT_1),
            lookup(WIDGET_1, ELEMENT_2),
            lookup(WIDGET_2, ELEMENT_1),
            lookup(WIDGET_2, ELEMENT_2)
    );

    /**
     * The nested element exists only under the second widget locator. The first locator still
     * matches a root, so both nested locators are searched there and miss.
     */
    @Test
    public void nestedElementIsFoundUnderALaterWidgetLocator() {
        assertFoundAcrossWidgets(new SamplePage(), new SelectiveDriver(WIDGET_1, WIDGET_2));
    }

    /**
     * {@code @AndroidFindAll} is the other way to declare the same set of alternative locators.
     */
    @Test
    public void nestedElementIsFoundUnderALaterAndroidFindAllWidget() {
        assertFoundAcrossWidgets(new FindAllPage(), new SelectiveDriver(WIDGET_1, WIDGET_2));
    }

    /**
     * The first widget locator matches nothing. {@code ALL_POSSIBLE} should move on to the second
     * root and find the nested element there.
     */
    @Test
    public void nestedElementIsFoundWhenTheFirstWidgetLocatorMisses() {
        SelectiveDriver driver = new SelectiveDriver(null, WIDGET_2);
        SamplePage page = new SamplePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readNestedElement());
        assertEquals(List.of(
                lookup(WIDGET_2, ELEMENT_1),
                lookup(WIDGET_2, ELEMENT_2)
        ), driver.lookups);
    }

    /**
     * A nested hit inside the first root must not search the later widget locators.
     */
    @Test
    public void nestedLookupStopsAtTheFirstWidgetRootThatContainsTheElement() {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_2, WIDGET_1);
        SamplePage page = new SamplePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readNestedElement());
        assertEquals(List.of(
                lookup(WIDGET_1, ELEMENT_1),
                lookup(WIDGET_1, ELEMENT_2)
        ), driver.lookups);
    }

    /**
     * Chained widget locators are one path, not alternative roots.
     */
    @Test
    public void chainedWidgetLocatorsDoNotTryLaterRoots() {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        ChainedPage page = new ChainedPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertThrows(NoSuchElementException.class, page::readNestedElement);
        assertEquals(List.of(lookup(WIDGET_1, WIDGET_2)), driver.lookups);
    }

    /**
     * {@link AppiumFieldDecorator}'s default strategy is another way to select {@code ALL_POSSIBLE}.
     */
    @Test
    public void nestedElementIsFoundWhenAllPossibleIsTheDecoratorDefault() {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        DefaultStrategyPage page = new DefaultStrategyPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO, ALL_POSSIBLE), page);

        try {
            assertEquals(FOUND_TEXT, page.readNestedElement());
        } catch (NoSuchElementException e) {
            fail("Nested element was not found across every widget locator. Lookups: "
                    + driver.lookups + ". " + e.getMessage());
        }
        assertEquals(EVERY_COMBINATION, driver.lookups);
    }

    /**
     * Reading the nested element tries every root inside that one call.
     * The widget method is not started again when the first root misses.
     */
    @Test
    public void widgetMethodIsNotReplayedWhenALaterRootHasTheElement() {
        OnceWidget.VISITS.set(0);
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        OncePage page = new OncePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readNestedElement());
        assertEquals(1, OnceWidget.VISITS.get());
        assertEquals(EVERY_COMBINATION, driver.lookups);
    }

    /**
     * A {@code NoSuchElementException} thrown by the method is not a missed root.
     */
    @Test
    public void deliberateMissIsNotRetriedOnAnotherRoot() {
        OnceWidget.VISITS.set(0);
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        OncePage page = new OncePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, page::blowUp);
        assertTrue(thrown.getMessage().startsWith("deliberate"));
        assertEquals(0, thrown.getSuppressed().length);
        assertEquals(1, OnceWidget.VISITS.get());
        assertEquals(List.of(), driver.lookups);
    }

    /**
     * The wrapped root stays the first match. A later call can still find an element
     * that exists only under that first root.
     */
    @Test
    public void cachedWidgetStaysOnTheFirstRoot() {
        SelectiveDriver driver = new SelectiveDriver(null, null);
        driver.defineRoot(WIDGET_1, OTHER);
        driver.defineRoot(WIDGET_2, ELEMENT_2);
        TwoFieldPage page = new TwoFieldPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readElement());
        assertEquals(WIDGET_1, page.wrappedRootTag());
        assertEquals(OTHER, page.readOther());
        assertEquals(WIDGET_1, page.wrappedRootTag());
    }

    /**
     * A root that appears after the original match is not skipped just because it is now first.
     */
    @Test
    public void laterLookupComparesTheResolvedRootInsteadOfSkippingTheFirstMatch() {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_2, null);
        RevealingPage page = new RevealingPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readNestedElement());
        assertEquals(List.of(
                lookup(WIDGET_2, ELEMENT_1),
                lookup(WIDGET_2, ELEMENT_2),
                lookup(WIDGET_1, ELEMENT_1),
                lookup(WIDGET_1, ELEMENT_2)
        ), driver.lookups);
    }

    /**
     * An application exception still leaves the widget cached when lookup caching is on.
     */
    @Test
    public void cachedWidgetIsKeptWhenTheMethodThrows() {
        ExplodingWidget.BUILDS.set(0);
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        ExplodingPage page = new ExplodingPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertThrows(IllegalStateException.class, page::explode);
        assertThrows(IllegalStateException.class, page::explode);
        assertEquals(1, ExplodingWidget.BUILDS.get());
    }

    @Test
    public void allRootMissesAreSuppressedOnTheFirstMiss() {
        FixedElement first = new FixedElement(new AbstractStubWebDriver.StubAndroidDriver(), "r1", null);
        FixedElement second = new FixedElement(first.getWrappedDriver(), "r2", null);
        HandBuiltWidget widget = handBuilt(new PairLocator(first, second, true));

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, widget::read);
        assertTrue(thrown.getMessage().contains("r1"));
        assertEquals(1, thrown.getSuppressed().length);
        assertTrue(thrown.getSuppressed()[0].getMessage().contains("r2"));
        assertEquals(1, HandBuiltWidget.VISITS.get());
    }

    @Test
    public void customLocatorSuppliesTheNextRootWithoutReplayingTheMethod() {
        AbstractStubWebDriver.StubAndroidDriver driver = new AbstractStubWebDriver.StubAndroidDriver();
        FixedElement first = new FixedElement(driver, "r1", null);
        TextElement child = new TextElement(driver, FOUND_TEXT);
        FixedElement second = new FixedElement(driver, "r2", child);
        PairLocator locator = new PairLocator(first, second, false);
        HandBuiltWidget.VISITS.set(0);
        HandBuiltWidget widget = handBuilt(locator);

        assertEquals(FOUND_TEXT, widget.read());
        assertEquals(1, HandBuiltWidget.VISITS.get());
        assertSame(first, locator.seenResolved);
    }

    @Test
    public void singleLocatorWidgetKeepsTheRealElement() {
        AbstractStubWebDriver.StubAndroidDriver driver = new AbstractStubWebDriver.StubAndroidDriver();
        RichElement real = new RichElement(driver, "dom-text");
        PlainWidget widget = plain(new PairLocator(real, null, false));

        WebElement wrapped = widget.root();
        assertSame(real, wrapped);
        assertTrue(wrapped instanceof Locatable);
        assertFalse(wrapped instanceof SearchingWidgetRoot);
        assertEquals("dom-text", widget.dom());
        assertTrue(wrapped.equals(real));
    }

    @Test
    public void searchingRootStillDelegatesDomAttribute() {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        SamplePage page = new SamplePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(WIDGET_1, page.rootDom());
        WebElement wrapped = page.widgetRoot();
        assertTrue(wrapped instanceof SearchingWidgetRoot);
        assertFalse(wrapped instanceof RootElement);
        WebElement real = ((WrapsElement) wrapped).getWrappedElement();
        assertTrue(real instanceof RootElement);
        assertEquals(WIDGET_1, real.getTagName());
    }

    @Test
    public void searchingWidgetRootHasNoPublicNestedType() {
        for (Class<?> nested : SearchingWidgetRoot.class.getDeclaredClasses()) {
            assertFalse(Modifier.isPublic(nested.getModifiers()));
        }
        assertEquals(0, SearchingWidgetRoot.class.getDeclaredClasses().length);
    }

    @Test
    public void byAllPrefersTheFirstRootBeforeLaterLocatorsOnTheNextRoot() {
        SelectiveDriver driver = new SelectiveDriver(null, null);
        driver.defineRoot(WIDGET_1, ELEMENT_2);
        driver.defineRoot(WIDGET_2, ELEMENT_1);
        SamplePage page = new SamplePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(FOUND_TEXT, page.readNestedElement());
        assertEquals(List.of(
                lookup(WIDGET_1, ELEMENT_1),
                lookup(WIDGET_1, ELEMENT_2)
        ), driver.lookups);
    }

    @Test
    public void directByAllDoesNotPreferALaterRoot() {
        AbstractStubWebDriver.StubAndroidDriver driver = new AbstractStubWebDriver.StubAndroidDriver();
        List<String> lookups = new ArrayList<>();
        RootElement root1 = new RootElement(driver, "r1", Set.of(ELEMENT_2), lookups);
        RootElement root2 = new RootElement(driver, "r2", Set.of(ELEMENT_1), lookups);
        WebElement proxy = SearchingWidgetRoot.wrap(root1, () -> List.<WebElement>of(root2).iterator());

        WebElement found = proxy.findElement(new ByAll(By.id(ELEMENT_1), By.id(ELEMENT_2)));

        assertEquals(FOUND_TEXT, found.getText());
        assertEquals(List.of(
                lookup("r1", ELEMENT_1),
                lookup("r1", ELEMENT_2)
        ), lookups);
    }

    @Test
    public void alternateRootQueryLeavesTheImplicitWaitAlone() throws InterruptedException {
        SelectiveDriver driver = new SelectiveDriver(WIDGET_1, WIDGET_2);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        SamplePage success = new SamplePage();
        SamplePage shared = new SamplePage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), success);
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), shared);

        assertEquals(FOUND_TEXT, success.readNestedElement());
        assertEquals(0, driver.implicitWaitClears());
        assertEquals(Duration.ofSeconds(10), driver.manage().timeouts().getImplicitWaitTimeout());
        assertTrue(driver.rootQueryWaits.stream().allMatch(Duration.ofSeconds(10)::equals));

        SelectiveDriver missing = new SelectiveDriver(WIDGET_1, null);
        missing.defineRoot(WIDGET_2);
        missing.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        SamplePage failure = new SamplePage();
        initElements(new AppiumFieldDecorator(missing, Duration.ZERO), failure);
        assertThrows(NoSuchElementException.class, failure::readNestedElement);
        assertEquals(0, missing.implicitWaitClears());
        assertEquals(Duration.ofSeconds(10), missing.manage().timeouts().getImplicitWaitTimeout());

        Thread other = new Thread(shared::readNestedElement);
        other.start();
        other.join();
        assertEquals(0, driver.implicitWaitClears());
        assertEquals(Duration.ofSeconds(10), driver.manage().timeouts().getImplicitWaitTimeout());
    }

    @Test
    public void alternateLocatorsAreResolvedOncePerBind() {
        ContextDriver multi = new ContextDriver(WIDGET_1, WIDGET_2);
        SamplePage multiPage = new SamplePage();
        initElements(new AppiumFieldDecorator(multi, Duration.ZERO), multiPage);
        assertEquals(FOUND_TEXT, multiPage.readNestedElement());

        ContextDriver single = new ContextDriver(null, null);
        single.defineRoot(WIDGET_1, ELEMENT_2);
        SinglePage singlePage = new SinglePage();
        initElements(new AppiumFieldDecorator(single, Duration.ZERO), singlePage);
        assertEquals(FOUND_TEXT, singlePage.readNestedElement());

        assertEquals(single.contextReads, multi.contextReads);
        // Widget lookup, the found root's content type, and the nested lookup.
        // Resolving alternate roots must not add another getContext call.
        assertEquals(3, single.contextReads);
    }

    @Test
    public void contentTypeSelectionDoesNotLeakAcrossThreads() throws InterruptedException {
        ContentMappedBy mapped = new ContentMappedBy(Map.of(
                HTML_OR_DEFAULT, By.id("html"),
                NATIVE_MOBILE_SPECIFIC, By.id("native")
        ));
        mapped.useContent(HTML_OR_DEFAULT);
        assertTrue(mapped.locatorFor(NATIVE_MOBILE_SPECIFIC).toString().contains("native"));
        assertTrue(mapped.getCurrentLocator().toString().contains("html"));

        AtomicBoolean failed = new AtomicBoolean();
        Thread html = contentReader(mapped, HTML_OR_DEFAULT, "html", failed);
        Thread nativeThread = contentReader(mapped, NATIVE_MOBILE_SPECIFIC, "native", failed);
        html.start();
        nativeThread.start();
        html.join();
        nativeThread.join();
        assertFalse(failed.get());
    }

    private static Thread contentReader(ContentMappedBy mapped, ContentType type, String expected,
                                        AtomicBoolean failed) {
        return new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                mapped.useContent(type);
                By selected = mapped.getCurrentLocator();
                if (selected == null || !selected.toString().contains(expected)) {
                    failed.set(true);
                }
            }
        });
    }

    private static PlainWidget plain(CacheableLocator locator) {
        try {
            Constructor<PlainWidget> constructor = PlainWidget.class.getDeclaredConstructor(WebElement.class);
            constructor.setAccessible(true);
            Map<ContentType, Constructor<? extends Widget>> map = new HashMap<>();
            map.put(ContentType.NATIVE_MOBILE_SPECIFIC, constructor);
            map.put(ContentType.HTML_OR_DEFAULT, constructor);
            WebElement primary = locator.findElement();
            WidgetInterceptor interceptor = new WidgetInterceptor(
                    locator,
                    new WeakReference<>(((WrapsDriver) primary).getWrappedDriver()),
                    null,
                    map,
                    Duration.ZERO
            );
            return ProxyFactory.getEnhancedProxy(
                    PlainWidget.class,
                    new Class<?>[] {WebElement.class},
                    new Object[] {primary},
                    interceptor
            );
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private static HandBuiltWidget handBuilt(CacheableLocator locator) {
        HandBuiltWidget.VISITS.set(0);
        try {
            Constructor<HandBuiltWidget> constructor =
                    HandBuiltWidget.class.getDeclaredConstructor(WebElement.class);
            constructor.setAccessible(true);
            Map<ContentType, Constructor<? extends Widget>> map = new HashMap<>();
            map.put(ContentType.NATIVE_MOBILE_SPECIFIC, constructor);
            map.put(ContentType.HTML_OR_DEFAULT, constructor);
            WebElement primary = locator.findElement();
            WidgetInterceptor interceptor = new WidgetInterceptor(
                    locator,
                    new WeakReference<>(((WrapsDriver) primary).getWrappedDriver()),
                    null,
                    map,
                    Duration.ZERO
            );
            return ProxyFactory.getEnhancedProxy(
                    HandBuiltWidget.class,
                    new Class<?>[] {WebElement.class},
                    new Object[] {primary},
                    interceptor
            );
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void assertFoundAcrossWidgets(Readable page, SelectiveDriver driver) {
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);
        try {
            assertEquals(FOUND_TEXT, page.readNestedElement());
        } catch (NoSuchElementException e) {
            fail("Nested element was not found across every widget locator. Lookups: "
                    + driver.lookups + ". " + e.getMessage());
        }
        assertEquals(EVERY_COMBINATION, driver.lookups);
    }

    private static String lookup(String parent, String child) {
        return parent + " -> " + child;
    }

    private static String idOf(By by) {
        String value = by.toString();
        int separator = value.lastIndexOf(": ");
        return separator < 0 ? value : value.substring(separator + 2);
    }

    public static class SampleWidget extends Widget {

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = ELEMENT_1)
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        protected SampleWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the nested element. The lookup happens while this widget method is running.
         *
         * @return the nested element text
         */
        public String read() {
            return element.getText();
        }

        /**
         * Reads a DOM attribute from the widget root.
         *
         * @return the attribute value
         */
        public String rootDom() {
            return getWrappedElement().getDomAttribute("text");
        }
    }

    public static class SingleWidget extends Widget {
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        protected SingleWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the only nested element.
         *
         * @return the nested element text
         */
        public String read() {
            return element.getText();
        }
    }

    public static class SinglePage implements Readable {
        @AndroidFindBy(id = WIDGET_1)
        private SingleWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }
    }

    public static class SamplePage implements Readable {

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private SampleWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }

        public String rootDom() {
            return widget.rootDom();
        }

        public WebElement widgetRoot() {
            return widget.getWrappedElement();
        }
    }

    public static class PlainWidget extends Widget {
        protected PlainWidget(WebElement element) {
            super(element);
        }

        public WebElement root() {
            return getWrappedElement();
        }

        public String dom() {
            return getWrappedElement().getDomAttribute("text");
        }
    }

    private static final class RichElement extends StubWebElement implements Locatable {
        private final String domText;

        private RichElement(WebDriver driver, String domText) {
            super(driver, By.id("rich"));
            this.domText = domText;
        }

        @Override
        public String getDomAttribute(String name) {
            return "text".equals(name) ? domText : null;
        }

        @Override
        public Coordinates getCoordinates() {
            return null;
        }
    }

    public static class FindAllWidget extends Widget {

        @AndroidFindAll({
            @AndroidBy(id = ELEMENT_1),
            @AndroidBy(id = ELEMENT_2)
        })
        private WebElement element;

        protected FindAllWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the nested element. The lookup happens while this widget method is running.
         *
         * @return the nested element text
         */
        public String read() {
            return element.getText();
        }
    }

    public static class FindAllPage implements Readable {

        @AndroidFindAll({
            @AndroidBy(id = WIDGET_1),
            @AndroidBy(id = WIDGET_2)
        })
        private FindAllWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }
    }

    public static class ChainedPage implements Readable {

        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private SampleWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }
    }

    public static class DefaultStrategyWidget extends Widget {

        @AndroidFindBy(id = ELEMENT_1)
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        protected DefaultStrategyWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the nested element. The lookup happens while this widget method is running.
         *
         * @return the nested element text
         */
        public String read() {
            return element.getText();
        }
    }

    public static class OnceWidget extends Widget {
        static final AtomicInteger VISITS = new AtomicInteger();

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = ELEMENT_1)
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        protected OnceWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the nested element and counts this invocation.
         *
         * @return the nested element text
         */
        public String read() {
            VISITS.incrementAndGet();
            return element.getText();
        }

        /**
         * Throws without looking up a nested element.
         *
         * @return nothing
         */
        public String blow() {
            VISITS.incrementAndGet();
            throw new NoSuchElementException("deliberate");
        }
    }

    public static class OncePage implements Readable {
        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private OnceWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }

        public void blowUp() {
            widget.blow();
        }
    }

    public static class TwoFieldWidget extends Widget {
        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = ELEMENT_1)
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        @AndroidFindBy(id = OTHER)
        private WebElement other;

        protected TwoFieldWidget(WebElement element) {
            super(element);
        }

        /**
         * Reads the element that exists only under the second root.
         *
         * @return the nested element text
         */
        public String readElement() {
            return element.getText();
        }

        /**
         * Reads the element that exists only under the first root.
         *
         * @return the nested element text
         */
        public String readOther() {
            return other.getText();
        }
    }

    public static class TwoFieldPage {
        @CacheLookup
        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private TwoFieldWidget widget;

        public String readElement() {
            return widget.readElement();
        }

        public String readOther() {
            return widget.readOther();
        }

        public String wrappedRootTag() {
            return widget.getWrappedElement().getTagName();
        }
    }

    public static class RevealingWidget extends Widget {
        private final SelectiveDriver driver;

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = ELEMENT_1)
        @AndroidFindBy(id = ELEMENT_2)
        private WebElement element;

        protected RevealingWidget(WebElement element) {
            super(element);
            this.driver = (SelectiveDriver) getWrappedDriver();
        }

        /**
         * Makes the first locator appear, then reads the nested element.
         *
         * @return the nested element text
         */
        public String read() {
            driver.defineRoot(WIDGET_1, ELEMENT_2);
            return element.getText();
        }
    }

    public static class RevealingPage implements Readable {
        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private RevealingWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }
    }

    public static class ExplodingWidget extends Widget {
        static final AtomicInteger BUILDS = new AtomicInteger();

        protected ExplodingWidget(WebElement element) {
            super(element);
            if (element instanceof SearchingWidgetRoot) {
                BUILDS.incrementAndGet();
            }
        }

        public void explode() {
            throw new IllegalStateException("boom");
        }
    }

    public static class ExplodingPage {
        @CacheLookup
        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private ExplodingWidget widget;

        public void explode() {
            widget.explode();
        }
    }

    public static class HandBuiltWidget extends Widget {
        static final AtomicInteger VISITS = new AtomicInteger();

        protected HandBuiltWidget(WebElement element) {
            super(element);
        }

        /**
         * Looks up a child of the current root.
         *
         * @return the child text
         */
        public String read() {
            VISITS.incrementAndGet();
            return getWrappedElement().findElement(By.id("child")).getText();
        }
    }

    private static final class PairLocator implements CacheableLocator {
        private final WebElement first;
        private final WebElement second;
        private final boolean cache;
        private WebElement seenResolved;

        private PairLocator(WebElement first, WebElement second, boolean cache) {
            this.first = first;
            this.second = second;
            this.cache = cache;
        }

        @Override
        public WebElement findElement() {
            return first;
        }

        @Override
        public List<WebElement> findElements() {
            return List.of(first);
        }

        @Override
        public boolean isLookUpCached() {
            return cache;
        }

        @Override
        public FurtherRoots furtherRoots(WebElement resolvedRoot, ContentType contentType) {
            seenResolved = resolvedRoot;
            if (second == null) {
                return FurtherRoots.none();
            }
            return new FurtherRoots() {
                @Override
                public boolean isPresent() {
                    return true;
                }

                @Override
                public Iterator<WebElement> iterator() {
                    return List.of(second).iterator();
                }
            };
        }
    }

    public static class DefaultStrategyPage implements Readable {

        @AndroidFindBy(id = WIDGET_1)
        @AndroidFindBy(id = WIDGET_2)
        private DefaultStrategyWidget widget;

        @Override
        public String readNestedElement() {
            return widget.read();
        }
    }

    public interface Readable {
        String readNestedElement();
    }

    /**
     * Driver whose roots are present or absent by id. A root listed with the target
     * contains {@code element2}. {@code element1} never matches unless configured.
     */
    private static final class ContextDriver extends SelectiveDriver implements SupportsContextSwitching {
        private int contextReads;

        private ContextDriver(String presentWithoutTarget, String presentWithTarget) {
            super(presentWithoutTarget, presentWithTarget);
        }

        @Override
        public boolean isBrowser() {
            return true;
        }

        @Override
        public String getContext() {
            contextReads++;
            return "NATIVE_APP";
        }
    }

    private static class SelectiveDriver extends AbstractStubWebDriver.StubAndroidDriver {
        private final Map<String, Set<String>> roots = new HashMap<>();
        private final Map<String, RootElement> elements = new HashMap<>();
        private final List<String> lookups = new ArrayList<>();
        private final List<Duration> rootQueryWaits = new ArrayList<>();

        private SelectiveDriver(String presentWithoutTarget, String presentWithTarget) {
            if (presentWithoutTarget != null) {
                roots.put(presentWithoutTarget, Set.of());
            }
            if (presentWithTarget != null) {
                roots.put(presentWithTarget, Set.of(ELEMENT_2));
            }
        }

        void defineRoot(String id, String... children) {
            roots.put(id, Set.of(children));
            elements.remove(id);
        }

        @Override
        public List<WebElement> findElements(By by) {
            rootQueryWaits.add(manage().timeouts().getImplicitWaitTimeout());
            return rootsFor(idOf(by));
        }

        @Override
        public StubWebElement findElement(By by) {
            if (!(by instanceof By.Remotable)) {
                return (StubWebElement) by.findElement(this);
            }
            List<WebElement> found = findElements(by);
            if (found.isEmpty()) {
                throw new NoSuchElementException("Cannot locate " + by);
            }
            return (StubWebElement) found.get(0);
        }

        private List<WebElement> rootsFor(String id) {
            Set<String> children = roots.get(id);
            if (children == null) {
                return List.of();
            }
            return List.of(elements.computeIfAbsent(
                    id, key -> new RootElement(this, key, children, lookups)));
        }
    }

    private static class RootElement extends StubWebElement {
        private final String id;
        private final Set<String> children;
        private final List<String> lookups;

        private RootElement(WebDriver driver, String id, Set<String> children, List<String> lookups) {
            super(driver, By.id(id));
            this.id = id;
            this.children = children;
            this.lookups = lookups;
        }

        @Override
        public List<WebElement> findElements(By by) {
            String child = idOf(by);
            lookups.add(lookup(id, child));
            if (!children.contains(child)) {
                return List.of();
            }
            String text = ELEMENT_2.equals(child) ? FOUND_TEXT : child;
            return List.of(new TextElement(getWrappedDriver(), text));
        }

        @Override
        public StubWebElement findElement(By by) {
            if (!(by instanceof By.Remotable)) {
                return (StubWebElement) by.findElement(this);
            }
            List<WebElement> found = findElements(by);
            if (found.isEmpty()) {
                throw new NoSuchElementException("Cannot locate " + by + " under " + id);
            }
            return (StubWebElement) found.get(0);
        }

        @Override
        public String getTagName() {
            return id;
        }

        @Override
        public String getDomAttribute(String name) {
            return "text".equals(name) ? id : null;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof RootElement && id.equals(((RootElement) other).id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    private static final class FixedElement extends StubWebElement {
        private final String id;
        private final WebElement child;

        private FixedElement(WebDriver driver, String id, WebElement child) {
            super(driver, By.id(id));
            this.id = id;
            this.child = child;
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (child != null && "child".equals(idOf(by))) {
                return List.of(child);
            }
            return List.of();
        }

        @Override
        public StubWebElement findElement(By by) {
            if (!(by instanceof By.Remotable)) {
                return (StubWebElement) by.findElement(this);
            }
            List<WebElement> found = findElements(by);
            if (found.isEmpty()) {
                throw new NoSuchElementException("miss under " + id);
            }
            return (StubWebElement) found.get(0);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof FixedElement && id.equals(((FixedElement) other).id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    private static final class TextElement extends StubWebElement {
        private final String text;

        private TextElement(WebDriver driver, String text) {
            super(driver, By.id("text"));
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }
}
