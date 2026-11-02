/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
import io.appium.java_client.pagefactory.DefaultElementByBuilder;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import io.appium.java_client.support.pagefactory.ByAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.pagefactory.LocatorGroupStrategy.CHAIN;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultLocatorGroupStrategyTest {

    private interface ByRecorder {
        List<By> recordedBys();
    }

    private static class RecordingStubElement extends StubWebElement {
        private final List<By> recordedBys;

        RecordingStubElement(WebDriver driver, By by, List<By> recordedBys) {
            super(driver, by);
            this.recordedBys = recordedBys;
        }

        @Override
        public RecordingStubElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (RecordingStubElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(getWrappedDriver(), by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(getWrappedDriver(), by, recordedBys),
                    new RecordingStubElement(getWrappedDriver(), by, recordedBys)
            );
        }
    }

    private static class RecordingAndroidDriver extends AbstractStubWebDriver.StubAndroidDriver implements ByRecorder {
        private final List<By> recordedBys = new ArrayList<>();

        @Override
        public List<By> recordedBys() {
            return recordedBys;
        }

        @Override
        public StubWebElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (StubWebElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(this, by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(this, by, recordedBys),
                    new RecordingStubElement(this, by, recordedBys)
            );
        }
    }

    private static class RecordingIOSDriver extends AbstractStubWebDriver.StubIOSXCUITDriver implements ByRecorder {
        private final List<By> recordedBys = new ArrayList<>();

        @Override
        public List<By> recordedBys() {
            return recordedBys;
        }

        @Override
        public StubWebElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (StubWebElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(this, by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(this, by, recordedBys),
                    new RecordingStubElement(this, by, recordedBys)
            );
        }
    }

    private static By lastRecordedBy(ByRecorder recorder, Runnable lookup) {
        recorder.recordedBys().clear();
        lookup.run();
        List<By> bys = recorder.recordedBys();
        return bys.get(bys.size() - 1);
    }

    public static class UnannotatedPage {
        @AndroidFindBy(id = "submit")
        @AndroidFindBy(id = "done")
        @iOSXCUITFindBy(id = "submit")
        @iOSXCUITFindBy(id = "done")
        private WebElement submitButton;

        @HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
        @AndroidFindBy(id = "parent")
        @AndroidFindBy(id = "child")
        @iOSXCUITFindBy(id = "parent")
        @iOSXCUITFindBy(id = "child")
        private WebElement explicitChainElement;

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = "one")
        @AndroidFindBy(id = "two")
        @iOSXCUITFindBy(id = "one")
        @iOSXCUITFindBy(id = "two")
        private WebElement androidOnlyAllPossibleElement;

        @AndroidFindBy(id = "root")
        @iOSXCUITFindBy(id = "root")
        private SubWidget widget;

        @AndroidFindBy(id = "itemRoot")
        @iOSXCUITFindBy(id = "itemRoot")
        private List<SubWidget> widgets;

        @AndroidFindBy(id = "annotatedWidget1")
        @AndroidFindBy(id = "annotatedWidget2")
        @iOSXCUITFindBy(id = "annotatedWidget1")
        @iOSXCUITFindBy(id = "annotatedWidget2")
        private ClassAnnotatedWidget annotatedWidget;
    }

    public static class SubWidget extends Widget {
        @AndroidFindBy(id = "inner1")
        @AndroidFindBy(id = "inner2")
        @iOSXCUITFindBy(id = "inner1")
        @iOSXCUITFindBy(id = "inner2")
        private WebElement innerButton;

        protected SubWidget(WebElement element) {
            super(element);
        }

        public WebElement getInnerButton() {
            return innerButton;
        }
    }

    @HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
    public static class ClassAnnotatedWidget extends Widget {
        @AndroidFindBy(id = "widgetInner1")
        @AndroidFindBy(id = "widgetInner2")
        @iOSXCUITFindBy(id = "widgetInner1")
        @iOSXCUITFindBy(id = "widgetInner2")
        private WebElement innerElement;

        protected ClassAnnotatedWidget(WebElement element) {
            super(element);
        }

        public WebElement getInnerElement() {
            return innerElement;
        }
    }

    @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
    public static class ClassAnnotatedPage {
        @AndroidFindBy(id = "submit")
        @AndroidFindBy(id = "done")
        @iOSXCUITFindBy(id = "submit")
        @iOSXCUITFindBy(id = "done")
        protected WebElement submitButton;

        @HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
        @AndroidFindBy(id = "parent")
        @AndroidFindBy(id = "child")
        @iOSXCUITFindBy(id = "parent")
        @iOSXCUITFindBy(id = "child")
        protected WebElement explicitChainElement;
    }

    public static class InheritedClassAnnotatedPage extends ClassAnnotatedPage {
        @AndroidFindBy(id = "cancel")
        @AndroidFindBy(id = "close")
        @iOSXCUITFindBy(id = "cancel")
        @iOSXCUITFindBy(id = "close")
        private WebElement cancelButton;
    }

    public static class UnannotatedBasePage {
        @AndroidFindBy(id = "base1")
        @AndroidFindBy(id = "base2")
        @iOSXCUITFindBy(id = "base1")
        @iOSXCUITFindBy(id = "base2")
        protected WebElement baseElement;
    }

    @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
    public static class AnnotatedSubclassOfUnannotatedBasePage extends UnannotatedBasePage {
    }

    @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
    public interface AllPossibleLocatorsMarker {
    }

    public static class InterfaceAnnotatedPage implements AllPossibleLocatorsMarker {
        @AndroidFindBy(id = "iface1")
        @AndroidFindBy(id = "iface2")
        @iOSXCUITFindBy(id = "iface1")
        @iOSXCUITFindBy(id = "iface2")
        private WebElement interfaceElement;
    }

    @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
    public static class EnclosingAnnotatedContainer {
        public static class NestedPage {
            @AndroidFindBy(id = "nested1")
            @AndroidFindBy(id = "nested2")
            @iOSXCUITFindBy(id = "nested1")
            @iOSXCUITFindBy(id = "nested2")
            private WebElement nestedElement;
        }
    }

    @Test
    public void defaultsToChainWhenNotConfigured() {
        RecordingAndroidDriver androidDriver = new RecordingAndroidDriver();
        UnannotatedPage androidPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(androidDriver), androidPage);
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(androidDriver, () -> androidPage.submitButton.isDisplayed())
        );

        RecordingIOSDriver iosDriver = new RecordingIOSDriver();
        UnannotatedPage iosPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(iosDriver), iosPage);
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(iosDriver, () -> iosPage.submitButton.isDisplayed())
        );
    }

    @Test
    public void rejectsNullDefaultLocatorGroupStrategy() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();
        assertThrows(
                NullPointerException.class,
                () -> new AppiumFieldDecorator(driver, (LocatorGroupStrategy) null)
        );
        assertThrows(
                NullPointerException.class,
                () -> new AppiumFieldDecorator(driver, Duration.ZERO, null)
        );
        assertThrows(
                NullPointerException.class,
                () -> new DefaultElementByBuilder("Android", "UiAutomator2", null)
        );
    }

    @Test
    public void programmaticDefaultLocatorStrategyAppliesToUnannotatedFieldsWidgetsAndWidgetLists() {
        RecordingAndroidDriver androidDriver = new RecordingAndroidDriver();
        UnannotatedPage androidPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(androidDriver, ALL_POSSIBLE), androidPage);

        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> androidPage.submitButton.isDisplayed())
        );
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(androidDriver, () -> androidPage.explicitChainElement.isDisplayed())
        );
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> androidPage.widget.getInnerButton().isDisplayed())
        );
        assertEquals(2, androidPage.widgets.size());
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> androidPage.widgets.get(0).getInnerButton().isDisplayed())
        );
        // ClassAnnotatedWidget carries @HowToUseLocators(CHAIN), overriding the decorator's ALL_POSSIBLE
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(androidDriver, () -> androidPage.annotatedWidget.getInnerElement().isDisplayed())
        );
        // Single-platform field annotation @HowToUseLocators(androidAutomation = ALL_POSSIBLE):
        // Android uses ALL_POSSIBLE, while iOS uses the annotation's default (CHAIN)
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> androidPage.androidOnlyAllPossibleElement.isDisplayed())
        );

        RecordingIOSDriver iosDriver = new RecordingIOSDriver();
        UnannotatedPage iosPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(iosDriver, Duration.ZERO, ALL_POSSIBLE), iosPage);

        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(iosDriver, () -> iosPage.submitButton.isDisplayed())
        );
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(iosDriver, () -> iosPage.explicitChainElement.isDisplayed())
        );
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(iosDriver, () -> iosPage.widget.getInnerButton().isDisplayed())
        );
        assertEquals(2, iosPage.widgets.size());
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(iosDriver, () -> iosPage.widgets.get(0).getInnerButton().isDisplayed())
        );
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(iosDriver, () -> iosPage.annotatedWidget.getInnerElement().isDisplayed())
        );
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(iosDriver, () -> iosPage.androidOnlyAllPossibleElement.isDisplayed())
        );
    }

    @Test
    public void classLevelHowToUseLocatorsAppliesAcrossHierarchyInterfacesAndEnclosingClasses() {
        RecordingAndroidDriver androidDriver = new RecordingAndroidDriver();
        InheritedClassAnnotatedPage inheritedPage = new InheritedClassAnnotatedPage();
        initElements(new AppiumFieldDecorator(androidDriver), inheritedPage);

        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> inheritedPage.submitButton.isDisplayed())
        );
        assertInstanceOf(
                ByChained.class,
                lastRecordedBy(androidDriver, () -> inheritedPage.explicitChainElement.isDisplayed())
        );
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> inheritedPage.cancelButton.isDisplayed())
        );

        AnnotatedSubclassOfUnannotatedBasePage subclassPage = new AnnotatedSubclassOfUnannotatedBasePage();
        initElements(new AppiumFieldDecorator(androidDriver), subclassPage);
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> subclassPage.baseElement.isDisplayed())
        );

        InterfaceAnnotatedPage interfacePage = new InterfaceAnnotatedPage();
        initElements(new AppiumFieldDecorator(androidDriver), interfacePage);
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> interfacePage.interfaceElement.isDisplayed())
        );

        EnclosingAnnotatedContainer.NestedPage nestedPage = new EnclosingAnnotatedContainer.NestedPage();
        initElements(new AppiumFieldDecorator(androidDriver), nestedPage);
        assertInstanceOf(
                ByAll.class,
                lastRecordedBy(androidDriver, () -> nestedPage.nestedElement.isDisplayed())
        );
    }
}
