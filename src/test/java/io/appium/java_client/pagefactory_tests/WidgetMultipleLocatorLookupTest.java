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
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.WidgetInterceptor;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import io.appium.java_client.pagefactory.utils.ProxyFactory;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import io.appium.java_client.support.CacheLookup;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
            if ("SearchingWidgetRoot".equals(element.getClass().getSimpleName())) {
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
        public Iterator<WebElement> furtherRoots(WebElement resolvedRoot) {
            seenResolved = resolvedRoot;
            if (second == null) {
                return Collections.emptyIterator();
            }
            return List.of(second).iterator();
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
    private static final class SelectiveDriver extends AbstractStubWebDriver.StubAndroidDriver {
        private final Map<String, Set<String>> roots = new HashMap<>();
        private final Map<String, RootElement> elements = new HashMap<>();
        private final List<String> lookups = new ArrayList<>();

        private SelectiveDriver(String presentWithoutTarget, String presentWithTarget) {
            if (presentWithoutTarget != null) {
                roots.put(presentWithoutTarget, Set.of());
            }
            if (presentWithTarget != null) {
                roots.put(presentWithTarget, Set.of(ELEMENT_2));
            }
        }

        private void defineRoot(String id, String... children) {
            roots.put(id, Set.of(children));
            elements.remove(id);
        }

        @Override
        public List<WebElement> findElements(By by) {
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
