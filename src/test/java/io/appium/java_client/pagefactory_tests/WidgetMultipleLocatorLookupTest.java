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
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
     * Driver whose roots are present or absent by id. The root named by
     * {@code presentWithTarget} contains {@code element2}. {@code element1} never matches.
     */
    private static final class SelectiveDriver extends AbstractStubWebDriver.StubAndroidDriver {
        private final String presentWithoutTarget;
        private final String presentWithTarget;
        private final List<String> lookups = new ArrayList<>();

        private SelectiveDriver(String presentWithoutTarget, String presentWithTarget) {
            this.presentWithoutTarget = presentWithoutTarget;
            this.presentWithTarget = presentWithTarget;
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
            if (id.equals(presentWithoutTarget)) {
                return List.of(new RootElement(this, id, false, lookups));
            }
            if (id.equals(presentWithTarget)) {
                return List.of(new RootElement(this, id, true, lookups));
            }
            return List.of();
        }
    }

    private static class RootElement extends StubWebElement {
        private final String id;
        private final boolean containsTarget;
        private final List<String> lookups;

        private RootElement(WebDriver driver, String id, boolean containsTarget, List<String> lookups) {
            super(driver, By.id(id));
            this.id = id;
            this.containsTarget = containsTarget;
            this.lookups = lookups;
        }

        @Override
        public List<WebElement> findElements(By by) {
            String child = idOf(by);
            lookups.add(lookup(id, child));
            if (containsTarget && ELEMENT_2.equals(child)) {
                return List.of(new TextElement(getWrappedDriver(), FOUND_TEXT));
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
                throw new NoSuchElementException("Cannot locate " + by + " under " + id);
            }
            return (StubWebElement) found.get(0);
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
